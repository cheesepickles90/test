package me.ownage.xenon.hacks.classes;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.RenderManager;

import org.lwjgl.input.Keyboard;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.XenonUtils;

public class Dolphin extends XenonMod {
	public Dolphin() {
		super("Dolphin", "Automatically swim faster than vanilla.", Keyboard.KEY_J, 0x3399FF, EnumGuiCategory.WORLD);
	}
	
	private int count = 0;
	
	@Override
	public void onPlayerUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			count++;
			if(count >= 3) {
				if(mc.thePlayer.handleWaterMovement()) {
					mc.thePlayer.motionY = 0.1D;
				}
				count = 0;
			}
		}
	}
}