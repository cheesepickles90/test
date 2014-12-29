package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

public class AutoRespawn extends Module {
    
	public AutoRespawn() {
		super("AutoRespawn", ".autorespawn", "Automagically respawns your player", "Emotional Patrick", 0x9370D8, Keyboard.KEY_C);
	}
	
	@Override
	public void onRespawn() {
		Helper.addChat("Player automagically respawned.");
	}

	@Override
	public void onDeath() {
		Wrapper.getPlayer().respawnPlayer();
	}
}
