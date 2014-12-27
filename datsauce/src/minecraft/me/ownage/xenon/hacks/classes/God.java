package me.ownage.xenon.hacks.classes;

import org.lwjgl.input.Keyboard;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;

public class God extends XenonMod {
	public God() {
		super("God", "Invincible. (buggy)", Keyboard.KEY_LBRACKET, 0x8A0808, EnumGuiCategory.PLAYER);
	}
}
