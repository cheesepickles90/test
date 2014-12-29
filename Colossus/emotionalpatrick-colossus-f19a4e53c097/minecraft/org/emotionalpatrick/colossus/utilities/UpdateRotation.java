package org.emotionalpatrick.colossus.utilities;

import net.minecraft.src.EntityPlayer;

// Thanks Haruko!
public class UpdateRotation {
	
	private float rotYaw;
	private float rotPitch;
	private float rotYawHead;

	public void savePrevRotations(EntityPlayer ep) {
		this.rotYaw = ep.rotationYaw;
		this.rotPitch = ep.rotationPitch;
		this.rotYawHead = ep.rotationYawHead;
	}

	public void setOriginalRotation(EntityPlayer ep) {
		ep.rotationYaw = this.rotYaw;
		ep.rotationPitch = this.rotPitch;
		ep.rotationYawHead = this.rotYawHead;
	}

	public float getRotationYaw() {
		return this.rotYaw;
	}

	public float getRotationPitch() {
		return this.rotPitch;
	}

	public float getRotationYawHead() {
		return this.rotYawHead;
	}
}
