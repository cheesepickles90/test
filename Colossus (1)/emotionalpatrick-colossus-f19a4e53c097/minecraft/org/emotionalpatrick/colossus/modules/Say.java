package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Helper;

public class Say extends Module {

	public Say() {
		super("Say", ".say", "Sends any chat message", "Emotional Patrick");
	}
	
	@Override
	public void runCommand (String s) {}
	
	@Override
	public void externalCommand(String s) {
		if (s.startsWith(".say ")) {
			try {
				Helper.sendChat(s.substring(5));
			} catch (Exception e) {
				Helper.addChat("Invalid syntax, .say (message)");
			}
		}
	}
}
