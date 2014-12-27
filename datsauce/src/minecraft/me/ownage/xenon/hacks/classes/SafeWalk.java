package me.ownage.xenon.hacks.classes;

import org.lwjgl.input.Keyboard;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;

public class SafeWalk extends XenonMod {
	public SafeWalk() {
		super("SafeWalk", "Prevents you from falling off of blocks.", Keyboard.KEY_F4, 0x79E8E2, EnumGuiCategory.PLAYER);
	}
}
