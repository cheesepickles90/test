package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.Block;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.ItemStack;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.values.Values;
import org.lwjgl.input.Keyboard;

public class Speedmine extends Module {
	
	public static int blockHitSpeed = 0;
	
	public Speedmine() {
		super("Speedy Gonzales", ".speedy", "Mine faster", "Emotional Patrick", 0xE0A341, Keyboard.KEY_O, "Modes");
	}
	
	@Override
	public void externalCommand (String s) {
		if (s.startsWith(".ms")) {
			String args[] = s.split(" ");
			try {
				Float f = Float.parseFloat(args[1]);
				Values.mineSpeed.setValue(f);
				Helper.addChat("Mine speed set to (" + f + ").");
			} catch (Exception err) {
				Helper.addChat("Invalid syntax, .ms (mine speed)");
			}
		}
		if (s.startsWith(".bhs")) {
			String args[] = s.split(" ");
			try {
				int hitDelay = Integer.parseInt(args[1]);
				setBlockHitSpeed(hitDelay);
				Helper.addChat("Block hit speed set to (" + hitDelay + ").");
			} catch (Exception err) {
				Helper.addChat("Invalid syntax, .bhs (block hit speed)");
			}
		}
	}

	@Override
	public float setCurBlockDamage(Block b, int i1, int i2, int i3, float i4) {
		float damage = b.getPlayerRelativeBlockHardness(
				Wrapper.getPlayer(), Wrapper.getWorld(), i1, i2, i3);
		return damage * Values.mineSpeed.getValue();
	}
    
	@Override
	public int onBlockHitDelay(int i) {
		return getBlockHitSpeed();
	}
	
	@Override
	public void onClickBlock(int i, int j, int k, int l) {
		autoTool(i, j, k);
	}
    
	private void autoTool(int i, int j, int k) {
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

	public static int getBlockHitSpeed() {
		return blockHitSpeed;
	}

	public static void setBlockHitSpeed(int blockHitSpeed) {
		Speedmine.blockHitSpeed = blockHitSpeed;
	}
}
