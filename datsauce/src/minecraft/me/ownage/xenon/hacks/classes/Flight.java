package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.Value;
import net.minecraft.src.EntityPlayerSP;

import org.lwjgl.input.Keyboard;

public class Flight extends XenonMod {
	public static Value flySpeed = new Value("Fly");
	
	public Flight() {
		super("Flight", "Flying mode.", Keyboard.KEY_R, 0xFFA500, EnumGuiCategory.PLAYER);
	}
	
	@Override
	public void onPlayerUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			player.motionX = 0;
			player.motionY = 0;
			player.motionZ = 0;
			player.landMovementFactor = flySpeed.getValue();
			player.jumpMovementFactor = flySpeed.getValue();
			if(mc.inGameHasFocus) {
				if(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.keyCode)) {
					player.motionY += flySpeed.getValue() / 2 + 0.2F;
				}
				if(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.keyCode)) {
					player.motionY -= flySpeed.getValue() / 2 + 0.2F;
				}
			}
		}
	}
}
