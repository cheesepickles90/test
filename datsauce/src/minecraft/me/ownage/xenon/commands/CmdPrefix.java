package me.ownage.xenon.commands;

import me.ownage.xenon.main.Xenon;

public class CmdPrefix extends Command {
	public CmdPrefix() {
		super("cmdprefix");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			Xenon.getCommandManager().cmdPrefix = args[0].charAt(0);
			Xenon.addChatMessage("Set command prefix to: " + Xenon.getCommandManager().cmdPrefix);
		} catch(Exception e) {
			Xenon.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Changes command prefix (default: .)";
	}

	@Override
	public String getSyntax() {
		return "cmdprefix <character>";
	}
}
