package me.ownage.xenon.irc;

public abstract class RemoteCommandHandler {
	public abstract void onRemoteCommand(String command, String sender);
}
