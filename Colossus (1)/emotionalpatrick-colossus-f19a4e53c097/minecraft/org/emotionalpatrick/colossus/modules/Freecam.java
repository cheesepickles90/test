package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.WorldClient;

import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.modules.manager.ModuleManager;
import org.lwjgl.input.Keyboard;

public class Freecam extends Module {
	
	private static Location locationHelper;

	public Freecam() {
		super("Freecam", ".freecam", "An out of body experience", "Emotional Patrick", 0xFFFFFF, Keyboard.KEY_R, "Misc");
		this.shown = false;
	}
	
	@Override
	public float setCurBlockDamage(Block b, int i1, int i2, int i3,
			float i4) {
		float damage = b.getPlayerRelativeBlockHardness(
				Wrapper.getPlayer(), Wrapper.getWorld(), i1,
				i2, i3);
		return damage = 1.0F;
	}
	
	@Override
	public void onTick() {
		Colossus.getManager();
		if (ModuleManager.getModuleByName("Fly").isEnabled()) {
			Wrapper.getPlayer().noClip = true;
		} else {
			Wrapper.getPlayer().noClip = false;
		}
	}
	
	@Override
	public void onToggle() {
		super.onToggle();
		this.createPlayer();
	}

	public void createPlayer() {
		try {
			EntityPlayerSP entityplayersp = Wrapper.getPlayer();
			if (this.isEnabled()) {
				if (Wrapper.getMinecraft().theWorld instanceof WorldClient) {
					locationHelper = new Location(entityplayersp);
					EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP(
							Wrapper.getMinecraft().theWorld,
							Wrapper.getPlayer().username);
					entityotherplayermp.setPositionAndRotation(locationHelper.posX,
							locationHelper.posY - entityplayersp.yOffset,
							locationHelper.posZ, locationHelper.rotationYaw,
							locationHelper.rotationPitch);
					entityotherplayermp.inventory.copyInventory(Wrapper
							.getPlayer().inventory);
					entityotherplayermp.setSneaking(entityotherplayermp
							.isSneaking());
					WorldClient worldclient1 = Wrapper.getMinecraft().theWorld;
					worldclient1.addEntityToWorld(-1, entityotherplayermp);
				}
			} else {
				entityplayersp.noClip = false;
				WorldClient worldclient = Wrapper.getMinecraft().theWorld;
				worldclient.removeEntityFromWorld(-1);
				entityplayersp.setPositionAndRotation(locationHelper.posX,
						locationHelper.posY, locationHelper.posZ, locationHelper.rotationYaw,
						locationHelper.rotationPitch);
			}
		} catch (Exception exception) {
			Helper.addChat("Freecam cannot be used in single player!");
		}
	}
	
	class Location {
		
		public double posX;
		public double posY;
		public double posZ;
		
		public float rotationYaw;
		public float rotationPitch;
		
		public String name;

		@Override
		public Location clone() {
			return new Location(posX, posY, posZ, rotationYaw, rotationPitch, name);
		}

		public Location(Entity entity) {
			this(entity, "");
		}

		public Location(Entity entity, String s) {
			this(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch, s);
		}

		public Location() {
			this(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, "");
		}

		public Location(double d, double d1, double d2, String s) {
			this(d, d1, d2, 0.0F, 0.0F, s);
		}

		public Location(double d, double d1, double d2) {
			this(d, d1, d2, 0.0F, 0.0F, "");
		}

		public Location(double d, double d1, double d2, float f, float f1) {
			this(d, d1, d2, f, f1, "");
		}

		public Location(double d, double d1, double d2, float f, float f1,
				String s) {
			posX = d;
			posY = d1;
			posZ = d2;
			rotationYaw = f;
			rotationPitch = f1;
			name = s;
		}

		public double distance(Location Location) {
			return Math.sqrt(distanceSquare(Location));
		}

		public double distanceSquare(Location Location) {
			double d = Location.posX - posX;
			double d1 = Location.posY - posY;
			double d2 = Location.posZ - posZ;
			return d * d + d1 * d1 + d2 * d2;
		}

		public double distance2D(Location Location) {
			return Math.sqrt(distance2DSquare(Location));
		}

		public double distance2DSquare(Location Location) {
			double d = Location.posX - posX;
			double d1 = Location.posZ - posZ;
			return d * d + d1 * d1;
		}

		public double distanceY(Location Location) {
			return Location.posY - posY;
		}

		public Location(String s) throws Exception {
			String as[] = s.split(";", 6);

			if (as.length != 6) {
				throw new Exception("Invalid line!");
			} else {
				name = as[5];
				posX = Double.parseDouble(as[0]);
				posY = Double.parseDouble(as[1]);
				posZ = Double.parseDouble(as[2]);
				rotationYaw = Float.parseFloat(as[3]);
				rotationPitch = Float.parseFloat(as[4]);
				return;
			}
		}

		public String export() {
			return (new StringBuilder()).append(posX).append(";").append(posY)
					.append(";").append(posZ).append(";").append(rotationYaw)
					.append(";").append(rotationPitch).append(";").append(name)
					.toString();
		}
	}
}
