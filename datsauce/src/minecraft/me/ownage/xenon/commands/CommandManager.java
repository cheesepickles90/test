package me.ownage.xenon.commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import me.ownage.xenon.gui.XenonGuiClick;
import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.hacks.classes.GUI;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;

public class CommandManager
{
	public static ArrayList<Command> commands = new ArrayList<Command>();

	public static char cmdPrefix = '.';

	public CommandManager()
	{
		addCommands();
	}

	public void addCommands()
	{
		commands.clear();
		commands.add(new CmdToggle());
		commands.add(new CmdBind());
		commands.add(new CmdXray());
		commands.add(new CmdTestColors());
		commands.add(new CmdHelp());
		commands.add(new CmdFriend());
		commands.add(new CmdAllOff());
		commands.add(new CmdPvP());
		commands.add(new CmdBreadcrumb());
		commands.add(new CmdInsult());
		commands.add(new CmdTeleport());
		commands.add(new CmdWaypoint());
		commands.add(new CmdSay());
		commands.add(new CmdLegit());
		commands.add(new CmdFreecamPos());
		commands.add(new CmdView());
		commands.add(new CmdFSpam());
		commands.add(new CmdTTF());
		commands.add(new CmdVClip());
		commands.add(new CmdMacro());
		commands.add(new CmdPrefix());
		commands.add(new CmdSearch());
		commands.add(new CmdPickupLine());
	}

	public void runCommands(String s)
	{
		if(!s.contains(Character.toString(cmdPrefix)) || !s.startsWith(Character.toString(cmdPrefix))) return;

		boolean commandResolved = false;
		String readString = s.trim().substring(Character.toString(cmdPrefix).length()).trim();
		boolean hasArgs = readString.trim().contains(" ");
		String commandName = hasArgs ? readString.split(" ")[0] : readString.trim();
		String[] args = hasArgs ? readString.substring(commandName.length()).trim().split(" ") : new String[0];

		for(Command command: commands)
		{
			if(command.getCommand().trim().equalsIgnoreCase(commandName.trim())) 
			{
				command.runCommand(readString, args);
				commandResolved = true;
				break;
			}
		}
		
		if(s.equalsIgnoreCase(".reload")) {
			Xenon.addChatMessage("Initializing Xenon...");
			Xenon.onStart();
			Xenon.addChatMessage("Initialized Xenon.");
			return;
		}
		
		for(XenonMod mod : Hacks.hackList) {
			try {
				if(s.startsWith("."  + mod.getName().toLowerCase().replace(" ", "")) || s.startsWith("." + ((String)mod.original[0]).toLowerCase().replace(" ", ""))) {
					commandResolved = true;
					String[] argz = s.split(" ");
					try {
						if(argz[1].equalsIgnoreCase("name")) {
							try {
								mod.setName(argz[2]);
								Xenon.addChatMessage("Name of \"" + mod.original[0] + "\"" + " changed to: \"" + mod.getName() + "\"");
							} catch(Exception e) {
								Xenon.addChatMessage("Invalid name.");
							}
						} else if(argz[1].equalsIgnoreCase("color")) {
							try {
								mod.setColor(Color.decode(argz[2].contains("#") ? argz[2] : "#" + argz[2]).getRGB());
								Xenon.addChatMessage("Changed color of: \"" + mod.getName() + "\"" + " to: " + argz[2]);
							} catch(Exception e) {
								Xenon.addChatMessage("Invalid color.");
							}
						} else if(argz[1].equalsIgnoreCase("reset")) {
							try {
								mod.setName((String)mod.original[0]);
								mod.setColor((Integer)mod.original[1]);
								Xenon.addChatMessage("Reset \"" + mod.getName() + "\" to defaults.");
							} catch(Exception e) {
								Xenon.addChatMessage("Failed to reset.");
							}
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		if(!commandResolved)
		{
			Xenon.addChatMessage("Invalid command. Type .help for a list of commands.");
		}
	}
}
