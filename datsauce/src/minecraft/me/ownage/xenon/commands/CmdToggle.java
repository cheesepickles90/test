package me.ownage.xenon.commands;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;

public class CmdToggle extends Command
{
	public CmdToggle()
	{
		super("toggle");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			boolean valid = false;
			for(XenonMod xMod: Hacks.hackList)
			{
				if(xMod.getName().trim().toLowerCase().equalsIgnoreCase(s.substring(7)))
				{
					xMod.toggle();
					Xenon.addChatMessage("Toggled " + xMod.getName() + ".");
					valid = true;
					break;
				}
			}
			
			if(!valid)
			{
				Xenon.addChatMessage("Invalid mod.");
			}
		}catch(Exception e)
		{
			Xenon.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "Toggles the specified hack";
	}

	@Override
	public String getSyntax()
	{
		return "toggle <name of hack>";
	}
}
