package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;

public class AntiAFK extends Module {

	public long keepalive = 0L;
	public long delay = 1L;
	public long threshold = 0L;
	
	public AntiAFK() {
		super("AntiAFK", ".antiafk", "Jumps every 5 seconds", "Ramisme", "Modes");
	}
	
	@Override
	public void onTick() {
		float delay = (5000);
		keepalive = System.nanoTime() / 1000000L;
		if (Wrapper.getPlayer().onGround
				&& ((keepalive - threshold >= delay) || (threshold == -1))) {
			threshold = System.nanoTime() / 1000000L;
			Helper.handleJump();
		}
	}
}
