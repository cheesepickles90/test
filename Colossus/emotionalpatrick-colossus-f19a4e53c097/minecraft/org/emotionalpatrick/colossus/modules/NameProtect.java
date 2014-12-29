package org.emotionalpatrick.colossus.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMovingObjectType;
import org.emotionalpatrick.colossus.files.FileManagerImpl;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.color.ChatColor;

public class NameProtect extends Module {

	public static ArrayList originalNames = new ArrayList();
	public static ArrayList protectedNames = new ArrayList();
	
	public static ArrayList enemies = new ArrayList();
	public static final File enemiesFile = new File(FileManagerImpl.getColossusDir(), "enemies.txt");

	public NameProtect() {
		super("Name Protect", ".protect", "Friend system with aliases", "Emotional Patrick", "Modes");
		this.enabled = true;		
	}
    
	@Override
	public void onMiddleClick() {
		if (Wrapper.getMinecraft().objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY
				&& Wrapper.getMinecraft().objectMouseOver.typeOfHit != null
				&& Wrapper.getMinecraft().objectMouseOver.entityHit instanceof EntityPlayer) {
			String s = Wrapper.getMinecraft().objectMouseOver.entityHit.getEntityName();
			if (!Helper.isFriend(s)) {
				NameProtect.getOriginalName().add(s);
				NameProtect.getProtectedName().add(s);
			} else {
				NameProtect.getOriginalName().remove(s);
				NameProtect.getProtectedName().remove(s);
			}
			Helper.addChat(Helper.isFriend(s) ? "Added \"" + s + "\"." : "Deleted \"" + s + "\".");
			saveFriends();
		}
	}
	
	@Override
	public String onChatMessage (String par1Str) {
		String s = par1Str;
		for (int protectCheck = 0; protectCheck < NameProtect.originalNames.size(); protectCheck++) {
			String oName = "" + originalNames.get(protectCheck);
			String pName = "" + protectedNames.get(protectCheck);
			s = s.replaceAll(oName,  ChatColor.GOLD + pName + ChatColor.WHITE);
		}
		return s;
	}

	@Override
	public void externalCommand (String s) {
		if (s.startsWith(".enemy")) {
			try {
				String[] args = s.split(" ");
				if (args[1].startsWith(("add"))) {
					String enemyName = args[2];
					if (!enemies.contains(enemyName)) {
						enemies.add(enemyName);
						Helper.addChat("\"" + enemyName + "\" is now an enemy.");
					} else {
						Helper.addChat("\"" + enemyName + "\" is already an enemy.");
					}
					saveEnemies();
				}
				if (args[1].startsWith(("del"))) {
					String enemyName = args[2];
					if (enemies.contains(enemyName)) {
						enemies.remove(enemyName);
						Helper.addChat("\"" + enemyName + "\" is no longer an enemy.");
					} else {
						Helper.addChat("\"" + enemyName + "\" is not an enemy.");
					}
					saveEnemies();
				}
			} catch (Exception err) {
				Helper.addChat("Invalid syntax, .enemy (add/del) (username)");
			}
		}
		
		if (s.startsWith(".add")) {
			try {
				String args[] = s.split(" ");
				String name = args[1];
				String prot = args[2];
				originalNames.add(name);
				protectedNames.add(prot);
				Helper.addChat("Added \"" + prot + "\".");
				saveFriends();
			} catch (Exception err) {
				Helper.addChat("Invalid syntax, .add (name) (protected name)");
			}
		}
		
		if (s.startsWith(".del")) {
			try {
				String args[] = s.split(" ");
				String name = args[1];
				originalNames.remove(name);
				protectedNames.remove(name);
				Helper.addChat("Deleted \"" + name + "\".");
				saveFriends();
			} catch (Exception err) {
				Helper.addChat("Invalid syntax, .del (name)");
			}
		}
		
		if (s.startsWith(".name")) {
			try {
				String args[] = s.split(" ");
				String name = Wrapper.getMinecraft().session.username;
				String prot = args[1];
				originalNames.add(name);
				protectedNames.add(prot);
				Helper.addChat("Added \"" + prot + "\".");
				saveFriends();
			} catch (Exception err) {
				Helper.addChat("Invalid syntax, .name (protected name)");
			}
		}
	}

	public static void loadFriends() throws IOException {
		BufferedReader bufferedreader = new BufferedReader(new FileReader(
				FileManagerImpl.friendsFile));
		if (!FileManagerImpl.friendsFile.exists()) {
			FileManagerImpl.friendsFile.createNewFile();
			Helper.addConsole("Creating protect.txt...");
		}
		for (String s = ""; (s = bufferedreader.readLine()) != null;) {
			try {
				String as[] = s.split(":");
				NameProtect.getOriginalName().add(as[0]);
				NameProtect.getProtectedName().add(as[1]);
				Helper.addConsole("Added \"" + as[0] + "\" as protected username \"" + as[1] + "\".");
			} catch (Exception exception1) {
			}
		}
		bufferedreader.close();
	}

	public void saveFriends() {
		try {
			FileManagerImpl.getInstance().saveFriends();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadEnemies() {
		try {
			if (enemiesFile.exists()) {
				enemies.clear();
				BufferedReader bufferedReader = new BufferedReader(new FileReader(enemiesFile));
				for (String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						enemies.add(s1.trim());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				bufferedReader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			saveEnemies();
		}
	}

	public static void saveEnemies() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(
					enemiesFile));
			for (int i1 = 0; i1 < enemies.size(); i1++) {
				printWriter.println(enemies.get(i1));
			}
			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			saveEnemies();
		}
	}
	
	public static ArrayList getEnemies() {
		return enemies;
	}
	
	public static ArrayList getProtectedName() {
		return protectedNames;
	}

	public static ArrayList getOriginalName() {
		return originalNames;
	}
}
