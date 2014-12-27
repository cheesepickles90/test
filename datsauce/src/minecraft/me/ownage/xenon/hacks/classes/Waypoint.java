package me.ownage.xenon.hacks.classes;

import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.RenderManager;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.XenonUtils;

public class Waypoint extends XenonMod {
	public Waypoint() {
		super("WayPoint", "Toggles waypoint rendering.", Keyboard.KEY_EQUALS, 0xFFFFFF, EnumGuiCategory.ESP);
	}
	
	@Override
	public void onRender() {
		if(isEnabled()) {
			for(me.ownage.xenon.util.Waypoint w : me.ownage.xenon.util.Waypoint.wayPoints) {
				w.update();			
				GL11.glDisable(2896 /*GL_LIGHTING*/);
				XenonUtils.drawESP(w.dX, w.dY, w.dZ, w.red, w.green, w.blue);
				XenonUtils.drawTag(w.getName(), w.dX, w.dY, w.dZ);
				GL11.glEnable(2896 /*GL_LIGHTING*/);
				XenonUtils.drawWayPointTracer(w);
			}
		}
	}
}
