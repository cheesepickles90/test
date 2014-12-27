package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;

import org.lwjgl.input.Keyboard;

public class Sneak extends XenonMod
{
	public Sneak()
	{
		super("Sneak", "Always sneaking server-side.", Keyboard.KEY_Z, 0x3399FF, EnumGuiCategory.PLAYER);
	}
}
