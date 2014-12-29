package org.emotionalpatrick.colossus.modules;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.src.StringUtils;

import org.emotionalpatrick.colossus.files.FileManagerImpl;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.ColossusHelper;
import org.emotionalpatrick.colossus.main.ColossusWrapper;
import org.emotionalpatrick.colossus.utilities.ChatColor;

public class Protect extends Module {
	
	public static ArrayList originalNames = new ArrayList();
	public static ArrayList protectedNames = new ArrayList();
	
	public Protect() {
		super("NameProtect", ".protect", "Friend system with aliases", "Emotional Patrick", "Misc");
		this.enabled = true;		
	}
	
	public String onChatMessage (String par1Str) {
		String s = par1Str;
    	if (Colossus.getManager().protect.isEnabled()) {
    		for (int protectCheck = 0; protectCheck < Colossus.getManager().protect.originalNames.size(); protectCheck++) {
    			String oName = "" + Colossus.getManager().protect.originalNames.get(protectCheck);
    			String pName = "" + Colossus.getManager().protect.protectedNames.get(protectCheck);
    			s = s.replaceAll(oName, "\2476" + pName + "\247f");
    		}
    	}
    	return s;
	}
	
	public void externalCommand (String s) {
		if (s.startsWith(".add")) {
			try {
				String args[] = s.split(" ");
				String name = args[1];
				String prot = args[2];
				originalNames.add(name);
				protectedNames.add(prot);
				ColossusHelper.addChat("Added \"" + prot + "\".");
				try {
					FileManagerImpl.getInstance().saveFriends();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception err) {
				ColossusHelper.addChat("Invalid syntax, .add (name) (protected name)");
			}
		}
		if (s.startsWith(".del")) {
			try {
				String args[] = s.split(" ");
				String name = args[1];
				originalNames.remove(name);
				protectedNames.remove(name);
				ColossusHelper.addChat("Deleted \"" + name + "\".");
				try {
					FileManagerImpl.getInstance().saveFriends();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception err) {
				ColossusHelper
						.addChat("Invalid syntax, .del (name)");
			}
		}
		if (s.startsWith(".name")) {
			try {
				String args[] = s.split(" ");
				String name = ColossusWrapper.getMinecraft().session.username;
				String prot = args[1];
				originalNames.add(name);
				protectedNames.add(prot);
				ColossusHelper.addChat("Added \"" + prot + "\".");
				try {
					FileManagerImpl.getInstance().saveFriends();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception err) {
				ColossusHelper
						.addChat("Invalid syntax, .name (protected name)");
			}
		}
	}
	
	
	public static void loadFriends() throws IOException {
		BufferedReader bufferedreader = new BufferedReader(new FileReader(
				FileManagerImpl.friendsFile));
		if (!FileManagerImpl.friendsFile.exists()) {
			FileManagerImpl.friendsFile.createNewFile();
			ColossusHelper.addConsole("Creating protect.txt...");
		}
		for (String s = ""; (s = bufferedreader.readLine()) != null;) {
			try {
				String as[] = s.split(":");
				Colossus.getManager().protect.getOriginalName().add(as[0]);
				Colossus.getManager().protect.getProtectedName().add(as[1]);
				ColossusHelper.addConsole("Added \"" + as[0] + "\" as protected username \"" + as[1] + "\".");
			} catch (Exception exception1) {
			}
		}
		bufferedreader.close();
	}

	public static ArrayList getProtectedName() {
		return protectedNames;
	}
	
	public static ArrayList getOriginalName() {
		return originalNames;
	}
}
