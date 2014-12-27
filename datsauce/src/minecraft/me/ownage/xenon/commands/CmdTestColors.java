package me.ownage.xenon.commands;

import me.ownage.xenon.main.Xenon;

public class CmdTestColors extends Command
{
	public CmdTestColors()
	{
		super("tc");
	}

	@Override
	public void runCommand(String s, String[] args) {
		for (int i = 0; i < 16; i++) {
			String color = Integer.toHexString(i);
			Xenon.addChatMessage("\247" + color + "Testing color: " + color);
		}
	}

	@Override
	public String getDescription() 
	{
		return "Prints out all Minecraft colors";
	}

	@Override
	public String getSyntax()
	{
		return "tc";
	}
}
