package org.emotionalpatrick.colossus.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.emotionalpatrick.colossus.files.FileManagerImpl;
import org.emotionalpatrick.colossus.main.Helper;

public class SongSpammer extends Module {

	public SongSpammer() {
		super("SongSpammer", ".songspam", "Spams any text in song.txt", "Emotional Patrick", "Misc");
	}
	
	@Override
	public void runCommand (String s) {
		if (s.equalsIgnoreCase(this.getCommand())) {
			(new Thread(new ThreadSpam())).start();
			Helper.addChat("Spamming song...");
		}
	}
	
	@Override
	public void onToggle() {
		(new Thread(new ThreadSpam())).start();
		Helper.addChat("Spamming song...");
	}
	
	public static ArrayList<String> getFileContents(String fileDirectory) {
		ArrayList<String> al = new ArrayList<String>();
		File fileName = new File(fileDirectory);
		al.clear();
		BufferedReader br = null;
		try {
			String currentLine;
			if (!fileName.exists()) {
				fileName.createNewFile();
			}
			br = new BufferedReader(new FileReader(fileName));
			while ((currentLine = br.readLine()) != null) {
				al.add(currentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return al;
	}
	
	class ThreadSpam extends Thread {
		
		public File songFile = new File(FileManagerImpl.getColossusDir(), "song.txt");

		@Override
		public void run() {
			ArrayList<String> alSong = SongSpammer.getFileContents("" + songFile);
			int l = alSong.size();
			for (int m = 1; m < l + 1; m++) {
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Helper.sendChat("" + alSong.get(m));
			}
		}
	}
}
