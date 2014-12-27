package me.ownage.xenon.commands;

import net.minecraft.src.RenderManager;
import me.ownage.xenon.hacks.classes.Freecam;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.FreecamEntity;
import me.ownage.xenon.util.Waypoint;

public class CmdWaypoint extends Command {
	public CmdWaypoint() {
		super("waypoint");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			if(args[0].equalsIgnoreCase("add")) {
				try {
					for(Waypoint w : Waypoint.wayPoints) {
						if(w.getName().equals(args[1])) {
							Xenon.addChatMessage("\"" + args[1] + "\"" + " already exists.");
							return;
						}
					}
					if(Hacks.findMod(Freecam.class).isEnabled()) {
						FreecamEntity e = ((Freecam)Hacks.findMod(Freecam.class)).freecamEnt;
						Waypoint point = new Waypoint(args[1], e.posX, e.posY, e.posZ, RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ);
					} else {
						Waypoint point = new Waypoint(args[1], Xenon.getMinecraft().thePlayer.posX, Xenon.getMinecraft().thePlayer.posY, Xenon.getMinecraft().thePlayer.posZ, RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ);
					}
					Xenon.addChatMessage("Added waypoint \"" + args[1] + "\"");
				} catch(Exception e) {
					Xenon.addChatMessage("Usage: " + getSyntax());
				}
			} else if(args[0].equalsIgnoreCase("del")) {
				try {
					for(Waypoint w : Waypoint.wayPoints) {
						if(w.getName().equals(args[1])) {
							Waypoint.wayPoints.remove(w);
							Xenon.addChatMessage("Removed waypoint: \"" + args[1] + "\".");
							break;
						} else {
							Xenon.addChatMessage(args[1] + " does not exist.");
						}
					}
				} catch(Exception e) {
					Xenon.addChatMessage("Usage: " + getSyntax());
				}
			} else if(args[0].equalsIgnoreCase("clear")) {
				Waypoint.wayPoints.clear();
				Xenon.addChatMessage("Cleared waypoints.");
			}
		} catch(Exception e) {
			Xenon.addChatMessage("Usage: " + getSyntax());
		}
	}
	
	@Override
	public String getDescription() {
		return "Adds and removes waypoints";
	}

	@Override
	public String getSyntax() {
		return "waypoint add/del <name>";
	}
}
