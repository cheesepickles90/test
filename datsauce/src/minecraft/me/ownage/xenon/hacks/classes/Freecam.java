package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.FreecamEntity;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.EntityPlayerSP;

import org.lwjgl.input.Keyboard;




public class Freecam extends XenonMod
{
	public Freecam()
	{
		super("Freecam", "Frees the player's camera to move freely.", Keyboard.KEY_V, 0xFFFFFF, EnumGuiCategory.WORLD);
	}
	
	private EntityOtherPlayerMP entity = null;
	public FreecamEntity freecamEnt = null;
	private double freecamX, freecamY, freecamZ, freecamPitch, freecamYaw;
	
	@Override
	public void onEnable()
	{	
		if(mc.thePlayer != null && mc.theWorld != null)
		{
			freecamEnt = new FreecamEntity(mc.theWorld, mc.thePlayer.username);
			freecamEnt.noClip = false;
			freecamEnt.setPosition(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY, mc.thePlayer.posZ);
			freecamEnt.boundingBox.setBB(mc.thePlayer.boundingBox.copy());
			mc.theWorld.spawnEntityInWorld(freecamEnt);
		}
		
		mc.renderViewEntity = freecamEnt;
	}
	
	@Override
	public void onDisable()
	{
		if(freecamEnt != null && mc.theWorld != null)
		{
			freecamEnt.setEntityHealth(0);
			freecamEnt.setDead();
			mc.theWorld.removeEntity(freecamEnt);
			freecamEnt = null;
		}
	
		mc.renderViewEntity = mc.thePlayer;
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player)
	{
		if(isEnabled() && freecamEnt != null)
		{
//			freecamEnt.rotationPitch = player.rotationPitch;
//			freecamEnt.rotationYaw = player.rotationYaw;
//			freecamEnt.rotationYawHead = player.rotationYawHead;
		}
	}
	
	@Override
	public void runTick()
	{
		try {
			if(isEnabled() && freecamEnt != null)
			{
				EntityPlayerSP player = mc.thePlayer;
				freecamEnt.inventory = player.inventory;
				freecamEnt.yOffset = player.yOffset;
				freecamEnt.ySize = player.ySize;
				freecamEnt.flyMode = Xenon.getHacks().findMod(Flight.class).isEnabled();
				freecamEnt.setMovementInput(player.movementInput);
				freecamEnt.rotationPitch = player.rotationPitch;
				freecamEnt.rotationYaw = player.rotationYaw;
				freecamEnt.rotationYawHead = player.rotationYawHead;
				freecamEnt.setSprinting(player.isSprinting());
				
				if(mc.renderViewEntity != freecamEnt)
				{
					mc.renderViewEntity = freecamEnt;
				}
			}else if(isEnabled())
			{
				this.toggle();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
