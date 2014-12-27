package me.ownage.xenon.commands;

import me.ownage.xenon.hacks.classes.Freecam;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.FreecamEntity;

public class CmdFreecamPos extends Command {
	public CmdFreecamPos() {
		super("freecampos");
	}

	@Override
	public void runCommand(String s, String[] args) {
		FreecamEntity e = ((Freecam)Hacks.findMod(Freecam.class)).freecamEnt;
		if(e != null) {
			Xenon.addChatMessage("X: " + (int)e.posX + " Y: " + (int)e.posY + " Z: " + (int)e.posZ);
		}
	}

	@Override
	public String getDescription() {
		return "Returns freecam entity's position.";
	}

	@Override
	public String getSyntax() {
		return "freecampos";
	}
}
