package me.ownage.xenon.commands;

import net.minecraft.src.EntityPlayer;
import me.ownage.xenon.main.Xenon;

public class CmdView extends Command {
	public CmdView() {
		super("view");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			if(args[0].equalsIgnoreCase("off")) {
				Xenon.getMinecraft().renderViewEntity = Xenon.getMinecraft().thePlayer;
				Xenon.addChatMessage("Now viewing normally.");
				return;
			}
			for(Object o : Xenon.getMinecraft().theWorld.loadedEntityList) {
				if(o instanceof EntityPlayer) {
					EntityPlayer e = (EntityPlayer) o;
					if(e.username.equalsIgnoreCase(args[0])) {
						Xenon.getMinecraft().renderViewEntity = e;
					}
				}
			}
		} catch(Exception e) {
			Xenon.addChatMessage("Invalid player or arguments. Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Lets you see another player's camera";
	}

	@Override
	public String getSyntax() {
		return "view <name>";
	}
}
