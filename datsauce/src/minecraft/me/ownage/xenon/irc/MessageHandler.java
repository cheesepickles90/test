package me.ownage.xenon.irc;

public abstract class MessageHandler {
	public abstract void onMessage(String sender, String message);
	public abstract void onMessage(String sender, String message, boolean act);
}
