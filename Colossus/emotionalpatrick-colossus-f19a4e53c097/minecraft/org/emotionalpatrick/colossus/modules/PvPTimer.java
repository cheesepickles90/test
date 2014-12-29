package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.Packet255KickDisconnect;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

public class PvPTimer extends Module {

	public int logHearts = 3;
	
	public PvPTimer() {
		super("PvPTimer", ".pvptimer", "Combat logs for you", "Emotional Patrick", 0xff0000, Keyboard.KEY_NONE);
	}
	
	@Override
	public void onTick() {
		if (Wrapper.getPlayer().getHealth() <= this.getLogHearts()) {
			Wrapper.getMinecraft().getSendQueue().quitWithPacket(new Packet255KickDisconnect("disconnect.endOfStream"));
			this.toggle();
			Helper.addConsole("PvPTimer toggled off.");
			Helper.addConsole("PvP logged at " + Wrapper.getPlayer().getHealth() + " heart(s) left.");
		}
	}
	
	@Override
	public void externalCommand (String s) {
		if (s.startsWith(".lh")) {
			String[] args = s.split(" ");
			try {
				int logHearts = Integer.parseInt(args[1]);
				this.setLogHearts(logHearts);
				Helper.addChat("PvPTimer set to log out at (" + logHearts + ") heart(s).");
			} catch (Exception err) {
				Helper.addChat("Invalid syntax, .lh (log hearts left)");
			}
		}
	}

	public int getLogHearts() {
		return logHearts;
	}

	public void setLogHearts(int logHearts) {
		this.logHearts = logHearts;
	}
}
