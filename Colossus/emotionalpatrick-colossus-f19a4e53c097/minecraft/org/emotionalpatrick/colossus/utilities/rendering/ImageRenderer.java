package org.emotionalpatrick.colossus.utilities.rendering;

import net.minecraft.src.Tessellator;

import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.opengl.GL11;

public class ImageRenderer {

	public final void renderSpecialBackground(String texturePath, int scaleWidth, int scaleHeight)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, Wrapper.getMinecraft().renderEngine.getTexture(texturePath));
		Tessellator var3 = Tessellator.instance;
		var3.startDrawingQuads();
		var3.addVertexWithUV(0.0D, (double)scaleHeight, -90.0D, 0.0D, 1.0D);
		var3.addVertexWithUV((double)scaleWidth, (double)scaleHeight, -90.0D, 1.0D, 1.0D);
		var3.addVertexWithUV((double)scaleWidth, 0.0D, -90.0D, 1.0D, 0.0D);
		var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		var3.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void drawScaledImage(String path, int xPosition, int yPosition, double width, double height)
	{
		width /= 2.0D;
		height /= 2.0D;

		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		Tessellator tess = Tessellator.instance;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, Wrapper.getMinecraft().renderEngine.getTexture(path));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		tess.startDrawingQuads();
		tess.addVertexWithUV(xPosition, yPosition + height, 0.0D, 0.0D, 1.0D);
		tess.addVertexWithUV(xPosition + width, yPosition + height, 0.0D, 1.0D, 1.0D);
		tess.addVertexWithUV(xPosition + width, yPosition, 0.0D, 1.0D, 0.0D);
		tess.addVertexWithUV(xPosition + 0.0D, yPosition, 0.0D, 0.0D, 0.0D);
		tess.draw();
		GL11.glDisable(3042);
	}
}
