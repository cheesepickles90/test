package org.emotionalpatrick.colossus.modules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.StringUtils;

import org.emotionalpatrick.colossus.main.Helper;

public class Killstreaks extends Module {

	public int killStreak;
	public int goal = 69;

	public boolean resetOnDeath = true;
	public boolean resetOnDisconnect = false;

	public Killstreaks() {
		super("Killstreaks", ".killstreaks", "Client side killstreaks", "Emotional Patrick");
	}

	@Override
	public void onRenderIngame(FontRenderer fr) {
		int color = 0xffffff;
		switch (killStreak) {
		case 10:
			color = 0xff0000;
			break;
		case 20:
			color = 0xFF4500;
			break;
		case 25:
			color = 0xFFFF00;
			break;
		case 30:
			color = 0xD2691E;
			break;
		case 40:
			color = 0x008B8B;
			break;
		case 50:
			color = 0xA9A9A9;
			break;
		case 60:
			color = 0x8B0000;
			break;
		case 70:
			color = 0x6495ED;
			break;
		case 80:
			color = 0x00008B;
			break;
		case 90:
			color = 0x0000FF;
			break;
		case 100:
			color = 0x32CD32;
			break;
		}
		
		if (goal != 0 && killStreak == goal) {
			color = 0x48D1CC;
		}
		
		fr.drawStringWithShadow("Killstreak: " + getKillStreak(), 2, 12, color);
	}

	@Override
	public boolean onRecieveChat(Packet3Chat c) {
		String m = StringUtils.stripControlCodes(c.message);
		
		Matcher matcher = Pattern.compile("You gained (.*?) experience for killing (.*?)!").matcher(m);
		if (matcher.find()) {
			killStreak++;
			Helper.addConsole("Experience Received: " + matcher.group(1) + " Killed player: " + matcher.group(2));
		}
		
		Matcher matcher2 = Pattern.compile("You recieved (.*?) credits for killing (.*?)").matcher(m);
		if (matcher2.find()) {
			killStreak++;
			Helper.addConsole("Credits Received: " + matcher2.group(1) + " Killed player: " + matcher2.group(2));
		}
		
		Matcher matcher3 = Pattern.compile("You are rewarded (.*?) for killing the Player (.*?)").matcher(m);
		if (matcher3.find()) {
			killStreak++;
			Helper.addConsole("Currency Received: " + matcher3.group(1) + " Killed player: " + matcher3.group(2));
		}
		
		Matcher matcher4 = Pattern.compile("You are rewarded (.*?) for killing (.*?)!").matcher(m);
		if (matcher4.find()) {
			killStreak++;
			Helper.addConsole("Currency Received: " + matcher4.group(1) + " Killed player: " + matcher4.group(2));
		}
		
		int mileStone = 10;
		switch (killStreak) {
		case 10:
			mileStone = 10;
			break;
		case 20:
			mileStone = 20;
			break;
		case 25:
			mileStone = 25;
			break;
		case 30:
			mileStone = 30;
			break;
		case 40:
			mileStone = 40;
			break;
		case 50:
			mileStone = 50;
			break;
		case 60:
			mileStone = 60;
			break;
		case 70:
			mileStone = 70;
			break;
		case 80:
			mileStone = 80;
			break;
		case 90:
			mileStone = 90;
			break;
		case 100:
			mileStone = 100;
			break;			
		}
		return true;
	}
	
	@Override
	public void externalCommand(String s) {
		if (s.startsWith(".streak")) {
			String[] args = s.split(" ");
			try {
				if (args[1].equalsIgnoreCase("clear")) {
					setKillStreak(0);
					Helper.addChat("Killstreak cleared!");
				}
				if (args[1].startsWith("goal")) {
					int goal = Integer.parseInt(args[2]);
					setGoal(goal);
					Helper.addChat("Killstreak goal set to (" + goal + ").");
				}
				if (args[1].startsWith("set")) {
					int streak = Integer.parseInt(args[2]);
					setKillStreak(streak);
					Helper.addChat("Killstreak current streak set to (" + streak + ").");
				}
				if (args[1].equalsIgnoreCase("death")) {
					resetOnDeath = !resetOnDeath;
					Helper.addChat(resetOnDeath ? "Killstreak will now reset upon death." : "Killstreak will not reset upon death.");
				}
				if (args[1].equalsIgnoreCase("disconnect")) {
					resetOnDisconnect = !resetOnDisconnect;
					Helper.addChat(resetOnDisconnect ? "Killstreak will now reset upon disconnect." : "Killstreak will not reset upon disconnect.");
				}
				if (args[1].equalsIgnoreCase("total")) {
					Helper.addChat("You are on a (" + getKillStreak() + ") killstreak!"); 
				}
				if (args[1].equalsIgnoreCase("gscore")) {
					int g = getGoal() - getKillStreak();
					Helper.addChat("You have (" + g + ") kills left before you reach your goal!");
				}
			} catch (Exception err)	{
				Helper.addChat(".streak (clear/total/set/death/goal/disconnect/gscore)");
			}
		}
	}

	@Override
	public void onDisconnect() {
		if (resetOnDisconnect) {
			setKillStreak(0);
			Helper.addConsole("Killstreak reset because you disconnected!");
		}
	}

	@Override
	public void onRespawn() {
		if (killStreak != goal || killStreak < goal) {
			Helper.addChat("You did not reach your goal and you died? Failure...");
		}
		Helper.addChat("You died on a killstreak of " + killStreak + ". Noob.");
		if (resetOnDeath) {
			setKillStreak(0);
			Helper.addChat("Killstreak reset due to player death!");
		}
	}
	
	public int getKillStreak() {
		return killStreak;
	}

	public void setKillStreak(int killStreak) {
		this.killStreak = killStreak;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}
}