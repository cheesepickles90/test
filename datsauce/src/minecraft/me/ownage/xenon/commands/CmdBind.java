package me.ownage.xenon.commands;



import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;

import org.lwjgl.input.Keyboard;




public class CmdBind extends Command {
	public CmdBind() {
		super("bind");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			if(args[0].equalsIgnoreCase("add")) {
				for(XenonMod xMod: Hacks.hackList) {
					if(xMod.getName().replace(" ", "").equalsIgnoreCase(args[1])) {
						if(Keyboard.getKeyIndex(args[2].toUpperCase()) == 0) {
							Xenon.addChatMessage("Invalid key.");
							return;
						}
						xMod.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
						Xenon.addChatMessage(xMod.getName() + " bound to: " + Keyboard.getKeyName(xMod.getKey()));
						Xenon.getFileManager().saveKeybinds();
						break;
					}
				}
			}
			if(args[0].equalsIgnoreCase("del")) {
				for(XenonMod xMod: Hacks.hackList) {
					if(xMod.getKey() == Keyboard.getKeyIndex(args[1].toUpperCase())) {
						xMod.setKey(0);
						Xenon.addChatMessage("Unbound: " + args[1].toUpperCase());
						Xenon.getFileManager().saveKeybinds();
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
		return "Binds a key to a hack";
	}

	@Override
	public String getSyntax() {
		return "bind add <hack> <key>, bind del <key>";
	}
}
