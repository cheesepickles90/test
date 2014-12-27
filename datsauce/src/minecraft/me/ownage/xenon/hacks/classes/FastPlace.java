package me.ownage.xenon.hacks.classes;

import net.minecraft.src.EntityPlayerSP;

import org.lwjgl.input.Keyboard;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;

public class FastPlace extends XenonMod {
	public FastPlace() {
		super("FastPlace", "Allows you to quickly place blocks.", Keyboard.KEY_SEMICOLON, 0xFF66CC, EnumGuiCategory.WORLD);
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			mc.rightClickDelayTimer = 0;
		}
	}
}
