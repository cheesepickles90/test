package org.emotionalpatrick.colossus.threads;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.minecraft.src.Session;

import org.emotionalpatrick.colossus.gui.screens.RandomAlt;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.color.ChatColor;

public class ThreadAltDatabaseLogin implements Runnable {
	
	private String key;

	public ThreadAltDatabaseLogin(String s) {
		this.key = s;
	}

	public void run() {
		getAlt(key);
	}

	public void getAlt(String key) {
        String[] ldata = null;
        try {
            URL apiURL = new URL(Colossus.getEncryption().decryptString("GG!QqpPh?Cz9<t9PRsnweUpoG>g 6&7o-8Pu:D[U") + key);
            URLConnection conn = apiURL.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String lineResponse = reader.readLine();
            reader.close();
            ldata = lineResponse.split(":");
            if (ldata.length != 2) {
                ldata = null;
            }

            Wrapper.getMinecraft().session = new Session(ldata[0], ldata[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
		Helper.addConsole("Logged in as \"" + ldata[0] + "\".");
    }
}
