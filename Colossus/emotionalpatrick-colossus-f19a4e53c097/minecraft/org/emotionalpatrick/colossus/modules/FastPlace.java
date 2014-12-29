package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.utilities.values.Values;
import org.lwjgl.input.Keyboard;

public class FastPlace extends Module {

	public FastPlace() {
		super("FastPlace", ".fastplace", "Place at a greater speed", "Emotional Patrick", 13383026, Keyboard.KEY_P, "Modes");
	}
	
	@Override
	public int onRightClick(int i) {
		return (int) Values.fastPlace.getValue();
	}
}
