package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;
import net.minecraft.src.EntityPlayerSP;

import org.lwjgl.input.Keyboard;

public class Sprint extends XenonMod {
	public Sprint() {
		super("Sprint", "Always sprinting.", Keyboard.KEY_F, 0x2E2EE6, EnumGuiCategory.PLAYER);
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			if(player.movementInput.moveForward > 0.0F && !player.isSneaking()) {
				player.setSprinting(true);
			}
		}
	}
}
