package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.StringUtils;

import java.util.ArrayList;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;

public class OpKill extends Module {

	public OpKill() {
		super("OpKill", ".opkill", "Kills all players", "Emotional Patrick");
	}
	
	@Override
	public void runCommand(String cmd) {
		if (cmd.equalsIgnoreCase(this.getCommand())) {
			for (GuiPlayerInfo playerInfo : (ArrayList<GuiPlayerInfo>) Wrapper.getMinecraft().getSendQueue().playerInfoList) {
				String n = StringUtils.stripControlCodes(playerInfo.name);
				if ((!n.equalsIgnoreCase(Wrapper.getPlayer().username)) && (!Helper.isFriend(n))) {
					Helper.sendChat("/kill " + n);
				}
			}
		}
	}
}
