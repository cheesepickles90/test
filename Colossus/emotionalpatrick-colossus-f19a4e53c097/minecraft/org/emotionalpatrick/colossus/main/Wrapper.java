package org.emotionalpatrick.colossus.main;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GameSettings;
import net.minecraft.src.PlayerControllerMP;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.WorldClient;

/**
 * I'm not a rapper
 */

public class Wrapper {

	public static final  Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}

	public static final  EntityClientPlayerMP getPlayer() {
		return getMinecraft().thePlayer;
	}

	public static final  WorldClient getWorld() {
		return getMinecraft().theWorld;
	}

	public static final  PlayerControllerMP getController() {
		return getMinecraft().playerController;
	}

	public static final  RenderGlobal getGlobal() {
		return getMinecraft().renderGlobal;
	}

	public static final  EntityRenderer getRenderer() {
		return getMinecraft().entityRenderer;
	}

	public static final  FontRenderer getFontRenderer() {
		return getMinecraft().fontRenderer;
	}

	public static final  GameSettings getGameSettings() {
		return getMinecraft().gameSettings;
	}

	public static final  ScaledResolution getScaledResolution() {
		return new ScaledResolution(getMinecraft().gameSettings, getMinecraft().displayWidth, getMinecraft().displayHeight);
	}
	
	public static final  int getScreenWidth() {
		ScaledResolution sr = new ScaledResolution(getMinecraft().gameSettings, getMinecraft().displayWidth, getMinecraft().displayHeight);
		return sr.getScaledWidth();
	}

	public static final  int getScreenHeight() {
		ScaledResolution sr = new ScaledResolution(getMinecraft().gameSettings, getMinecraft().displayWidth, getMinecraft().displayHeight);
		return sr.getScaledHeight();
	}
}
