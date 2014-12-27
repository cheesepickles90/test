package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;

public class AuraPlayer extends XenonMod
{
	public AuraPlayer()
	{
		super("Players", "Players hit mode for Kill Aura.", EnumGuiCategory.AURA);
		setState(true);
	}
}
