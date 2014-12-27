package me.ownage.xenon.gui.windows;

import me.ownage.xenon.gui.elements.XWindow;
import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.util.EnumGuiCategory;

public class WindowPlayer extends XWindow
{
	public WindowPlayer()
	{
		super("Player", 94, 2);
	}
	
	public XWindow init()
	{
		for(XenonMod mod: Hacks.hackList)
		{
			if(mod.getCategory() == EnumGuiCategory.PLAYER)
			{
				addButton(mod);
			}
		}
		return this;
	}
}
