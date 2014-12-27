package me.ownage.xenon.hacks.classes;



import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;
import net.minecraft.src.EntityPlayerSP;

import org.lwjgl.input.Keyboard;




public class NoFall extends XenonMod
{
	public NoFall()
	{
		super("NoFall", "Avoid fall damage.", Keyboard.KEY_N, 0x00FF00, EnumGuiCategory.PLAYER);
	}
}
