package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.Packet19EntityAction;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

public class Sneak extends Module {

	public Sneak() {
		super("Sneak", ".sneak", "Force-sneaks serverside", "Emotional Patrick", 0x009000, Keyboard.KEY_Z, "Modes");
	}
	
	@Override
	public void onEnable() {
		Helper.sendPacket(new Packet19EntityAction(Wrapper.getPlayer(), 1));
	}

	@Override
	public void onDisable() {
		Helper.sendPacket(new Packet19EntityAction(Wrapper.getPlayer(), 2));
	}
}
