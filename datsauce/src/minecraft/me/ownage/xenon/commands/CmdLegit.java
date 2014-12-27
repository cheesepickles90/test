package me.ownage.xenon.commands;

import me.ownage.xenon.hacks.classes.TTFChat;
import me.ownage.xenon.hooks.XGuiIngame;
import me.ownage.xenon.main.Hacks;

public class CmdLegit extends Command {
	public CmdLegit() {
		super("legit");
	}

	@Override
	public void runCommand(String s, String[] args) {
		XGuiIngame.hideGui = !XGuiIngame.hideGui;
		Hacks.findMod(TTFChat.class).setState(!XGuiIngame.hideGui);
	}

	@Override
	public String getDescription() {
		return "Hides Xenon HUD.";
	}

	@Override
	public String getSyntax() {
		return "legit";
	}
}
