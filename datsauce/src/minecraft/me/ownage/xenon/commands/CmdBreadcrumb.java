package me.ownage.xenon.commands;

import me.ownage.xenon.hacks.classes.Breadcrumb;
import me.ownage.xenon.main.Xenon;

public class CmdBreadcrumb extends Command {
	public CmdBreadcrumb() {
		super("breadcrumb");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			if(args[0].equalsIgnoreCase("clear")) {
				Breadcrumb.positionsList.clear();
				Xenon.addChatMessage("Cleared breadcrumbs.");
			}
		} catch(Exception e) {}
	}

	@Override
	public String getDescription() {
		return "Clears breadcrumbs.";
	}

	@Override
	public String getSyntax() {
		return "breadcrumb clear";
	}
}
