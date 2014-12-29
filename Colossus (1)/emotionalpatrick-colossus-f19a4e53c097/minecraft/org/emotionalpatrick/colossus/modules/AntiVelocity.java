package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

import net.minecraft.src.Packet28EntityVelocity;

public class AntiVelocity extends Module {

	public AntiVelocity() {
		super("AntiVelocity", ".ave", "Stops all velocity to the player", "Emotional Patrick", 0xFFFFFF, Keyboard.KEY_NONE, "Kill Aura");
		this.shown = false;
	}
	
	@Override
	public boolean entityVelocity(Packet28EntityVelocity ev) {
		return ev.entityId == Wrapper.getPlayer().entityId ? !this.isEnabled() : true;
	}
}
