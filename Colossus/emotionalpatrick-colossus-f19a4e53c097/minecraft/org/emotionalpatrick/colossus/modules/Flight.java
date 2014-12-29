package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

public class Flight extends Module {
	
	private boolean flightMode = false;
	
	public Flight() {
		super("Flight", ".flight", "Allows your player to fly", "Emotional Patrick", 0x00CCFF, Keyboard.KEY_F, "Modes");
	}

	@Override
	public void onEnable() {
		if (!flightMode)
		Wrapper.getPlayer().capabilities.isFlying = true;
	}

	@Override
	public void onDisable() {
		if (!flightMode)
		Wrapper.getPlayer().capabilities.isFlying = false;
	}

	@Override
	public void onTick() {
		if (!flightMode)
		Wrapper.getPlayer().capabilities.isFlying = true;
		
		// Gay, but requested by iKinky (my slave master)
		if (flightMode) {
			Wrapper.getPlayer().onGround = false;
			Wrapper.getPlayer().motionX = 0;
			Wrapper.getPlayer().motionY = ((Keyboard
					.isKeyDown(Wrapper.getMinecraft().gameSettings.keyBindForward.keyCode) || Keyboard
					.isKeyDown(Wrapper.getMinecraft().gameSettings.keyBindBack.keyCode)) && !Wrapper
					.getPlayer().onGround) ? -(Wrapper
					.getPlayer().rotationPitch / 100) : 0;
			Wrapper.getPlayer().motionZ = 0;

			boolean forward = Wrapper.getMinecraft().gameSettings.keyBindForward.pressed
					&& Wrapper.getMinecraft().inGameHasFocus;
			boolean back = Wrapper.getMinecraft().gameSettings.keyBindBack.pressed
					&& Wrapper.getMinecraft().inGameHasFocus;
			boolean left = Wrapper.getMinecraft().gameSettings.keyBindLeft.pressed
					&& Wrapper.getMinecraft().inGameHasFocus;
			boolean right = Wrapper.getMinecraft().gameSettings.keyBindRight.pressed
					&& Wrapper.getMinecraft().inGameHasFocus;
			double d5 = Wrapper.getPlayer().rotationPitch + 90F;
			double d6 = Wrapper.getPlayer().rotationYaw + 90F;

			if (forward) {
				if (left) {
					d6 -= 45D;
				} else if (right) {
					d6 += 45D;
				}
			} else if (back) {
				d6 += 180D;
				if (left) {
					d6 += 45D;
				} else if (right) {
					d6 -= 45D;
				}
			} else if (left) {
				d6 -= 90D;
			} else if (right) {
				d6 += 90D;
			}

			if (forward || left || back || right) {
				Wrapper.getPlayer().motionX = Math.cos(Math
						.toRadians(d6)) * 0.7;
				Wrapper.getPlayer().motionZ = Math.sin(Math
						.toRadians(d6)) * 0.7;
			}
		}
	}
	
	@Override
	public void externalCommand (String s) {
		if (s.equalsIgnoreCase(".fm")) {
			flightMode = !flightMode;
			Helper.addChat(flightMode ? "Flight mode set to 3D." : "Flight mode set to creative.");
		}
	}
}
