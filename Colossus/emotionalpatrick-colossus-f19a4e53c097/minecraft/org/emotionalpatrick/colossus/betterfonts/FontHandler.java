package org.emotionalpatrick.colossus.betterfonts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.emotionalpatrick.colossus.files.FileManagerImpl;
import org.emotionalpatrick.colossus.main.Helper;

public class FontHandler {

	private static FontHandler fontHandler = new FontHandler();
	
	private final File ttfConfiguration = new File(FileManagerImpl.getColossusDir(), "font-config.txt");	

	public boolean globalTTF = false;

	public String fontName = "Tahoma";
	public int fontSize = 18;

	public static FontHandler getInstance() {
		return fontHandler;
	}
	
	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	
	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	public boolean isGlobalTTF() {
		return globalTTF;
	}

	public void setGlobalTTF(boolean globalTTF) {
		this.globalTTF = globalTTF;
	}

	public void loadConfig() throws IOException {
		BufferedReader bufferedreader = new BufferedReader(new FileReader(
				ttfConfiguration));
		for (String s = ""; (s = bufferedreader.readLine()) != null;) {
			try {
				String as[] = s.split(":");
				setFontName(as[0]);
				setFontSize(Integer.parseInt(as[1]));
				setGlobalTTF(Boolean.parseBoolean(as[2]));
				Helper.addConsole("Loaded TTF configuration successfully.");
			} catch (Exception e) {
				Helper.addConsole("Failed to load TTF configuration.");
			}
		}
		bufferedreader.close();
	}

	public void saveConfig() {
		try {
			PrintWriter printwriter = new PrintWriter(new FileWriter(
					ttfConfiguration));
			String fontName = getFontName();
			int fontSize = getFontSize();
			boolean globalTTF = isGlobalTTF();
			
			String total = new StringBuilder().append(fontName).append(":")
					.append(fontSize).append(":").append(globalTTF).toString();
			printwriter.println(total);
			printwriter.close();
			Helper.addConsole("Saved TTF configuration successfully.");
		} catch (Exception e) {
			Helper.addConsole("Failed to save TTF configuration.");
		}
	}
}
