package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet15Place;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

public class AutoSoup extends Module {

	private long current, last = -1L;
	private boolean switchSoup;

	private final int MUSHROOM_SOUP = Item.bowlSoup.itemID, MUSHROOM_BOWL = Item.bowlEmpty.itemID;

	public AutoSoup() {
		super("AutoSoup", ".autosoup", "Automagically eat soup", "Ramisme", -1179427, Keyboard.KEY_EQUALS, "Kill Aura");
	}

	@Override
	public void preMotionUpdate() {
		if (Wrapper.getPlayer().getHealth() <= 16) 
			getSoup();
	}

	private void getSoup() {
		current = getCurrentMilliseconds();
		ItemStack itemstack = null;
		int soupSlot = -1;

		for (int slot = 0; slot < 36; slot++) {
			ItemStack stack = Wrapper.getPlayer().inventory.mainInventory[slot];
			if(stack == null)
				continue;

			if(!(stack.itemID == MUSHROOM_SOUP))
				continue;

			if (slot < 9) {
				soupSlot = slot;
				itemstack = stack;
				continue;
			} else if (slot > 9 && isHotbarEmpty() && hasCurrentDelay(100L)) {
				soupSlot = slot;
				itemstack = stack;
			}
		}

		if((soupSlot < 0) || itemstack == null)
			return;

		if (soupSlot < 9) {
			Wrapper.getPlayer().inventory.currentItem = soupSlot;
			Wrapper.getController().updateController();
			Helper.sendPacket(new Packet15Place(-1, -1, -1, -1, itemstack, 0F, 0F, 0F));
			last = current;
			return;
		} else if (soupSlot > 9 && isHotbarEmpty()) {
			Wrapper.getController().windowClick(0, soupSlot, 0,
					0, Wrapper.getPlayer());
			Wrapper.getController().windowClick(0, getFirstAvailableSlot(), 0,
					0, Wrapper.getPlayer());
			last = current;
		}

	}

	private int getFirstAvailableSlot() {
		for(int slot = 36; slot < 44; slot++) {
			ItemStack itemstack = Wrapper.getPlayer().inventoryContainer.getSlot(slot).getStack();
			if(itemstack == null)
				continue;

			if(itemstack.itemID == MUSHROOM_BOWL)
				return slot;
		}

		return -1;
	}

	private boolean isHotbarEmpty() {
		boolean flag = true;

		for(int slot = 0; slot < 9; slot++) {
			ItemStack itemstack = Wrapper.getPlayer().inventory.mainInventory[slot];
			if(itemstack == null)
				continue;

			if(itemstack.itemID == MUSHROOM_SOUP)
				flag = false;
		}

		return flag;
	}
	
	public long getCurrentMilliseconds() {
		return System.nanoTime() / 1000000;
	}

	private boolean hasCurrentDelay(long l) {
		return ((current - last) >= l);
	}
}
