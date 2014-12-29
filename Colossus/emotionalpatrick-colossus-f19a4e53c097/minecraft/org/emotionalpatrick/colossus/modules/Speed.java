package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.values.Values;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {

    public float movmentFactorL = 1.05F;
    public float movementFactorJ = 1.05F;
    
	public Speed() {
		super("Speed", ".speed", "Fast sprint", "Emotional Patrick", 1826905, Keyboard.KEY_V, "Modes");
	}
	
	@Override
	public void externalCommand (String s) {
		if (s.startsWith(".gs")) {
			String args[] = s.split(" ");
			try {
				Float f = Float.parseFloat(args[1]);
				Values.speedHack.setValue(f);
				Helper.addChat("Speed hack speed set to (" + f + ").");
			} catch (Exception err) {
				Helper.addChat("Invalid syntax, .gs (speed)");
			}
		}
	}

	@Override
	public void onTick() {
		boolean shouldSprint = Wrapper.getPlayer().movementInput.moveForward > 0.0F && !Wrapper.getPlayer().isSneaking();
		if (shouldSprint) {
			Wrapper.getPlayer().setSprinting(true);
		}
    	Wrapper.getPlayer().landMovementFactor *= 1.05;
    	Wrapper.getPlayer().jumpMovementFactor *= 1.05;
	}
	
	@Override
	public void onEnable() {
		this.movmentFactorL = Wrapper.getPlayer().landMovementFactor;
		this.movementFactorJ = Wrapper.getPlayer().jumpMovementFactor;
	}

	@Override
	public void onDisable() {
		Wrapper.getPlayer().landMovementFactor = this.movmentFactorL;
		Wrapper.getPlayer().jumpMovementFactor = this.movementFactorJ;
	}
}
