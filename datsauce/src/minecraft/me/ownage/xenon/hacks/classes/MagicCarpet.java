package me.ownage.xenon.hacks.classes;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;

import org.lwjgl.input.Keyboard;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;

public class MagicCarpet extends XenonMod {
	public MagicCarpet() {
		super("Magic Carpet", "Places blocks around you, creating a \"magic carpet\".", Keyboard.KEY_RBRACKET, 0xFFFFFF, EnumGuiCategory.WORLD);
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			if(player.movementInput.moveForward != 0.0F || player.isSneaking() || player.isJumping && player.inventory.getCurrentItem() != null) {
				int maxH = 0;
				int minH = 0;
				int maxW = 1;
				int minW = -1;
	
				for(int y = maxH; y >= minH; y--) {
					for(int z = minW; z <= maxW; z++) {
						for(int x = minW; x <= maxW; x++) {
							int xPos = (int) Math.round(mc.thePlayer.posX + x);
							int yPos = ((int) Math.round(mc.thePlayer.posY + y)) - 2;
							int zPos = (int) Math.round(mc.thePlayer.posZ + z);
							
							int id = mc.theWorld.getBlockId(xPos, yPos, zPos);
	
							if(id == Block.tallGrass.blockID) {
								yPos -= 1;
								id = mc.theWorld.getBlockId(xPos, yPos, zPos);
							}
							
							if(!player.isSneaking() && player.inventory.getCurrentItem() != null && id != player.inventory.getCurrentItem().itemID) {
								mc.getSendQueue().addToSendQueue(new Packet15Place(xPos, yPos , zPos, 0, player.inventory.getCurrentItem(), xPos, yPos, zPos));
							}
							
							int remID = mc.theWorld.getBlockId(xPos, yPos - 1, zPos);
							int remPlaceID = mc.theWorld.getBlockId(xPos, yPos - 2, zPos);
							
							if(player.isSneaking() && player.inventory.getCurrentItem() != null && remID == player.inventory.getCurrentItem().itemID) {
								mc.getSendQueue().addToSendQueue(new Packet14BlockDig(0, xPos, yPos - 1, zPos, 1));
								mc.getSendQueue().addToSendQueue(new Packet14BlockDig(2, xPos, yPos - 1, zPos, 1));
								
								if(remPlaceID <= 0 && player.inventory.getCurrentItem() != null) {
									mc.getSendQueue().addToSendQueue(new Packet15Place(xPos, yPos - 2, zPos, 0, player.inventory.getCurrentItem(), xPos, yPos, zPos));
								}
							}
						}
					}
				}
			}
		}
	}
}
