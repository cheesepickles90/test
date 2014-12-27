package me.ownage.xenon.hooks;

import me.ownage.xenon.gui.XenonOptions;
import me.ownage.xenon.gui.XenonUpdate;
import me.ownage.xenon.main.Xenon;
import net.minecraft.src.*;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Random;
import net.minecraft.client.Minecraft;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;




public class XMainMenu extends GuiScreen
{
	private Minecraft mc = Xenon.getMinecraft();
	
	/** The RNG used by the Main Menu Screen. */
    private static final Random rand = new Random();

    /** Counts the number of screen updates. */
    private float updateCounter = 0.0F;

    /** The splash message. */
    private String splashText = "missingno";
    private GuiButton field_73973_d;

    /** Timer used to rotate the panorama, increases every tick. */
    private int panoramaTimer = 0;

    /**
     * Texture allocated for the current viewport of the main menu's panorama background.
     */
    private int viewportTexture;
    private static final String[] field_73978_o = new String[] {"/title/bg/panorama0.png", "/title/bg/panorama1.png", "/title/bg/panorama2.png", "/title/bg/panorama3.png", "/title/bg/panorama4.png", "/title/bg/panorama5.png"};

    public XMainMenu()
    {
        BufferedReader var1 = null;

        try
        {
            ArrayList var2 = new ArrayList();
            var1 = new BufferedReader(new InputStreamReader(GuiMainMenu.class.getResourceAsStream("/title/splashes.txt"), Charset.forName("UTF-8")));
            String var3;

            while ((var3 = var1.readLine()) != null)
            {
                var3 = var3.trim();

                if (var3.length() > 0)
                {
                    var2.add(var3);
                }
            }

            do
            {
                this.splashText = (String)var2.get(rand.nextInt(var2.size()));
            }
            while (this.splashText.hashCode() == 125780783);
        }
        catch (IOException var12)
        {
            ;
        }
        finally
        {
            if (var1 != null)
            {
                try
                {
                    var1.close();
                }
                catch (IOException var11)
                {
                    ;
                }
            }
        }

        this.updateCounter = rand.nextFloat();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ++this.panoramaTimer;
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
    	if(par2 == 1)
    	{
    		clickedDonate = false;
    	}
    }
    
    public String downloadString(String uri)
    {
    	try
    	{
	        URL url = new URL(uri);
	        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        
	        String text = "";
	        
	        String line = "";
			while ((line = reader.readLine()) != null)
			{
				String curLine = line;
				text += curLine;
			}
			return text;
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    		return "Failed to retrieve string.";
    	}
    }
    
    private static String[] motdlines = {"@", "@", "@", "@", "@", "@", "@", "@", "@"};
    
    public String processMOTDString(String m)
    {
    	return (m
    			.replace("%MC_USER%", mc.session.username)
    			.replace("%OS_USER%", System.getProperty("user.name"))
    			.replace("%OS%", System.getProperty("os.name"))
    			.replace("%CLIENT_VERSION%", "1.2.5")
    			.replace("%XENON_VERSION%", Xenon.CLIENT_VERSION)
    			.replace("%CLIENT_SPLASH%", splashText)
    			.replace("%PARSER_LINE%", "@")
    			.replace("%OS_MAC_OR_PC%", (System.getProperty("os.name").toUpperCase().contains("MAC")) ? "Mac" : "PC")
    			.replace("§", "\247")
    			);
    	
    }
    
