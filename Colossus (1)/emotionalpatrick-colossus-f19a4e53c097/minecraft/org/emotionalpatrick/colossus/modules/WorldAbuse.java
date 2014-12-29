package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.utilities.color.ChatColor;

public class WorldAbuse extends Module {

	public WorldAbuse() {
		super("World Abuse", ".worldabuse", "Various WorldEdit abuse commands", "Emotional Patrick");
	}
	
	@Override
	public void runCommand (String s) {
		if (s.equalsIgnoreCase(this.getCommand())) {
			Helper.addChat(ChatColor.GOLD + "Availiable WorldAbuse Commands: " + ChatColor.WHITE + ".wterrible, .crater, .chunk" + ".");
		}
	}
	
	@Override
	public void externalCommand (String s) {
		if (s.equalsIgnoreCase(".wterrible")) {
			Helper.sendChat("//set cobblestone,web,lava,sponge,glass,leaves,obsidian,netherrack");
		}
		
		if (s.equalsIgnoreCase(".chunk")) {
			Helper.sendChat("//chunk");
			Helper.sendChat("//expand vert");
			Helper.sendChat("//set 0");
		}
		
		if (s.startsWith(".crater")) {
			String[] args = s.split(" ");
			try {
				int i = Integer.parseInt(args[1]);
				Helper.sendChat("//sphere 0 " + i);
			} catch (Exception err) {
				Helper.addChat("Invalid syntax, .crater (size)");
			}
		}
	}
}
