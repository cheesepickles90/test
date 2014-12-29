package org.emotionalpatrick.colossus.gui.components;

import java.util.ArrayList;

import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.ColossusWrapper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.GL11.*;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.Item;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MapData;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Tessellator;



public class GuiHelper {
	
	public GuiHelper() {
		g = new Gui();
	}
	
	Minecraft mc;
	
	public static void drawCenteredString(String var1, int var2, int var3, int var4) {
		FontRenderer fr = ColossusWrapper.getFontRenderer();

		fr.drawString(var1, var2 - fr.getStringWidth(var1) / 2, var3, var4);
	}
	
	public static void drawBorderedRect2(int var0, int var1, int var2, int var3, int var4, int var5, int var6)
	{
		Gui.drawRect(var0 + var4, var1 + var4, var2 - var4, var3 - var4, var6);
		Gui.drawRect(var0 + var4, var1 + var4, var2, var1, var5);
		Gui.drawRect(var0, var1, var0 + var4, var3, var5);
		Gui.drawRect(var2, var3, var2 - var4, var1 + var4, var5);
		Gui.drawRect(var0, var3 - var4, var2, var3, var5);
	}
	
	public static void drawColossusChat(float g, float h, float i, int y2, float l1, int col1, int col2) {
		drawRect(g, h, i, y2, col2);

		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glPushMatrix();
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glLineWidth(l1);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(g, h);
		GL11.glVertex2d(g, y2);
		GL11.glVertex2d(i, y2);
		GL11.glVertex2d(i, h);
		GL11.glVertex2d(g, h);
		GL11.glVertex2d(i, h);
		GL11.glVertex2d(g, y2);
		GL11.glVertex2d(i, y2);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}
	
	
	static Gui g;
	
