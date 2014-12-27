package me.ownage.xenon.gui.elements;

import java.util.ArrayList;

import me.ownage.xenon.gui.XenonGuiClick;
import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.Value;
import me.ownage.xenon.util.XenonUtils;

import org.lwjgl.opengl.GL11;

public class XWindow
{
	private String title;
	private int xPos;
	private int yPos;
	
	private boolean isOpen;
	private boolean isExtended;
	private boolean isPinned;
	
	public int dragX;
	public int dragY;
	public int lastDragX;
	public int lastDragY;
	protected boolean dragging;
	
	public ArrayList<XButton> buttons = new ArrayList<XButton>();
	public ArrayList<XSlider> sliders = new ArrayList<XSlider>();
	
	public XWindow(String title, int x, int y)
	{
		this.title = title;
		this.xPos = x;
		this.yPos = y;
		XenonGuiClick.windows.add(this);
		XenonGuiClick.unFocusedWindows.add(this);
	}
	
	public void windowDragged(int x, int y) {
		dragX = x - lastDragX;
		dragY = y - lastDragY;
	}
	
	private int buttonCount = 0, sliderCount = 0;
	
	public void addButton(XenonMod mod) {
		buttons.add(new XButton(this, mod, xPos + 2, yPos + (11 * buttons.size()) + 16));
	}
	
	public XSlider addSlider(Value value)
	{
		return addSlider(value, 10.0F);
	}
	
	public XSlider addSlider(Value value, float maxValue)
	{
		return addSlider(value, 0.0F, maxValue, false);
	}
	
	public XSlider addSlider(Value value, float minValue, float maxValue, boolean shouldRound)
	{
		XSlider slider = new XSlider(this, value, xPos + 2, yPos + (18 * sliderCount) + 16, minValue, maxValue, shouldRound);
		sliders.add(slider);
		sliderCount++;
		
		return slider;
	}
	
	public void draw(int x, int y)
	{
		if(isOpen)
		{
			if(dragging)
			{
				windowDragged(x, y);
			}
			
			XenonUtils.drawGradientBorderedRect(xPos + dragX, yPos + dragY, xPos + 90 + dragX, yPos + 12 + dragY, 0.5F, 0xFF666666, 0xCF999999, 0xCF777777);
			XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, title, xPos + 2.5 + dragX, yPos + dragY, 0xFFFFFFFF);
			
			XenonUtils.drawGradientBorderedRect(xPos + 70 + dragX, yPos + 2 + dragY, xPos + 78 + dragX, yPos + 10 + dragY, 1F, 0xFF666666, isPinned ? 0xFF777777 : 0xFF888888, isPinned ? 0xFF555555 : 0xFF666666);
			XenonUtils.drawGradientBorderedRect(xPos + 80 + dragX, yPos + 2 + dragY, xPos + 88 + dragX, yPos + 10 + dragY, 1F, 0xFF666666, isExtended ? 0xFF777777 : 0xFF888888, isExtended ? 0xFF555555 : 0xFF666666);
			
			if(isExtended)
			{
				XenonUtils.drawGradientBorderedRect(xPos + dragX, yPos + 14 + dragY, xPos + 90 + dragX, yPos + (11 * buttons.size() + 17) + (18 * sliders.size()) + dragY, 0.5F, 0xFF666666, 0xCF999999, 0xCF7A7A7A);
				
				for(XButton button: buttons)
				{
					button.draw();
					if(x >= button.getX() + dragX && y >= button.getY() + 1 + dragY && x <= button.getX() + 23 + dragX && y <= button.getY() + 10 + dragY)
					{
						button.isOverButton = true;
					}else
					{
						button.isOverButton = false;
					}
				}
				
				for(XSlider slider: sliders)
				{
					slider.draw(x);
				}
			}
		}
	}
	
	public void mouseClicked(int x, int y, int button)
	{
		for(XButton xButton: buttons)
		{
			xButton.mouseClicked(x, y, button);
		}
		
		for(XSlider slider: sliders)
		{
			slider.mouseClicked(x, y, button);
		}
		
		if(x >= xPos + 80 + dragX && y >= yPos + 2 + dragY && x <= xPos + 88 + dragX && y <= yPos + 10 + dragY)
		{
			Xenon.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			isExtended = !isExtended;
		}
		if(x >= xPos + 70 + dragX && y >= yPos + 2 + dragY && x <= xPos + 78 + dragX && y <= yPos + 10 + dragY)
		{
			Xenon.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			isPinned = !isPinned;
		}
		if(x >= xPos + dragX && y >= yPos + dragY && x <= xPos + 69 + dragX && y <= yPos + 12 + dragY)
		{
			XenonGuiClick.sendPanelToFront(this);
			dragging = true;
			lastDragX = x - dragX;
			lastDragY = y - dragY;
		}
	}
	
	public void mouseMovedOrUp(int x, int y, int b)
	{
		for(XSlider slider: sliders)
		{
			slider.mouseMovedOrUp(x, y, b);
		}
		if(b == 0) {
			dragging = false;
		}
	}
	
	public final String getTitle()
	{
		return this.title;
	}
	
	public final int getX()
	{
		return this.xPos;
	}
	
	public final int getY()
	{
		return this.yPos;
	}
	
	public boolean isExtended()
	{
		return isExtended;
	}
	
	public boolean isOpen()
	{
		return isOpen;
	}
	
	public boolean isPinned()
	{
		return isPinned;
	}
	
	public void setOpen(boolean flag)
	{
		this.isOpen = flag;
	}
	
	public void setExtended(boolean flag)
	{
		this.isExtended = flag;
	}
	
	public void setPinned(boolean flag)
	{
		this.isPinned = flag;
	}
}
