package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.XenonUtils;
import net.minecraft.src.*;

import org.lwjgl.input.Keyboard;




public class AutoSoup extends XenonMod {
    private long currentMS = 0L;
    private long lastSoup = -1L;
	
	public AutoSoup() {
		super("Auto Soup", "Automatically eats soup on Kit PvP servers.", Keyboard.KEY_PERIOD, 0xFF0066, EnumGuiCategory.AURA);
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled() && mc.thePlayer.getHealth() <= 14) {
			eatSoup();
			return;
		}
	}

	private void eatSoup() {
		currentMS = System.nanoTime() / 1000000;
		boolean foundSoup = false;
		
		for(int slot = 44; slot >= 9; slot--) {
			ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
			
			if(stack != null) {
				if(slot >= 36 && slot <= 44) {
					if(stack.itemID  == 282) {
						mc.thePlayer.inventory.currentItem = slot - 36;
						XenonUtils.sendPacket(new Packet15Place(-1, -1, -1, -1, mc.thePlayer.inventory.getCurrentItem(), 0F, 0F, 0F));
						foundSoup = true;
						lastSoup = System.nanoTime() / 1000000;
						return;
					}
				} else if(stack.itemID == 282) {
					mc.playerController.windowClick(0, slot, 0, 0, mc.thePlayer);
					mc.playerController.windowClick(0, 37, 0, 0, mc.thePlayer);
					foundSoup = true;
					lastSoup = System.nanoTime() / 1000000;
				}
			}
		}
	}
	
	private boolean hasDelayRun(long l) {
		return ((currentMS - lastSoup) >= l);
	}
}
