package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.FreecamEntity;
import me.ownage.xenon.util.Value;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;

public class KillAura extends XenonMod {
	public static Value auraSpeed = new Value("Aura Speed");
	public static Value auraRange = new Value("Aura Range");
	
	private float yaw, pitch, yawHead;
	
	private long currentMS = 0L;
	private long lastMS = -1L;
	
	public KillAura()
	{
		super("Kill Aura", "Automatically attacks players or mobs.", Keyboard.KEY_K, 0xFF0000, EnumGuiCategory.AURA);
	}
	
	@Override
	public void beforeUpdate(EntityPlayerSP player)
	{
		if(!Hacks.findMod(AimBot.class).isEnabled() && isEnabled())
		{
			this.yaw = player.rotationYaw;
			this.pitch = player.rotationPitch;
			this.yawHead = player.rotationYawHead;
		}
	}
	
	public boolean hasDelayRun(long time) {
		return (currentMS - lastMS) >= time;
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player)
	{
		if(auraRange.getValue() < 3)
		{
			auraRange.setValue(4.2F);
		}
		if(isEnabled())
		{
			currentMS = System.nanoTime() / 1000000;
			if(hasDelayRun((long)(1000 / auraSpeed.getValue())))
			{
				for(Object o: mc.theWorld.loadedEntityList)
				{
					try
					{
						if(!Hacks.findMod(AuraPlayer.class).isEnabled() || !Hacks.findMod(AuraMob.class).isEnabled())
						{
							if(Hacks.findMod(AuraPlayer.class).isEnabled())
							{
								Entity e = (Entity) o;
								boolean checks = !(e instanceof FreecamEntity) && !Xenon.getFriends().isFriend(((EntityPlayer)e).username) && !(e instanceof EntityPlayerSP) && (e instanceof EntityPlayer) && mc.thePlayer.getDistanceToEntity(e) <= auraRange.getValue() && mc.thePlayer.canEntityBeSeen(e) && !e.isDead && mc.thePlayer.hurtTime <= 0;
								if(checks)
								{
									player.setSprinting(false);
									faceEntity(e);
									player.swingItem();
									if(Hacks.findMod(AutoBlock.class).isEnabled() && mc.thePlayer.getCurrentEquippedItem() != null)
									{
										if(this.isSword(this.mc.thePlayer.getCurrentEquippedItem().getItem())) 
										{
											mc.thePlayer.getCurrentEquippedItem().useItemRightClick(mc.theWorld, mc.thePlayer);
										}
									}

									mc.playerController.attackEntity(mc.thePlayer, e);
									player.setSprinting(false);
									lastMS = System.nanoTime() / 1000000;
									break;
								}
							}else
							if(Hacks.findMod(AuraMob.class).isEnabled())
							{
								Entity e = (Entity) o;
								boolean checks = !(e instanceof FreecamEntity) && !(e instanceof EntityPlayerSP) && !(e instanceof EntityPlayer) && (e instanceof EntityLiving) && mc.thePlayer.getDistanceToEntity(e) <= auraRange.getValue() && mc.thePlayer.canEntityBeSeen(e) && !e.isDead;
								if(checks)
								{
									player.setSprinting(false);
									faceEntity(e);
									player.swingItem();
									if(Hacks.findMod(AutoBlock.class).isEnabled() && mc.thePlayer.getCurrentEquippedItem() != null)
									{
										if(this.isSword(this.mc.thePlayer.getCurrentEquippedItem().getItem())) 
										{
											mc.thePlayer.getCurrentEquippedItem().useItemRightClick(mc.theWorld, mc.thePlayer);
										}
									}
									mc.playerController.attackEntity(mc.thePlayer, e);
									lastMS = System.nanoTime() / 1000000;
									break;
								}
							}
						}else
						if(Hacks.findMod(AuraPlayer.class).isEnabled() && Hacks.findMod(AuraMob.class).isEnabled())
						{
							Entity e = (Entity) o;
							boolean checks = !(e instanceof FreecamEntity) && !(e instanceof EntityPlayerSP) && (e instanceof EntityLiving) && mc.thePlayer.getDistanceToEntity(e) <= auraRange.getValue() && mc.thePlayer.canEntityBeSeen(e) && !e.isDead;
			
							if(e instanceof EntityPlayer) 
							{
								EntityPlayer ep = (EntityPlayer) o;
								checks = checks && !Xenon.getFriends().isFriend(ep.username);
								checks = checks && !ep.isPotionActive(Potion.invisibility);
							}
							if(checks)
							{
								player.setSprinting(false);
								faceEntity(e);
								player.swingItem();
								if(Hacks.findMod(AutoBlock.class).isEnabled() && mc.thePlayer.getCurrentEquippedItem() != null)
								{
									if(this.isSword(this.mc.thePlayer.getCurrentEquippedItem().getItem())) 
									{
										mc.thePlayer.getCurrentEquippedItem().useItemRightClick(mc.theWorld, mc.thePlayer);
									}
								}
								mc.playerController.attackEntity(mc.thePlayer, e);
								lastMS = System.nanoTime() / 1000000;
								break;
							}
						}
					}catch(Exception e) {}
				}
			}
		}
	}
	
	@Override
	public void afterUpdate(EntityPlayerSP player)
	{
		if(!Hacks.findMod(AimBot.class).isEnabled() && isEnabled())
		{
			player.rotationYaw = this.yaw;
			player.rotationPitch = this.pitch;
			player.rotationYawHead = this.yawHead;
		}
	}
	
	private boolean isSword(Item item)
	{
		return item instanceof ItemSword;
	}
	
	public void faceEntity(Entity entity)
    {
		double x = entity.posX - mc.thePlayer.posX;
		double z = entity.posZ - mc.thePlayer.posZ;
		double y = entity.posY + (entity.getEyeHeight()/1.4D) - mc.thePlayer.posY + (mc.thePlayer.getEyeHeight()/1.4D);
		double helper = MathHelper.sqrt_double(x * x + z * z);

		float newYaw = (float)((Math.toDegrees(-Math.atan(x / z))));
		float newPitch = (float)-Math.toDegrees(Math.atan(y / helper));

		if(z < 0 && x < 0) { newYaw = (float)(90D + Math.toDegrees(Math.atan(z / x))); }
		else if(z < 0 && x > 0) { newYaw = (float)(-90D + Math.toDegrees(Math.atan(z / x))); }

		mc.thePlayer.rotationYaw = newYaw; 
		mc.thePlayer.rotationPitch = newPitch;
		mc.thePlayer.rotationYawHead = newPitch;
    }
}
