package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.Material;

import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

public class Glide extends Module {

	public Glide() {
		super("Glide", ".glide", "Makes your player glide", "Emotional Patrick", 0xADFF2F, Keyboard.KEY_NONE);
	}

	@Override
	public void onTick() {
		EntityClientPlayerMP player = Wrapper.getPlayer();
		if (player.motionY < 0.0D && !player.onGround && player.isAirBorne
				&& !player.isInWater()
				&& !player.isInsideOfMaterial(Material.lava)) {
			player.motionY = -0.15D;
		}
	}
}
