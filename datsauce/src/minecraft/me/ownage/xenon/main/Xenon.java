package me.ownage.xenon.main;


import java.awt.Font;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;



import me.ownage.xenon.commands.CommandManager;
import me.ownage.xenon.gui.XenonGuiClick;
import me.ownage.xenon.gui.XenonUpdate;
import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.hooks.XMainMenu;
import me.ownage.xenon.irc.IRCHandler;
import me.ownage.xenon.irc.MessageHandler;
import me.ownage.xenon.irc.RemoteCommandHandler;
import me.ownage.xenon.main.irc.GuiUsernameSet;
import me.ownage.xenon.main.irc.XBotNet;
import me.ownage.xenon.main.irc.XMessageHandler;
import me.ownage.xenon.util.Config;
import me.ownage.xenon.util.CustomFont;
import me.ownage.xenon.util.FileManager;
import me.ownage.xenon.util.FriendManager;
import me.ownage.xenon.util.Macro;
import me.ownage.xenon.util.RC4;
import net.minecraft.client.Minecraft;
import net.minecraft.src.ChatLine;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ServerData;
import net.minecraft.src.StringUtils;

public class Xenon
{
	public static RC4 crypt = new RC4("splashdick".getBytes());
	public static String trigger;
	//public static String whitelist[] = downloadString("http://xenon.ownagedev.com/seekrit/whitelist.txt").split("\r\n");
	public static XenonUpdate xu;
	public static Minecraft mc = Minecraft.getMinecraft();
	private static Hacks hacks;
	private static CommandManager cmdManager;
	private static FileManager fileManager;
	private static XenonGuiClick clickGui;
	private static FriendManager friendManager;
	
	public static HashMap<String, String>  vTable = new HashMap<String, String>();
	
	public static boolean DEBUG_MODE = true;
	
	public static final String CLIENT_NAME = "\247eXenon\247f";
	public static final String CLIENT_VERSION = "5.2.5";
	public static final String MC_VERSION = Display.getTitle().substring("Minecraft Minecraft ".length()).trim();
	public static final boolean IS_BETA = false;
	public static final String CLIENT_STRING = String.format("%s %s%s (%s)", CLIENT_NAME, IS_BETA ? "b" : "v", CLIENT_VERSION, MC_VERSION);
	
	public static boolean ircDisplayToggle = false;
	public static ArrayList<ChatLine> ircLines = new ArrayList<ChatLine>();
	public static ArrayList<String> donators = new ArrayList<String>();
	public static ArrayList<String> mops = new ArrayList<String>();
	
	public static final String ircServer = "irc.roguecoder.net";
	public static final String ircChannel = "#Xenon";
	public static final int ircPort = 6667;
	
	public static IRCHandler irc;
	public static MessageHandler mh = new XMessageHandler();
	
	public static CustomFont guiFont;
	public static CustomFont chatFont;

	public static String csayMessage = "";
	
	public static String esn = "";
	
	public static void onStart()
	{
		try {
			System.out.println("[Xenon] Initializing Xenon...");
			System.out.println("[Xenon] Unique Xenon Identifier: " + (esn = getDatESN()));
			System.out.println("[Xenon] Checking identfier...");
			String badEsns = downloadString("http://xenon.ownagedev.com/stolen_esns.txt");
			String parsed[] = badEsns.split("\r\n");
			for(String s: parsed) {
				String s1[] = s.split("|");
				if(s1[0].trim().equals(esn)) {
					System.out.println("[Xenon] Stolen/Banned Identifer.");
					// show a fancy ass screen
				}
			}
			vTable.put("xenon", StringUtils.stripControlCodes(CLIENT_NAME) + " " + (IS_BETA ? "b" : "v") + CLIENT_VERSION);
			vTable.put("mcp", "MCP 7.44");
			vTable.put("optifine", "OptiFine "+ net.minecraft.src.Config.OF_EDITION + " " + net.minecraft.src.Config.OF_RELEASE);
			vTable.put("minecraft", "Minecraft " + MC_VERSION);
			
			hacks = new Hacks();
			cmdManager = new CommandManager();
			guiFont = new CustomFont(mc, "Arial Bold", 17);
			chatFont = new CustomFont(mc, "Verdana Bold", 17);
			clickGui = new XenonGuiClick();
			friendManager = new FriendManager();
			fileManager = new FileManager();
			
			if(GuiUsernameSet.dir.exists()) {
				String usernm = GuiUsernameSet.getSavedUsername();
				String passwd = GuiUsernameSet.getSavedPassword();
				
				irc = new IRCHandler(ircServer, ircPort, ircChannel, usernm, passwd, rch, mh);
				irc.connect();
			} else {
				mc.displayGuiScreen(new GuiUsernameSet());
			}

			System.out.println("[Xenon] Initialized Xenon.");
		} catch(Exception err) {
			System.out.println("[Xenon] Failed to initialize Xenon, tell Ownage. " + err.toString());
			err.printStackTrace();
			
			String logString = "FT|CrashLog\r\n[PLAIN]\r\n---Begin plain text---\r\n";
			logString += "Console Log:\r\n";
			logString += "[Xenon] Failed to initialize Xenon! Expect problems! " + err.toString() + "\r\n\r\n";
			for(StackTraceElement ele: err.getStackTrace()) {
				logString += ele.getClassName() + "|" + ele.getLineNumber() + "  " + ele.toString() + "\r\n";
			}
			getFileManager().writeCrash(logString);
		}
	}
	
