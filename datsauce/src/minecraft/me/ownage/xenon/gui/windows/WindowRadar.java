package me.ownage.xenon.gui.windows;

import me.ownage.xenon.gui.elements.XButton;
import me.ownage.xenon.gui.elements.XSlider;
import me.ownage.xenon.gui.elements.XWindow;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.Friend;
import me.ownage.xenon.util.XenonUtils;
import net.minecraft.src.EntityPlayer;

public class WindowRadar extends XWindow {
	public WindowRadar() {
		super("Radar", 2, 104);
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
				int rect = 0;
				for(Object o: Xenon.getMinecraft().theWorld.playerEntities) {
					EntityPlayer e = (EntityPlayer) o;
					if(e != Xenon.getMinecraft().thePlayer && !e.isDead) {
						rect += 10;
					}
				}
				
				XenonUtils.drawGradientBorderedRect(getX() + dragX, getY() + 14 + dragY, getX() + 90 + dragX, getY() + rect + 14 + dragY, 0.5F, 0xFF666666, 0xCF999999, 0xCF7A7A7A);
				
				int count = 0;
				for(Object o: Xenon.getMinecraft().theWorld.playerEntities)
				{
					EntityPlayer e = (EntityPlayer) o;
					if(e != Xenon.getMinecraft().thePlayer && !e.isDead)
					{
						int distance = (int)Xenon.getMinecraft().thePlayer.getDistanceToEntity(e);
						String text = "";
						if(distance <= 20)
						{
							text = "\247c" + e.username + "\247f: " + (int)distance;
						}else
						if(distance <= 50 && distance > 20)
						{
							text = "\2476" + e.username + "\247f: " + (int)distance;
						}else
						if(distance > 50)
						{
							text = "\247a" + e.username + "\247f: " + (int)distance;
						}
						for(Friend friend: Xenon.getFriends().friendsList)
						{
							text = text.replace(friend.getName(), friend.getAlias());
						}
						int xPosition = getX() + 2 + dragX;
						int yPosition = getY() + (10 * count) + 13 + dragY;
						XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, text, xPosition, yPosition, 0xFFFFFF);
						count++;
					}
				}
				
				if(rect == 0 && count == 0)
				{
					XenonUtils.drawGradientBorderedRect(getX() + dragX, getY() + 14 + dragY, getX() + 90 + dragX, getY() + 24.5 + dragY, 0.5F, 0xFF666666, 0xCF999999, 0xCF7A7A7A);
					XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, "No players in range.", getX() + 2 + dragX, getY() + 13 + dragY, 0xFFFFFF);
				}
				
				for(XButton button: buttons)
				{
					button.draw();
				}
				
				for(XSlider slider: sliders)
				{
					slider.draw(x);
				}
			}
		}
	}
}
