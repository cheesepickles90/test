package me.ownage.xenon.main.irc;

import org.jibble.pircbot.User;

import net.minecraft.src.ChatLine;
import me.ownage.xenon.irc.MessageHandler;
import me.ownage.xenon.main.Xenon;

public class XMessageHandler extends MessageHandler {

	@Override
	public void onMessage(String sender, String message) {
		this.onMessage(sender, message, false);
	}

	@Override
	public void onMessage(String sender, String message, boolean act) {
		Xenon.addIRCMessage(sender, message, act);
		Xenon.mops.clear();
		for(User user: Xenon.irc.getUsers(Xenon.ircChannel)) {
			if(user.isOp()) {
				Xenon.mops.add(user.getNick());	
			}
		}
	}
}
