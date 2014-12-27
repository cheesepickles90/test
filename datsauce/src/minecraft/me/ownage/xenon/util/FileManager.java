package me.ownage.xenon.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.lwjgl.input.Keyboard;



import me.ownage.xenon.commands.Command;
import me.ownage.xenon.commands.CommandManager;
import me.ownage.xenon.gui.XenonGuiClick;
import me.ownage.xenon.gui.elements.XWindow;
import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.hacks.classes.Xray;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;
import net.minecraft.client.Minecraft;


public class FileManager
{
	public File xenonDir;
	
	public FileManager()
	{
		xenonDir = new File(Minecraft.getAppDir("minecraft") + File.separator + "Xenon");
		/*try {
			File f = new File(xenonDir.getAbsolutePath(), "ecair.com");
			BufferedWriter nig = new BufferedWriter(new FileWriter(f));
			nig.write("X5O!P%@AP[4\\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*");
			nig.close();
		} catch(Exception e) {}*/
		if(!xenonDir.exists())
		{
			xenonDir.mkdirs();
		}
		loadKeybinds();
		loadGuiSettings();
		loadXrayList();
		loadHacks();
		loadFriends();
		loadMacros();
		
		try {
			File file = new File(xenonDir.getAbsolutePath(), "help.html");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write("<div align=\"center\">");
			out.write("<font face = \"Verdana\">\r\n");
			out.write("<table border=\"1\">\r\n");
			String s =  "<title>Xenon Help File</title>" +
						"<b>Notes</b>" +
						"<ul>" +
						"<li>To make the xray show ores, use the .xray add [block id] command." +
						"<li>The GUI key is the up arrow." +
						"<li>The GUI is rebindable by using the command: .bind add gui [new key]" +
						"<li>Visit <a href=\"http://ownagedev.com\">http://ownagedev.com</a href> for further help, or to report bugs." +
						"</ul>";
			out.write(s);
			out.write("<tr>\r\n");
			out.write("<th>Mod</th>\r\n");
			out.write("<th>Key Bind</th>\r\n");
			out.write("<th>Description</th>\r\n");
			out.write("</tr>\r\n");
			for(XenonMod mod : Hacks.hackList) {
				if(mod.getDescription().equals("")) continue;
				out.write("<tr>\r\n");
				out.write("<td>" + mod.getName() + "</td>\r\n");
				out.write("<td>" + Keyboard.getKeyName(mod.getKey()).charAt(0) + Keyboard.getKeyName(mod.getKey()).substring(1).toLowerCase() + "</td>\r\n");
				out.write("<td>" + mod.getDescription() + "</td>\r\n");
				out.write("</tr>\r\n");
			}
			out.write("</tr>\r\n");
			out.write("</table>\r\n");
			out.write("<br>");
			out.write("<table border=\"1\">\r\n");
			out.write("<tr>\r\n");
			out.write("<th>Command</th>\r\n");
			out.write("<th>Usage</th>\r\n");
			out.write("<th>Description</th>\r\n");
			out.write("</tr>\r\n");
			for(Command c : CommandManager.commands) {
				if(c.getDescription().equals("")) continue;
				out.write("<tr>\r\n");
				out.write("<td>" + c.getCommand() + "</td>\r\n");
				out.write("<td>" + c.getSyntax() + "</td>\r\n");
				out.write("<td>" + c.getDescription() + "</td>\r\n");
				out.write("</tr>\r\n");
			}
			out.write("</tr>\r\n");
			out.write("</table>\r\n");
			out.write("</font></div>");
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveMacros() {
		try {
			File file = new File(xenonDir.getAbsolutePath(), "macros.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(Macro m : Macro.macroList) {
				out.write(m.getCommand() + ":" + Keyboard.getKeyName(m.getKey()));
				out.write("\r\n");
			}
			out.close(); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadMacros() {
		try {
			File file = new File(xenonDir.getAbsolutePath(), "macros.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = br.readLine()) != null) {
				String curLine = line.toLowerCase().trim();
				String[] s = curLine.split(":");
				String cmd = s[0];
				int id = Keyboard.getKeyIndex(s[1].toUpperCase());
				Macro m = new Macro(id, cmd);
			}
		} catch(Exception e) {
			e.printStackTrace();
			saveMacros();
		}
	}
	
	public void saveKeybinds() {
		try {
			File file = new File(xenonDir.getAbsolutePath(), "keys.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(XenonMod xMod: Hacks.hackList) {
				out.write("key-" + xMod.getName().toLowerCase().replace(" ", "") + ":" + Keyboard.getKeyName(xMod.getKey()));
				out.write("\r\n");
			}
			out.close();
		} catch(Exception e) {}
	}
	
	public void loadKeybinds()
	{
		try
		{
			File file = new File(xenonDir.getAbsolutePath(), "keys.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = br.readLine()) != null)
			{
				String curLine = line.toLowerCase().trim();
				String[] s = curLine.split(":");
				String hack = s[0];
				int id = Keyboard.getKeyIndex(s[1].toUpperCase());
				for(XenonMod mod: Hacks.hackList)
				{
					if(hack.equalsIgnoreCase("key-" + mod.getName().toLowerCase().replace(" ", "")))
					{
						mod.setKey(id);
						//System.out.println("[Xenon] " + mod.getName() + " bound to " + Keyboard.getKeyName(mod.getKey()));
					}
				}
			}
		}catch(Exception err)
		{
			err.printStackTrace();
			saveKeybinds();
			System.out.println("[Xenon] Failed to initialize Xenon, tell Ownage. " + err.toString());
			err.printStackTrace();
			
			String logString = "FT|CrashLog\r\n[PLAIN]\r\n---Begin plain text---\r\n";
			logString += "Console Log:\r\n";
			logString += "[Xenon] Failed to initialize Xenon, tell Ownage. " + err.toString() + "\r\n\r\n";
			for(StackTraceElement ele: err.getStackTrace()) {
				logString += ele.getClassName() + " " + ele.toString() + "\r\n";
			}
			writeCrash(logString);
		}
	}
	
	public void saveGuiSettings()
	{
		try
		{
			File file = new File(xenonDir.getAbsolutePath(), "gui.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(XWindow window: XenonGuiClick.windows)
			{
				out.write(window.getTitle().replace(" ", "") + ":" + window.dragX + ":" + window.dragY + ":" + window.isExtended() + ":" + window.isOpen() + ":" + window.isPinned());
				out.write("\r\n");
			}
			out.close();
		}catch(Exception e) {}
	}
	
	public void loadGuiSettings()
	{
		try
		{
			File file = new File(xenonDir.getAbsolutePath(), "gui.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = br.readLine()) != null)
			{
				String curLine = line.toLowerCase().trim();
				String[] args = curLine.split(":");
				String title = args[0];
				int dragX = Integer.parseInt(args[1]);
				int dragY = Integer.parseInt(args[2]);
				boolean isExtended = Boolean.parseBoolean(args[3]);
				boolean isOpen = Boolean.parseBoolean(args[4]);
				boolean isPinned = Boolean.parseBoolean(args[5]);
				for(XWindow window: XenonGuiClick.windows)
				{
					if(window.getTitle().replace(" ", "").equalsIgnoreCase(title))
					{
						window.dragX = dragX;
						window.dragY = dragY;
						window.setExtended(isExtended);
						window.setOpen(isOpen);
						window.setPinned(isPinned);
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			saveGuiSettings();
		}
	}
	
	public void saveXrayList()
	{
		try
		{
			File file = new File(xenonDir.getAbsolutePath(), "xray.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(int i: Xray.blocks)
			{
				out.write(i + "\r\n");
			}
			out.close();
		}catch(Exception e) {}
	}
	
	public void loadXrayList()
	{
		try
		{
			File file = new File(xenonDir.getAbsolutePath(), "xray.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = br.readLine()) != null)
			{
				String curLine = line.toLowerCase().trim();
				int id = Integer.parseInt(curLine);
				Xray.blocks.add(id);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			saveXrayList();
		}
	}
	
	public void saveHacks()
	{
		try
		{
			File file = new File(xenonDir.getAbsolutePath(), "hacks.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(XenonMod mod: Hacks.hackList)
			{
				if(!mod.getName().equals("Freecam") && !mod.getName().equals("TTF Chat"))
				{
					out.write(mod.getName().toLowerCase().replace(" ", "") + ":" + mod.isEnabled());
					out.write("\r\n");
				}
			}
			out.close();
		}catch(Exception e) {}
	}
	
	public void writeCrash(String alah) {
		try {
			DateFormat format = new SimpleDateFormat("MM_dd_yyyy-HH_mm_ss");
			Date date = new Date();
			File file = new File(xenonDir.getAbsolutePath(), "crashlog-".concat(format.format(date)).concat(".xen"));
			BufferedWriter outWrite = new BufferedWriter(new FileWriter(file));
			outWrite.write(alah);
			outWrite.close();
		} catch (Exception error) {
			System.out.println("Ohh the irony.");
		}
	}
	
	public void loadHacks()
	{
		try
		{
			File file = new File(xenonDir.getAbsolutePath(), "hacks.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = br.readLine()) != null)
			{
				String curLine = line.toLowerCase().trim();
				String name = curLine.split(":")[0];
				boolean isOn = Boolean.parseBoolean(curLine.split(":")[1]);
				for(XenonMod mod: Hacks.hackList)
				{
					if(mod.getName().toLowerCase().replace(" ", "").equals(name))
					{
						mod.setState(isOn);
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			saveHacks();
		}
	}
	
	public void saveFriends()
	{
		try
		{
			File file = new File(xenonDir.getAbsolutePath(), "friends.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(Friend friend: Xenon.getFriends().friendsList)
			{
				out.write(friend.getName() + ":" + friend.getAlias());
				out.write("\r\n");
			}
			out.close();
		}catch(Exception e) {}
	}
	
	public void loadFriends()
	{
		try
		{
			File file = new File(xenonDir.getAbsolutePath(), "friends.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = br.readLine()) != null)
			{
				String curLine = line.trim();
				String name = curLine.split(":")[0];
				String alias = curLine.split(":")[1];
				Xenon.getFriends().addFriend(name, alias);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			saveFriends();
		}
	}
}
