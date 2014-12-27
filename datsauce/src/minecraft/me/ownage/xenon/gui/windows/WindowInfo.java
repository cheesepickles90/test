package me.ownage.xenon.gui.windows;

import me.ownage.xenon.gui.elements.XButton;
import me.ownage.xenon.gui.elements.XWindow;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.XenonUtils;

public class WindowInfo extends XWindow
{
	public WindowInfo() 
	{
		super("Info", 278, 85 + 13);
	}
	
	@Override
	public void draw(int x, int y)
	{
		if(isOpen())
		{
			if(dragging)
			{
				windowDragged(x, y);
			}
			
			XenonUtils.drawGradientBorderedRect(getX() + dragX, getY() + dragY, getX() + 90 + dragX, getY() + 12 + dragY, 0.5F, 0xFF666666, 0xCF999999, 0xCF777777);
			XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, getTitle(), getX() + 2.5 + dragX, getY() + dragY, 0xFFFFFFFF);
			
			XenonUtils.drawGradientBorderedRect(getX() + 70 + dragX, getY() + 2 + dragY, getX() + 78 + dragX, getY() + 10 + dragY, 1F, 0xFF666666, isPinned() ? 0xFF777777 : 0xFF888888, isPinned() ? 0xFF555555 : 0xFF666666);
			XenonUtils.drawGradientBorderedRect(getX() + 80 + dragX, getY() + 2 + dragY, getX() + 88 + dragX, getY() + 10 + dragY, 1F, 0xFF666666, isExtended() ? 0xFF777777 : 0xFF888888, isExtended() ? 0xFF555555 : 0xFF666666);
			
			if(isExtended())
			{
				XenonUtils.drawGradientBorderedRect(getX() + dragX, getY() + 14 + dragY, getX() + 90 + dragX, getY() + 64 + dragY, 0.5F, 0xFF666666, 0xCF999999, 0xCF7A7A7A);
				
				XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, Xenon.getMinecraft().debug.split(",")[0].toUpperCase(), getX() + 2 + dragX, getY() + 13 + dragY, 0xFFFFFF);
				XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, "X: " + (int)Xenon.getMinecraft().thePlayer.posX, getX() + 2 + dragX, getY() + 23 + dragY, 0xFFFFFF);
				XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, "Y: " + (int)Xenon.getMinecraft().thePlayer.posY, getX() + 2 + dragX, getY() + 33 + dragY, 0xFFFFFF);
				XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, "Z: " + (int)Xenon.getMinecraft().thePlayer.posZ, getX() + 2 + dragX, getY() + 43 + dragY, 0xFFFFFF);
				XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, Xenon.getMinecraft().session.username, getX() + 2 + dragX, getY() + 53 + dragY, 0xFFFFFF);
				
				for(XButton button: buttons)
				{
					button.draw();
				}	
			}
		}
	}
}
