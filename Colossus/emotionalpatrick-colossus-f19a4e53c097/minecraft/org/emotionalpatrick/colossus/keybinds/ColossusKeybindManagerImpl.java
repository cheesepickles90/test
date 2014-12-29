package org.emotionalpatrick.colossus.keybinds;

import org.emotionalpatrick.colossus.modules.Module;
import org.emotionalpatrick.colossus.modules.manager.ModuleManager;

public class ColossusKeybindManagerImpl implements ColossusKeybindManager{

	//y u no have getter for dis? -ramisconfused-
	
	private static final ColossusKeybindManager instance = new ColossusKeybindManagerImpl();
	
	public static final ColossusKeybindManager getInstance() {
		return instance;
	}
	
	public void runKeybinds(int k) {
		for (Module m : ModuleManager.getModules()) {
			if (m.getKey() == k)
				m.onToggle();
		}
	}
}
