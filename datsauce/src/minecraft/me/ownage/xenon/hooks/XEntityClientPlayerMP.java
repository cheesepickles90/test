package me.ownage.xenon.hooks;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.hacks.classes.Flight;
import me.ownage.xenon.hacks.classes.Freecam;
import me.ownage.xenon.hacks.classes.God;
import me.ownage.xenon.hacks.classes.Sneak;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.XenonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.ItemStack;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;
import net.minecraft.src.Packet202PlayerAbilities;
import net.minecraft.src.Packet205ClientCommand;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Session;
import net.minecraft.src.StatBase;
import net.minecraft.src.World;

public class XEntityClientPlayerMP extends EntityClientPlayerMP
{
	private double oldPosX;

	/** Old Minimum Y of the bounding box */
	private double oldMinY;
	private double oldPosY;
	private double oldPosZ;
	private float oldRotationYaw;
	private float oldRotationPitch;

	/** Check if was on ground last update */
	private boolean wasOnGround = false;

	/** should the player stop sneaking? */
	private boolean shouldStopSneaking = false;
	private boolean wasSneaking = false;
	private int field_71168_co = 0;

	public XEntityClientPlayerMP(Minecraft par1Minecraft, World par2World, Session par3Session, NetClientHandler par4NetClientHandler) 
	{
		super(par1Minecraft, par2World, par3Session, par4NetClientHandler);
	}

	@Override
	public void sendMotionUpdates() {
		if(Hacks.findMod(Freecam.class).isEnabled() || Hacks.findMod(God.class).isEnabled()) return;
		boolean var1 = this.isSprinting();

		if (var1 != this.wasSneaking)
		{
			if (var1)
			{
				this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 4));
			}
			else
			{
				this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 5));
			}

			this.wasSneaking = var1;
		}

		boolean var2 = this.isSneaking() || Hacks.findMod(Sneak.class).isEnabled();

		if (var2 != this.shouldStopSneaking)
		{
			if (var2)
			{
				this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 1));
			}
			else
			{
				this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 2));
			}

			this.shouldStopSneaking = var2;
		}

		double var3 = this.posX - this.oldPosX;
		double var5 = this.boundingBox.minY - this.oldMinY;
		double var7 = this.posZ - this.oldPosZ;
		double var9 = (double)(this.rotationYaw - this.oldRotationYaw);
		double var11 = (double)(this.rotationPitch - this.oldRotationPitch);
		boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D || this.field_71168_co >= 20;
		boolean var14 = var9 != 0.0D || var11 != 0.0D;

		if (this.ridingEntity != null)
		{
			this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.motionX, -999.0D, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
			var13 = false;
		}
		else if (var13 && var14)
		{
			this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
		}
		else if (var13)
		{
			this.sendQueue.addToSendQueue(new Packet11PlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.onGround));
		}
		else if (var14)
		{
			this.sendQueue.addToSendQueue(new Packet12PlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
		}
		else
		{
			this.sendQueue.addToSendQueue(new Packet10Flying(this.onGround));
		}

		++this.field_71168_co;
		this.wasOnGround = this.onGround;

		if (var13)
		{
			this.oldPosX = this.posX;
			this.oldMinY = this.boundingBox.minY;
			this.oldPosY = this.posY;
			this.oldPosZ = this.posZ;
			this.field_71168_co = 0;
		}

		if (var14)
		{
			this.oldRotationYaw = this.rotationYaw;
			this.oldRotationPitch = this.rotationPitch;
		}
	}

	public void moveEntity(double par1, double par3, double par5)
	{
		if(Xenon.getHacks().findMod(Freecam.class).isEnabled()) {
			return;
		}
		float d = distanceWalkedModified;
		super.moveEntity(par1, par3, par5);
		if(Xenon.getHacks().findMod(Flight.class).isEnabled() && !Hacks.findMod(Freecam.class).isEnabled())
		{
			distanceWalkedModified = d;
			onGround = true;
			this.inWater = false;
		}
	}

	public boolean handleWaterMovement() {
		if(Hacks.findMod(Flight.class).isEnabled()) {
			return false;
		}

		return super.handleWaterMovement();
	}

	@Override
	public void jump() {
		super.jump();
	}

	public void onUpdate()
	{
		if (this.worldObj.blockExists(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)))
		{
			for(XenonMod mod: Hacks.hackList)
			{
				mod.onPlayerUpdate(this);
			}
			super.onUpdate();
			for(XenonMod xMod: Hacks.hackList)
			{
				xMod.beforeUpdate(this);
			}
			for(XenonMod xMod: Hacks.hackList)
			{
				xMod.onUpdate(this);
			}
			//this.sendMotionUpdates();
			for(XenonMod xMod: Hacks.hackList)
			{
				xMod.afterUpdate(this);
			}
		}
	}

	public void sendChatMessage(String par1Str)
	{
		if(!par1Str.startsWith(Character.toString(Xenon.getCommandManager().cmdPrefix)))
		{
			this.sendQueue.addToSendQueue(new Packet3Chat(par1Str));
		}
		Xenon.getCommandManager().runCommands(par1Str);
	}
}
