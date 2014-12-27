package me.ownage.xenon.gui.windows;

import me.ownage.xenon.gui.elements.XWindow;
import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.util.EnumGuiCategory;

public class WindowWorld extends XWindow
{
	public WindowWorld()
	{
		super("World", 186, 2);
	}
	
	public XWindow init()
	{
		for(XenonMod mod: Hacks.hackList)
		{
			if(mod.getCategory() == EnumGuiCategory.WORLD)
			{
				addButton(mod);
			}
		}
		return this;
	}
}
