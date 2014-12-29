package org.emotionalpatrick.colossus.modules;

import java.util.Iterator;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;

import net.minecraft.src.EntityFishHook;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet15Place;

public class AutoFish extends Module {

	public AutoFish() {
		super("AutoFish", ".autofish", "Automagically fishes", "Ramisme");
	}
	
	@Override
	public void onTick() {
		Iterator i = Wrapper.getWorld().loadedEntityList.iterator();
		while (i.hasNext()) {
			Object o = i.next();
			ItemStack stack = Wrapper.getPlayer().inventory
					.getCurrentItem();
			if (stack == null) {
				return;
			}
			if (o instanceof EntityFishHook && stack.itemID == 346) {
				EntityFishHook f = (EntityFishHook) o;
				if (f.motionX == 0.0D && f.motionZ == 0.0D && f.motionY != 0.0D) {
					Helper.sendPacket(new Packet15Place(-1, -1, -1, -1,
							Wrapper.getPlayer().inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
					break;
				}
			}
		}
	}
}
