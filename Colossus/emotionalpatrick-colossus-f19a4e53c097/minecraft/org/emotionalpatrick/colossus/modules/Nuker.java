package org.emotionalpatrick.colossus.modules;

import java.util.ArrayList;

import net.minecraft.src.Packet14BlockDig;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.rendering.GL11Helper;
import org.lwjgl.input.Keyboard;

public class Nuker extends Module {

	private ArrayList<NukerObj> blockList = new ArrayList<NukerObj>();

	public Nuker() {
		super("Nuker", ".nuker", "Creative nuker", "Emotional Patrick", 0x008B8B, Keyboard.KEY_N, "World");
	}
	
	@Override
	public String getName() {
		String s = "";
		if (blockList.size() > 0) {
			s = "Nuker (" + blockList.size() + ")"; 
		} else if (blockList.size() < 1) {
			s = "Nuker";
		}
		return s;
	}

	@Override
	public void onGlobalRender(float f) {
		boolean flag = true;
		if (flag) {
			for (int i = 0; i < blockList.size(); i++) {
				NukerObj n = blockList.get(i);
				int posX = n.getX();
				int posY = n.getY();
				int posZ = n.getZ();
				int blockID = Wrapper.getWorld().getBlockId(posX, posY, posZ);
				if (blockID == 0) {
					blockList.remove(i);
				}
				if (!(Wrapper.getPlayer().getDistance(posX, posY, posZ) <= 6)) {
					GL11Helper.drawNukerESP(posX, posY, posZ, 1, 0, 0);
				} else if (Wrapper.getPlayer().getDistance(posX, posY, posZ) <= 6) {
					GL11Helper.drawNukerESP(posX, posY, posZ, 0, 1, 0);
				}
			}
		} else if (blockList.size() > 0) {
			blockList.clear();
		}
	}

	@Override
	public void onTick() {
		if (shouldNuke()) {
			Thread threadBreak = new Thread(new NukerThreadBreak(), "Nuker Thread Break");
			threadBreak.start();
		}
	}

	@Override
	public void onClickBlock(int i) {
		if (i == 0) {
			selectBlocks();
		}
	}

	@Override
	public void externalCommand(String s) {
		if (s.equalsIgnoreCase(".nc")) {
			blockList.clear();
			Helper.addChat("Nuker list cleared.");
		}
	}

	private void selectBlocks() {
		int range = 9;
		for (int y = range; y > -range; y--) {
			for (int x = range; x > -range; x--) {
				for (int z = range; z > -range; z--) {
					int posX = (int) Wrapper.getPlayer().posX + y;
					int posY = (int) Wrapper.getPlayer().posY + x;
					int posZ = (int) Wrapper.getPlayer().posZ + z;
					int blockID = Wrapper.getWorld().getBlockId(posX, posY, posZ);
					if (blockID != 0 && Wrapper.getPlayer().getDistance(posX, posY, posZ) <= range) {
						blockList.add(new NukerObj(posX, posY, posZ));
					}
				}
			}
		}
	}

	public boolean shouldNuke() {
		for (int i = 0; i < blockList.size(); i++) {
			NukerObj n = blockList.get(i);
			int posX = n.getX();
			int posY = n.getY();
			int posZ = n.getZ();
			if (Wrapper.getPlayer().getDistance(posX, posY, posZ) <= 6 && blockList.contains(n)) {
				return true;
			}
		}
		return false;
	}
	
	// TODO: Write a survival nuker?
	private void faceBlock(double posX, double posY, double posZ) {
		double angle = 0.0D;
		double xDif = posX - Wrapper.getPlayer().posX;
		double zDif = posZ - Wrapper.getPlayer().posZ;
		double yDif = posY - Wrapper.getPlayer().posY;
		if (zDif > 0.0D && xDif > 0.0D) {
			angle = Math.toDegrees(-Math.atan(xDif / zDif));
		} else if (zDif > 0.0D && xDif < 0.0D) {
			angle = Math.toDegrees(-Math.atan(xDif / zDif));
		} else if (zDif < 0.0D && xDif > 0.0D) {
			angle = -90.0D + Math.toDegrees(Math.atan(zDif / xDif));
		} else if (zDif < 0.0D && xDif < 0.0D) {
			angle = 90.0D + Math.toDegrees(Math.atan(zDif / xDif));
		}
		double d = Math.sqrt(zDif * zDif + xDif * xDif);
		double d1 = -Math.toDegrees(Math.atan(yDif / d));
		Wrapper.getPlayer().rotationYaw = (float) angle;
		Wrapper.getPlayer().rotationPitch = (float) d1 - 5.5F;
	}

	class NukerThreadBreak implements Runnable {

		@Override
		public void run() {
			this.handleNuker();
			try {
				Thread.sleep(450L);
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}

		private void handleNuker() {
			for (int i = 0; i < blockList.size(); i++) {
				NukerObj n = blockList.get(i);
				int posX = n.getX();
				int posY = n.getY();
				int posZ = n.getZ();
				int blockID = Wrapper.getWorld().getBlockId(posX, posY, posZ);
				if (Wrapper.getPlayer().getDistance(posX, posY, posZ) <= 6 && blockList.contains(n) && blockID != 0) {
					Helper.sendPacket(new Packet14BlockDig(0, posX, posY, posZ, 1));
					Helper.sendPacket(new Packet14BlockDig(2, posX, posY, posZ, 1));
				}
			}
		}
	}

	class NukerObj {

		public int x;
		public int y;
		public int z;

		public NukerObj(int i, int j, int k) {
			x = i;
			y = j;
			z = k;
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
	}
}
