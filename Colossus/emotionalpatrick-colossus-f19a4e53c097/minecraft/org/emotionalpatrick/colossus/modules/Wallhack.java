package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.Block;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.values.Values;
import org.lwjgl.input.Keyboard;

public class Wallhack extends Module {
	
	public Wallhack() {
		super("Wallhack", ".wallhack", "Opacity Xray", "Ramisme", 0x7C9E9A, Keyboard.KEY_X, "World");
	}
	
	@Override
	public void externalCommand (String s) {
		if (s.startsWith(".wh")) {
			String[] sp = s.split(" ");
			try {
				float a = Float.parseFloat(sp[1]);
				Values.worldAlpha.setValue(a);
				if (this.isEnabled()) {
					Wrapper.getGlobal().loadRenderers();
				}
				Helper.addChat("Wallhack opacity set to (" + a + ").");
			} catch (Exception e) {
				Helper.addChat("Invalid syntax, .wh (opacity)");
			}
		}
	}
	
	@Override
	public float setGammaSetting (float f) {
		return 200F;	
	}
	
	@Override
	public int setBlockOpacity() {
		return (int) Values.worldAlpha.getValue();
	}

	@Override
	public boolean onRenderAllFaces(Block b, int i, int j, int k) {
		return b.blockID == 10 || b.blockID == 11 || b.blockID == 46
				|| b.blockID == 56 || b.blockID == 15 || b.blockID == 16
				|| b.blockID == 54 || b.blockID == 48 || b.blockID == 52;
	}

	@Override
	public int onRenderBlockPass(int i, Block b) {
		return b.blockID == 10 || b.blockID == 11 || b.blockID == 46
				|| b.blockID == 56 || b.blockID == 15 || b.blockID == 16
				|| b.blockID == 54 || b.blockID == 48 || b.blockID == 52 ? 0 : 1;
	}

	@Override
	public float setBlockBrightness(float i) {
		return 1000F;
	}

	@Override
	public int setBlockBrightnessInteger(int i) {
		return 1000;
	}
	
	@Override
	public void onToggle() {
		super.onToggle();
		Wrapper.getGlobal().loadRenderers();
	}
}
