package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet19EntityAction;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {

	private boolean bypass;
	
	private int bypassTick = 0;
	
	public NoFall() {
		super("NoFall", ".nofall", "Avoids fall damage", "Emotional Patrick", 0x13C422, Keyboard.KEY_G, "Modes");
	}
	
	@Override
	public void externalCommand (String s) {
		if (s.equalsIgnoreCase(".nfb")) {
			bypass = !bypass;
			Helper.addChat(bypass ? "NC+ nofall bypass toggled on." : "NC+ nofall bypass toggled off.");
		}
	}
	
	@Override
	public void onTick() {
		if (!Wrapper.getPlayer().onGround && !bypass) {
			Helper.sendPacket(new Packet10Flying(true));
		}
		
		EntityClientPlayerMP ep = Wrapper.getPlayer();
		if (bypass) {
			if (!ep.onGround && !ep.isInWater() && ep.motionY < 0.0F && ep.fallDistance > 0) {
				ep.motionY -= 2.0;
				if (bypassTick > 30) {
					Helper.sendPacket(new Packet19EntityAction(ep, 3));
					Helper.sendPacket(new Packet10Flying(true));
					bypassTick = 0;
				}
				bypassTick++;
			}
		}
	}
}
