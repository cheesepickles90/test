package me.ownage.xenon.hacks.classes;



import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.Value;

import org.lwjgl.input.Keyboard;




public class SpeedMine extends XenonMod
{
	public static Value mineSpeed = new Value("Mine Speed");
	
	public SpeedMine()
	{
		super("Speed Mine", "Speeds up mining.", Keyboard.KEY_H, 0xE0AA38, EnumGuiCategory.WORLD);
	}
}
