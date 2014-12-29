package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityBat;
import net.minecraft.src.EntityCreature;
import net.minecraft.src.EntityDragon;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntityWitch;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.values.Values;
import org.lwjgl.input.Keyboard;

public class KillAura extends Module {

	private EntityLiving entity = null;

	private long current, last = -1;

	private float prevYaw, prevPitch;

	public boolean attackPlayers = true,
			attackHostile = true, attackPassive = true, lockView = false;

	public KillAura() {
		super("Kill Aura", ".killaura", "Kills entities", "Emotional Patrick/Ramisme", 0xA9A9A9, Keyboard.KEY_K, "Kill Aura");
	}

	@Override
	public void onTick () {
		if (!lockView) {
			onPreTick();
		}

		float delayValue = Values.auraSpeed.getValue();
		long delay = convertToMillis(delayValue);
		current = getCurrentMillis();
		if (!isEnabled()) { return; }
		for (Object o : Wrapper.getWorld().loadedEntityList) {
			Entity e = (Entity)o;
			if (e == Wrapper.getPlayer()) { continue; }
			if (isAttackable(e) && (isDelayComplete(delay))) {
				Wrapper.getPlayer().setSprinting(false);
				getBestWeapon();
				if (e instanceof EntityPlayer && !Helper.isFriend(Helper.getNearestPlayer().username))
					handleAimbot(Helper.getNearestPlayer());
				else 
					handleAimbot(e);
				Wrapper.getPlayer().swingItem();
				Wrapper.getController().attackEntity(Wrapper.getPlayer(), e);
				last = current;
				break;
			}
		}

		if (!lockView) {
			onPostTick();
		}
	}

	private void onPreTick() {
		prevYaw = Wrapper.getPlayer().rotationYaw;
		prevPitch = Wrapper.getPlayer().rotationPitch;
		prevYaw = Wrapper.getPlayer().rotationYawHead;
	}

	private void onPostTick() {
		Wrapper.getPlayer().rotationYaw = prevYaw;
		Wrapper.getPlayer().rotationPitch = prevPitch;
		Wrapper.getPlayer().rotationYawHead = prevYaw;
	}

	@Override
	public void externalCommand(String cmd) {
		if (cmd.equalsIgnoreCase(".lv")) {
			lockView = !lockView;
			Helper.addChat(lockView ? "Lockview toggled on." : "Lockview toggled off.");
		}

		if (cmd.startsWith(".ks")) {
			String[] sp = cmd.split(" ");
			try {
				float a = Float.parseFloat(sp[1]);
				Values.auraSpeed.setValue(a);
				Helper.addChat("Kill Aura has been set to attack (" + a
						+ ") time(s) per second.");
			} catch (Exception e) {
				Helper.addChat("Invalid syntax, .ks (attacks per second)");
			}
		}

		if (cmd.startsWith(".kr")) {
			try {
				String[] nig = cmd.split(" ");
				String penis = nig[1];
				float range = Float.parseFloat(penis);
				Values.auraRange.setValue(range);
				Helper.addChat("Kill aura range set to (" + range + ").");
			} catch (Exception e) {
				Helper.addChat("Invalid syntax, .kr (range)");
			}
		}
	}

	private void handleAimbot(Entity entity) {
		if (entity != null) {
			boolean isDead = ((EntityLiving) entity).deathTime > 0;
			if (!isDead) {
				faceEntity(entity);
			}
		}
	}

	private void faceEntity(Entity e) {
		double angle = 0.0D;
		double xDif = e.posX - Wrapper.getPlayer().posX;
		double zDif = e.posZ - Wrapper.getPlayer().posZ;
		double yDif = e.posY - Wrapper.getPlayer().posY + e.height / 1.4F;
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
		Wrapper.getPlayer().rotationPitch = (float) d1;
		Wrapper.getPlayer().rotationYaw = (float) angle;
		Wrapper.getPlayer().rotationYawHead = (float) angle;
	}

	private boolean isAttackable(Entity e) {
		if (!(e instanceof EntityLiving)) {
			return false;
		}
		EntityLiving eLiving = (EntityLiving) e;
		if (e instanceof EntityPlayer) {
			eLiving = Helper.getNearestPlayer();
		}
		return (e.getDistanceToEntity(Wrapper.getPlayer()) <= Values.auraRange.getValue()
				&& ((isPlayer(e) && attackPlayers)
						|| (isHostile(e) && attackHostile) 
						|| (isPassive(e) && attackPassive))
						&& eLiving.isEntityAlive() 
						&& canEntityBeSeenAtAll(e));
	}

	private boolean isPlayer(Entity e) {
		if (!(e instanceof EntityPlayer)) {
			return false;
		}
		EntityPlayer player = (EntityPlayer) e;
		if (!Helper.isFriend(player.username)) {
			return true;
		}
		return false;
	}

	private boolean isHostile(Entity e) {
		return (e instanceof EntityMob || e instanceof EntityDragon
				|| e instanceof EntityGhast || e instanceof EntitySlime || e instanceof EntityWitch);
	}

	private boolean isPassive(Entity e) {
		return (e instanceof EntityAnimal || e instanceof EntityCreature
				&& !(e instanceof EntityWolf) || e instanceof EntityBat);
	}

	public boolean canEntityBeSeenAtAll(Entity par1Entity) {
		return rayTraceAllBlocks(
				Wrapper.getWorld().getWorldVec3Pool().getVecFromPool(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight(), Wrapper.getPlayer().posZ),
				Wrapper.getWorld().getWorldVec3Pool().getVecFromPool(par1Entity.posX, par1Entity.posY + par1Entity.getEyeHeight(), par1Entity.posZ)) == null;
	}

	public MovingObjectPosition rayTraceAllBlocks(Vec3 par1Vec3, Vec3 par2Vec3) {
		return Wrapper.getWorld().rayTraceBlocks_do_do(par1Vec3,
				par2Vec3, false, true);
	}

	public void getBestWeapon() {
		float damageModifier = 1;
		int newItem = -1;
		for (int slot = 0; slot < 9; slot++) {
			ItemStack stack = Wrapper.getPlayer().inventory.mainInventory[slot];
			if (stack == null) 
				continue;
			float dmg = stack.getDamageVsEntity(Wrapper.getPlayer());
			if (dmg > damageModifier) {
				newItem = slot;
				damageModifier = dmg;
			}
		}

		if (newItem > -1)
			Wrapper.getPlayer().inventory.currentItem = newItem;
	}

	private long convertToMillis(double par1) {
		return (long)(1000 / par1);
	}

	private long getCurrentMillis() {
		return System.nanoTime() / 1000000;
	}

	private boolean isDelayComplete(long delay) {
		return (current - last > delay);
	}
}
