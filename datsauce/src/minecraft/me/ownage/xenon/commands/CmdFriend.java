package me.ownage.xenon.commands;

import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.Friend;

public class CmdFriend extends Command
{
	public CmdFriend()
	{
		super("friend");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			if(args[0].equalsIgnoreCase("add"))
			{
				String name = args[1];
				String alias = args[2];
				if(!Xenon.getFriends().isFriend(name))
				{
					Xenon.addChatMessage("Protected " + name + " as " + alias + ".");
					Xenon.getFriends().addFriend(name, alias);
					Xenon.getFileManager().saveFriends();
				}else
				{
					Xenon.addChatMessage(name + " is already your friend.");
				}
			}
			if(args[0].equalsIgnoreCase("del"))
			{
				String name = args[1];
				if(Xenon.getFriends().isFriend(name))
				{
					Xenon.getFriends().removeFriend(name);
					Xenon.addChatMessage("Removed " + name + " from friends.");
					Xenon.getFileManager().saveFriends();
				}else
				{
					Xenon.addChatMessage(name + " is not your friend.");
				}
			}
			if(args[0].equalsIgnoreCase("list"))
			{
				for(Friend friend: Xenon.getFriends().friendsList)
				{
					Xenon.addChatMessage(friend.getAlias());
				}
				Xenon.addChatMessage(Xenon.getFriends().friendsList.size() + " friend(s).");
			}
			if(args[0].equalsIgnoreCase("clear"))
			{
				try
				{
					Xenon.getFriends().friendsList.clear();
					Xenon.getFileManager().saveFriends();
					Xenon.addChatMessage("Cleared friends.");
				}catch(Exception e) {}
			}
		}catch(Exception e)
		{
			Xenon.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "Adds and removes friends.";
	}

	@Override
	public String getSyntax()
	{
		return "friend add <name> <alias>, friend del <name>";
	}
}
