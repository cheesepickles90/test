package me.ownage.xenon;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;

public class LogicAutoTool {
	
	/*private static int lastBestItem = -1;
	private static HotbarInventory lastHotbar = null;*/
	
	/** returns slot number */
	/*public static int getBestTool(EntityPlayer player, boolean attacking) {
		player.in
	}
	
	private HotbarInventory getHotBarInventory(InventoryPlayer inv) {
		ArrayList<Integer> itz = new ArrayList<Integer>();
		for(int i = 36; i <= 44; i++) {
			itz.add(inv.getStackInSlot(i).itemID);
		}
		return new HotbarInventory((int[])(itz.toArray(new Integer[9])));
	}
	
	static class HotbarInventory {
		public int[] inventoryItems;
		
		public HotbarInventory(int[] items) throws Exception {
			if(items.length == 9) {
					this.inventoryItems = items;
			} else {
				throw new Exception("Arrays must contain nine integers.");
			}
		}
		
		public int getItemIdInSlot(int i) {
			return inventoryItems[i - 1];
		}
	}*/

}
