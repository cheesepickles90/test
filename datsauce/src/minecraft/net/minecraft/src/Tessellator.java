package net.minecraft.src;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Random;

import me.ownage.xenon.hacks.classes.LSD;
import me.ownage.xenon.hacks.classes.Xray;
import me.ownage.xenon.main.Hacks;

import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class Tessellator
{
    /**
     * Boolean used to check whether quads should be drawn as two triangles. Initialized to false and never changed.
     */
    private static boolean convertQuadsToTriangles = false;

    /**
     * Boolean used to check if we should use vertex buffers. Initialized to false and never changed.
     */
    private static boolean tryVBO = false;

    /** The byte buffer used for GL allocation. */
    private ByteBuffer byteBuffer;

    /** The same memory as byteBuffer, but referenced as an integer buffer. */
    private IntBuffer intBuffer;

    /** The same memory as byteBuffer, but referenced as an float buffer. */
    private FloatBuffer floatBuffer;

    /** Short buffer */
    private ShortBuffer shortBuffer;

    /** Raw integer array. */
    private int[] rawBuffer;

    /**
     * The number of vertices to be drawn in the next draw call. Reset to 0 between draw calls.
     */
    private int vertexCount;

    /** The first coordinate to be used for the texture. */
    private double textureU;

    /** The second coordinate to be used for the texture. */
    private double textureV;
    private int brightness;

    /** The color (RGBA) value to be used for the following draw call. */
    private int color;

    /**
     * Whether the current draw object for this tessellator has color values.
     */
    private boolean hasColor;

    /**
     * Whether the current draw object for this tessellator has texture coordinates.
     */
    private boolean hasTexture;
    private boolean hasBrightness;

    /**
     * Whether the current draw object for this tessellator has normal values.
     */
    private boolean hasNormals;

    /** The index into the raw buffer to be used for the next data. */
    private int rawBufferIndex;

    /**
     * The number of vertices manually added to the given draw call. This differs from vertexCount because it adds extra
     * vertices when converting quads to triangles.
     */
    private int addedVertices;

    /** Disables all color information for the following draw call. */
    private boolean isColorDisabled;

    /** The draw mode currently being used by the tessellator. */
    public int drawMode;

    /**
     * An offset to be applied along the x-axis for all vertices in this draw call.
     */
    public double xOffset;

    /**
     * An offset to be applied along the y-axis for all vertices in this draw call.
     */
    public double yOffset;

    /**
     * An offset to be applied along the z-axis for all vertices in this draw call.
     */
    public double zOffset;

    /** The normal to be applied to the face being drawn. */
    private int normal;

    /** The static instance of the Tessellator. */
    public static Tessellator instance = new Tessellator(524288);

    /** Whether this tessellator is currently in draw mode. */
    public boolean isDrawing;

    /** Whether we are currently using VBO or not. */
    private boolean useVBO;

    /** An IntBuffer used to store the indices of vertex buffer objects. */
    private IntBuffer vertexBuffers;

    /**
     * The index of the last VBO used. This is used in round-robin fashion, sequentially, through the vboCount vertex
     * buffers.
     */
    private int vboIndex;

    /** Number of vertex buffer objects allocated for use. */
    private int vboCount;

    /** The size of the buffers used (in integers). */
    private int bufferSize;
    private boolean renderingChunk;
    private static boolean littleEndianByteOrder = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
    public static boolean renderingWorldRenderer = false;
    public boolean defaultTexture;
    public int textureID;
    public boolean autoGrow;
    private Tessellator[] subTessellators;
    private int[] subTextures;
    private static int terrainTexture = 0;
    private long textureUpdateTime;

    public Tessellator()
    {
        this(65536);
        this.defaultTexture = false;
    }

    public Tessellator(int par1)
    {
        this.renderingChunk = false;
        this.defaultTexture = true;
        this.textureID = 0;
        this.autoGrow = true;
        this.subTessellators = new Tessellator[0];
        this.subTextures = new int[0];
        this.textureUpdateTime = 0L;
        this.vertexCount = 0;
        this.hasColor = false;
        this.hasTexture = false;
        this.hasBrightness = false;
        this.hasNormals = false;
        this.rawBufferIndex = 0;
        this.addedVertices = 0;
        this.isColorDisabled = false;
        this.isDrawing = false;
        this.useVBO = false;
        this.vboIndex = 0;
        this.vboCount = 10;
        this.bufferSize = par1;
        this.byteBuffer = GLAllocation.createDirectByteBuffer(par1 * 4);
        this.intBuffer = this.byteBuffer.asIntBuffer();
        this.floatBuffer = this.byteBuffer.asFloatBuffer();
        this.shortBuffer = this.byteBuffer.asShortBuffer();
        this.rawBuffer = new int[par1];
        this.useVBO = tryVBO && GLContext.getCapabilities().GL_ARB_vertex_buffer_object;

        if (this.useVBO)
        {
            this.vertexBuffers = GLAllocation.createDirectIntBuffer(this.vboCount);
            ARBVertexBufferObject.glGenBuffersARB(this.vertexBuffers);
        }
    }

    private void draw(int var1, int var2)
    {
        int var3 = var2 - var1;

        if (var3 > 0)
        {
            if (var3 % 4 == 0)
            {
                if (this.useVBO)
                {
                    throw new IllegalStateException("VBO not implemented");
                }
                else
                {
                    this.floatBuffer.position(3);
                    GL11.glTexCoordPointer(2, 32, this.floatBuffer);
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                    this.shortBuffer.position(14);
                    GL11.glTexCoordPointer(2, 32, this.shortBuffer);
                    GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                    this.byteBuffer.position(20);
                    GL11.glColorPointer(4, true, 32, this.byteBuffer);
                    this.floatBuffer.position(0);
                    GL11.glVertexPointer(3, 32, this.floatBuffer);

                    if (this.drawMode == 7 && convertQuadsToTriangles)
                    {
                        GL11.glDrawArrays(GL11.GL_TRIANGLES, var1, var3);
                    }
                    else
                    {
                        GL11.glDrawArrays(this.drawMode, var1, var3);
                    }
                }
            }
        }
    }

    /**
     * Draws the data set up in this tessellator and resets the state to prepare for new drawing.
     */
    public int draw()
    {
        if (!this.isDrawing)
        {
            throw new IllegalStateException("Not tesselating!");
        }
        else
        {
            if (this.renderingChunk && this.subTessellators.length > 0)
            {
                boolean var1 = false;

                for (int var2 = 0; var2 < this.subTessellators.length; ++var2)
                {
                    int var3 = this.subTextures[var2];

                    if (var3 <= 0)
                    {
                        break;
                    }

                    Tessellator var4 = this.subTessellators[var2];

                    if (var4.isDrawing)
                    {
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, var3);
                        var1 = true;
                        var4.draw();
                    }
                }

                if (var1)
                {
                    if (this.textureID > 0)
                    {
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureID);
                    }
                    else
                    {
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTerrainTexture());
                    }
                }
            }

            this.isDrawing = false;

            if (this.vertexCount > 0)
            {
                this.intBuffer.clear();
                this.intBuffer.put(this.rawBuffer, 0, this.rawBufferIndex);
                this.byteBuffer.position(0);
                this.byteBuffer.limit(this.rawBufferIndex * 4);

                if (this.useVBO)
                {
                    this.vboIndex = (this.vboIndex + 1) % this.vboCount;
                    ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.vertexBuffers.get(this.vboIndex));
                    ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.byteBuffer, ARBVertexBufferObject.GL_STREAM_DRAW_ARB);
                }

                if (this.hasTexture)
                {
                    if (this.useVBO)
                    {
                        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 32, 12L);
                    }
                    else
                    {
                        this.floatBuffer.position(3);
                        GL11.glTexCoordPointer(2, 32, this.floatBuffer);
                    }

                    GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                }

                if (this.hasBrightness)
                {
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);

                    if (this.useVBO)
                    {
                        GL11.glTexCoordPointer(2, GL11.GL_SHORT, 32, 28L);
                    }
                    else
                    {
                        this.shortBuffer.position(14);
                        GL11.glTexCoordPointer(2, 32, this.shortBuffer);
                    }

                    GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                }

                if (this.hasColor)
                {
                    if (this.useVBO)
                    {
                        GL11.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 32, 20L);
                    }
                    else
                    {
                        this.byteBuffer.position(20);
                        GL11.glColorPointer(4, true, 32, this.byteBuffer);
                    }

                    GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
                }

                if (this.hasNormals)
                {
                    if (this.useVBO)
                    {
                        GL11.glNormalPointer(GL11.GL_UNSIGNED_BYTE, 32, 24L);
                    }
                    else
                    {
                        this.byteBuffer.position(24);
                        GL11.glNormalPointer(32, this.byteBuffer);
                    }

                    GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
                }

                if (this.useVBO)
                {
                    GL11.glVertexPointer(3, GL11.GL_FLOAT, 32, 0L);
                }
                else
                {
                    this.floatBuffer.position(0);
                    GL11.glVertexPointer(3, 32, this.floatBuffer);
                }

                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

                if (this.drawMode == 7 && convertQuadsToTriangles)
                {
                    GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, this.vertexCount);
                }
                else
                {
                    GL11.glDrawArrays(this.drawMode, 0, this.vertexCount);
                }

                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

                if (this.hasTexture)
                {
                    GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                }

                if (this.hasBrightness)
                {
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                    GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                }

                if (this.hasColor)
                {
                    GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
                }

                if (this.hasNormals)
                {
                    GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
                }
            }

            int var5 = this.rawBufferIndex * 4;
            this.reset();
            return var5;
        }
    }

    /**
     * Clears the tessellator state in preparation for new drawing.
     */
    private void reset()
    {
        this.vertexCount = 0;
        this.byteBuffer.clear();
        this.rawBufferIndex = 0;
        this.addedVertices = 0;
    }

    /**
     * Sets draw mode in the tessellator to draw quads.
     */
    public void startDrawingQuads()
    {
        this.startDrawing(7);
    }

    /**
     * Resets tessellator state and prepares for drawing (with the specified draw mode).
     */
    public void startDrawing(int par1)
    {
        if (this.isDrawing)
        {
            throw new IllegalStateException("Already tesselating!");
        }
        else
        {
            this.isDrawing = true;
            this.reset();
            this.drawMode = par1;
            this.hasNormals = false;
            this.hasColor = false;
            this.hasTexture = false;
            this.hasBrightness = false;
            this.isColorDisabled = false;
        }
    }

    /**
     * Sets the texture coordinates.
     */
    public void setTextureUV(double par1, double par3)
    {
        this.hasTexture = true;
        this.textureU = par1;
        this.textureV = par3;
    }

    public void setBrightness(int par1)
    {
        this.hasBrightness = true;
        this.brightness = par1;
    }

    /**
     * Sets the RGB values as specified, converting from floats between 0 and 1 to integers from 0-255.
     */
    public void setColorOpaque_F(float par1, float par2, float par3)
    {
    	if(Hacks.findMod(LSD.class).isEnabled()) {
    		this.setColorOpaque(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
    	} else {
    		this.setColorOpaque((int)(par1 * 255.0F), (int)(par2 * 255.0F), (int)(par3 * 255.0F));
    	}
    }

    /**
     * Sets the RGBA values for the color, converting from floats between 0 and 1 to integers from 0-255.
     */
    public void setColorRGBA_F(float par1, float par2, float par3, float par4)
    {
    	if(Hacks.findMod(LSD.class).isEnabled()) {
    		this.setColorRGBA(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255), (int)(par4 * 255.0F));
    	} else {
    		this.setColorRGBA((int)(par1 * 255.0F), (int)(par2 * 255.0F), (int)(par3 * 255.0F), (int)(par4 * 255.0F));
    	}
    }

    /**
     * Sets the RGB values as specified, and sets alpha to opaque.
     */
    public void setColorOpaque(int par1, int par2, int par3)
    {
    	if(Hacks.findMod(LSD.class).isEnabled()) {
    		this.setColorRGBA(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255), Hacks.findMod(Xray.class).isEnabled() && Xray.opacity.getValue() != 0 ? (int)Xray.opacity.getValue() : 255);
    	} else {
    		this.setColorRGBA(par1, par2, par3, Hacks.findMod(Xray.class).isEnabled() && Xray.opacity.getValue() != 0 ? (int)Xray.opacity.getValue() : 255);
    	}
    }

    /**
     * Sets the RGBA values for the color. Also clamps them to 0-255.
     */
    public void setColorRGBA(int par1, int par2, int par3, int par4)
    {
        if (!this.isColorDisabled)
        {
            if (par1 > 255)
            {
                par1 = 255;
            }

            if (par2 > 255)
            {
                par2 = 255;
            }

            if (par3 > 255)
            {
                par3 = 255;
            }

            if (par4 > 255)
            {
                par4 = 255;
            }

            if (par1 < 0)
            {
                par1 = 0;
            }

            if (par2 < 0)
            {
                par2 = 0;
            }

            if (par3 < 0)
            {
                par3 = 0;
            }

            if (par4 < 0)
            {
                par4 = 0;
            }

            this.hasColor = true;

            if (littleEndianByteOrder)
            {
                this.color = par4 << 24 | par3 << 16 | par2 << 8 | par1;
            }
            else
            {
                this.color = par1 << 24 | par2 << 16 | par3 << 8 | par4;
            }
        }
    }

    /**
     * Adds a vertex specifying both x,y,z and the texture u,v for it.
     */
    public void addVertexWithUV(double par1, double par3, double par5, double par7, double par9)
    {
        this.setTextureUV(par7, par9);
        this.addVertex(par1, par3, par5);
    }

    /**
     * Adds a vertex with the specified x,y,z to the current draw call. It will trigger a draw() if the buffer gets
     * full.
     */
    public void addVertex(double par1, double par3, double par5)
    {
        if (this.autoGrow && this.rawBufferIndex >= this.bufferSize - 32)
        {
            Config.dbg("Expand tessellator buffer, old: " + this.bufferSize + ", new: " + this.bufferSize * 2);
            this.bufferSize *= 2;
            int[] var7 = new int[this.bufferSize];
            System.arraycopy(this.rawBuffer, 0, var7, 0, this.rawBuffer.length);
            this.rawBuffer = var7;
            this.byteBuffer = GLAllocation.createDirectByteBuffer(this.bufferSize * 4);
            this.intBuffer = this.byteBuffer.asIntBuffer();
            this.floatBuffer = this.byteBuffer.asFloatBuffer();
            this.shortBuffer = this.byteBuffer.asShortBuffer();
        }

        ++this.addedVertices;

        if (this.drawMode == 7 && convertQuadsToTriangles && this.addedVertices % 4 == 0)
        {
            for (int var9 = 0; var9 < 2; ++var9)
            {
                int var8 = 8 * (3 - var9);

                if (this.hasTexture)
                {
                    this.rawBuffer[this.rawBufferIndex + 3] = this.rawBuffer[this.rawBufferIndex - var8 + 3];
                    this.rawBuffer[this.rawBufferIndex + 4] = this.rawBuffer[this.rawBufferIndex - var8 + 4];
                }

                if (this.hasBrightness)
                {
                    this.rawBuffer[this.rawBufferIndex + 7] = this.rawBuffer[this.rawBufferIndex - var8 + 7];
                }

                if (this.hasColor)
                {
                    this.rawBuffer[this.rawBufferIndex + 5] = this.rawBuffer[this.rawBufferIndex - var8 + 5];
                }

                this.rawBuffer[this.rawBufferIndex + 0] = this.rawBuffer[this.rawBufferIndex - var8 + 0];
                this.rawBuffer[this.rawBufferIndex + 1] = this.rawBuffer[this.rawBufferIndex - var8 + 1];
                this.rawBuffer[this.rawBufferIndex + 2] = this.rawBuffer[this.rawBufferIndex - var8 + 2];
                ++this.vertexCount;
                this.rawBufferIndex += 8;
            }
        }

        if (this.hasTexture)
        {
            this.rawBuffer[this.rawBufferIndex + 3] = Float.floatToRawIntBits((float)this.textureU);
            this.rawBuffer[this.rawBufferIndex + 4] = Float.floatToRawIntBits((float)this.textureV);
        }

        if (this.hasBrightness)
        {
            this.rawBuffer[this.rawBufferIndex + 7] = this.brightness;
        }

        if (this.hasColor)
        {
            this.rawBuffer[this.rawBufferIndex + 5] = this.color;
        }

        if (this.hasNormals)
        {
            this.rawBuffer[this.rawBufferIndex + 6] = this.normal;
        }

        this.rawBuffer[this.rawBufferIndex + 0] = Float.floatToRawIntBits((float)(par1 + this.xOffset));
        this.rawBuffer[this.rawBufferIndex + 1] = Float.floatToRawIntBits((float)(par3 + this.yOffset));
        this.rawBuffer[this.rawBufferIndex + 2] = Float.floatToRawIntBits((float)(par5 + this.zOffset));
        this.rawBufferIndex += 8;
        ++this.vertexCount;

        if (!this.autoGrow && this.addedVertices % 4 == 0 && this.rawBufferIndex >= this.bufferSize - 32)
        {
            this.draw();
            this.isDrawing = true;
        }
    }

    /**
     * Sets the color to the given opaque value (stored as byte values packed in an integer).
     */
    public void setColorOpaque_I(int par1)
    {
        int var2 = par1 >> 16 & 255;
        int var3 = par1 >> 8 & 255;
        int var4 = par1 & 255;
        this.setColorOpaque(var2, var3, var4);
    }

    /**
     * Sets the color to the given color (packed as bytes in integer) and alpha values.
     */
    public void setColorRGBA_I(int par1, int par2)
    {
        int var3 = par1 >> 16 & 255;
        int var4 = par1 >> 8 & 255;
        int var5 = par1 & 255;
        this.setColorRGBA(var3, var4, var5, par2);
    }

    /**
     * Disables colors for the current draw call.
     */
    public void disableColor()
    {
        this.isColorDisabled = true;
    }

    /**
     * Sets the normal for the current draw call.
     */
    public void setNormal(float par1, float par2, float par3)
    {
        this.hasNormals = true;
        byte var4 = (byte)((int)(par1 * 127.0F));
        byte var5 = (byte)((int)(par2 * 127.0F));
        byte var6 = (byte)((int)(par3 * 127.0F));
        this.normal = var4 & 255 | (var5 & 255) << 8 | (var6 & 255) << 16;
    }

    /**
     * Sets the translation for all vertices in the current draw call.
     */
    public void setTranslation(double par1, double par3, double par5)
    {
        this.xOffset = par1;
        this.yOffset = par3;
        this.zOffset = par5;
    }

    /**
     * Offsets the translation for all vertices in the current draw call.
     */
    public void addTranslation(float par1, float par2, float par3)
    {
        this.xOffset += (double)par1;
        this.yOffset += (double)par2;
        this.zOffset += (double)par3;
    }

    public boolean isRenderingChunk()
    {
        return this.renderingChunk;
    }

    public void setRenderingChunk(boolean var1)
    {
        if (this.renderingChunk != var1)
        {
            for (int var2 = 0; var2 < this.subTextures.length; ++var2)
            {
                this.subTextures[var2] = 0;
            }
        }

        this.renderingChunk = var1;
    }

    public Tessellator getSubTessellator(Icon var1)
    {
        return instance;
    }

    public Tessellator getSubTessellatorImpl(Icon var1)
    {
        return null;
    }

    public static int getTerrainTexture()
    {
        if (terrainTexture == 0)
        {
            terrainTexture = Config.getRenderEngine().getTexture("/terrain.png");
        }

        return terrainTexture;
    }
}
