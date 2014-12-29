package org.emotionalpatrick.colossus.main;

import java.io.IOException;

import org.emotionalpatrick.colossus.betterfonts.FontHandler;
import org.emotionalpatrick.colossus.commands.CommandManagerImpl;
import org.emotionalpatrick.colossus.files.FileManager;
import org.emotionalpatrick.colossus.files.FileManagerImpl;
import org.emotionalpatrick.colossus.gui.clickable.PanelBase;
import org.emotionalpatrick.colossus.gui.clickable.panels.PanelCombat;
import org.emotionalpatrick.colossus.gui.clickable.panels.PanelMisc;
import org.emotionalpatrick.colossus.gui.clickable.panels.PanelModes;
import org.emotionalpatrick.colossus.gui.clickable.panels.PanelValues;
import org.emotionalpatrick.colossus.gui.clickable.panels.PanelWorld;
import org.emotionalpatrick.colossus.modules.Module;
import org.emotionalpatrick.colossus.modules.loader.ModuleLoader;
import org.emotionalpatrick.colossus.modules.manager.ModuleManager;
import org.emotionalpatrick.colossus.utilities.Encryption;
import org.emotionalpatrick.colossus.utilities.values.Values;
import org.emotionalpatrick.colossus.utilities.values.ValuesBase;

import net.minecraft.client.Minecraft;

public class Colossus {

	public static Minecraft mc;
	
	public static Helper helper;
	public static Wrapper wrapper;
	public static ModuleManager manager;
	public static CommandManagerImpl cmds;
	public static ModuleLoader loader;
	public static Encryption crypt;
	
	private static final FileManager fileManager = new FileManagerImpl();
	
    public static PanelBase modes;
    public static PanelBase world;
    public static PanelBase killaura;
    public static PanelBase misc;
    public static PanelBase values;

	public static final String clientName = "Colossus";
	public static final String clientVersion = "v7.8.5";
	public static final String minecraftVersion = "(1.4.7)";
	public static final String clientTitle = clientName + " " + clientVersion + " " + minecraftVersion;
	
	/** Called upon startup */
	public static void init(Minecraft minecraft) {
		mc = minecraft;
		wrapper = new Wrapper();
		crypt = new Encryption();
		helper = new Helper();
		helper.addConsole("Initializing Colossus...");
		helper.setDebugMode(helper.isInDebugMode());
		manager.getModules().clear();
		manager = new ModuleManager();
		cmds = new CommandManagerImpl();
		fileManager.initLoad();
		for (Module m : manager.getModules()) {
			helper.addConsole("Module \"" + m.getName() + "\" successfully loaded.");
		}
		helper.addConsole("Initialized " + manager.getModules().size() + " module(s).");
		initPanels();
		try {
			manager.nameProtect.loadFriends();
			FontHandler.getInstance().loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
		helper.addConsole("Colossus initialized successfully!");
	}
    
	/** Initializes/adds panels and values to the draggable GUI */
	public static void initPanels() {
		killaura = new PanelCombat("Combat", 2, 2, 80, 30);
		modes = new PanelModes("Modes", 110, 2, 80, 30);
		world = new PanelWorld("World", 218, 2, 80, 30);
		misc = new PanelMisc("Miscellaneous", 2, 118, 80, 30);
		values = new PanelValues("Value Configuration", 315, 2, 80, 30);
		helper.addConsole("Initialized GUI.");
	}
	
	/** Returns the helper */
	public static final Helper getHelper() {
		return helper;
	}

	/** Returns the wrapper */
	public static final Wrapper getWrapper() {
		return wrapper;
	}
	
	/** Returns the module manager */
	public static final ModuleManager getManager() {
		return manager;
	}

	/** Returns the command manager */
	public static final CommandManagerImpl getCommands() {
		return cmds;
	}

	/** Returns the module loader */
	public static final ModuleLoader getLoader() {
		return loader;
	}

	/** Returns the Minecraft version */
	public static final String getMinecraftVersion() {
		return minecraftVersion;
	}

	/** Returns the client's name */
	public static final String getClientName() {
		return clientName;
	}

	/** Returns the client's version */
	public static final String getClientVersion() {
		return clientVersion;
	}
	
	/** Returns the file manager */
	public static final FileManager getFileManager() {
		return fileManager;
	}
	
	/** Returns the encryption helper */
	public static final Encryption getEncryption() {
		return crypt;
	}

	/** Returns the client's title */
	public static final String getClientTitle() {
		return clientTitle;
	}
}
