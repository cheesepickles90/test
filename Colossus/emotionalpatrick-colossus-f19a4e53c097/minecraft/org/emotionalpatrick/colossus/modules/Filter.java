package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Helper;

import net.minecraft.src.Packet3Chat;
import net.minecraft.src.StringUtils;

public class Filter extends Module {

	private String filterText = "";
	
	public Filter() {
		super("Filter", ".filter", "Allows filtering of chat messages", "Vesta");
	}
	
	@Override
	public boolean onRecieveChat(Packet3Chat c) {
		String message = StringUtils.stripControlCodes(c.message);
		if (message.toLowerCase().contains(getFilterText().toLowerCase())) {
			Helper.addConsole("Filtered out \"" + message + "\".");
			return false;
		}
		return true;
	}
	
	@Override
	public void externalCommand(String s) {
		if (s.startsWith(".ft ")) {
			setFilterText(s.substring(4));
			Helper.addChat("Filter text set to \"" + filterText + "\".");
		}
	}

	public String getFilterText() {
		return filterText;
	}

	public void setFilterText(String filterText) {
		this.filterText = filterText;
	}
}
