package me.ownage.xenon.commands;

import me.ownage.xenon.hacks.classes.Xray;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;

public class CmdXray extends Command
{
	public CmdXray()
	{
		super("xray");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			if(args[0].equalsIgnoreCase("add"))
			{
				int id = Integer.parseInt(args[1]);
				if(!((Xray)Hacks.findMod(Xray.class)).blocks.contains(id))
				{
					((Xray)Hacks.findMod(Xray.class)).blocks.add(id);
					Xenon.addChatMessage("Added " + id + " to xray list.");
					if(Hacks.findMod(Xray.class).isEnabled())
					{
						Xenon.getMinecraft().renderGlobal.loadRenderers();
					}
					Xenon.getFileManager().saveXrayList();
				}else
				{
					Xenon.addChatMessage(id + " is already in the xray list.");
				}
			}else if(args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("rem"))
			{
				int id = Integer.parseInt(args[1]);
				if(((Xray)Hacks.findMod(Xray.class)).blocks.contains(id))
				{
					((Xray)Hacks.findMod(Xray.class)).blocks.remove(((Xray)Hacks.findMod(Xray.class)).blocks.indexOf(id));
					Xenon.addChatMessage("Removed " + id + " from xray list.");
					if(Hacks.findMod(Xray.class).isEnabled())
					{
						Xenon.getMinecraft().renderGlobal.loadRenderers();
					}
					Xenon.getFileManager().saveXrayList();
				}else
				{
					Xenon.addChatMessage(id + " is not in the xray list.");
				}
			}
		}catch(Exception e)
		{
			Xenon.addChatMessage("Usage: " + getSyntax());
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() 
	{
		return "Custom xray.";
	}

	@Override
	public String getSyntax() 
	{
		return "xray add/del <block id>";
	}
}
