package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.modules.manager.ModuleManager;
import org.emotionalpatrick.colossus.utilities.color.ChatColor;

public class Help extends Module {

	public Help() {
		super("Help", ".help", "Displays all client commands", "Emotional Patrick");
	}
	
	@Override
	public void runCommand(String cmd) {
		if (cmd.equals(getCommand())) {
			String help = ChatColor.GOLD + "Availiable Commands: " + ChatColor.WHITE;
			Colossus.getManager();
			for (Module b : ModuleManager.getModules()) {
				help += (b.getCommand() + ", ");
			}
			Helper.addChat(help
					+ ".keys, .streak, .sh, .ks, .kr, .bcc, .bcr, " +
					".add, .del, .name, .enemy, .wh, .gs, " +
					".ms, .cc, .ytp, .tc, .nfb, .fm, .ct, " +
					".point, .sm, .ss, .ft, .bhs, .lh, .nc, .treload" + ".");
		}
	}
}
