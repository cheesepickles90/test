package org.emotionalpatrick.colossus.gui.clickable;

import java.text.*;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Tessellator;

import org.emotionalpatrick.colossus.gui.components.GuiHelper;
import org.emotionalpatrick.colossus.main.ColossusWrapper;
import org.emotionalpatrick.colossus.utilities.CustomFont;
import org.emotionalpatrick.colossus.utilities.ModuleValues;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ColossusSlider {
	
	public float sliderValue;

	boolean dragging;

	int x,y,width,height;

	public ModuleValues value;

	float mult=1.0f;

	float plus=1.0f;

	DecimalFormat df;
	
	private CustomFont cf;

	Minecraft mc = ColossusWrapper.getMinecraft();

	float divided;

	boolean isEnabled = true;

	float orMult;

	public static ArrayList sliderList = new ArrayList();

	ColossusPanelBase draggable;

	public ColossusSlider(ModuleValues v, int x, int y, int w, int h, float m, String s, float f, ColossusPanelBase d) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		value = v;
		orMult = m - value.getMinimal();
		mult = m - value.getMinimal();
		df = new DecimalFormat(s);
		divided = f;
		sliderList.add(this);
		draggable = d;
		sliderValue = v.getValue() / m;
		// MainClass.loadOptions();
		setValue(value.getValue());
	}
	
	public ColossusSlider(ModuleValues v, int x, int y, int w, int h, float m, String s, ColossusPanelBase d) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		value = v;
		orMult = m-value.getMinimal();
		mult = m-value.getMinimal();
		df = new DecimalFormat(s);
		divided = -1;
		sliderList.add(this);
		draggable = d;
		sliderValue = v.getValue()/m;
		//MainClass.loadOptions();
		setValue(value.getValue());
	}
	
	public void draw(int xx, int yy, int i, int j)
	{
		if(cf == null) {
			cf = new CustomFont(ColossusWrapper.getMinecraft(), "Tahoma", 17);
		}

		mult = orMult;

		drag(xx, yy, i, j);
		drawSliderBar(draggable.x+8,yy+y);

		if(!isEnabled){
			drawSliderNipple(draggable.x + 2, yy+y, 3);
		}

		if(mc.fontRenderer.getStringWidth(value.getName() + " (\247e" + df.format(value.getValue()) + "\247f)") > width)
			width = mc.fontRenderer.getStringWidth(value.getName() + " (\247e" + df.format(value.getValue()) + "\247f)");

		FontRenderer.drawTTFStringWithShadow(cf,value.getName()/* + " (\247e" + df.format(value.getValue()) + "\247f)"*/, draggable.x+(x+7), draggable.y+(y-12), 0xffffff);
		FontRenderer.drawTTFStringWithShadow(cf,df.format(value.getValue()), draggable.x+(x+81), draggable.y+(y-12), 0xffffff);

		int w = (draggable.width);

		int sliderX = (int)(draggable.x + (sliderValue * (w - 23.5f)));

		ModuleValues v = value;
		GuiHelper.drawWBorderedGradientRect(draggable.x + 9, yy+y + 1,  sliderX + 11, yy+y+height-6,2,0xff,0xFFFF8C00, 0xFFC36900);

		if(mouseAround(sliderX + 3, yy+y, sliderX + 9, yy+y+height, i, j)&&isEnabled||dragging)
			drawSliderNipple(sliderX + 8, yy+y-0.5f, 1);
		else
			drawSliderNipple(sliderX + 8, yy+y-0.5f, 2);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return y + height + 4;
	}

	public void drag(int x, int y, int i, int j) {
		if (dragging && Mouse.isButtonDown(0)) {
			int w = (draggable.width);

			sliderValue = (float) (i - (draggable.x + 6)) / (float) (w - 12);

			if (sliderValue < 0.0F)
				sliderValue = 0.0F;

			if (sliderValue > 1.0F)
				sliderValue = 1.0F;

			value.setValue(getValue(true));
		} else {
			dragging = false;
			sliderValue = (value.getValue() - value.getMinimal()) / mult;
		}
	}
	
	public boolean mouseClicked(int x, int y, int i, int j)
	{
		if(mouseAround(draggable.x + 1,y+this.y, draggable.x+draggable.width - 1, y+this.y+height, i, j)&&isEnabled)
			dragging = true;
		else
			dragging = false;
		return dragging;
	}
	
	public float getValue(boolean f) {
		if (divided != -1) {
			float formatted = sliderValue * mult;
			if (sliderValue * mult % divided != 0)
				return ((sliderValue * mult) - sliderValue * mult % divided + value
						.getMinimal());
			else
				return (sliderValue * mult + value.getMinimal());
		} else
			return sliderValue * mult + value.getMinimal();
	}
	
	public float getMaxValue(boolean f){
		if(divided != -1)
		{
			float formatted = mult;

			if(mult % divided != 0)
				return ((mult)-mult % divided+value.getMinimal());
			else 
				return (mult+value.getMinimal());
		}else

			return mult+value.getMinimal();
	}

	public void setValue(float f) {
		sliderValue = f / mult;
	}

	public boolean mouseAround(int x, int y, int x1, int y1, int i, int j) {
		return i > x && i < x1 && j > y && j < y1;
	}

	public void setUsable(boolean b) {
		isEnabled = b;
	}

	public void drawSliderFill(float x, float y, float f) {
		x *= 2;
		y *= 2;
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glEnable(GL11.GL_BLEND);
		ColossusWrapper.getMinecraft().renderEngine
				.bindTexture(ColossusWrapper.getMinecraft().renderEngine
						.getTexture("/misc/colossus/GuiRedux.png"));
		float xNow = x;
		this.drawTexturedRect(xNow += 5, y, 1, 53, 5, 10);
		xNow += 4;
		for (int i1 = 0; i1 <= f; i1++) {
			this.drawTexturedRect(xNow += 1, y, 6, 53, 1, 10);
		}
		this.drawTexturedRect(xNow, y, 94, 53, 5, 10);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}
	
	public void drawSliderBar(float x, float y) {
		x *= 2;
		y *= 2;
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glEnable(GL11.GL_BLEND);
		ColossusWrapper.getMinecraft().renderEngine
				.bindTexture(ColossusWrapper.getMinecraft().renderEngine
						.getTexture("/misc/colossus/GuiRedux.png"));
		this.drawTexturedRect(x, y, 1, 64, 5, 13);
		this.drawTexturedRect(x + 5, y, 6, 64, 89, 13);
		this.drawTexturedRect(x + 5 + 89, y, 6, 64, 89, 13);
		this.drawTexturedRect(x + 5 + 89 + 89, y, 95, 64, 5, 13);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}
	
	public void drawSliderNipple(float x, float y, int state) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		int uX[] = {1, 19, 37};
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		ColossusWrapper.getMinecraft().renderEngine.bindTexture(ColossusWrapper.getMinecraft().renderEngine.getTexture("/misc/colossus/GuiRedux.png"));
		drawTexturedRect(x * 2,y * 2,uX[state - 1],79,16,16);
		GL11.glScalef(2F, 2F, 2F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	protected float zLevel = 0.0F;
	
	public void drawTexturedRect(float par1, float par2, float par3, float par4, float par5, float par6) {
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(par1 + 0, par2 + par6, this.zLevel, (par3 + 0) * var7, (par4 + par6) * var8);
		var9.addVertexWithUV(par1 + par5, par2 + par6, this.zLevel, (par3 + par5) * var7, (par4 + par6) * var8);
		var9.addVertexWithUV(par1 + par5, par2 + 0, this.zLevel, (par3 + par5) * var7, (par4 + 0) * var8);
		var9.addVertexWithUV(par1 + 0, par2 + 0, this.zLevel, (par3 + 0) * var7, (par4 + 0) * var8);
		var9.draw();
	}
}