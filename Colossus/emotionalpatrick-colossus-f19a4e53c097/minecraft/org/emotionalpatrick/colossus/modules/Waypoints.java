package org.emotionalpatrick.colossus.modules;

import java.util.ArrayList;
import java.util.Random;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.rendering.GL11Helper;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;

public class Waypoints extends Module {

	public float r, g, b;
	Random rand = new Random();

	public Waypoints() {
		super("Waypoints", ".waypoints", "Waypoints to positions", "Vesta", "World");
	}
	
	@Override
	public void externalCommand(String s) {
		if (s.startsWith(".point")) {
			try {
				String[] args = s.split(" ");
				if (args[1].equals("here")) {
					randomColor();
					WaypointObj.add(args[2], Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, r, g, b);
					Helper.addChat("Waypoint \"" + args[2] + "\" added at current location.");
				} else if (args[1].equals("add")) {
					double x, y, z;
					x = Wrapper.getPlayer().posX;
					y = Wrapper.getPlayer().posY ;
					z = Wrapper.getPlayer().posZ;
					x = Double.parseDouble(args[2]);
					y = Double.parseDouble(args[3]);
					z = Double.parseDouble(args[4]);
					randomColor();
					WaypointObj.add(args[5], x, y, z, r, g, b);
					Helper.addChat("Waypoint \"" + args[5] + "\" added at (X: " + (int)x + ", Y: " + (int)y + ", Z: " + (int)z + ")");
				} else if (args[1].equals("clear")) {
					WaypointObj.waypoints.clear();
					Helper.addChat("Waypoints cleared!");
				}
			} catch (Exception e) {
				Helper.addChat("Invalid syntax, .point (here/add/clear) (name)");
			}
		}
	}
	
	public void randomColor() {
		this.r = rand.nextFloat();
		this.g = rand.nextFloat();
		this.b = rand.nextFloat();
	}
	
	@Override
	public void onGlobalRender(float f) {
		for (int p = 0; p < WaypointObj.size(); p++) {
			WaypointObj wp = WaypointObj.waypoints.get(p);
			double x = wp.x - RenderManager.renderPosX;
			double y = wp.y - RenderManager.renderPosY;
			double z = wp.z - RenderManager.renderPosZ;
			GL11.glPushMatrix();
			GL11Helper.enableDefaults();
			GL11.glColor3f(wp.r, wp.g, wp.b);
			GL11.glLineWidth(1.5F);
			GL11.glTranslated(x, y + wp.bounceCount, z);
			GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex3d(0.0D, 0.0D, 0.0D);
			GL11.glVertex3d(-x, -y - wp.bounceCount, -z);
			GL11.glEnd();
			GL11.glRotatef(wp.rotationCount, 0, 1, 0);
			GL11Helper.drawCrossedOutlinedBoundingBox(new AxisAlignedBB(-0.6, 1.0, -0.6, 0.6, 0, 0.6));
			GL11.glColor4f(wp.r, wp.g, wp.b, 0.2f);
			GL11Helper.drawBox(x, y + wp.bounceCount, z, 0.6f);
			GL11Helper.disableDefaults();
			GL11.glPopMatrix();
			drawWaypointName(Wrapper.getMinecraft(), wp.name + " ("
					+ (int) wp.getDistance() + ")", "" + wp.getDistance(), x,
					y, z, wp);
		}
	}

	public static void drawWaypointName(Minecraft mc, String s, String dist,
			double x, double y, double z, WaypointObj w) {
		float f1 = 1.6F;
		float f2 = 0.01666667F * f1;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.0F, (float) y + 2.3F, (float) z);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-mc.thePlayer.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(mc.thePlayer.rotationPitch, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-f2, -f2, f2);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator tessellator = Tessellator.instance;
		byte byte0 = 0;
		if (Float.parseFloat(dist) / 8 >= 1)
			GL11.glScalef(Float.parseFloat(dist) / 8F,
					Float.parseFloat(dist) / 8F, Float.parseFloat(dist) / 8F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		int i = Wrapper.getFontRenderer().getStringWidth(s) / 2;
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Wrapper.getFontRenderer().drawStringWithShadow(s,
				-Wrapper.getFontRenderer().getStringWidth(s) / 2, byte0, 0xFFffffff);
		if (Float.parseFloat(dist) / 8 >= 1)
			GL11.glScalef(1 / Float.parseFloat(dist) / 8F,
					1 / Float.parseFloat(dist) / 8F,
					1 / Float.parseFloat(dist) / 8F);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	static class WaypointObj {

		public static ArrayList<WaypointObj> waypoints = new ArrayList<WaypointObj>();

		public float r, g, b;
		public float rotationCount;
		public float bounceCount;

		public String name;

		public double x, y, z;

		public WaypointObj(String s, double x, double y, double z, float r,
				float g, float b) {
			name = s;
			this.x = x;
			this.y = y;
			this.z = z;
			this.r = r;
			this.g = g;
			this.b = b;
			new Thread(new Runnable() {
				@Override
				public void run() {
					boolean up = true;
					while (true) {
						rotationCount++;
						if (bounceCount >= 0.1)
							up = false;
						if (bounceCount <= -0.1)
							up = true;
						if (up)
							bounceCount += 0.001f;
						else
							bounceCount -= 0.001f;
						try {
							Thread.sleep(15L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

	   static void add(String s, double x, double y, double z, float r,
				float g, float b) {
			WaypointObj wp = new WaypointObj(s, x, y - 1, z, r, g, b);
			waypoints.add(wp);
		}

	    static int size() {
			return waypoints.size();
		}

	    float getDistance() {
			EntityPlayer player = Wrapper.getPlayer();
			return getDifference(x, y, z, player.posX, player.posY, player.posZ);
		}

	    float getDifference(double posX, double posY, double posZ,
				double d, double e, double g) {
			float f = (float) (posX - d);
			float f1 = (float) (posY - e);
			float f2 = (float) (posZ - g);
			return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
		}
	}
}