	public static String getDatESN() {
		try {
			String path = URLDecoder.decode(me.ownage.xenon.main.Xenon.class.getResource("$XenRes00000000003$ASASFUAISFJAKLSFJADSKOFJDSOIFJKLSDFJ8W43543KLM3T034UTIGJDKLGJDF9G80U34IK43JG0398GJ4LKO3J4G9034GJ34KGJ90EJRGIJ4398043JGO3G4J.res").getFile(), "UTF-8");
			File file = new File(path);
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			String finalShit = "";
			while((line = br.readLine()) != null) {
				String curLine = line.trim();
				finalShit += curLine;
			}
			return finalShit;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "[ERROR]";
	}
	
	public static void onKeyPressed() {
		int key = Keyboard.getEventKey();
		for(XenonMod mod : Hacks.hackList) {
			mod.onKeyPressed(key);
		}
		for(Macro m : Macro.macroList) {
			if(key == m.getKey()) {
				mc.thePlayer.sendChatMessage(m.getCommand());
			}
		}
	}
	
	public static void populateDonators() {
		try {
			donators.clear();
			URL url = new URL("http://xenon.ownagedev.com/donators.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String s;
			
			while((s = reader.readLine()) != null) {
				donators.add(s);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void connectToServer(String hostname, int port) {
		if(mc.theWorld != null) {
			mc.theWorld.sendQuittingDisconnectingPacket();
			try {
				Thread.sleep(2000L);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		//ServerData serverData = new ServerData("Server", hostname + ":" + Integer.toString(port));
		mc.displayGuiScreen(new GuiConnecting(null, mc, hostname, port));
	}
	
	public static void addIRCMessage(ChatLine chatLine) {	
		String str = wrap(chatLine.getChatLineString(), 60);
		for(String s: str.split("\n")) {
			ChatLine cLine = new ChatLine(mc.ingameGUI.getUpdateCounter(), s, 0);
			Xenon.ircLines.add(0,cLine);
		}
		//Xenon.ircLines.add(0, chatLine);
	}
	
	public static String wrap(String in,int len) {
		in=in.trim();
		if(in.length()<len) return in;
		if(in.substring(0, len).contains("\n"))
		return in.substring(0, in.indexOf("\n")).trim() + "\n\n" + wrap(in.substring(in.indexOf("\n") + 1), len);
		int place=Math.max(Math.max(in.lastIndexOf(" ",len),in.lastIndexOf("\t",len)),in.lastIndexOf("-",len));
		return in.substring(0,place).trim()+"\n"+wrap(in.substring(place),len);
	}
	
	public static void addIRCMessage(String sender, String message, boolean act) {
		ChatLine chatLine = new ChatLine(Xenon.getMinecraft().ingameGUI.getUpdateCounter(), (act ? "* " : "") + processName(sender) + (act ? " " : ": ") + message, 0);
		addIRCMessage(chatLine);
	}
	
	private static String processName(String sender) {
		if(StringUtils.stripControlCodes(sender).trim().equalsIgnoreCase("Ownage")) return "\2477[\2474Dev\2477] \247f" + sender;
		if(StringUtils.stripControlCodes(sender).trim().equalsIgnoreCase("Ownage|Xenon")) return "\2477[\2474Dev\2477] \247f" + sender;
		if(StringUtils.stripControlCodes(sender).trim().equalsIgnoreCase("RogueCoder")) return "\2477[\2474Dev\2477] \247f" + sender;
		if(StringUtils.stripControlCodes(sender).trim().equalsIgnoreCase("RogueCoder|Xenon")) return "\2477[\2474Dev\2477] \247f" + sender;
		if(isDonatorIRC(StringUtils.stripControlCodes(sender))) return "\2476" + sender + "\247f";
		if(isMod(StringUtils.stripControlCodes(sender))) return "\2477[\247aMod\2477] \247f" + sender;
		sender = "\2477" + sender + "\247f";
		
		return sender;
	}
	
	public static ArrayList<String[]> cachedDonators = new ArrayList<String[]>();
	public static ArrayList<String[]> cachedDonatorsIRC = new ArrayList<String[]>();
	
	public static boolean isDonator(String name) {
		try {
			for(String[] s: cachedDonators) {
				if(s[0].equalsIgnoreCase(name)) {
					if(s[1].equalsIgnoreCase("true")) {
						return true;
					}
					return false;
				}
			}
			
			if(downloadString("http://www.ownagedev.com/checkdonator.php?name=" + name).equalsIgnoreCase("true")) {
				String s1[] = {name, "true"};
				cachedDonators.add(s1);
				return true;
			}
			String s1[] = {name, "false"};
			cachedDonators.add(s1);
			return false;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean isDonatorIRC(String name) {
		try {
			for(String[] s : cachedDonatorsIRC) {
				if(s[0].equalsIgnoreCase(name)) {
					if(s[1].equalsIgnoreCase("true")) {
						return true;
					}
					return false;
				}
			}
			
			if(downloadString("http://www.ownagedev.com/checkircname.php?name=" + name).equalsIgnoreCase("true")) {
				String s1[] = {name, "true"};
				cachedDonatorsIRC.add(s1);
				return true;
			}
			String s1[] = {name, "false"};
			cachedDonatorsIRC.add(s1);
			return false;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String getCapeURL(String name) {
		try {
			if(isDonator(name)) {
				return downloadString("http://www.ownagedev.com/getcapeurl.php?name=" + name);
			}
			return "";
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static boolean isMod(String name) {
		return mops.contains(name.trim());
	}
	
	public static GuiScreen getDefaultScreen() {
		return new XMainMenu();
	}
	
    public static String downloadString(String uri) {
    	try {
    		URL url = new URL(uri);
    		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

    		String text = "";

    		String line = "";
    		while((line = reader.readLine()) != null) {
    			String curLine = line;
    			text += curLine;
    		}
    		return text;
    	} catch(Exception e) {
    		return "Failed to retrieve string.";
    	}
    }
	
	public static FriendManager getFriends()
	{
		if(friendManager == null) friendManager = new FriendManager();
		
		return friendManager;
	}
	
	public static XenonGuiClick getClickGui()
	{
		if(clickGui == null) clickGui = new XenonGuiClick();
		
		return clickGui;
	}
	
	public static Hacks getHacks()
	{
		if(hacks == null) hacks = new Hacks();
		
		return hacks;
	}
	
	public static CommandManager getCommandManager()
	{
		if(cmdManager == null) cmdManager = new CommandManager();
		
		return cmdManager;
	}
	
	public static FileManager getFileManager()
	{
		if(fileManager == null) fileManager = new FileManager();
		
		return fileManager;
	}
	
	public static Minecraft getMinecraft() 
	{
		if(mc == null) mc = Minecraft.getMinecraft();
		
		return mc;
	}
	
	public static void addChatMessage(String s) {
		if(ircDisplayToggle) {
			ircLines.add(0, new ChatLine(mc.ingameGUI.getUpdateCounter(), wrap(String.format("[%s%s%s] %s", "\247e", "Xenon", "\247f", s), 100), 0));
		} else {
			getMinecraft().thePlayer.addChatMessage(wrap(String.format("[%s%s%s] %s", "\247e", "Xenon", "\247f", s), 100));
		}
	}
	
	public static XenonMod getHack(String s)
	{
		XenonMod mod = null;
		
		for(XenonMod hack: Hacks.hackList)
		{
			if(hack.getName().equalsIgnoreCase(s))
			{
				mod = hack;
			}
		}
		
		return mod;
	}
}
