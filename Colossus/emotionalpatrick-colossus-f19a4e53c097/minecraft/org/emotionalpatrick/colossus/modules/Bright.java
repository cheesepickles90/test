package org.emotionalpatrick.colossus.modules;

import org.lwjgl.input.Keyboard;

public class Bright extends Module {
	
	public Bright() {
		super("Bright", ".bright", "Makes the world brighter", "Emotional Patrick", 0xFFFFFF, Keyboard.KEY_B, "World");
	}
}
