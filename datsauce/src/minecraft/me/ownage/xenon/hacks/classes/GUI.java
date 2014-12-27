package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.gui.XenonGuiClick;
import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.EnumGuiCategory;

import org.lwjgl.input.Keyboard;

public class GUI extends XenonMod {
	public GUI() {
		super("GUI", "", Keyboard.KEY_UP, EnumGuiCategory.NONE);
	}
	
	@Override
	public void onToggled() {
		mc.displayGuiScreen(Xenon.getClickGui());
	}
}
