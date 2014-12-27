package me.ownage.xenon.util;




import me.ownage.xenon.hacks.classes.Flight;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.MovementInput;
import net.minecraft.src.World;

public class FreecamEntity extends EntityOtherPlayerMP
{
	public FreecamEntity(World par1World, String par2Str)
	{
		super(par1World, par2Str);
	}
	
	public MovementInput movementInput = null;

	public boolean flyMode = false;
	
	public void setMovementInput(MovementInput par1MovementInput)
	{
		this.movementInput = par1MovementInput;
		
		if(par1MovementInput.jump && !flyMode && onGround)
		{
			this.jump();
		}
		
		this.moveEntityWithHeading(par1MovementInput.moveStrafe, par1MovementInput.moveForward);
	}
	
	public void moveEntity(double x, double y, double z)
	{
		if(flyMode) onGround = true;
		super.moveEntity(x, y, z);
		if(flyMode) onGround = true;
	}
	
	public boolean isSneaking()
	{
		return this.movementInput.sneak && !flyMode;
	}
	
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(flyMode)
		{
			noClip = true;
			motionX = 0;
			motionY = 0;
			motionZ = 0;
			this.landMovementFactor = Flight.flySpeed.getValue();
			this.jumpMovementFactor = Flight.flySpeed.getValue();
			if(this.movementInput != null)
			{
				if(this.movementInput.jump)
				{
					motionY += Flight.flySpeed.getValue() / 2 + 0.2F;
				}
				if(this.movementInput.sneak)
				{
					motionY -= Flight.flySpeed.getValue() / 2 + 0.2F;
				}
			}
		}else
		{
			if(!isSprinting())
			{
				this.landMovementFactor = 0.1F;
				this.jumpMovementFactor = 0.02F;
			}
			noClip = false;
		}
	}
}
