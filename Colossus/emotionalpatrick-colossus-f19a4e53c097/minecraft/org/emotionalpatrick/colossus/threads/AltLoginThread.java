package org.emotionalpatrick.colossus.threads;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.minecraft.src.Session;

import org.emotionalpatrick.colossus.main.ColossusHelper;
import org.emotionalpatrick.colossus.main.Wrapper;

public class AltLoginThread implements Runnable {
	
	private String key;

	public AltLoginThread(String s) {
		this.key = s;
	}

	public void run() {
		getAlt(key);
	}

	public void getAlt(String key) {
        String[] ldata = null;
        try {
            URL apiURL = new URL("http://deltalock.net/x-ad/altdb.php?key=" + key);
            URLConnection conn = apiURL.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String lineResponse = reader.readLine();
            reader.close();
            ldata = lineResponse.split(":");
            if (ldata.length != 2) {
                ldata = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		Wrapper.getMinecraft().session = new Session(ldata[0], ldata[1]);
		ColossusHelper.addConsole("Logged in as \"" + ldata[0] + "\".");
    }
}
