package me.ownage.xenon.gui.windows;

import me.ownage.xenon.gui.elements.XWindow;
import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.util.EnumGuiCategory;

public class WindowRender extends XWindow
{
	public WindowRender()
	{
		super("Render", 278, 2);
	}
	
	public XWindow init()
	{
		for(XenonMod mod: Hacks.hackList)
		{
			if(mod.getCategory() == EnumGuiCategory.ESP)
			{
				addButton(mod);
			}
		}
		return this;
	}
}
