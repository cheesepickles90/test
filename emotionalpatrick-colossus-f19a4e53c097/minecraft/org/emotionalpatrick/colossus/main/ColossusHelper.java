package org.emotionalpatrick.colossus.main;

import java.awt.Desktop;
import java.net.URI;

import net.minecraft.src.Block;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Potion;
import net.minecraft.src.StatCollector;
import net.minecraft.src.StringUtils;

import org.emotionalpatrick.colossus.modules.Module;
import org.emotionalpatrick.colossus.modules.NameProtect;
import org.emotionalpatrick.colossus.utilities.ChatColor;

public class ColossusHelper {

	public static void sendPacket(Packet packet) {
		if (!ColossusWrapper.getMinecraft().isSingleplayer()) {
			ColossusWrapper.getMinecraft().getSendQueue().addToSendQueue(packet);
		}
	}

	public static void sendChat(String message) {
		sendPacket(new Packet3Chat(message));
	}

	public static void addChat(String message) {
		if(message != null)
			ColossusWrapper.getPlayer().addChatMessage(ChatColor.GOLD + "[Colossus]: " + ChatColor.WHITE + message);
	}

	public static void addConsole(String message) {
		System.out.println("[Colossus]: " + message);
	}
	
	public static boolean isFriend (String s) {
		NameProtect protect = (NameProtect) Colossus.getManager().getModuleByName("NameProtect");
		String n = StringUtils.stripControlCodes(s);
		if (protect.getOriginalName().contains(s) || protect.getProtectedName().contains(n)) {
			return true;
		}
		return false;
	}

	public static String blockIDtoName(int id) {
		if (id <= 0)
			return "Nothing";
		String s = "Nothing";
		if ((Block.blocksList.length - 1 >= id) && Block.blocksList[id] != null
				&& Block.blocksList[id].translateBlockName() != null) {
			s = Block.blocksList[id].translateBlockName();
		} else if (Item.itemsList[id] != null
				&& Item.itemsList[id].getItemName() != null) {
			s = StatCollector.translateToLocal(Item.itemsList[id].getItemName()
					+ ".name");
		} else {
			s = "Nothing";
		}
		return s;
	}

	public static int blockNameToID(String s) {
		Block[] b = Block.blocksList;
		for (int i = 0; i < b.length; i++) {
			Block bl = b[i];
			if ((bl == null) || (bl.getBlockName() == null))
				continue;
			String str = bl.getBlockName().toLowerCase();
			if (str.contains("tile"))
				str = str.replace("tile.", "");
			else if (str.contains("block")) {
                str = str.replace("block", "");
                str = str + "block";
			} else if (str.contains("ore"))
                str = str.replace("ore", "");
                str = str + "ore";
			if (str.contains(s.toLowerCase()))
				return bl.blockID;
		}
		return -1;
	}
	
	public static EntityPlayer getNearestPlayer() {
		EntityPlayer nearest = null;
		if (ColossusWrapper.getWorld() == null) {
			return null;
		} else {
			for (Object o : ColossusWrapper.getWorld().playerEntities) {
				if (o != null && !(o instanceof EntityPlayerSP)) {
					EntityPlayer e = (EntityPlayer) o;
					if (!e.isDead) {
						if (nearest == null) {
							nearest = e;
						} else if (nearest.getDistanceToEntity(ColossusWrapper
								.getPlayer()) > e
								.getDistanceToEntity(ColossusWrapper
										.getPlayer())) {
							nearest = e;
						}
					}
				}
			}
			return nearest;
		}
	}

	public static void openURL(String url) {
		Desktop desktop = Desktop.getDesktop();
		try {
			URI uri = new URI(url);
			desktop.browse(uri);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void handleJump() {
		EntityClientPlayerMP ep = ColossusWrapper.getPlayer();
		ep.motionY = 0.41999998688697815D;
		if (ep.isPotionActive(Potion.jump)) {
			ep.motionY += (double) ((float) (ep.getActivePotionEffect(
					Potion.jump).getAmplifier() + 1) * 0.1F);
		}
		if (ep.isSprinting()) {
			float var27 = ep.rotationYaw * 0.017453292F;
			ep.motionX -= (double) (MathHelper.sin(var27) * 0.2F);
			ep.motionZ += (double) (MathHelper.cos(var27) * 0.2F);
		}
		ep.isAirBorne = true;
	}
}
