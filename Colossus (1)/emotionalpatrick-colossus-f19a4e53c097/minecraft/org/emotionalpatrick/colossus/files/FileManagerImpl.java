package org.emotionalpatrick.colossus.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.minecraft.client.Minecraft;

import org.emotionalpatrick.colossus.betterfonts.FontHandler;
import org.emotionalpatrick.colossus.gui.clickable.PanelBase;
import org.emotionalpatrick.colossus.gui.clickable.panels.PanelModes;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.modules.Module;
import org.emotionalpatrick.colossus.modules.NameProtect;
import org.emotionalpatrick.colossus.modules.manager.ModuleManager;
import org.lwjgl.input.Keyboard;

public class FileManagerImpl implements FileManager {

    private static File colossusDir;
    private static File pluginsDir;
    
    private static final File keybindsFile = new File(getColossusDir(), "binds.txt");	
	public static final File friendsFile = new File(getColossusDir(), "protect.txt");
	
	private static FileManager instance = new FileManagerImpl();
   	
	public void initLoad() {        
		pluginsDir = new File(getColossusDir(), "plugins");
		pluginsDir.mkdirs();
		
		loadKeybinds();
		NameProtect.loadEnemies();
		PanelBase.loadPanels();
	}
	
	public static final FileManager getInstance() {
		return instance;
	}
	
	public static File getColossusDir() {
		if (colossusDir == null)
			colossusDir = Wrapper.getMinecraft().getAppDir("minecraft/colossus");
		return colossusDir;
	}
	
	public void initSave() {
		try {
			saveKeybinds();
			saveFriends();
			PanelBase.savePanels();
			FontHandler.getInstance().saveConfig();
			NameProtect.saveEnemies();
		} catch (Exception err) {
			err.printStackTrace();
			Helper.addConsole("Failed to save files!");
		}
	}
	
	public void saveKeybinds() {
		IOManager iomanager = new IOManagerImpl(keybindsFile);
		iomanager.startWriting();
		
		for(Module module : ModuleManager.getModules()) {
			if(module.getKey() != -1) 
				iomanager.write(new StringBuilder().append(module.getName())
						.append(":").append(Keyboard.getKeyName(module.getKey()))
						.append("\r\n").toString());				
		}
		
		iomanager.stopWriting();
	}

	public void loadKeybinds() {
		if (!keybindsFile.exists()) {
			try {
				keybindsFile.createNewFile();
				Helper.addConsole("Creating binds.txt...");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		IOManager iomanager = new IOManagerImpl(keybindsFile);
		iomanager.startReading();
		
		String var0;
		while((var0 = iomanager.readLine()) != null) {
			String array[] = var0.split(":");
			String hackname = array[0];
			String key = array[1];
			for (int i = 0; i < ModuleManager.getModules().size(); i++) {
				Module m = ModuleManager.getModules().get(i);
				if (m.name.equals(hackname)) {
					m.setKey(Keyboard.getKeyIndex(key));
				}
			}
		}
		
		iomanager.stopReading();
	}
	
	public void saveFriends() throws IOException {
		try {
			PrintWriter printwriter = new PrintWriter(new FileWriter(
					friendsFile));
			
			for (int protectCheck = 0; protectCheck < Colossus.getManager().nameProtect.originalNames
					.size(); protectCheck++) {
				String oName = ""
						+ Colossus.getManager().nameProtect.originalNames
								.get(protectCheck);
				String pName = ""
						+ Colossus.getManager().nameProtect.protectedNames
								.get(protectCheck);
				String total = oName + ":" + pName;
				printwriter.println(total);
			}
			printwriter.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void loadFriends() {
		// dafaq
	}

	public static File getPluginsDir() {
		return pluginsDir;
	}
}
