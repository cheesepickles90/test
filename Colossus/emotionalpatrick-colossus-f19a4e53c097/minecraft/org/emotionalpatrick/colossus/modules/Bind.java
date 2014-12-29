package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.files.FileManagerImpl;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.modules.manager.ModuleManager;
import org.emotionalpatrick.colossus.utilities.color.ChatColor;
import org.lwjgl.input.Keyboard;

public class Bind extends Module {

	public Bind() {
		super("Bind", ".bind", "Used to rebind keybinds", "Emotional Patrick");
	}

	@Override
	public void runCommand(String par1Str) { }

	@Override
	public void externalCommand(String s) {
		if (s.equalsIgnoreCase(".keys")) {
			String binds = ChatColor.GOLD + "Availiable Keybinds: " + ChatColor.WHITE;
			Colossus.getManager();
			for (Module m : ModuleManager.getModules()) {
				int key = m.getKey();
				String keyStr = new StringBuilder().append((key > 0)? Keyboard.getKeyName(key) : "NONE").toString();
				binds += (m.getName() + " - " + keyStr + ", ");
			}
			Helper.addChat(binds);
		}
		if (s.startsWith(".bind"))
			if (s.split(" ").length == 3) {
				String args[] = s.split(" ");
				String cmd = args[1];
				String key = args[2];
				Colossus.getManager();
				for (Module hb : ModuleManager.getModules()) {
					if (cmd.equals(hb.getCommand())) {
						hb.setKey(Keyboard.getKeyIndex(key.toUpperCase()));
						Helper.addChat(ChatColor.GRAY + hb.getName()
								+ ChatColor.WHITE + " has been bound to "
								+ ChatColor.GRAY + key + ChatColor.WHITE + ".");
						FileManagerImpl.getInstance().saveKeybinds();
						break;
					}
				}
			} else {
				Helper.addChat("Invalid syntax, .bind .(command) (key)");
			}
	}
}
