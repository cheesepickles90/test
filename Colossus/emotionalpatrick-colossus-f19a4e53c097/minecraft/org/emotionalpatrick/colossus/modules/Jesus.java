package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;

import org.lwjgl.input.Keyboard;

public class Jesus extends Module {

	public Jesus() {
		super("Jesus", ".jesus", "Allows you to walk on water", "Emotional Patrick", 14477544, Keyboard.KEY_J, "Modes");
	}
	
	public AxisAlignedBB onFluidBoundingBoxCreate(Block b, int i, int j, int k) {
		return AxisAlignedBB.getBoundingBox(i + b.minX, j + b.minY, k + b.minZ,
				i + b.maxX, j + b.maxY - 0.3D, k + b.maxZ);
	}
}
