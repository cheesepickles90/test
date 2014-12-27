package me.ownage.xenon.irc;

import java.util.Arrays;

import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.XenonUtils;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

public class IRCHandler extends PircBot {
	private String server;
	private String username;
	private String ident_passwd;
	private String channel;
	private int port;
	private RemoteCommandHandler rch;
	private MessageHandler mh;
	
	public boolean isConnectedToServer = false;
	
	private boolean isReady = false;
	
	public IRCHandler(String server, int port, String channel, String username, String identPass, RemoteCommandHandler handler, MessageHandler mhandler) {
		this.server = server;
		this.port = port;
		this.channel = channel;
		this.username = username;
		this.ident_passwd = identPass;
		this.rch = handler;
		this.mh = mhandler;
		
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if(message.trim().startsWith("!")) {
			this.rch.onRemoteCommand(message.substring(1), sender);
		} else {
			this.mh.onMessage(sender, message);
		}
	}
	
	public User findUserByName(String username, String channel) {
		User users[] = this.getUsers(channel);
		for(User user: users) {
			if(user.getNick().trim().equalsIgnoreCase(username.trim())) {
				return user;
			}
		}
		return null;
	}
	
	public void onAction(String sender, String login, String hostname, String target, String action) {
		this.mh.onMessage(sender, action, true);
	}
	
	public void connect() {
		try {
			super.setName(username);
			super.connect(server, port);
		}catch(Exception error) {
			error.printStackTrace();
		}
	}
	
	public boolean isChatReady() {
		if(isReady) return true;
		else return isReady = isConnectedToServer && this.getChannels() != null && XenonUtils.stringListContains(Arrays.asList(this.getChannels()), channel);
	}
	
	public void onConnect() {
		isConnectedToServer = true;
	
		if(this.ident_passwd != null && !this.ident_passwd.trim().isEmpty()) this.identify(this.ident_passwd);
		super.joinChannel(this.channel);
	}
	
	public void onDisconnect() {
		isConnectedToServer = false;
		this.isReady = false;
	}
}
