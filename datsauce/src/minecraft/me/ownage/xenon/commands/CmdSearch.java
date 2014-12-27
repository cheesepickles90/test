package me.ownage.xenon.commands;

import me.ownage.xenon.hacks.classes.Search;
import me.ownage.xenon.main.Xenon;

public class CmdSearch extends Command {
	public CmdSearch() {
		super("search");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			if(args[0].equalsIgnoreCase("add")) {
				int id = Integer.parseInt(args[1]);
				Search.espList.add(id);
				Xenon.addChatMessage("Added " + id + " to the search list.");
			}
			if(args[0].equalsIgnoreCase("del")) {
				int id = Integer.parseInt(args[1]);
				if(Search.espList.contains(id)) {
					Search.espList.remove(Search.espList.indexOf(id));
					Xenon.addChatMessage("Removed " + id + " from search.");
				} else {
					Xenon.addChatMessage(id + " is not in the search list.");
				}
			}
			if(args[0].equalsIgnoreCase("clear")) {
				Search.espList.clear();
				Xenon.addChatMessage("Cleared search.");
			}
		} catch(Exception e) {
			Xenon.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Adds and removes blocks from the search list";
	}

	@Override
	public String getSyntax() {
		return "search add/del <block name>, search clear";
	}
}
