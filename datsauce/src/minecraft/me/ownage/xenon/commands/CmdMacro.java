package me.ownage.xenon.commands;

import org.lwjgl.input.Keyboard;

import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.Macro;

public class CmdMacro extends Command {
	public CmdMacro() {
		super("macro");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			if(args[0].equalsIgnoreCase("add")) {
				if(Keyboard.getKeyIndex(args[1].toUpperCase()) == 0) {
					Xenon.addChatMessage("Invalid key.");
					return;
				}
				Macro m = new Macro(Keyboard.getKeyIndex(args[1].toUpperCase()), s.substring(11 + args[1].length()));
				Xenon.addChatMessage("Added \"" + m.getCommand() + "\" on key: " + Keyboard.getKeyName(m.getKey()));
				Xenon.getFileManager().saveMacros();
			}
			if(args[0].equalsIgnoreCase("del")) {
				if(Keyboard.getKeyIndex(args[1].toUpperCase()) == 0) {
					Xenon.addChatMessage("Invalid key.");
					return;
				}
				for(Macro m : Macro.macroList) {
					if(Keyboard.getKeyName(m.getKey()).equalsIgnoreCase(args[1].toUpperCase())) {
						Macro.macroList.remove(m);
						Xenon.addChatMessage("Removed macro on key: " + Keyboard.getKeyName(m.getKey()));
						Xenon.getFileManager().saveMacros();
						break;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			Xenon.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Adds and removes macros.";
	}

	@Override
	public String getSyntax() {
		return "macro add <key> <command>, macro del <key>";
	}
}
