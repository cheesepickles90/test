package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.FreecamEntity;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;

import org.lwjgl.input.Keyboard;




public class AimBot extends XenonMod {
	public AimBot() {
		super("AimBot", "Physically looks at nearby entities.", Keyboard.KEY_M, 0x339933, EnumGuiCategory.AURA);
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			for(Object o: mc.theWorld.loadedEntityList) {
				if(o instanceof EntityPlayer) {
					EntityPlayer e = (EntityPlayer) o;
					boolean checks = !(Xenon.getFriends().isFriend(e.username)) && !(e instanceof FreecamEntity) && !(e instanceof EntityPlayerSP) && (e instanceof EntityLiving) && player.getDistanceToEntity(e) <= KillAura.auraRange.getValue() && player.canEntityBeSeen(e) && !e.isDead;
					if(checks) {
						player.faceEntity(e, 100F, 100F);
					}
				}
			}
		}
	}
}
