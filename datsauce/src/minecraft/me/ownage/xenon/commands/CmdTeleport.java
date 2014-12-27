package me.ownage.xenon.commands;

import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.Teleport;

public class CmdTeleport extends Command {
	public CmdTeleport() {
		super("tp");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			double x = Double.parseDouble(args[0]);
			double y = Double.parseDouble(args[1]);
			double z = Double.parseDouble(args[2]);
			Teleport.teleport(x, y, z);
			Xenon.addChatMessage("Teleported to: (" + args[0] + ", " + args[1] + ", " + args[2] + ")");
		} catch(Exception e) {
			e.printStackTrace();
			Xenon.addChatMessage("Usage: .tp <§aX§f> <§aY§f> <§aZ§f>");
		}	
	}

	@Override
	public String getDescription() {
		return "Teleports to coords.";
	}

	@Override
	public String getSyntax() {
		return "tp <x> <y> <z>";
	}
}
