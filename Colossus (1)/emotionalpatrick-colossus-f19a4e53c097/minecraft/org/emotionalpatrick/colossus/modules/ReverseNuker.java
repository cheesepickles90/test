package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet15Place;

public class ReverseNuker extends Module {

	private Thread reverseNuker;

	public ReverseNuker() {
		super("Reverse Nuker", ".reversenuker", "Reverse nuker", "Emotional Patrick", 0x008B8B, Keyboard.KEY_Y, "World");
	}

	@Override
	public void onClickBlock(int i) {
		if (i == 1) {
			handleReverseNuker();
		}
	}
	
	private void handleReverseNuker() {
		if (reverseNuker != null && reverseNuker.isAlive()) {
			reverseNuker.interrupt();
		}
		else
			reverseNuker = new Thread(new Runnable() {
				@Override
				public void run() {
					int range = 6;
					for (int y = -range; y < range; y++) {
						for (int x = -range; x < range; x++) {
							for (int z = -range; z < range; z++) {
								int posX = (int) (x + Wrapper.getPlayer().posX);
								int posY = (int) (y + Wrapper.getPlayer().posY); 
								int	posZ = (int) (z + Wrapper.getPlayer().posZ);
								if (Wrapper.getWorld().blockExists(
										MathHelper.floor_double(posX),
										MathHelper.floor_double(posY - 1),
										MathHelper.floor_double(posZ)))
									Helper.sendPacket(new Packet15Place(posX, posY, posZ, 1, Wrapper.getPlayer().getCurrentEquippedItem(), 0, 0, 0));
								else	
									continue;
							}
						}
					}
				}
			});
		if (reverseNuker != null && !reverseNuker.isAlive()) {
			reverseNuker.start();
		}
	}
}