package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

public class AutoSwim extends Module {

	private long keepalive = 0L, threshold = 0L;

	public AutoSwim() {
		super("AutoSwim", ".autoswim", "Automagically swims for you", "Ramisme", 0x008B8B, Keyboard.KEY_NONE, "Misc");
	}

	@Override
	public void onTick() {
		keepalive = System.nanoTime() / 1000000L;
		long delay = (1000 / 9);
		if (Wrapper.getWorld() != null
				&& Wrapper.getPlayer().isInWater()
				&& keepalive - threshold >= delay) {
			threshold = System.nanoTime() / 1000000L;
			Helper.handleJump();
		}
	}
}
