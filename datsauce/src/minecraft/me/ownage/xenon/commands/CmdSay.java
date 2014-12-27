package me.ownage.xenon.commands;

import net.minecraft.src.Packet3Chat;
import me.ownage.xenon.main.Xenon;

public class CmdSay extends Command {
	public CmdSay() {
		super("say");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			String s1 = s.substring(4);
			Xenon.getMinecraft().getSendQueue().addToSendQueue(new Packet3Chat(s1));
		} catch(Exception e) {
			Xenon.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Sends a chat message.";
	}

	@Override
	public String getSyntax() {
		return "say <message>";
	}
}
