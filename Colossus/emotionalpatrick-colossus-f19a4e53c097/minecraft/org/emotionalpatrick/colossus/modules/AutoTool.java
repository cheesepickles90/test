package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

public class AutoTool extends Module {

	public AutoTool() {
		super("AutoTool", ".autotool", "Automagically gets the best tool", "Emotional Patrick", 0x48D1CC, Keyboard.KEY_PERIOD);
	}
	
	@Override
	public void onClickBlock(int i, int j, int k, int l) {
		this.autoTool(i, j, k);
	}
	
	@Override
	public void onAttackEntity(EntityPlayer p, Entity e) {
		getBestWeapon();
	}
    
	public void getBestWeapon() {
		float damageModifier = 1;
		int newItem = -1;
		for (int slot = 0; slot < 9; slot++) {
			ItemStack stack = Wrapper.getPlayer().inventory.mainInventory[slot];
			if (stack == null) 
				continue;
			float dmg = stack.getDamageVsEntity(Wrapper.getPlayer());
			if (dmg > damageModifier) {
				newItem = slot;
				damageModifier = dmg;
			}
		}
		
		if (newItem > -1)
			Wrapper.getPlayer().inventory.currentItem = newItem;
	}
	
	public void autoTool(int i, int j, int k) {
		int blockID = Wrapper.getWorld().getBlockId(i, j, k);
		if (blockID != 0) {
			EntityClientPlayerMP ep = Wrapper.getPlayer();
			float s = 0.1F;
			int currentItem = Wrapper.getPlayer().inventory.currentItem;
			for (int var8 = 36; var8 < 45; ++var8) {
				ItemStack is = ep.openContainer.getSlot(var8).getStack();
				if (is != null) {
					float strength = is
							.getStrVsBlock(Block.blocksList[blockID]);
					if (strength > s) {
						s = strength;
						currentItem = var8 - 36;
					}
				}
			}
			Wrapper.getPlayer().inventory.currentItem = currentItem;
		}
	}
}
