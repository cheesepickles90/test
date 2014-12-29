package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.GuiNewChat;
import net.minecraft.src.Packet3Chat;

import org.emotionalpatrick.colossus.gui.screens.CGuiNewChat;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.color.ChatColor;

public class Chat extends Module {

	private String greenChar = ">";

	public Chat() {
		super("Chat", ".chat", "TTF Chat w/ greentext support", "Vesta");
	}

	@Override
	public void onEnable() {
		Wrapper.getMinecraft().ingameGUI.persistantChatGUI = new CGuiNewChat(Wrapper.getMinecraft());
	}

	@Override
	public void onDisable() {
		Wrapper.getMinecraft().ingameGUI.persistantChatGUI = new GuiNewChat(Wrapper.getMinecraft());
	}

	// Thanks for the BETTER method of greentext sh0ecock <3
	@Override
	public boolean onRecieveChat(Packet3Chat c) {
		StringBuilder stringBuilder = new StringBuilder(c.message);
		if (stringBuilder.indexOf(greenChar) == 0) {
			return false;
		}
		for (int index = stringBuilder.indexOf(greenChar); index >= 0; index = stringBuilder
				.indexOf(greenChar, index + 1)) {
			try {
				char c1 = stringBuilder.charAt(index + 1);
				if (isValidGreenCharacter(c1)) {
					stringBuilder.insert(index - 1, ChatColor.DARK_GREEN);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		c.message = stringBuilder.toString();
		return true;
	}

	private boolean isValidGreenCharacter(char c) {
		return Character.isLetter(c) || Character.isDigit(c);
	}
}
