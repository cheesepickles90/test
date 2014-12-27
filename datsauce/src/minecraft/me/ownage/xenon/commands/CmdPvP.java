package me.ownage.xenon.commands;



import me.ownage.xenon.main.Xenon;
import net.minecraft.src.Packet3Chat;


public class CmdPvP extends Command
{
	public CmdPvP()
	{
		super("pvp");
	}

	@Override
	public void runCommand(String s, String[] args) {
		Xenon.getMinecraft().getSendQueue().addToSendQueue(new Packet3Chat("/i 276"));
		Xenon.getMinecraft().getSendQueue().addToSendQueue(new Packet3Chat("/i diamondhelmet"));
		Xenon.getMinecraft().getSendQueue().addToSendQueue(new Packet3Chat("/i diamondchest"));
		Xenon.getMinecraft().getSendQueue().addToSendQueue(new Packet3Chat("/i diamondpants"));
		Xenon.getMinecraft().getSendQueue().addToSendQueue(new Packet3Chat("/i diamondboots"));
	}

	@Override
	public String getDescription()
	{
		return "Gives you all items for PvP battles.";
	}

	@Override
	public String getSyntax()
	{
		return "pvp";
	}
}
