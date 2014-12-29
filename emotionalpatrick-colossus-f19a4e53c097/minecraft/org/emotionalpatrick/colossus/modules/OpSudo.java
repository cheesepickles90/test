package org.emotionalpatrick.colossus.modules;

import java.util.ArrayList;

import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.StringUtils;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;

public class OpSudo extends Module {

	public OpSudo() {
		super("OpSudo", ".opsudo", "Forces players to run commands", "Emotional Patrick");
	}

	@Override
	public void runCommand (String s ) {}

	@Override
	public void externalCommand (String s) {
		if (s.startsWith(".opsudo ")) {
			try {
				for (GuiPlayerInfo playerInfo : (ArrayList<GuiPlayerInfo>) Wrapper.getMinecraft().getSendQueue().playerInfoList) {
					String n = StringUtils.stripControlCodes(playerInfo.name);
					String c = s.substring(8);
					Helper.addConsole("/sudo dong " + c);
					if ((!n.equalsIgnoreCase(Wrapper.getPlayer().username)) && (!Helper.isFriend(n))) {
						Helper.sendChat("/sudo " + n + " " + c);
					}
				}
			} catch (Exception err) {
				Helper.addChat("Invalid syntax, .opsudo (command)");
			}
		}
	}
}
