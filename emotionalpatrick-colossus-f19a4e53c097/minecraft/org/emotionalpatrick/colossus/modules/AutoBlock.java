package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;

import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

public class AutoBlock extends Module {

	public AutoBlock() {
		super("AutoBlock", ".autoblock", "Automagically blocks", "Emotional Patrick", 0xC0C0C0, Keyboard.KEY_NONE, "Kill Aura");
	}
	
	@Override
	public void onTick() {
		if (Wrapper.getPlayer().getCurrentEquippedItem() != null
				&& Wrapper.getMinecraft().inGameHasFocus && Wrapper.getWorld() != null
				&& Wrapper.getPlayer().swingProgress > 0
				&& isItemSword()) {
			Wrapper.getPlayer().getCurrentEquippedItem().useItemRightClick(
					Wrapper.getMinecraft().theWorld, Wrapper.getPlayer());
		}
	}
	
	public boolean isItemSword() {
		try {
			InventoryPlayer inventory = Wrapper.getPlayer().inventory;
			if (inventory.getStackInSlot(inventory.currentItem) != null
					&& inventory.getStackInSlot(inventory.currentItem)
							.getItem() == Item.swordDiamond
					|| inventory.getStackInSlot(inventory.currentItem)
							.getItem() == Item.swordGold
					|| inventory.getStackInSlot(inventory.currentItem)
							.getItem() == Item.swordSteel
					|| inventory.getStackInSlot(inventory.currentItem)
							.getItem() == Item.swordStone
					|| inventory.getStackInSlot(inventory.currentItem)
							.getItem() == Item.swordWood) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
