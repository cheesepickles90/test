package me.ownage.xenon.hacks.classes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.RenderManager;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.XenonUtils;

public class Search extends XenonMod {
	private int size = 0;

	public static List<Integer> espList = new ArrayList<Integer>();
	public static BlockCoord[] espBlocks = new BlockCoord[10000000];

	public Search() {
		super("Search", "Draws an ESP around blocks.", 0, 0xFFFFFF, null);
	}

	private int timer = 0;

	public void refresh() {
		size = 0;
		int radius = 72;
		for(int y = 0; y < 128; y++) {
			for(int x = 0; x < radius; x++) {
				for(int z = 0; z < radius; z++) {

					int cX = (int)mc.thePlayer.posX - (int)radius/2+x;
					int cY = y;
					int cZ = (int)mc.thePlayer.posZ - (int)radius/2+z;
					int ids = mc.theWorld.getBlockId(cX, cY, cZ);

					if (espList.contains(ids)) {
						espBlocks[size++] = new BlockCoord(cX, cY, cZ);
					}
				}
			}
		}
	}

	@Override
	public void onRender() {
		if(isEnabled()) {
			timer++;

			if(timer >= 60) {
				refresh();
				timer = 0;
			}

			for(int cur = 0; cur < size; cur++) {
				BlockCoord curBlock = espBlocks[cur];
				XenonUtils.drawESP(curBlock.getDeltaX(), curBlock.getDeltaY(), curBlock.getDeltaZ(), 1.0F, 0.0F, 0.0F);
			}
		}
	}

	public class BlockCoord {
		private int x, y, z;

		public BlockCoord(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getZ() {
			return z;
		}

		public double getDeltaX() {
			return getX() - RenderManager.renderPosX;
		}

		public double getDeltaY() {
			return getY() - RenderManager.renderPosY;
		}

		public double getDeltaZ() {
			return getZ() - RenderManager.renderPosZ;
		}
	}
}
