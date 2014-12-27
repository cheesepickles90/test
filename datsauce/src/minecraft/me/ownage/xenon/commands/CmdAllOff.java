package me.ownage.xenon.commands;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;

public class CmdAllOff extends Command
{
	public CmdAllOff() 
	{
		super("alloff");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		int count = 0;
		for(XenonMod mod: Hacks.hackList)
		{
			if(mod.isEnabled() && !mod.getName().equalsIgnoreCase("ttf chat"))
			{
				mod.toggle();
				count++;
			}
		}
		
		Xenon.addChatMessage(count + (count == 1 ? " hack" : " hacks") + " turned off.");
	}

	@Override
	public String getDescription()
	{
		return "Turns all hacks off.";
	}

	@Override
	public String getSyntax()
	{
		return "alloff";
	}
}
