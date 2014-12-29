package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.values.Values;

public class Step extends Module {
	
	public Step() {
		super("Step", ".step", "Step height modifier", "Emotional Patrick");
		this.enabled = true;
	}
	
	@Override
	public void externalCommand (String cmd) {
		if (cmd.startsWith(".sh")) {
			try {
				String[] nig = cmd.split(" ");
				String penis = nig[1];
				float speed = Float.parseFloat(penis);
				Values.stepHeight.setValue(speed);
				Helper.addChat("Step height set to " + speed);
			} catch (Exception e) {
				Helper.addChat("Invalid syntax, .sh (height)");
			}
		}
	}
	
	@Override
	public void onTick() {
		if (this.isEnabled()) {
			Wrapper.getPlayer().stepHeight = Values.stepHeight.getValue();
		}else{
			Wrapper.getPlayer().stepHeight = 0.5f;
		}
	}
}
