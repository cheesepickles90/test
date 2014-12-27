package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.XenonUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.RenderManager;
import net.minecraft.src.TileEntityChest;

import org.lwjgl.input.Keyboard;

public class ChestESP extends XenonMod {
	public ChestESP() {
		super("Chest ESP", "Puts a box around chests.", Keyboard.KEY_Y, 0x6600CC, EnumGuiCategory.ESP);
	}
	
	@Override
	public void onRender() {
		if(isEnabled()) {
			for(Object o : mc.theWorld.loadedTileEntityList) {
				if(o instanceof TileEntityChest) {
					TileEntityChest chest = (TileEntityChest)o;
					this.drawESP(chest, chest.xCoord, chest.yCoord, chest.zCoord, chest.prevLidAngle);
				}
			}
		}
	}
	
	public void drawESP(TileEntityChest chest, double x, double y, double z, float f) {
		if(isEnabled()) {
			if(!(chest.xCoord == 0 && chest.yCoord == 0 && chest.zCoord == 0)) {
				mc.entityRenderer.disableLightmap(f);
				XenonUtils.drawESP(x - RenderManager.renderPosX, y - RenderManager.renderPosY, z - RenderManager.renderPosZ, 0.0F, 1.0F - chest.getDistanceFrom(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ) / 20000, 1.0F);
				mc.entityRenderer.enableLightmap(f);
			}
		}
	}
}
