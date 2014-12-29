package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

public class TimeWarp extends Module {

	public TimeWarp() {
		super("Time Warp", ".timewarp", "Speeds up gametime", "Emotional Patrick", 0x00FFFF, Keyboard.KEY_NONE, "Modes");
	}

	@Override
	public void onEnable() {
		Wrapper.getMinecraft().timer.timerSpeed = 2.5F;
	}

	@Override
	public void onDisable() {
		Wrapper.getMinecraft().timer.timerSpeed = 1.0F;
	}
}
