package org.emotionalpatrick.colossus.modules;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.net.InetSocketAddress;

import org.emotionalpatrick.colossus.files.FileManagerImpl;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.color.ChatColor;

import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.StringUtils;

public class UserDump extends Module {

	public UserDump() {
		super("UserDump", ".userdump", "Scrapes all usernames from server", "Emotional Patrick");
	}

	@Override
	public void runCommand(String cmd) {
		if (cmd.equalsIgnoreCase(this.getCommand())) {
			dumpUsers();
		}
	}
	
	private String getServerIP() {
		return ((InetSocketAddress) Wrapper.getMinecraft().getSendQueue()
				.getNetManager().getSocketAddress()).getHostName();
	}
	
	private void dumpUsers() {
		File scrapeFile = new File(FileManagerImpl.getColossusDir() + "/dumps/",
				getServerIP() + "-users.txt");
		File scrapeFolder = new File(FileManagerImpl.getColossusDir() + "/dumps/");
		if (!scrapeFolder.exists()) {
			scrapeFolder.mkdir();
		}
		PrintStream ps = null;
		NetClientHandler nch = Wrapper.getPlayer().sendQueue;
		List list = nch.playerInfoList;
		int size = list.size();
		Helper.addChat(String.format(
				"Attempted to scrape " + ChatColor.GOLD + "%s \247rusername(s) to \"%s", size,
				getServerIP() + "-users.txt\"!"));
		try {
			ps = new PrintStream(scrapeFile);
			for (Object ob : list) {
				GuiPlayerInfo pi = (GuiPlayerInfo) ob;
				String s = StringUtils.stripControlCodes(pi.name);
				if (!s.equalsIgnoreCase(Wrapper.getPlayer().username)) {
					ps.println(s);
					Helper.addConsole("Scraped username \"" + s
							+ "\" successfully!");
				}
			}
			Helper.addConsole(String.format(
					"Saved %s username(s) to \"%s", size, getServerIP()
							+ "-users.txt\"!"));
		} catch (IOException err) {
			err.printStackTrace();
			Helper.addConsole("Failed to scrape usernames ;(");
		}
	}
}
