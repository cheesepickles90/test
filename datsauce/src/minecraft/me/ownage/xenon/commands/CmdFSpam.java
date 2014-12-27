package me.ownage.xenon.commands;

import java.util.Random;

import net.minecraft.src.Packet3Chat;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.XenonUtils;

public class CmdFSpam extends Command {
	public CmdFSpam() {
		super("fspam");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			String name = args[0];
			for(int i = 0; i < Integer.parseInt(args[1]); i++) {
				Random r = new Random();
				int random = r.nextInt(100);
				XenonUtils.sendPacket(new Packet3Chat("/f create " + name + random));
				XenonUtils.sendPacket(new Packet3Chat("/f desc " + name + random));
				XenonUtils.sendPacket(new Packet3Chat("/f disband"));
			}
			Xenon.addChatMessage("Done.");
		} catch(Exception e) {
			e.printStackTrace();
			Xenon.addChatMessage("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Spams factions.";
	}

	@Override
	public String getSyntax() {
		return "fspam <prefix> <number of factions>";
	}
}
