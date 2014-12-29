package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityEnderman;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.RenderManager;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.rendering.GL11Helper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Tracers extends Module {
	
	public Tracers() {
		super("Tracers", ".tracers", "3D radar to entities", "Emotional Patrick", 0xFFFFFF, Keyboard.KEY_H, "World");
		this.shown = false;
	}
	
	@Override
	public void onGlobalRender(float f) {
		drawTracer();
	}
	
	public boolean shouldDraw(EntityLiving eL) {
		if (Wrapper.getPlayer().getDistanceToEntity(eL) <= 30.0F) {
			return false;
		}
		return true;
	}
	
	public boolean traceToEntity(EntityLiving eL) {
		if (eL.entityId == Wrapper.getPlayer().entityId) {
			return false;
		} else if (eL instanceof EntityPlayer) {
			EntityPlayer ep = (EntityPlayer) eL;
			return true;
		}
		return true;
	}
	
	public void renderESP(double pX, double pY, double pZ, EntityLiving eL) {		
		if (this.traceToEntity(eL)) {
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11Helper.enableDefaults();
			
			boolean var22 = true;
			if (!(eL instanceof EntityPlayer)) {
				var22 = false;
				if (Wrapper.getPlayer().getDistanceToEntity(eL) <= 30.0F) {
					var22 = true;
				}
			}
			if (!shouldDraw(eL) || var22) {
				Wrapper.getRenderer().disableLightmap(0.0D);
				GL11.glPushMatrix();
				GL11.glLineWidth(1.5F);
				GL11.glTranslated(pX, pY, pZ);
				GL11.glRotatef(eL.rotationYaw, 0.0F, (float) pY, 0.0F);
				GL11.glTranslated(-pX, -pY, -pZ);
				GL11.glTranslated(0.0D, eL.isSneaking() ? 0.2D : 0.1D, 0.0D);
				AxisAlignedBB aabb = AxisAlignedBB
						.getBoundingBox(pX - eL.width, pY, pZ
								- eL.width, pX
								+ eL.width, pY
								+ eL.height, pZ
								+ eL.width);
				if (eL instanceof EntityPlayer
						|| eL instanceof EntityZombie
						|| eL instanceof EntitySkeleton
						|| eL instanceof EntityEnderman) {
					aabb = aabb.contract(0.1D, -0.08D, 0.1D);
				}
				float distance = Wrapper.getPlayer()
						.getDistanceToEntity(eL);
				
				if (eL.hurtTime > 0) {
					GL11.glColor4f(1.0F, 0.4F, 0.0F, 1.0F);
				}
				else if (Helper.isEnemy(eL.getEntityName())) {
					GL11.glColor3f(1.0F, 0F, 0F);
				} else if (Helper.isFriend(eL.getEntityName())) {
					GL11.glColor3d(1.0D, 0.7D, 0.0D);
				} else {
					GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.85F);
				}
				
				if (!(eL instanceof EntityPlayer)) {
					GL11Helper.drawCrossedOutlinedBoundingBox(aabb);
				}
				if ((eL instanceof EntityPlayer)) {
					GL11.glDisable(GL11.GL_DEPTH_TEST);
					GL11.glDepthMask(false);
					GL11Helper.drawCrossedOutlinedBoundingBox(aabb);
					GL11.glDepthMask(true);
					GL11.glEnable(GL11.GL_DEPTH_TEST);
				}
				GL11.glPopMatrix();
				Wrapper.getRenderer().enableLightmap(0.0D);
			}
			GL11Helper.disableDefaults();
		}
	}
	
	public void drawTracer() {
		GL11.glPushMatrix();
	    GL11.glBlendFunc(770, 771);
	    GL11.glEnable(3042);
	    GL11.glDisable(3553);
	    GL11.glDisable(2929);
	    GL11.glDepthMask(false);
		for (int i = 0; i < Wrapper.getWorld().loadedEntityList.size(); ++i) {
			Entity e = (Entity) Wrapper.getWorld().loadedEntityList.get(i);
			if (e instanceof EntityLiving
					&& e != Wrapper.getPlayer()
					&& traceToEntity((EntityLiving) e)) {
				EntityLiving eL = (EntityLiving) e;
				double x = eL.lastTickPosX;
				double y = eL.lastTickPosY;
				double z = eL.lastTickPosZ;
				double x1 = RenderManager.renderPosX;
				double y1 = RenderManager.renderPosY;
				double z1 = RenderManager.renderPosZ;
				double pX = x1 - x;
				double pY = y1 - y;
				double pZ = z1 - z;
				
				GL11.glLineWidth(1.5F);
				boolean shouldTrace = true;
				if (!(eL instanceof EntityPlayer)) {
					shouldTrace = false;
					if (Wrapper.getPlayer().getDistanceToEntity(eL) <= 30.0F) {
						shouldTrace = true;
					}
				}
				if (!shouldDraw(eL) || shouldTrace) {
					float distance = Wrapper.getPlayer()
							.getDistanceToEntity(eL);
					
					if (Helper.isFriend(eL.getEntityName())) {
						GL11.glColor3d(1.0D, 0.7D, 0.0D);
					} else if (Helper.isEnemy(eL.getEntityName())) {
						GL11.glColor3f(1.0F, 0F, 0F);
					} else if (distance >= 64) {
						GL11.glColor3f(0.0F, 0.9F, 0.0F);
					} else {
				      GL11.glColor4f(1.0F, distance / (64 * 1.7F) * 1.3F, 0.0F, 1.0F);
				      // GL11.glColor3f(1.0F, (distance / 100F), 0.0F);
					}
					
					GL11.glBegin(GL11.GL_LINES);
					GL11.glVertex3d(0.0D, 0.0D, 0.0D);
					GL11.glVertex3d(-pX, -pY, -pZ);
					GL11.glEnd();
				}
			}
		}
	    GL11.glEnable(2929);
	    GL11.glDepthMask(true);
	    GL11.glDisable(3042);
	    GL11.glEnable(3553);
		GL11.glPopMatrix();
	}
}
