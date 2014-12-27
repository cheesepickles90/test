package me.ownage.xenon.altmanager;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.Tessellator;

public class SlotAlt extends GuiSlot
{
	private GuiAltList aList;
	private int selected;
	
	public SlotAlt(Minecraft theMinecraft, GuiAltList aList)
	{
		super(theMinecraft, aList.width, aList.height, 32, aList.height - 59, Manager.slotHeight);
		this.aList = aList;
		this.selected = 0;
	}
	
	@Override
	protected int getContentHeight()
	{
		return this.getSize() * Manager.slotHeight;
	}
	
	@Override
	protected int getSize()
	{
		return Manager.altList.size();
	}

	@Override
	protected void elementClicked(int var1, boolean var2)
	{
		this.selected = var1;
	}

	@Override
	protected boolean isSelected(int var1)
	{
		return this.selected == var1;
	}
	
	protected int getSelected()
	{
		return this.selected;
	}

	@Override
	protected void drawBackground()
	{
		aList.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int selectedIndex, int x, int y, int var4, Tessellator var5)
	{
		try
		{
			Alt theAlt = Manager.altList.get(selectedIndex);
			aList.drawString(aList.getLocalFontRenderer(), theAlt.getUsername(), x, y + 1, 0xFFFFFF);
			if(theAlt.isPremium())
			{
				aList.drawString(aList.getLocalFontRenderer(), Manager.makePassChar(theAlt.getPassword()), x, y + 12, 0x808080);
			}else
			{
				aList.drawString(aList.getLocalFontRenderer(), "N/A", x, y + 12, 0x808080);
			}
		}catch(AccountManagementException error)
		{
			error.printStackTrace();
		}
	}

}
