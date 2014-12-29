package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.gui.screens.GuiHub;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

public class Gui extends Module {
	
	public Gui() {
		super("GUI", ".gui", "Opens the GUI", "Emotional Patrick", -1, Keyboard.KEY_RSHIFT);
		this.shown = false;
	}
	
	@Override
	public void onToggle() {
		Wrapper.getMinecraft().displayGuiScreen(new GuiHub());
	}
	
	@Override
	public void runCommand(String s) {
		if (s.equalsIgnoreCase(this.getCommand())) {
			Helper.addChat("You can only open the GUI using a keybind!");
		}
	}
}
