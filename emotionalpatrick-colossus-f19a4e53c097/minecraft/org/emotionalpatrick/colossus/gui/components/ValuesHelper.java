package org.emotionalpatrick.colossus.gui.components;

import org.emotionalpatrick.colossus.utilities.ModuleValues;

public class ValuesHelper {
	
	public static ModuleValues auraRange = new ModuleValues("Kill Aura Range", 4.2f, 1.0f, 1f, -1f);
	public static ModuleValues auraSpeed = new ModuleValues("Kill Aura APS", 8.0f, 1.0F, 1f, -1f);
	public static ModuleValues worldAlpha = new ModuleValues("World Alpha", (int) 128f, (int) 0f, 1f, -1f);
	public static ModuleValues stepHeight = new ModuleValues("Step Height", 0.5f, 0f, 1f, -1f);
	public static ModuleValues mineSpeed = new ModuleValues("Mine Speed", 2.3f, 0f, 1f, -1f);
	public static ModuleValues speedHack = new ModuleValues("Speed Hack", 1.05f, 0f, 1f, -1f);
	public static ModuleValues fastPlace = new ModuleValues("Fast Place", 0f, 0f, 1f, -1f);
	public static ModuleValues flightSpeed = new ModuleValues("Flight Speed", 0.1f, 0.0010f, 1f, -1f);
}
