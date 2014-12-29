package org.emotionalpatrick.colossus.keybinds;

import org.emotionalpatrick.colossus.modules.Module;
import org.emotionalpatrick.colossus.modules.manager.ModuleManager;

public class KeybindManagerImpl implements KeybindManager{
	
	private static final KeybindManager instance = new KeybindManagerImpl();
	
	public static final KeybindManager getInstance() {
		return instance;
	}
	
	@Override
	public void runKeybinds(int k) {
		for (Module m : ModuleManager.getModules()) {
			if (m.getKey() == k)
				m.onToggle();
		}
	}
}
