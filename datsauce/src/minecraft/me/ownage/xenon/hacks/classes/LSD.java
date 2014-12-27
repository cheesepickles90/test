package me.ownage.xenon.hacks.classes;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.EntityPlayerSP;
import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;

public class LSD extends XenonMod {
	public LSD() {
		super("LSD", "Pretty colors!", Keyboard.KEY_DOWN, EnumGuiCategory.ESP);
	}
	
	private int count = 0;
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			count++;
			if(count >= 5) {
		        int var0 = (int)player.posX;
		        int var1 = (int)player.posY;
		        int var2 = (int)player.posZ;
		        mc.renderGlobal.markBlockRangeForRenderUpdate(var0 - 200, var1 - 200, var2 - 200, var0 + 200, var1 + 200, var2 + 200);
		        count = 0;
			}
		}
	}
	
	@Override
	public void onToggled() {
		mc.renderGlobal.loadRenderers();
	}
}
