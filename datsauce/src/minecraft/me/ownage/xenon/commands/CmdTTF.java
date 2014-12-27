package me.ownage.xenon.commands;

import me.ownage.xenon.hacks.classes.TTFChat;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.CustomFont;

public class CmdTTF extends Command {
	public CmdTTF() {
		super("ttf");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			if(s.equalsIgnoreCase("ttf")) {
				Xenon.getHacks().findMod(TTFChat.class).toggle();
				Xenon.addChatMessage("Toggled TTF.");
				return;
			}
			if(args[0].equalsIgnoreCase("font")) {
				String fontName = s.trim().substring(9);
				Xenon.chatFont = new CustomFont(Xenon.getMinecraft(), fontName, Xenon.chatFont.fSize);
				Xenon.addChatMessage("Set font to: " + fontName);
			}
			if(args[0].equalsIgnoreCase("size")) {
				int size = Integer.parseInt(args[1]);
				Xenon.chatFont = new CustomFont(Xenon.getMinecraft(), Xenon.chatFont.fontName, size);
				Xenon.addChatMessage("Set font size to: " + size);
			}
			if(args[0].equalsIgnoreCase("reset")) {
				Xenon.chatFont = new CustomFont(Xenon.getMinecraft(), "Verdana Bold", 17);
				Xenon.addChatMessage("Chat font reset.");
			}
		} catch(Exception e) {
			Xenon.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Custom TTF options.";
	}

	@Override
	public String getSyntax() {
		return "ttf font <font name>, ttf size <size>, ttf reset";
	}

}
