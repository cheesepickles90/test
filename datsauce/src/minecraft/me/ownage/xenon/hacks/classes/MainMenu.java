package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.hooks.XMainMenu;
import me.ownage.xenon.util.EnumGuiCategory;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;

public class MainMenu extends XenonMod {
	public MainMenu() {
		super("Main Menu", "", EnumGuiCategory.NONE);
	}

	public GuiScreen onDisplayGuiScreen(GuiScreen scr) {
		if(scr instanceof GuiMainMenu) {
			return new XMainMenu();
		} else {
			return scr;
		}
	}
}
