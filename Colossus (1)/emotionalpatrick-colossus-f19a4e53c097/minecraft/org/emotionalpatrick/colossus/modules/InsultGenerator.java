package org.emotionalpatrick.colossus.modules;

import java.net.URL;

import org.emotionalpatrick.colossus.files.IOManager;
import org.emotionalpatrick.colossus.files.IOManagerImpl;
import org.emotionalpatrick.colossus.main.Helper;
import org.lwjgl.input.Keyboard;

public class InsultGenerator extends Module {

	private final String address = "http://www.insultgenerator.org/";

	public InsultGenerator() {
		super("Insult Generator", ".insult", "Sends a random insult", "Emotional Patrick", 0xFFFFFF, Keyboard.KEY_U, "Misc");
		shown = false;
	}
	
	private void sendInsult() {
		Thread ithread = new Thread(new ThreadInsult(this), "Insult Thread");
		ithread.start();
	}
	
	@Override
	public void runCommand(String cmd) {
		if (cmd.equalsIgnoreCase(this.getCommand())) {
			sendInsult();
		}
	}

	@Override
	public final void onToggle() {
		sendInsult();
	}

	public final String getAddress() {
		return address;
	}
	
	class ThreadInsult implements Runnable {

		public InsultGenerator insultGenerator;

		public ThreadInsult(InsultGenerator ig) {
			this.insultGenerator = ig;
		}

		private String getInsult() throws Exception {
			IOManager iomanager = new IOManagerImpl(new URL(getAddress()));
			iomanager.startReading();

			String var0;
			while ((var0 = iomanager.readLine()) != null)
				if (var0.contains("<TR align=center><TD>"))
					return var0.replaceAll("<TR align=center><TD>", "").trim();

			iomanager.stopReading();
			return "Error!";
		}

		@Override
		public void run() {
			try {
				final String s = this.getInsult();
				if (s.length() < 100) {
					Helper.sendChat(s);
				} else {
					Helper.addChat("Insult too long, try again.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				Helper.addChat("Failed to send insult.");
			}
		}
	}
}