    public static String motdRaw;
	private String updateAvaliable = "";
	private static String xenVersionDLStr = Xenon.downloadString("http://ownage.c12craft.com/xenversion.txt");
	private boolean clickedDonate = false;

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
    	float tString = convVersionString(xenVersionDLStr);
    	float lString = convVersionString(Xenon.CLIENT_VERSION);
        if(tString <= lString)
        {
        	isUpToDate = true;
        	updateAvaliable = "\247bClient up to date.";
        }else
        {
        	updateAvaliable = "\247cNew version available: " + xenVersionDLStr;
        }
        if(xenVersionDLStr.equalsIgnoreCase("Failed to retrieve string.")) {
        	updateAvaliable = "\2474Unknown";
        }
        if(motdRaw == null) {
        	motdRaw = downloadString("http://ownage.c12craft.com/v2.0/motd.txt");
        }
    	String s1array[] = processMOTDString(motdRaw).split("@");
    	motdlines = s1array;
        this.viewportTexture = this.mc.renderEngine.allocateAndSetupTexture(new BufferedImage(256, 256, 2));
        Calendar var1 = Calendar.getInstance();
        var1.setTime(new Date());

        if (var1.get(2) + 1 == 11 && var1.get(5) == 9)
        {
            this.splashText = "Happy birthday, ez!";
        }
        else if (var1.get(2) + 1 == 6 && var1.get(5) == 1)
        {
            this.splashText = "Happy birthday, Notch!";
        }
        else if (var1.get(2) + 1 == 12 && var1.get(5) == 24)
        {
            this.splashText = "Merry X-mas!";
        }
        else if (var1.get(2) + 1 == 1 && var1.get(5) == 1)
        {
            this.splashText = "Happy new year!";
        }

        StringTranslate var2 = StringTranslate.getInstance();
        int var4 = this.height / 4 + 48;

        /*if (this.mc.isDemo())
        {
            this.func_73972_b(var4, 24, var2);
        }
        else
        {
            this.func_73969_a(var4, 24, var2);
        }*/

