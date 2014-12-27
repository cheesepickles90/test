package me.ownage.xenon.hacks.classes;

import org.lwjgl.input.Keyboard;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;

public class NameTags extends XenonMod {
	public NameTags() {
		super("NameTags", "Makes nametags larger or smaller depending on distance.", Keyboard.KEY_U, 0x99CC00, EnumGuiCategory.ESP);
	}
}
