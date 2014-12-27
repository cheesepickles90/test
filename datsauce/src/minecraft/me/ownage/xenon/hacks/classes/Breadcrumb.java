package me.ownage.xenon.hacks.classes;

import java.util.ArrayList;
import java.util.Iterator;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.RenderManager;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;



public class Breadcrumb extends XenonMod {
	public static ArrayList<double[]> positionsList = new ArrayList<double[]>();
	
	public Breadcrumb() {
		super("Breadcrumb", "Draws a trail behind the player.", Keyboard.KEY_G, 0xCCFFCC, EnumGuiCategory.ESP);
	}
	
	static int count = 0;
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(this.isEnabled()) {
			count++;
			if(count >= 50) {
				count = 0;
				if(positionsList.size() > 5) {
					positionsList.remove(0);
				}
			}
			for(Object o : mc.theWorld.playerEntities){
				if(o instanceof EntityPlayer) {
					EntityPlayer player1 = (EntityPlayer)o;

					boolean shouldBreadCrumb = (player1 == player) && ((player.movementInput.moveForward != 0) || (player.movementInput.moveStrafe != 0));

					if(shouldBreadCrumb) {
						double x =  (RenderManager.renderPosX);
						double y =  (RenderManager.renderPosY);
						double z =  (RenderManager.renderPosZ);

						positionsList.add(new double[] {x, y - player1.height, z});
					}
				}
			}
		}
	}

	public static double posit(double val) {
		return (val == 0) ? (val) : (val < 0 ? val * -1 : val); 
	}
	
	@Override
	public void onRender() {
		if(this.isEnabled()) {	
			GL11.glPushMatrix();
			GL11.glLineWidth(2F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_DEPTH_TEST);

			GL11.glBegin(GL11.GL_LINE_STRIP);
			for(double[] pos : positionsList) {
				double distance = posit(Math.hypot(pos[0] - RenderManager.renderPosX, pos[1] - RenderManager.renderPosY));
				if(distance > 100) continue;
				GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F - (float)(distance / 100D));
				GL11.glVertex3d(pos[0] - RenderManager.renderPosX, pos[1]  - RenderManager.renderPosY, pos[2] - RenderManager.renderPosZ);
			}
			GL11.glEnd();

			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}
}