        /*this.controlList.add(new GuiButton(3, this.width / 2 - 100, var4 + 48, var2.translateKey("menu.mods")));

        if (this.mc.hideQuitButton)
        {
            this.controlList.add(new GuiButton(0, this.width / 2 - 100, var4 + 72, var2.translateKey("menu.options")));
        }
        else
        {
            this.controlList.add(new GuiButton(0, this.width / 2 - 100, var4 + 72 + 12, 98, 20, var2.translateKey("menu.options")));
            this.controlList.add(new GuiButton(4, this.width / 2 + 2, var4 + 72 + 12, 98, 20, var2.translateKey("menu.quit")));
        }

        this.controlList.add(new GuiButtonLanguage(5, this.width / 2 - 124, var4 + 72 + 12));*/
    }

    private void func_73969_a(int par1, int par2, StringTranslate par3StringTranslate)
    {
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, par1, par3StringTranslate.translateKey("menu.singleplayer")));
        this.controlList.add(new GuiButton(2, this.width / 2 - 100, par1 + par2 * 1, par3StringTranslate.translateKey("menu.multiplayer")));
    }

    private void func_73972_b(int par1, int par2, StringTranslate par3StringTranslate)
    {
        this.controlList.add(new GuiButton(11, this.width / 2 - 100, par1, par3StringTranslate.translateKey("menu.playdemo")));
        this.controlList.add(this.field_73973_d = new GuiButton(12, this.width / 2 - 100, par1 + par2 * 1, par3StringTranslate.translateKey("menu.resetdemo")));
        ISaveFormat var4 = this.mc.getSaveLoader();
        WorldInfo var5 = var4.getWorldInfo("Demo_World");

        if (var5 == null)
        {
            this.field_73973_d.enabled = false;
        }
    }

    public void confirmClicked(boolean par1, int par2)
    {
        if (par1 && par2 == 12)
        {
            ISaveFormat var3 = this.mc.getSaveLoader();
            var3.flushCache();
            var3.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Draws the main menu panorama
     */
    private void drawPanorama(int par1, int par2, float par3)
    {
        Tessellator var4 = Tessellator.instance;
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GLU.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        byte var5 = 8;

        for (int var6 = 0; var6 < var5 * var5; ++var6)
        {
            GL11.glPushMatrix();
            float var7 = ((float)(var6 % var5) / (float)var5 - 0.5F) / 64.0F;
            float var8 = ((float)(var6 / var5) / (float)var5 - 0.5F) / 64.0F;
            float var9 = 0.0F;
            GL11.glTranslatef(var7, var8, var9);
            GL11.glRotatef(MathHelper.sin(((float)this.panoramaTimer + par3) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-((float)this.panoramaTimer + par3) * 0.1F, 0.0F, 1.0F, 0.0F);

            for (int var10 = 0; var10 < 6; ++var10)
            {
                GL11.glPushMatrix();

                if (var10 == 1)
                {
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (var10 == 2)
                {
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                }

                if (var10 == 3)
                {
                    GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (var10 == 4)
                {
                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (var10 == 5)
                {
                    GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture(field_73978_o[var10]));
                var4.startDrawingQuads();
                var4.setColorRGBA_I(16777215, 255 / (var6 + 1));
                float var11 = 0.0F;
                var4.addVertexWithUV(-1.0D, -1.0D, 1.0D, (double)(0.0F + var11), (double)(0.0F + var11));
                var4.addVertexWithUV(1.0D, -1.0D, 1.0D, (double)(1.0F - var11), (double)(0.0F + var11));
                var4.addVertexWithUV(1.0D, 1.0D, 1.0D, (double)(1.0F - var11), (double)(1.0F - var11));
                var4.addVertexWithUV(-1.0D, 1.0D, 1.0D, (double)(0.0F + var11), (double)(1.0F - var11));
                var4.draw();
                GL11.glPopMatrix();
            }

            GL11.glPopMatrix();
            GL11.glColorMask(true, true, true, false);
        }

        var4.setTranslation(0.0D, 0.0D, 0.0D);
        GL11.glColorMask(true, true, true, true);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    /**
     * Rotate and blurs the skybox view in the main menu
     */
    private void rotateAndBlurSkybox(float par1)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.viewportTexture);
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColorMask(true, true, true, false);
        Tessellator var2 = Tessellator.instance;
        var2.startDrawingQuads();
        byte var3 = 3;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            var2.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F / (float)(var4 + 1));
            int var5 = this.width;
            int var6 = this.height;
            float var7 = (float)(var4 - var3 / 2) / 256.0F;
            var2.addVertexWithUV((double)var5, (double)var6, (double)this.zLevel, (double)(0.0F + var7), 0.0D);
            var2.addVertexWithUV((double)var5, 0.0D, (double)this.zLevel, (double)(1.0F + var7), 0.0D);
            var2.addVertexWithUV(0.0D, 0.0D, (double)this.zLevel, (double)(1.0F + var7), 1.0D);
            var2.addVertexWithUV(0.0D, (double)var6, (double)this.zLevel, (double)(0.0F + var7), 1.0D);
        }

        var2.draw();
        GL11.glColorMask(true, true, true, true);
    }

    /**
     * Renders the skybox in the main menu
     */
    public void renderSkybox(int par1, int par2, float par3)
    {
        GL11.glViewport(0, 0, 256, 256);
        this.drawPanorama(par1, par2, par3);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        float var5 = this.width > this.height ? 120.0F / (float)this.width : 120.0F / (float)this.height;
        float var6 = (float)this.height * var5 / 256.0F;
        float var7 = (float)this.width * var5 / 256.0F;
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        var4.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
        int var8 = this.width;
        int var9 = this.height;
        var4.addVertexWithUV(0.0D, (double)var9, (double)this.zLevel, (double)(0.5F - var6), (double)(0.5F + var7));
        var4.addVertexWithUV((double)var8, (double)var9, (double)this.zLevel, (double)(0.5F - var6), (double)(0.5F - var7));
        var4.addVertexWithUV((double)var8, 0.0D, (double)this.zLevel, (double)(0.5F + var6), (double)(0.5F - var7));
        var4.addVertexWithUV(0.0D, 0.0D, (double)this.zLevel, (double)(0.5F + var6), (double)(0.5F + var7));
        var4.draw();
    }
    
    public int highlightedButton = -1;
    
    protected void mouseMovedOrUp(int x, int y, int par3)
    {
	    	if(x >= 2 && y >= 2 && x <= 78 && y <= 22)
	    	{
	    		highlightedButton = 0;
	    	}else
	    	if(x >= 2 && y >= 24 && x <= 78 && y <= 44)
	    	{
	    		highlightedButton = 1;
	    	}else
	    	if(x >= 2 && y >= 46 && x <= 78 && y <= 66)
	    	{
	    		highlightedButton = 2;
	    	}else
	    	if(x >= 2 && y >= 68 && x <= 78 && y <= 88)
	    	{
	    		highlightedButton = 3;
	    	}else
	        if(x >= 2 && y >= 90 && x <= 78 && y <= 110)
	        {
	        	highlightedButton = 4;
	        }else
	    	if(x >= 2 && y >= 112 && x <= 78 && y <= 132)
	    	{
	    		highlightedButton = 5;
	    	}else
	    	if(x >= 2 && y >= 134 && x <= 78 && y <= 154)
	    	{
	    		highlightedButton = 6;
	    	}else
	    	{
	    		highlightedButton = -1;
	    	}
    }
    
    public void mouseClicked(int x, int y, int button)
    {
    	if(button == 0)
    	{
    		if(x >= 2 && y >= 2 && x <= 78 && y <= 22)
    		{
    			mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
    			mc.displayGuiScreen(new GuiSelectWorld(this));
    		}
    		if(x >= 2 && y >= 24 && x <= 78 && y <= 44)
    		{
    			mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
    			mc.displayGuiScreen(new GuiMultiplayer(this));
    		}
    		if(x >= 2 && y >= 46 && x <= 78 && y <= 66)
    		{
    			mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
    			mc.displayGuiScreen(new GuiTexturePacks(this, mc.gameSettings));
    		}
    		if(x >= 2 && y >= 68 && x <= 78 && y <= 88)
    		{
    			mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
    			mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
    		}
    		if(x >= 2 && y >= 90 && x <= 78 && y <= 110)
    		{
    			mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
    			mc.displayGuiScreen(new XenonOptions(this));
    		}
    		if(x >= 2 && y >= 112 && x <= 78 && y <= 132)
    		{
    			mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
    			mc.shutdown();
    		}
        	if(x >= 2 && y >= 134 && x <= 78 && y <= 154)
        	{
    			mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
    			Sys.openURL("http://ownagedev.com/managedonator.php");
        	}
		    if(x >= 2 && y >= 156 && x <= 78 && y <= 176)
		    {
		    	mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
		    	Sys.openURL(Xenon.getFileManager().xenonDir.getAbsolutePath() + File.separator + "help.html");
		    }
        	if((x >= 158 + fontRenderer.getStringWidth(updateAvaliable) && y >= 37 && x <= 163 + fontRenderer.getStringWidth(updateAvaliable) + 20 && y <= 47) && !isUpToDate)
        	{
    			mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
    			if(Xenon.xu == null) {
    				Xenon.xu = new XenonUpdate(this);
    			}
    			mc.displayGuiScreen(Xenon.xu);
        	}
    	}
    }
    
	private float convVersionString(String s)
	{
		try
		{
			String s1 = "";
			char[] cArray = s.replace("_", ".").replace("-", ".").trim().toCharArray();
			boolean flag = false;
			for(char c: cArray)
			{
				if(!(c == '.' && flag))
				{
					s1 = s1+c;
				}
				if(c == '.')
				{
					flag = true;
				}
			}
			return Float.parseFloat(s1);
		}catch(Exception e) {}
		return 0F;
	}
	
	private boolean isUpToDate = false;
    
    public void drawBorderedRect(int x, int y, int x1, int y1, int size, int borderC, int insideC) 
    {
        drawRect(x + size, y + size, x1 - size, y1 - size, insideC);
        drawRect(x+size, y+size, x1, y, borderC);
        drawRect(x, y, x+size, y1, borderC);
        drawRect(x1, y1, x1-size, y+size, borderC);
        drawRect(x, y1-size, x1, y1, borderC);
    }
    
    public void drawRoundedRect(int x, int y, int x1, int y1, int size, int borderC, int insideC) 
    {
    	x *= 2; y *= 2; x1 *= 2; y1 *= 2;
    	GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawVerticalLine(x, y + 1, y1 -2, borderC);
        drawVerticalLine(x1 - 1, y + 1, y1 - 2, borderC);
        drawHorizontalLine(x + 2, x1 - 3, y, borderC);
        drawHorizontalLine(x + 2, x1 - 3, y1 -1, borderC);
        drawHorizontalLine(x + 1, x + 1, y + 1, borderC);
        drawHorizontalLine(x1 - 2, x1 - 2, y + 1, borderC);
        drawHorizontalLine(x1 - 2, x1 - 2, y1 - 2, borderC);
        drawHorizontalLine(x + 1, x + 1, y1 - 2, borderC);
        drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
		if (par1 >= 2 && par2 >= 2 && par1 <= 78 && par2 <= 22) {
			highlightedButton = 0;
		} else if (par1 >= 2 && par2 >= 24 && par1 <= 78 && par2 <= 44) {
			highlightedButton = 1;
		} else if (par1 >= 2 && par2 >= 46 && par1 <= 78 && par2 <= 66) {
			highlightedButton = 2;
		} else if (par1 >= 2 && par2 >= 68 && par1 <= 78 && par2 <= 88) {
			highlightedButton = 3;
		} else if (par1 >= 2 && par2 >= 90 && par1 <= 78 && par2 <= 110) {
			highlightedButton = 4;
		} else if (par1 >= 2 && par2 >= 112 && par1 <= 78 && par2 <= 132) {
			highlightedButton = 5;
		} else if (par1 >= 2 && par2 >= 134 && par1 <= 78 && par2 <= 154) {
			highlightedButton = 6;
		} else if (par1 >= 2 && par2 >= 156 && par1 <= 78 && par2 <= 176) {
			highlightedButton = 7;
		} else {
			highlightedButton = -1;
		}
        this.renderSkybox(par1, par2, par3);
        Tessellator var4 = Tessellator.instance;
        short var5 = 274;
        int var6 = this.width / 2 - var5 / 2;
        byte var7 = 30;
        this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
        this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/title/mclogo.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        /*if ((double)this.updateCounter < 1.0E-4D)
        {
            this.drawTexturedModalRect(var6 + 0, var7 + 0, 0, 0, 99, 44);
            this.drawTexturedModalRect(var6 + 99, var7 + 0, 129, 0, 27, 44);
            this.drawTexturedModalRect(var6 + 99 + 26, var7 + 0, 126, 0, 3, 44);
            this.drawTexturedModalRect(var6 + 99 + 26 + 3, var7 + 0, 99, 0, 26, 44);
            this.drawTexturedModalRect(var6 + 155, var7 + 0, 0, 45, 155, 44);
        }
        else
        {
            this.drawTexturedModalRect(var6 + 0, var7 + 0, 0, 0, 155, 44);
            this.drawTexturedModalRect(var6 + 155, var7 + 0, 0, 45, 155, 44);
        }*/

        var4.setColorOpaque_I(16777215);
        /*GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
        GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
        float var8 = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * (float)Math.PI * 2.0F) * 0.1F);
        var8 = var8 * 100.0F / (float)(this.fontRenderer.getStringWidth(this.splashText) + 32);
        GL11.glScalef(var8, var8, var8);
        this.drawCenteredString(this.fontRenderer, this.splashText, 0, -8, 16776960);
        GL11.glPopMatrix();*/
        String var9 = "Minecraft 1.3.1";
        
        /** XENON BEGIN */
    
        
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        this.drawCenteredString(this.fontRenderer, Xenon.CLIENT_NAME + (Xenon.IS_BETA ? " b" : " v") + Xenon.CLIENT_VERSION, width / 4 + 23, 5, 0xFFFFFF);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        
        drawBorderedRect(-1, -1, 81, height + 1, 1, 0xAF000000, 0xFFFFFF);
        drawHorizontalLine(-1, 79, 178, 0xAF000000);
        
        drawBorderedRect(85, 35, width - 5, height - 5, 1, 0xAF000000, 0x2F000000);
        drawHorizontalLine(86, width - 7, 48, 0xAF000000);
        
        int count = 0;
        for(String s: motdlines)
        {
        	fontRenderer.drawStringWithShadow(s, 87, 50 + (9 * count), 0xFFFFFF);
        	count++;
        }
        
        int i = 0;
        
        fontRenderer.drawStringWithShadow("MOTD", 88, 38, 0xFFFFFF);
        drawVerticalLine(113, 48, 35, 0xAF000000);
        
        drawRoundedRect(2 - (highlightedButton == 0 ? i : 0), 2 - (highlightedButton == 0 ? i : 0), 78 + (highlightedButton == 0 ? i : 0), 22 + (highlightedButton == 0 ? i : 0), 1, 0xFF000000, highlightedButton == 0 ? 0x9F000000 : 0x8F000000);
        fontRenderer.drawString("Singleplayer", 10, 8, highlightedButton == 0 ? 0xFFFF00 : 0xFFFFFF);
        
        drawRoundedRect(2 - (highlightedButton == 1 ? i : 0), 24 - (highlightedButton == 1 ? i : 0), 78 + (highlightedButton == 1 ? i : 0), 44 + (highlightedButton == 1 ? i : 0), 1, 0xFF000000, highlightedButton == 1 ? 0x9F000000 : 0x8F000000);
        fontRenderer.drawString("Multiplayer", 14, 30, highlightedButton == 1 ? 0xFFFF00 : 0xFFFFFF);
        
        drawRoundedRect(2 - (highlightedButton == 2 ? i : 0), 46 - (highlightedButton == 2 ? i : 0), 78 + (highlightedButton == 2 ? i : 0), 66 + (highlightedButton == 2 ? i : 0), 1, 0xFF000000, highlightedButton == 2 ? 0x9F000000 : 0x8F000000);
        fontRenderer.drawString("Texture Packs", 4, 52,highlightedButton == 2 ? 0xFFFF00 :  0xFFFFFF);
        
        drawRoundedRect(2 - (highlightedButton == 3 ? i : 0), 68 - (highlightedButton == 3 ? i : 0), 78 + (highlightedButton == 3 ? i : 0), 88 + (highlightedButton == 3 ? i : 0), 1, 0xFF000000, highlightedButton == 3 ? 0x9F000000 : 0x8F000000);
        fontRenderer.drawString("Options", 22, 74, highlightedButton == 3 ? 0xFFFF00 : 0xFFFFFF);
        
        drawRoundedRect(2 - (highlightedButton == 4 ? i : 0), 90 - (highlightedButton == 4 ? i : 0), 78 + (highlightedButton == 4 ? i : 0), 110 + (highlightedButton == 4 ? i : 0), 1, 0xFF000000, highlightedButton == 4 ? 0x9F000000 : 0x8F000000);
        fontRenderer.drawString("Xenon Options", 6, 96, highlightedButton == 4 ? 0xFFFF00 : 0xFFFFFF);
        
        drawRoundedRect(2 - (highlightedButton == 5 ? i : 0), 112 - (highlightedButton == 5 ? i : 0), 78 + (highlightedButton == 5 ? i : 0), 132 + (highlightedButton == 5 ? i : 0), 1, 0xFF000000, highlightedButton == 5 ? 0x9F000000 : 0x8F000000);
        fontRenderer.drawString("Quit Game", 18, 118, highlightedButton == 5 ? 0xFFFF00 : 0xFFFFFF);
        
        drawRoundedRect(2 - (highlightedButton == 6 ? i : 0), 134 - (highlightedButton == 6 ? i : 0), 78 + (highlightedButton == 6 ? i : 0), 154 + (highlightedButton == 6 ? i : 0), 1, 0xFF000000, highlightedButton == 6 ? 0x9F000000 : 0x8F000000);
        fontRenderer.drawString("Donate", 24, 140, highlightedButton == 6 ? 0xFFFF00 : 0x55FF55);
        
        drawRoundedRect(2 - (highlightedButton == 7 ? i : 0), 156 - (highlightedButton == 7 ? i : 0), 78 + (highlightedButton == 7 ? i : 0), 176 + (highlightedButton == 7 ? i : 0), 1, 0xFF000000, highlightedButton == 7 ? 0x9F000000 : 0x8F000000);
        fontRenderer.drawString("Help", 30, 162, highlightedButton == 7 ? 0xFFFF00 : 0xFFFFFF);
        
        fontRenderer.drawStringWithShadow("Status: " + updateAvaliable, 116, 38, 0xFFFFFF);
                
        if(updateAvaliable.startsWith("\247cNew version"))
        {
        	drawBorderedRect(158 + fontRenderer.getStringWidth(updateAvaliable), 37, 163 + fontRenderer.getStringWidth(updateAvaliable) + 20, 47, 1, 0xFF000000, 0x8F000000);
        	GL11.glScalef(0.5F, 0.5F, 0.5F);
        	fontRenderer.drawString("Update", (162 + fontRenderer.getStringWidth(updateAvaliable)) * 2, 80, 0xFFFFFF);
        	GL11.glScalef(2.0F, 2.0F, 2.0F);
        }
        
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        
        int niggaMonkeyBitch = 1;
        
        for(Entry<String, String> version: Xenon.vTable.entrySet()) {
        	this.drawString(this.fontRenderer, version.getValue(), 2, 2 * height - 10 * niggaMonkeyBitch, 16777215);
        	niggaMonkeyBitch++;
        }
        /* 
        this.drawString(this.fontRenderer, "Minecraft " + Xenon.MC_VERSION, 2, 2 * height - 10, 16777215);
        this.drawString(this.fontRenderer, "Xenon " + (Xenon.IS_BETA ? "b" : "v") + Xenon.CLIENT_VERSION, 2, 2 * height - 20, 16777215);
        */
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        
		/*if(clickedDonate)
		{
			drawRect(0, 0, width, height, 0xBF000000);
			drawBorderedRect(2, 2, 13, 13, 1, 0xFFFF0000, 0xAFCC0000);
			fontRenderer.drawString("x", 5, 3, 0xFFFFFF);
			drawCenteredString(fontRenderer, "\2472If you would like to donate add me on Skype: ownagehf", width / 2, height / 3 - 5, 0xFFFFFF);
			drawCenteredString(fontRenderer, "\247eDonator features: (so far)", width / 2, height / 3 + 5, 0xFFFFFF);
			drawCenteredString(fontRenderer, "\247aHD/Normal cape for up to 2 accounts.", width / 2, height / 3 + 15, 0xFFFFFF);
			drawCenteredString(fontRenderer, "\2476Gold name.", width / 2, height / 3 + 25, 0xFFFFFF);
			drawCenteredString(fontRenderer, "\2472Thanks.", width / 2, height / 3 + 35, 0xFFFFFF);
		}*/
		
        /** XENON END */

        if (this.mc.isDemo())
        {
            var9 = var9 + " Demo";
        }

        //this.drawString(this.fontRenderer, var9, 2, this.height - 10, 16777215);
        String var10 = "Copyright Mojang AB. Do not distribute!";
        //this.drawString(this.fontRenderer, var10, this.width - this.fontRenderer.getStringWidth(var10) - 2, this.height - 10, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
