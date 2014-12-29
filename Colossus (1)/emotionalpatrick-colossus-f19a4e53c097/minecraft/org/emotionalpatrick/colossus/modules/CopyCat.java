package org.emotionalpatrick.colossus.modules;

import java.util.Iterator;

import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.UpdateRotation;
import org.emotionalpatrick.colossus.utilities.color.ChatColor;
import org.lwjgl.input.Keyboard;

import net.minecraft.src.*;

public class CopyCat extends Module {

	private EntityPlayer copycatTarget;

	private final UpdateRotation updateRotation = new UpdateRotation();

	public CopyCat() {
		super("CopyCat", ".copycat", "Copies other players", "Emotional Patrick", 0xC456FF, Keyboard.KEY_NONE, "Modes");
	}
	
	@Override
	public void externalCommand (String s) {
		if (s.startsWith(".ct")) {
			try {
				String args[] = s.split(" ");
				if (args[1].equalsIgnoreCase("clear")) {
					setCopycatTarget(null);
					Helper.addChat("CopyCat target cleared.");
				}
				if (args[1].startsWith("set")) {
					String target = args[2];
					setCopycatTarget(fetchTarget(target));
					Helper.addChat("CopyCat target set to \"" + target + "\".");
				}
			} catch (Exception err) {
				Helper.addChat("Invalid syntax, .ct (set/clear) (username)");
			}
		}
	}
	
	@Override
	public void preMotionUpdate() {
        this.updateRotation.savePrevRotations(Wrapper.getPlayer());
	}

	@Override
	public void onTick() {
		if (this.copycatTarget != null) {
			double targetPositionY = this.copycatTarget.posY * 32.0D;
			long targetY = Math.round(targetPositionY) / 32L;

			double playerPositionY = Wrapper.getPlayer().posY * 32.0D;
			long playerY = Math.round(playerPositionY) / 32L;

			Wrapper.getPlayer().rotationYaw = this.copycatTarget.rotationYaw;
			Wrapper.getPlayer().rotationPitch = this.copycatTarget.rotationPitch;
			Wrapper.getPlayer().rotationYawHead = this.copycatTarget.rotationYawHead;

			if (this.copycatTarget.isSneaking()) {
				Wrapper.getGameSettings().keyBindSneak.pressed = true;
			} else {
				Wrapper.getGameSettings().keyBindSneak.pressed = false;
			}

			if (targetY > playerY - 1L) {
				Wrapper.getGameSettings().keyBindJump.pressed = true;
			} else {
				Wrapper.getGameSettings().keyBindJump.pressed = false;
			}
			
			if (this.copycatTarget.isSwingInProgress
					&& Wrapper.getPlayer().swingProgress % 10.0F == 0.0F) {
				Wrapper.getPlayer().swingItem();
			}
		}
	}
	
	@Override
	public void postMotionUpdate() {
        this.updateRotation.setOriginalRotation(Wrapper.getPlayer());
	}
	
	private EntityPlayer fetchTarget(String s) throws Exception {
		s = s.toLowerCase();
		EntityPlayer ep = null;
		Iterator i = Wrapper.getWorld().playerEntities.iterator();
		while (i.hasNext()) {
			EntityPlayer ep1;
			if (ChatColor.stripColor((ep1 = (EntityPlayer) i.next()).username)
					.toLowerCase().indexOf(s) >= 0
					&& !ep1.isDead && ep1 != Wrapper.getPlayer()) {
				if (ep != null) {
					throw new Exception("Multiple players");
				}
				ep = ep1;
			}
		}
		if (ep == null) {
			throw new Exception("No player");
		} else {
			return ep;
		}
	}
	
	public void setCopycatTarget(EntityPlayer copycatTarget) {
		this.copycatTarget = copycatTarget;
	}
}
