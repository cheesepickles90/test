package me.ownage.xenon.util;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.RenderManager;

import me.ownage.xenon.main.Xenon;

public class Waypoint {
	public static ArrayList<Waypoint> wayPoints = new ArrayList<Waypoint>();
	
	private String name;
	private double posX;
	private double posY;
	private double posZ;
	public double dX;
	public double dY;
	public double dZ;
	
	public float red, green, blue;
	
	public Waypoint(String name, double x, double y, double z, double renderX, double renderY, double renderZ) {
		this.name = name;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.red = (new Random().nextInt(255)) / 255F;
		this.green = (new Random().nextInt(255)) / 255F;
		this.blue = (new Random().nextInt(255)) / 255F;
		System.out.println(red + " " + green + " " + blue);
		update();
		wayPoints.add(this);
	}
	
	public void update() {
//		dX = (int)posX - Xenon.getMinecraft().thePlayer.lastTickPosX + (Xenon.getMinecraft().thePlayer.posX - Xenon.getMinecraft().thePlayer.lastTickPosX) * (double)Xenon.getMinecraft().timer.renderPartialTicks;
//		dY = (int)posY - Xenon.getMinecraft().thePlayer.lastTickPosY + (Xenon.getMinecraft().thePlayer.posY - Xenon.getMinecraft().thePlayer.lastTickPosY) * (double)Xenon.getMinecraft().timer.renderPartialTicks;
//		dZ = (int)posZ - Xenon.getMinecraft().thePlayer.lastTickPosZ + (Xenon.getMinecraft().thePlayer.posZ - Xenon.getMinecraft().thePlayer.lastTickPosZ) * (double)Xenon.getMinecraft().timer.renderPartialTicks;
		
		dX = (int)posX - RenderManager.renderPosX;
		dY = (int)posY - RenderManager.renderPosY;
		dZ = (int)posZ - RenderManager.renderPosZ;
	}
	
	public String getName() {
		return name;
	}
	
	public double getX() {
		return posX;
	}
	
	public double getY() {
		return posY;
	}
	
	public double getZ() {
		return posZ;
	}
}
