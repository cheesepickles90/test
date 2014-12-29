package org.emotionalpatrick.colossus.modules;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.src.Packet3Chat;
import net.minecraft.src.StringUtils;

import org.emotionalpatrick.colossus.main.Helper;

public class Spam extends Module {

	private long current = 1000L, last = -1L, threshold = 0L;
	
	public String spamText = "le spam xd xd";
	
	public Spam() {
		super("Spam", ".spam", "Spams any text", "Emotional Patrick", "Misc");
	}
	
	@Override
	public boolean onRecieveChat(Packet3Chat c) {
		String message = StringUtils.stripControlCodes(c.message);
		Matcher m = Pattern.compile("Please type '(.*?)' to continue sending messages/commands.").matcher(message);
		if (m.find()) {
			Helper.sendChat(m.group(1));
		}
		return true;
	}
	
	@Override
	public void externalCommand (String s) {
		if (s.startsWith(".sm ")) {
			String t = s.substring(4);
			setSpamText(t);
			Helper.addChat("Spam message set to \"" + t + "\".");
		}
		if (s.startsWith(".ss")) {
			String[] args = s.split(" ");
			try {
				long speed = Long.parseLong(args[1]);
                long d = 1000 / speed;
                setCurrent(d);
    			Helper.addChat("Spam messages per second set to (" + 1000 / d + ").");
			} catch (Exception err) {
				Helper.addChat("Invalid syntax, .ss (messages per second)");
			}
		}
 	}
	
	@Override
	public void onTick() {
		threshold = getCurrentMilliseconds();
		if (threshold - last >= current || last == -1) {
			Helper.sendChat(getSpamText() + "" + (new Random()).nextInt(100));
			last = getCurrentMilliseconds();
		}
	}
	
	@Override
	public void runCommand (String cmd) {
		onToggle();
		Helper.addChat((enabled ? "Started spamming..." : "Stopped spamming."));
	}
	
	private long getCurrentMilliseconds() {
		return System.nanoTime() / 1000000;
	}
	
	public String getSpamText() {
		return spamText;
	}

	public void setSpamText(String spamText) {
		this.spamText = spamText;
	}

	public void setCurrent(long current) {
		this.current = current;
	}
}
