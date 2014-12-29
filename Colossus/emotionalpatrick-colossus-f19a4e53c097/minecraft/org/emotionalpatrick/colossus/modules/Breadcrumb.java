package org.emotionalpatrick.colossus.modules;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.src.RenderManager;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.rendering.GL11Helper;
import org.lwjgl.opengl.GL11;

public class Breadcrumb extends Module {

	long prevPosition = -1L;
	long delayTime = 100L;
	
	private ArrayList<double[]> crumbs = new ArrayList<double[]>();
	
	private boolean recording = true;

	public Breadcrumb() {
		super("Breadcrumb", ".crumb", "Draws a line everywhere you go", "Emotional Patrick", "World");
	}

	@Override
	public void onTick() {
		if (System.currentTimeMillis() >= prevPosition + delayTime && recording) {
			crumbs.add(getPosition());
			this.prevPosition = System.currentTimeMillis();
		}
	}

	public static double[] getPosition() {
		double pos[] = { Wrapper.getPlayer().posX,
				Wrapper.getPlayer().posY,
				Wrapper.getPlayer().posZ };
		return pos;
	}

	@Override
	public void onGlobalRender(float tickTime) {
		drawCrumbs();
	}

	@Override
	public void onDisable() {
		this.prevPosition = -1L;
	}

	private void drawCrumbs() {
		GL11.glPushMatrix();
		GL11Helper.enableDefaults();
		GL11.glBegin(GL11.GL_LINE_STRIP);
		Iterator iterator = crumbs.iterator();

		while (iterator.hasNext()) {
			double[] positionArray = (double[]) iterator.next();
			RenderManager rm = RenderManager.instance;
			double x1 = positionArray[0] - rm.viewerPosX;
			double y1 = positionArray[1] - rm.viewerPosY;
			double z1 = positionArray[2] - rm.viewerPosZ;
			
	        double distance = getDistToXYZ(positionArray[0], positionArray[1], positionArray[2]);
            // GL11.glColor4f(0.5F + 0.1F * (float)distance, 0.5F - 0.01F * (float)distance, 0.0F, 1.0F);
			GL11.glColor4f(1.0F - (float)distance * 0.006F, 0.0F + (float)distance * 0.1F, 0.0F, 0.8F);
            
			GL11.glVertex3d(x1, y1 - 1.5D, z1);
		}
		
		GL11.glEnd();
		GL11Helper.disableDefaults();
		GL11.glPopMatrix();
	}
	
	public double getDistToXYZ(double x, double y, double z) {
		double d1 = Wrapper.getPlayer().posX - x;
		double d2 = Wrapper.getPlayer().posY - (y + 0.5D);
		double d3 = Wrapper.getPlayer().posZ - z;
		double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
		return d4;
	}
	
	@Override
	public void externalCommand (String s) {
		if (s.equalsIgnoreCase(".bcc")) {
			crumbs.clear();
			Helper.addChat("Breadcrumbs cleared.");
		}
		if (s.equalsIgnoreCase(".bcr")) {
			recording = !recording;
			Helper.addChat(recording ? "Breadcrumb is now recording."
					: "Breadcrumb has stopped recording.");
		}
	}
}
