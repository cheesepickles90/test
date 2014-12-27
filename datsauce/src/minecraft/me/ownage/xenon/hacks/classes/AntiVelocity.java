package me.ownage.xenon.hacks.classes;


import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;

import org.lwjgl.input.Keyboard;



public class AntiVelocity extends XenonMod {
	public AntiVelocity() {
		super("No Knockback", "Blocks velocity from players or mobs.", Keyboard.KEY_L, 0xCC66FF, EnumGuiCategory.AURA);
	}
}
