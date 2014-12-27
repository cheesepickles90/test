package me.ownage.xenon.gui.windows;

import me.ownage.xenon.gui.elements.XWindow;
import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.hacks.classes.GUI;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.EnumGuiCategory;

public class WindowAura extends XWindow
{
	public WindowAura()
	{
		super("Kill Aura", 94, 98 + 11);
	}
	
	public XWindow init()
	{
		for(XenonMod mod: Hacks.hackList)
		{
			if(mod.getCategory() == EnumGuiCategory.AURA)
			{
				addButton(mod);
			}
		}
		return this;
	}
}
