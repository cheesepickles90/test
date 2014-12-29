package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.modules.manager.ModuleManager;
import org.lwjgl.input.Keyboard;

import net.minecraft.src.*;

public class BowAimbot extends Module {

	public BowAimbot() {
		super("Bow Aimbot", ".bowaimbot", "Aims your bow for you", "Emotional Patrick", 0xFF8C00, Keyboard.KEY_NONE);
	}
	
	@Override
	public void onTick() {
		Colossus.getManager();
		if (!ModuleManager.getModuleByName("Freecam").isEnabled()) {
			if (Wrapper.getPlayer().getCurrentEquippedItem() != null) {
				ItemStack itemstack = Wrapper.getPlayer()
						.getCurrentEquippedItem();
				if (itemstack.itemID == 261) {
					for (Object o : Wrapper.getWorld().playerEntities) {
						EntityPlayer e = (EntityPlayer) o;
						EntityPlayerSP ep = Wrapper.getPlayer();
						if (Helper.getNearestPlayer() != ep
								&& !Helper.isFriend(ep.username)) {
							if (ep.getDistanceToEntity(Helper.getNearestPlayer()) <= 35
									&& Colossus.getManager().killAura
									.canEntityBeSeenAtAll(Helper.getNearestPlayer())
									&& ep.isUsingItem()) {
								faceEntityWithBow(Helper.getNearestPlayer());
								break;
							}
						}
					}
				}
			}
		}
	}
	
	private void faceEntityWithBow(Entity e) {
		double angle = 0.0D;
		double xDif = e.posX - Wrapper.getPlayer().posX;
		double zDif = e.posZ - Wrapper.getPlayer().posZ;
		double yDif = e.posY - Wrapper.getPlayer().posY + 1.2D;
		if (zDif > 0.0D && xDif > 0.0D) {
			angle = Math.toDegrees(-Math.atan(xDif / zDif));
		} else if (zDif > 0.0D && xDif < 0.0D) {
			angle = Math.toDegrees(-Math.atan(xDif / zDif));
		} else if (zDif < 0.0D && xDif > 0.0D) {
			angle = -90.0D + Math.toDegrees(Math.atan(zDif / xDif));
		} else if (zDif < 0.0D && xDif < 0.0D) {
			angle = 90.0D + Math.toDegrees(Math.atan(zDif / xDif));
		}
		double d = Math.sqrt(zDif * zDif + xDif * xDif);
		double d1 = (-Math.toDegrees(Math.atan(yDif / d)));
		Wrapper.getPlayer().rotationPitch = (float) d1 - 3.0F;
		Wrapper.getPlayer().rotationYaw = (float) angle;
	}
}
