package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.StringUtils;

import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.input.Keyboard;

public class OpAura extends Module {

	private long current;
	private long last = -1;
	public float attacksPerSecond = 15F;
	
	public OpAura() {
		super("OpAura", ".opaura", "OP ass aura", "Ramisme", 0xFF0000, Keyboard.KEY_NONE, "Kill Aura");
	}
		
	@Override
	public void onTick() {
		long delay = (long) (1000 / attacksPerSecond);
		current = System.nanoTime() / 1000000;
		if (!isEnabled()) {
			return;
		}
		for (Object o : Wrapper.getWorld().loadedEntityList) {
			Entity e = (Entity) o;
			if (e == Wrapper.getPlayer()) {
				continue;
			}
			if (((current - last >= delay) || (last == -1)) && isAttackable(e)) {
				Colossus.getManager().killAura.getBestWeapon();
				Wrapper.getPlayer().swingItem();
				Wrapper.getMinecraft().playerController.attackEntity(Wrapper.getPlayer(), e);
				last = System.nanoTime() / 1000000;
				break;
			}
		}
	}
	
	@Override
	public void onAttackEntity(EntityPlayer par1EntityPlayer, Entity par2Entity) {
		if (par2Entity instanceof EntityPlayer) {
			String name = StringUtils.stripControlCodes(par2Entity.getEntityName());
			Helper.sendChat("/kill " + name);
		}
	}

	private boolean isAttackable(Entity e) {
		if (!(e instanceof EntityLiving)) {
			return false;
		}
		EntityLiving eLiving = (EntityLiving) e;
		return (e.getDistanceToEntity(Wrapper.getPlayer()) <= 6
				&& eLiving.isEntityAlive() && isPlayer(e));
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
}