	public static void drawButtonReliant(float x, float y, int state) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		int uX[] = {1, 51, 101};
		int uY = 1;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		ColossusWrapper.getMinecraft().renderEngine.bindTexture(ColossusWrapper.getMinecraft().renderEngine.getTexture("/misc/colossus/GuiRedux.png"));
		drawTexturedRect(x * 2,y * 2,uX[state - 1],uY,48,24);
		GL11.glScalef(2F, 2F, 2F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
		
	public static void drawTexturedRect(float par1, float par2, float par3, float par4, float par5, float par6) {
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(par1 + 0, par2 + par6, zLevel, (par3 + 0) * var7, (par4 + par6) * var8);
		var9.addVertexWithUV(par1 + par5, par2 + par6, zLevel, (par3 + par5) * var7, (par4 + par6) * var8);
		var9.addVertexWithUV(par1 + par5, par2 + 0, zLevel, (par3 + par5) * var7, (par4 + 0) * var8);
		var9.addVertexWithUV(par1 + 0, par2 + 0, zLevel, (par3 + 0) * var7, (par4 + 0) * var8);
		var9.draw();
	}
	
	public static void drawReliantButtonHoverEffectOn(int par1, int par2, int par3, int par4)
	{
		drawFHorizontalLine(par1 + 0.5F, par1 + 20F, par2 + 9.4F, 0x8FD77D00);
		drawFVerticalLine(par1 + 21.4F, par2 - 0.5F, par2 + 9F,0x8FD77D00);
		drawFVerticalLine(par1 + 21F, par2 + 8F, par2 + 9.5F,0x8FD77D00);
		drawFVerticalLine(par1 + 20.5F, par2 + 9F, par2 + 9.5F,0x8FD77D00);
		drawRoundedRectr(par1 - 1, par2 - 1, par3+22, par4+10, 0xFFD77D00);
		drawRoundedRectr(par1, par2, par3+22, par4+10, 0xFFC36900);
		drawRoundedRectr(par1 - 1, par2, par3+13, par4 + 9, 0xFFFFA514);
		drawRoundedRectr(par1, par2+9, par3+21, par4 + 9, 0xFFFFA514); //FF8C00
		drawGradientRect(par1, par2, par3 + 21, par4 + 9, 0xFFFFA514, 0xFFC36900);
	}
	
	public static void drawReliantButtonHoverEffectOff(int par1, int par2, int par3, int par4)
	{
		drawFHorizontalLine(par1 + 0.5F, par1 + 20F, par2 + 9.4F, 0x8F000000);
		drawFVerticalLine(par1 + 21.4F, par2 - 0.5F, par2 + 9F,0x8F000000);
		drawFVerticalLine(par1 + 21F, par2 + 8F, par2 + 9.5F,0x8F000000);
		drawFVerticalLine(par1 + 20.5F, par2 + 9F, par2 + 9.5F,0x8F000000);
		drawRoundedRectr(par1 - 1, par2 - 1, par3+22, par4+10F, 0xFF555555);
		drawRoundedRectr(par1, par2, par3+22, par4+10, 0xFF2E2E2E);
		drawRoundedRectr(par1 - 1, par2, par3+13, par4 + 9, 0xFF4C4C4C);
		drawRoundedRectr(par1, par2+9, par3+21, par4 + 9, 0xFF454545);
		drawRoundedRectr(par1, par2+9, par3+21, par4 + 9, 0xFF2E2E2E);
		drawGradientRect(par1, par2, par3 + 21, par4 + 9, 0xFF3A3A3A, 0xFF242424);
	}
   
	public static void drawReliantButtonOn(int par1, int par2, int par3, int par4)
	{
		drawFHorizontalLine(par1 + 0.5F, par1 + 20F, par2 + 9.4F, 0x8FD77D00);
		drawFVerticalLine(par1 + 21.4F, par2 - 0.5F, par2 + 9F,0x8FD77D00);
		drawFVerticalLine(par1 + 21F, par2 + 8F, par2 + 9.5F,0x8FD77D00);
		drawFVerticalLine(par1 + 20.5F, par2 + 9F, par2 + 9.5F,0x8FD77D00);
		drawRoundedRectr(par1 - 1, par2 - 1, par3+22, par4+10, 0xFFD77D00);
		drawRoundedRectr(par1, par2, par3+22, par4+10, 0xFFC36900);
		drawRoundedRectr(par1 - 1, par2, par3+13, par4 + 9, 0xFFFFA514);
		drawRoundedRectr(par1, par2+9, par3+21, par4 + 9, 0xFFFFA514); //FF8C00
		drawGradientRect(par1, par2, par3 + 21, par4 + 9, 0xFFFF8C00, 0xFFC36900);
	}
	
	public static void drawReliantButtonOff(int par1, int par2, int par3, int par4)
	{
		drawFHorizontalLine(par1 + 0.5F, par1 + 20F, par2 + 9.4F, 0x8F000000);
		drawFVerticalLine(par1 + 21.4F, par2 - 0.5F, par2 + 9F,0x8F000000);
		drawFVerticalLine(par1 + 21F, par2 + 8F, par2 + 9.5F,0x8F000000);
		drawFVerticalLine(par1 + 20.5F, par2 + 9F, par2 + 9.5F,0x8F000000);
		drawRoundedRectr(par1 - 1, par2 - 1, par3+22, par4+10F, 0xFF555555);
		drawRoundedRectr(par1, par2, par3+22, par4+10, 0xFF2E2E2E);
		drawRoundedRectr(par1 - 1, par2, par3+13, par4 + 9, 0xFF4C4C4C);
		drawRoundedRectr(par1, par2+9, par3+21, par4 + 9, 0xFF454545);
		drawRoundedRectr(par1, par2+9, par3+21, par4 + 9, 0xFF2E2E2E);
		drawGradientRect(par1, par2, par3 + 21, par4 + 9,/* isMouseAround(mx,my) ? 0xFF3A3A3A : */0xFF404040, /*isMouseAround(mx,my)  ? 0xFF242424 :*/ 0xFF282828);
	}
	
	protected static float zLevel = 0.0F;

    public void drawTexturedModalRect(double i, double d, int par3, int par4, double e, double f)
    {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV((double)(i + 0), (double)(d + f), (double)this.zLevel, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + f) * var8));
        var9.addVertexWithUV((double)(i + e), (double)(d + f), (double)this.zLevel, (double)((float)(par3 + e) * var7), (double)((float)(par4 + f) * var8));
        var9.addVertexWithUV((double)(i + e), (double)(d + 0), (double)this.zLevel, (double)((float)(par3 + e) * var7), (double)((float)(par4 + 0) * var8));
        var9.addVertexWithUV((double)(i + 0), (double)(d + 0), (double)this.zLevel, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + 0) * var8));
        var9.draw();
    }
	
	public void drawButtonOn(int x, int y, int state) 
	{
		Minecraft mc = Minecraft.getMinecraft();

		int uY = 27;
		int uX[] = {1, 51, 101};
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/misc/colossus/GuiRedux.png"));
		drawTexturedModalRect(x, y, uX[state - 1], uY, 48, 24);
		GL11.glScalef(2F, 2F, 2F);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public void drawButtonOff(int x, int y, int state) 
	{
		Minecraft mc = Minecraft.getMinecraft();

		int uY = 1;
		int uX[] = {1, 51, 101};
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/misc/colossus/GuiRedux.png"));
		drawTexturedModalRect(x, y, uX[state - 1], uY, 48, 24);
		GL11.glScalef(2F, 2F, 2F);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void drawRoundedRectr(float par1, float par2, float par3, float par4, int par5)
   	{
   		par1 *= 2; par3 *= 2; par2 *= 2; par4 *= 2;
   		GL11.glScalef(0.5F, 0.5F, 0.5F);
   		float x = par1; float y = par2; float x1 = par3; float y1 = par4; int borderC = par5;
        drawFVerticalLine(x, y + 1, y1 -2, borderC);
        drawFVerticalLine(x1 - 1, y + 1, y1 - 2, borderC);
        drawFHorizontalLine(x + 2, x1 - 3, y, borderC);
        drawFHorizontalLine(x + 2, x1 - 3, y1 -1, borderC);
        drawFHorizontalLine(x + 1, x + 1, y + 1, borderC);
        drawFHorizontalLine(x1 - 2, x1 - 2, y + 1, borderC);
        drawFHorizontalLine(x1 - 2, x1 - 2, y1 - 2, borderC);
        drawFHorizontalLine(x + 1, x + 1, y1 - 2, borderC);
        drawFRect(x + 1, y + 1, x1 - 1, y1 - 1, par5);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
   	}
	
	/*public void drawGradientRectWithOutline(int xs, int ys, int xe, int ye, int c, int c1, int c2, int c3)
	{	
		this.drawGradientRect(xs + 1, ys + 1, xe- 1, ye - 1, c1,c2);
		drawVerticalLine((xs+1), (ys), (ye) -1, c3);
		drawHorizontalLine(xs + 1, xe - 2, ys+1, c3);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f,0.5f);	
		this.drawWHollowBorderedRect(xs*2+1, ys*2+1, xe*2-1, ye*2-1,1, c);
		GL11.glPopMatrix();
	}
	
	public void drawWHollowBorderedRect(int x, int y, int x1, int y1, int CO1) {
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);
	}*/
	
	public static void drawRGRect(int x, int y, int x1, int y1, int col1, int col2, int borderC) {
		
		
		
		x *= 2;
		y *= 2;
		x1 *= 2;
		y1 *= 2;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawVerticalLine(x, y + 1, y1 - 2, borderC);
		drawVerticalLine(x1 - 1, y + 1, y1 - 2, borderC);
		drawHorizontalLine(x + 2, x1 - 3, y, borderC);
		drawHorizontalLine(x + 2, x1 - 3, y1 - 1, borderC);
		drawHorizontalLine(x + 1, x + 1, y + 1, borderC);
		drawHorizontalLine(x1 - 2, x1 - 2, y + 1, borderC);
		drawHorizontalLine(x1 - 2, x1 - 2, y1 - 2, borderC);
		drawHorizontalLine(x + 1, x + 1, y1 - 2, borderC);
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		float f4 = (float)(col2 >> 24 & 0xFF) / 255F;
		float f5 = (float)(col2 >> 16 & 0xFF) / 255F;
		float f6 = (float)(col2 >> 8 & 0xFF) / 255F;
		float f7 = (float)(col2 & 0xFF) / 255F;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glVertex2d(x1, y);
		GL11.glVertex2d(x, y);

		GL11.glColor4f(f5, f6, f7, f4);
		GL11.glVertex2d(x, y1);
		GL11.glVertex2d(x1, y1);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glScalef(2F, 2F, 2F);
		
		
	}
	
	public static void drawBorderedRect(int x, int y, int x1, int y1, int color, float lineWidth, int color1)
    {
     drawRect(x,y,x1,y1,color);
     setupOverlayRendering();
     disableDefaults();
     GL11.glColor4d(getRedFromHex(color1),getGreenFromHex(color1),getBlueFromHex(color1),getAlphaFromHex(color1));
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(GL11.GL_LINES);
         GL11.glVertex2d(x, y);
      GL11.glVertex2d(x, y1);
      GL11.glVertex2d(x1, y1);
      GL11.glVertex2d(x1, y);
      GL11.glVertex2d(x,y);
      GL11.glVertex2d(x1,y);
      GL11.glVertex2d(x,y1);
      GL11.glVertex2d(x1,y1);
     GL11.glEnd();
     enableDefaults();
     
    }
	
	public static void setupOverlayRendering()
    {
        GL11.glClear(256);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, ColossusWrapper.getScreenWidth(), ColossusWrapper.getScreenHeight(), 0.0D, 1000D, 3000D);
        GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000F);
    }
	
	 public static double getAlphaFromHex(int color)
	 {
	  return((double)((color >> 24 & 0xff) / 255F));
	 }
	 public static double getRedFromHex(int color)
	 {
	  return((double)((color >> 16 & 0xff) / 255F));
	 }
	 public static double getGreenFromHex(int color)
	 {
	  return((double)((color >> 8 & 0xff) / 255F));
	 }
	 public static double getBlueFromHex(int color)
	 {
	  return((double)((color & 0xff) / 255F));
	 }
	 
	 public static void drawGradientRoundedRect(int x, int y, int x1, int y1, int size,int col1, int col2, int borderC) {
			x *= 2;
			y *= 2;
			x1 *= 2;
			y1 *= 2;
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			drawVerticalLine(x, y + 1, y1 - 2, borderC);
			drawVerticalLine(x1 - 1, y + 1, y1 - 2, borderC);
			drawHorizontalLine(x + 2, x1 - 3, y, borderC);
			drawHorizontalLine(x + 2, x1 - 3, y1 - 1, borderC);
			drawHorizontalLine(x + 1, x + 1, y + 1, borderC);
			drawHorizontalLine(x1 - 2, x1 - 2, y + 1, borderC);
			drawHorizontalLine(x1 - 2, x1 - 2, y1 - 2, borderC);
			drawHorizontalLine(x + 1, x + 1, y1 - 2, borderC);
			float f = (float)(col1 >> 24 & 0xFF) / 255F;
			float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
			float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
			float f3 = (float)(col1 & 0xFF) / 255F;

			float f4 = (float)(col2 >> 24 & 0xFF) / 255F;
			float f5 = (float)(col2 >> 16 & 0xFF) / 255F;
			float f6 = (float)(col2 >> 8 & 0xFF) / 255F;
			float f7 = (float)(col2 & 0xFF) / 255F;

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glShadeModel(GL11.GL_SMOOTH);

			GL11.glPushMatrix();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor4f(f1, f2, f3, f);
			GL11.glVertex2d(x1, y);
			GL11.glVertex2d(x, y);

			GL11.glColor4f(f5, f6, f7, f4);
			GL11.glVertex2d(x, y1);
			GL11.glVertex2d(x1, y1);
			GL11.glEnd();
			GL11.glPopMatrix();

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
			GL11.glShadeModel(GL11.GL_FLAT);
			GL11.glScalef(2F, 2F, 2F);
		}
	 

	public static void drawBorderedRectChat(int var0, int var1, int var2, int var3, int var4, int var5, int var6)
	{
		Gui.drawRect(var0 + var4, var1 + var4, var2 - var4, var3 - var4, var6);
		Gui.drawRect(var0 + var4, var1 + var4, var2, var1, var5);
		Gui.drawRect(var0, var1, var0 + var4, var3, var5);
		Gui.drawRect(var2, var3, var2 - var4, var1 + var4, var5);
		Gui.drawRect(var0, var3 - var4, var2, var3, var5);
	}
	
	public static void drawWBorderedGradientRect(int x, int y, int x1, int y1, int size, int borderC, int insideC1, int insideC2) {
        drawVerticalLine(x, y, y1 -1, borderC);
        drawVerticalLine(x1 - 1, y, y1 - 1, borderC);
        drawHorizontalLine(x + 1, x1 - 2, y, borderC);
        drawHorizontalLine(x + 1, x1 - 2, y1 -1, borderC);
        drawGradientRect(x + size, y + size, x1 - size, y1 - size, insideC1, insideC2);
    }

	public static void drawRoundedRect(int x, int y, int x1, int y1, int borderC, int insideC)
	{
		x *= 2; x1 *= 2; y *= 2; y1 *= 2;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
		drawVerticalLine(x, y + 1, y1 -2, borderC);
		drawVerticalLine(x1 - 1, y + 1, y1 - 2, borderC);
		drawHorizontalLine(x + 2, x1 - 3, y, borderC);
		drawHorizontalLine(x + 2, x1 - 3, y1 -1, borderC);
		drawHorizontalLine(x + 1, x + 1, y + 1, borderC);
		drawHorizontalLine(x1 - 2, x1 - 2, y + 1, borderC);
		drawHorizontalLine(x1 - 2, x1 - 2, y1 - 2, borderC);
		drawHorizontalLine(x + 1, x + 1, y1 - 2, borderC);
		//drawGradientRect(x,y,x1,y,insideC,insideC2);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public void drawBaseRect(int i, int j, int k, int l, int i1, int j1, boolean flag)
	{
		if (flag)
		{
			drawRect(i + 1, j + 1, k, l, i1);
			drawHorizontalLine(i, k, j, j1);
			drawVerticalLine(i, j, l, j1);
			drawVerticalLine(k, j, l, j1);
			drawHorizontalLine(i, k, l, j1);
		}
		else
		{
			drawRect(i + 2, j + 2, k - 1, l - 1, i1);
			drawHorizontalLine(i + 2, k - 2, j + 1, j1);
			drawVerticalLine(i + 1, j + 1, l - 1, j1);
			drawVerticalLine(k - 1, j + 1, l - 1, j1);
			drawHorizontalLine(i + 2, k - 2, l - 1, j1);
		}
	}

	public void drawButtonRects(float x, float y, float x2, float y2, boolean var1) {
		GuiHelper.drawHollowBorderedRect(x, y, x2, y2, 0xFF13161D);
		GuiHelper.drawHollowBorderedRectCustom(x + 0.5F, y + 0.5F, x2 - 1, y2 - 1, var1 ? 0xFF1A587E : 0xFF4C4C4C, false);
		GuiHelper.drawHollowBorderedRectCustom(x + 1, y + 1, x2 - 0.5F, y2 - 0.5F, var1 ? 0xFF093B5B : 0xFF2E2E2E, true);
		//this.drawRect(x + 1F, y2 - 1, x + 1.5F, y2 - 2F, 0xFF400000);
		GuiHelper.drawGradientRect(x + 1, y + 1, x2 - 1, y2 - 1, var1 ? 0xFF1A587E : 0xFF3F3F3F, var1 ? 0xFF093B5B : 0xFF292929);
	}

	public void drawTitleRect(int x, int y, int x1, int y1, int borderC, int insideC, int insideC2, boolean flag)
	{
		int size = 1;
		x *= 2; x1 *= 2; y *= 2; y1 *= 2;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawRect(x+size, y+size, x1, y, borderC);
		drawRect(x, y, x+size, y1, borderC);
		drawRect(x1, y1, x1-size, y+size, borderC);
		if(!flag)
		{
			drawRect(x, y1-size, x1, y1 + 1, borderC);
		}
		drawGradientRect(x + 1, y + 1, x1 - 1, y1, insideC, insideC2);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public void drawBorderedRect(int i, int j, int k, int l, int i1, int j1, boolean flag)
	{
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawBaseRect(i * 2, j * 2, k * 2, l * 2, i1, j1, flag);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public void drawButtonRect(int var1, int var2, int var3, int var4, int var5)
	{
		var1 *= 2;
		var3 *= 2;
		var2 *= 2;
		var4 *= 2;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawRect(var1 + 1, var2, var3 - 1, var2 + 1, var5);
		drawRect(var1, var2 + 1, var1 + 1, var4 - 1, var5);
		drawRect(var1 + 1, var4, var3 - 1, var4 - 1, var5);
		drawRect(var3, var2 + 1, var3 - 1, var4 - 1, var5);
		drawRect(var1 + 1, var2 + 1, var3 - 1, var4 - 1, var5);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public static void drawBorderedGradientRect(int i, int j, int k, int l, int i1, int j1, int k1)
	{
		drawGradientRect(i, j, k, l, j1, k1);
		drawVerticalLine(i, j, l - 1, i1);
		drawVerticalLine(k - 1, j, l - 1, i1);
		drawHorizontalLine(i, k - 1, j, i1);
		drawHorizontalLine(i, k - 1, l - 1, i1);
	}
	
	public static void drawSmallCenteredString(String var1, int var2, int var3, int var4) {
		glPushMatrix();
		glScalef(0.5F, 0.5F, 0.5F);
		drawCenteredString(var1, var2 * 2, var3 * 2, var4);
		glPopMatrix();
	}

	public static void enableDefaults() {
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);

		glPushMatrix();
	}

	public static void disableDefaults() {
		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
	}

	public static void drawRect(float x, float y, float x2, float y2, int col1) {
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		enableDefaults();
		glColor4f(f1, f2, f3, f);
		glBegin(GL_QUADS);
		glVertex2d(x2, y);
		glVertex2d(x, y);
		glVertex2d(x, y2);
		glVertex2d(x2, y2);
		glEnd();
		disableDefaults();
	}

	public static void drawGradientRect(float par1, float par2, float par3, float par4, int par5, int par6) {
		float var7 = (float)(par5 >> 24 & 255) / 255.0F;
		float var8 = (float)(par5 >> 16 & 255) / 255.0F;
		float var9 = (float)(par5 >> 8 & 255) / 255.0F;
		float var10 = (float)(par5 & 255) / 255.0F;
		float var11 = (float)(par6 >> 24 & 255) / 255.0F;
		float var12 = (float)(par6 >> 16 & 255) / 255.0F;
		float var13 = (float)(par6 >> 8 & 255) / 255.0F;
		float var14 = (float)(par6 & 255) / 255.0F;
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDisable(GL_ALPHA_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glShadeModel(GL_SMOOTH);
		Tessellator var15 = Tessellator.instance;
		var15.startDrawingQuads();
		var15.setColorRGBA_F(var8, var9, var10, var7);
		var15.addVertex((double)par3, (double)par2, (double)0);
		var15.addVertex((double)par1, (double)par2, (double)0);
		var15.setColorRGBA_F(var12, var13, var14, var11);
		var15.addVertex((double)par1, (double)par4, (double)0);
		var15.addVertex((double)par3, (double)par4, (double)0);
		var15.draw();
		glShadeModel(GL_FLAT);
		glDisable(GL_BLEND);
		glEnable(GL_ALPHA_TEST);
		glEnable(GL_TEXTURE_2D);
	}

	public static void drawCircle(int xx, int yy, float g, int col) {
		float f = (float)(col >> 24 & 0xFF) / 255F;
		float f1 = (float)(col >> 16 & 0xFF) / 255F;
		float f2 = (float)(col >> 8 & 0xFF) / 255F;
		float f3 = (float)(col & 0xFF) / 255F;

		int sections = 70;
		double dAngle = 2 * Math.PI / sections;
		float x, y;

		glPushMatrix();
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_POLYGON_SMOOTH);
		glShadeModel(GL_SMOOTH);
		glBegin(GL_LINE_LOOP);

		for(int i = 0; i < sections; i++) {
			x = (float)(g * Math.cos(i * dAngle));
			y = (float)(g * Math.sin(i * dAngle));

			glColor4f(f1, f2, f3, f);
			glVertex2f(xx + x, yy + y);
		}

		glEnd();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
		glPopMatrix();
	}

	public static void drawFilledCircle(float xx, float yy, float radius, int col) {
		float f = (float)(col >> 24 & 0xFF) / 255F;
		float f1 = (float)(col >> 16 & 0xFF) / 255F;
		float f2 = (float)(col >> 8 & 0xFF) / 255F;
		float f3 = (float)(col & 0xFF) / 255F;

		int sections = 50;
		double dAngle = 2 * Math.PI / sections;
		float x, y;

		glPushMatrix();
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_POLYGON_SMOOTH);
		glBegin(GL_TRIANGLE_FAN);

		for(int i = 0; i < sections; i++) {
			x = (float)(radius * Math.sin((i * dAngle)));
			y = (float)(radius * Math.cos((i * dAngle)));

			glColor4f(f1, f2, f3, f);
			glVertex2f(xx + x, yy + y);
		}

		glEnd();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
		glPopMatrix();
	}

	public static void drawTriangle(int x, int y, int col) {
		float f = (float)(col >> 24 & 0xFF) / 255F;
		float f1 = (float)(col >> 16 & 0xFF) / 255F;
		float f2 = (float)(col >> 8 & 0xFF) / 255F;
		float f3 = (float)(col & 0xFF) / 255F;

		glPushMatrix();
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_POLYGON_SMOOTH);

		glBegin(GL_TRIANGLES);
		glColor4f(f1, f2, f3, f);
		glVertex2f(x, y + 2);
		glVertex2f(x + 2, y - 2);
		glVertex2f(x - 2, y - 2);
		glEnd();

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
		glPopMatrix();
	}

	public static void drawHorizontalLine(float x, float y, float x2, int hex) {
		if (y < x) {
			float var5 = x;
			x = y;
			y = var5;
		}

		drawRect(x, x2, y + 1, x2 + 1, hex);
	}

	public static void drawVerticalLine(float x, float y, float y2, int hex) {
		if (y2 < y) {
			float var5 = y;
			y = y2;
			y2 = var5;
		}

		drawRect(x, y + 1, x + 1, y2, hex);
	}

	public static void drawBaseBorderedRect(float x, float y, float x2, float y2, int col1, int col2) {
		drawRect(x + 1, y + 1, x2 - 1, y2 - 1, col2);
		drawVerticalLine(x, y, y2 - 1, col1);
		drawVerticalLine(x2 - 1, y, y2 - 1, col1);
		drawHorizontalLine(x, x2 - 1, y, col1);
		drawHorizontalLine(x, x2 - 1, y2 -1, col1);
	}

	public static void drawBaseHollowBorderedRect(float x, float y, float x2, float y2, int col1) {
		drawVerticalLine(x, y, y2 - 1, col1);
		drawVerticalLine(x2 - 1, y, y2 - 1, col1);
		drawHorizontalLine(x, x2 - 1, y, col1);
		drawHorizontalLine(x, x2 - 1, y2 -1, col1);
	}

	public static void drawBaseHollowBorderedRectCustom(float x, float y, float x2, float y2, int col1, boolean var1) {
		drawVerticalLine(x, y, y2 + (var1 ? 0 : 1), col1);
		drawVerticalLine(x2 - 1, y - (var1 ? 2 : 0), y2 - 1, col1);
		drawHorizontalLine(x, x2 - 1, y, col1);
		drawHorizontalLine(x, x2 - 1, y2 -1, col1);
	}

	public static void drawBaseGradientBorderedRect(float x, float y, float x2, float y2, int col1, int col2, int col3) {
		drawGradientRect(x + 1, y + 1, x2 - 1, y2 - 1, col2, col3);
		drawVerticalLine(x, y, y2 - 1, col1);
		drawVerticalLine(x2 - 1, y, y2 - 1, col1);
		drawHorizontalLine(x, x2 - 1, y, col1);
		drawHorizontalLine(x, x2 - 1, y2 -1, col1);
	}

	public static void drawBorderedRect(float x, float y, float x2, float y2, int col1, int col2) {
		glPushMatrix();
		glScalef(0.5F, 0.5F, 0.5F);
		drawBaseBorderedRect(x * 2, y * 2, x2 * 2, y2 * 2, col1, col2);
		glPopMatrix();
	}

	public static void drawHollowBorderedRect(float x, float y, float x2, float y2, int col1) {
		glPushMatrix();
		glScalef(0.5F, 0.5F, 0.5F);
		drawBaseHollowBorderedRect(x * 2, y * 2, x2 * 2, y2 * 2, col1);
		glPopMatrix();
	}

	public static void drawHollowBorderedRectCustom(float x, float y, float x2, float y2, int col1, boolean var1) {
		glPushMatrix();
		glScalef(0.5F, 0.5F, 0.5F);
		drawBaseHollowBorderedRectCustom(x * 2, y * 2, x2 * 2, y2 * 2, col1, var1);
		glPopMatrix();
	}

	public static void drawGradientBorderedRect(float x, float y, float x2, float y2, int col1, int col2, int col3) {
		glPushMatrix();
		glScalef(0.5F, 0.5F, 0.5F);
		drawBaseGradientBorderedRect(x * 2, y * 2, x2 * 2, y2 * 2, col1, col2, col3);
		glPopMatrix();
	}

	public static void drawButtonRect(float x, float y, float x2, float y2, boolean var1) {
		drawHollowBorderedRect(x, y, x2, y2, 0xFF13161D);
		drawHollowBorderedRectCustom(x + 0.5F, y + 0.5F, x2 - 1, y2 - 1, var1 ? 0xFF1A587E : 0xFF4C4C4C, false);
		drawHollowBorderedRectCustom(x + 1, y + 1, x2 - 0.5F, y2 - 0.5F, var1 ? 0xFF093B5B : 0xFF2E2E2E, true);
		drawGradientRect(x + 1, y + 1, x2 - 1, y2 - 1, var1 ? 0xFF014C7D : 0xFF3F3F3F, var1 ? 0xFF013150 : 0xFF292929);
	}

	public static void drawBase(int x, int y, int x1, int y1, float width, boolean shadow, int color) {
		glPushMatrix();
		float f1 = (float)(color >> 24 & 255) / 255.0F;
		float f2 = (float)(color >> 16 & 255) / 255.0F;
		float f3 = (float)(color >> 8 & 255) / 255.0F;
		float f4 = (float)(color & 255) / 255.0F;
		glTranslatef(x, y, 0);
		glColor4f(f2, f3, f4, f1);
		glLineWidth(width);
		glEnable(GL_BLEND);
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_LINE_SMOOTH);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		glEnable(GL13.GL_MULTISAMPLE);
		glBegin(GL_LINES);
		glVertex2i(x, y);
		glVertex2i(x1, y1);
		glEnd();
		if(shadow)
		{
			glColor4f(0f, 0f, 0f, f1*0.75f);
			glBegin(GL_LINES);
			glVertex2f(x- width/2, y);
			glVertex2f(x1- width/2, y1);
			glEnd();
		}
		glDisable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_LINE_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glDisable(GL13.GL_MULTISAMPLE);
		glPopMatrix();
	}


	public static void drawStrip(int x, int y, float width, double angle, float points, float radius, int color) {
		glPushMatrix();
		float f1 = (float)(color >> 24 & 255) / 255.0F;
		float f2 = (float)(color >> 16 & 255) / 255.0F;
		float f3 = (float)(color >> 8 & 255) / 255.0F;
		float f4 = (float)(color & 255) / 255.0F;
		glTranslatef(x, y, 0);
		glColor4f(f2, f3, f4, f1);
		glLineWidth(width);
		glEnable(GL_BLEND);
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_LINE_SMOOTH);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_ALPHA_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		glEnable(GL13.GL_MULTISAMPLE);
		if(angle>0)
		{
			glBegin(GL_LINE_STRIP);
			for( int i=0; i<angle; i++ )
			{
				float a = (float) (i*(angle*Math.PI/points));
				float xc = (float) (Math.cos(a)*radius);
				float yc = (float) (Math.sin(a)*radius);
				glVertex2f(xc, yc);
			}
			glEnd();	
		}
		if(angle<0)
		{
			glBegin(GL_LINE_STRIP);
			for( int i=0; i>angle; i-- )
			{
				float a = (float) (i*(angle*Math.PI/points));
				float xc = (float) (Math.cos(a)*-radius);
				float yc = (float) (Math.sin(a)*-radius);
				glVertex2f(xc, yc);
			}
			glEnd();
		}
		glDisable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_LINE_SMOOTH);
		glEnable(GL_ALPHA_TEST);
		glEnable(GL_DEPTH_TEST);
		glDisable(GL13.GL_MULTISAMPLE);
		glDisable(GL_MAP1_VERTEX_3);
		glPopMatrix();
	}

	public static void drawCheck(int x, int y, float width, int color) {
		glPushMatrix();
		glScalef(0.5F, 0.5F, 0.5F);

		glPushMatrix();
		glRotatef(8F, x, y, 1);
		drawStrip(x * 2, y * 2, width, -18, 1000, 20, color);
		glPopMatrix();

		glPushMatrix();
		glRotatef(-95F, x, y, 1);
		drawStrip(x * 2 - 8, y * 2 + 6, width, -18, 1000, 20, color);
		glPopMatrix();
		glPopMatrix();
	}

	public static void drawyeprect(int x, int y, int x1, int y1, int CO1, int CO2) {
		drawRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2);
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y, CO1);
	}

	public static void drawTopRectb(int x, int y, int x1, int y1, int CO1, int CO2) {
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawyeprect(x * 2, y * 2, x1 * 2, y1 * 2, CO1, CO2);
		GL11.glPopMatrix();
	}

	public static void drawFHorizontalLine(float par1, float par2, float par3, int par4)
	{
		if (par2 < par1)
		{
			float var5 = par1;
			par1 = par2;
			par2 = var5;
		}

		drawFRect(par1, par3, par2 + 1, par3 + 1, par4);
	}

	public static void drawFVerticalLine(float par1, float par2, float par3, int par4)
	{
		if (par3 < par2)
		{
			float var5 = par2;
			par2 = par3;
			par3 = var5;
		}

		drawFRect(par1, par2 + 1, par1 + 1, par3, par4);
	}

	public static void drawRect(float x, float y, float x1, float y1, float r, float g, float b, float a)
	{

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(r, g, b, a);
		drawRect(x, y, x1, y1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	public static void drawRect(float x, float y, float x1, float y1)
	{ 
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x1, y);
		GL11.glVertex2f(x1, y1);
		GL11.glVertex2f(x, y1);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y1);
		GL11.glVertex2f(x1, y1);
		GL11.glVertex2f(x1, y);
		GL11.glVertex2f(x, y);
		GL11.glEnd();
	}

	/**
	 * Draws a solid color rectangle with the specified coordinates and color.
	 */
	public static void drawRect(int par0, int par1, int par2, int par3, int par4)
	{
		int var5;

		if (par0 < par2)
		{
			var5 = par0;
			par0 = par2;
			par2 = var5;
		}

		if (par1 < par3)
		{
			var5 = par1;
			par1 = par3;
			par3 = var5;
		}

		float var10 = (float)(par4 >> 24 & 255) / 255.0F;
		float var6 = (float)(par4 >> 16 & 255) / 255.0F;
		float var7 = (float)(par4 >> 8 & 255) / 255.0F;
		float var8 = (float)(par4 & 255) / 255.0F;
		Tessellator var9 = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(var6, var7, var8, var10);
		var9.startDrawingQuads();
		var9.addVertex((double)par0, (double)par3, 0.0D);
		var9.addVertex((double)par2, (double)par3, 0.0D);
		var9.addVertex((double)par2, (double)par1, 0.0D);
		var9.addVertex((double)par0, (double)par1, 0.0D);
		var9.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawFRect(float par0, float par1, float par2, float par3, int par4)
	{
		float var5;

		if (par0 < par2)
		{
			var5 = par0;
			par0 = par2;
			par2 = var5;
		}

		if (par1 < par3)
		{
			var5 = par1;
			par1 = par3;
			par3 = var5;
		}

		float var10 = (float)(par4 >> 24 & 255) / 255.0F;
		float var6 = (float)(par4 >> 16 & 255) / 255.0F;
		float var7 = (float)(par4 >> 8 & 255) / 255.0F;
		float var8 = (float)(par4 & 255) / 255.0F;
		Tessellator var9 = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(var6, var7, var8, var10);
		var9.startDrawingQuads();
		var9.addVertex((double)par0, (double)par3, 0.0D);
		var9.addVertex((double)par2, (double)par3, 0.0D);
		var9.addVertex((double)par2, (double)par1, 0.0D);
		var9.addVertex((double)par0, (double)par1, 0.0D);
		var9.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawRoundedRect(float par1, float par2, float par3, float par4, int par5)
	{
		par1 *= 2; par3 *= 2; par2 *= 2; par4 *= 2;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		float x = par1; float y = par2; float x1 = par3; float y1 = par4; int borderC = par5;
		drawVerticalLine(x, y + 1, y1 -2, borderC);
		drawVerticalLine(x1 - 1, y + 1, y1 - 2, borderC);
		drawHorizontalLine(x + 2, x1 - 3, y, borderC);
		drawHorizontalLine(x + 2, x1 - 3, y1 -1, borderC);
		drawHorizontalLine(x + 1, x + 1, y + 1, borderC);
		drawHorizontalLine(x1 - 2, x1 - 2, y + 1, borderC);
		drawHorizontalLine(x1 - 2, x1 - 2, y1 - 2, borderC);
		drawHorizontalLine(x + 1, x + 1, y1 - 2, borderC);
		drawFRect(x + 1, y + 1, x1 - 1, y1 - 1, par5);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}
	public static void drawHRoundedRect(float par1, float par2, float par3, float par4, int par5)
	{
		par1 *= 2; par3 *= 2; par2 *= 2; par4 *= 2;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		float x = par1; float y = par2; float x1 = par3; float y1 = par4; int borderC = par5;
		drawVerticalLine(x, y + 1, y1 -2, borderC);
		drawVerticalLine(x1 - 1, y + 1, y1 - 2, borderC);
		drawHorizontalLine(x + 2, x1 - 3, y, borderC);
		drawHorizontalLine(x + 2, x1 - 3, y1 -1, borderC);
		drawHorizontalLine(x + 1, x + 1, y + 1, borderC);
		drawHorizontalLine(x1 - 2, x1 - 2, y + 1, borderC);
		drawHorizontalLine(x1 - 2, x1 - 2, y1 - 2, borderC);
		drawHorizontalLine(x + 1, x + 1, y1 - 2, borderC);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

}