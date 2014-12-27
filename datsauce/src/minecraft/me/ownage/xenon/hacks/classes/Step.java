package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.Value;
import net.minecraft.src.EntityPlayerSP;

import org.lwjgl.input.Keyboard;

public class Step extends XenonMod {
	public Step() {
		super("Step", "Step up more than half of a block.", Keyboard.KEY_B, 0x777777, EnumGuiCategory.PLAYER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(mc.theWorld != null && mc.thePlayer != null) {
			if(isEnabled()) {
				if(mc.thePlayer.onGround && mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isInWater()) {
					mc.thePlayer.boundingBox.offset(0.0D, 1.0628, 0.0D);
					mc.thePlayer.motionY = -420;
					mc.thePlayer.isCollidedHorizontally = false;
				}
			}
		}
	}
}
