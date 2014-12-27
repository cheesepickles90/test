package me.ownage.xenon.commands;

import net.minecraft.src.Packet11PlayerPosition;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.XenonUtils;

public class CmdVClip extends Command {
	public CmdVClip() {
		super("vclip");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			int clip = Integer.parseInt(args[0]);
			setPos(Xenon.getMinecraft().thePlayer.posX, Xenon.getMinecraft().thePlayer.posY + clip, Xenon.getMinecraft().thePlayer.posZ);
			Xenon.addChatMessage("Teleported " + clip + " blocks.");
		} catch(Exception e) {
			e.printStackTrace();
			Xenon.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Allows you to teleport vertically.";
	}

	@Override
	public String getSyntax() {
		return "vclip <amount>";
	}

	private void setPos(double d, double d1, double d2) {
		Xenon.getMinecraft().thePlayer.setPosition(d, d1, d2);
		double d3 = Xenon.getMinecraft().thePlayer.posX;
		double d4 = Xenon.getMinecraft().thePlayer.boundingBox.minY;
		double d5 = Xenon.getMinecraft().thePlayer.posY;
		double d6 = Xenon.getMinecraft().thePlayer.posZ;
		XenonUtils.sendPacket(new Packet11PlayerPosition(d3, d4, d5, d6, Xenon.getMinecraft().thePlayer.onGround));
	}
}
