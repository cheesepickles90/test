package org.emotionalpatrick.colossus.utilities.rendering;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.Tessellator;

import org.emotionalpatrick.colossus.main.Wrapper;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL13;

public class GL11Helper {

	public static void drawBlockESP(double x, double y, double z, float r, float g, float b) {
		EntityPlayerSP ep = Wrapper.getPlayer();
		double d = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * Wrapper.getMinecraft().timer.renderPartialTicks;
		double d1 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * Wrapper.getMinecraft().timer.renderPartialTicks;
		double d2 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * Wrapper.getMinecraft().timer.renderPartialTicks;
		double d3 = x - d;
		double d4 = y - d1;
		double d5 = z - d2;
		GL11.glPushMatrix();
		GL11.glDisable(2929);
	    GL11.glDepthMask(false);
	    GL11.glEnable(3042);
	    GL11.glDisable(3553);
	    GL11.glEnable(2884);
	    GL11.glCullFace(1029);
	    GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0F);
		GL11.glColor4d(r, g, b, 0.2F);
		drawBox(d3, d4, d5, d3 + 1, d4 + 1, d5 + 1);
		GL11.glColor4d(r, g, b, 1.0F);
		drawOutlinedBox(d3, d4, d5, d3 + 1, d4 + 1, d5 + 1, 1.6F);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2884);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
		GL11.glPopMatrix();
	}

	public static void drawNukerESP(double x, double y, double z, float r, float g, float b) {
		EntityPlayerSP ep = Wrapper.getPlayer();
		double d = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * Wrapper.getMinecraft().timer.renderPartialTicks;
		double d1 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * Wrapper.getMinecraft().timer.renderPartialTicks;
		double d2 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * Wrapper.getMinecraft().timer.renderPartialTicks;
		double d3 = x - d;
		double d4 = y - d1;
		double d5 = z - d2;
		GL11.glPushMatrix();
		GL11.glDisable(2929);
	    GL11.glDepthMask(false);
	    GL11.glEnable(3042);
	    GL11.glDisable(3553);
	    GL11.glEnable(2884);
	    GL11.glCullFace(1029);
	    GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0F);
		GL11.glColor4f(r, g, b, 0.15F);
		drawBox(d3, d4, d5, d3 + 1, d4 + 1, d5 + 1);
		GL11.glColor4f(r, g, b, 0.15F);
		drawOutlinedBox(d3, d4, d5, d3 + 1, d4 + 1, d5 + 1, 1.6F);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2884);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
		GL11.glPopMatrix();
	}
	
	public static void drawBox(double x, double y, double z, double x2, double y2, double z2) {
		glBegin(GL_QUADS);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();

		glBegin(GL_QUADS);      
		glVertex3d(x, y2, z);        
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_QUADS);       
		glVertex3d(x, y, z);        
		glVertex3d(x2, y, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y, z);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y, z);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();
	}

	public static void drawOutlinedBox(double x, double y, double z, double x2, double y2, double z2, float l1) {
		glLineWidth(l1);

		glBegin(GL_LINES);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();

		glBegin(GL_LINES);      
		glVertex3d(x, y2, z);        
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_LINES);       
		glVertex3d(x, y, z);        
		glVertex3d(x2, y, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y, z);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();
	}

	public static void enableDefaults() {
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
	}

	public static void disableDefaults() {
	    GL11.glDisable(3042);
	    GL11.glEnable(3553);
	    GL11.glEnable(2929);
	    GL11.glDisable(2848);
	    GL11.glDisable(2881);
	    GL11.glEnable(2896);
	    GL11.glDisable(32925);
	    GL11.glDisable(32926);
	    GL11.glDepthMask(true);
    }

	public static void drawCrossedOutlinedBoundingBox(AxisAlignedBB ax) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(3);
		tessellator.addVertex(ax.minX, ax.minY, ax.minZ);
		tessellator.addVertex(ax.maxX, ax.minY, ax.minZ);
		tessellator.addVertex(ax.maxX, ax.minY, ax.maxZ);
		tessellator.addVertex(ax.minX, ax.minY, ax.maxZ);
		tessellator.addVertex(ax.minX, ax.minY, ax.minZ);
		tessellator.draw();
		tessellator.startDrawing(3);
		tessellator.addVertex(ax.minX, ax.maxY, ax.minZ);
		tessellator.addVertex(ax.maxX, ax.maxY, ax.minZ);
		tessellator.addVertex(ax.maxX, ax.maxY, ax.maxZ);
		tessellator.addVertex(ax.minX, ax.maxY, ax.maxZ);
		tessellator.addVertex(ax.minX, ax.maxY, ax.minZ);
		tessellator.draw();
		tessellator.startDrawing(1);
		tessellator.addVertex(ax.minX, ax.minY, ax.minZ);
		tessellator.addVertex(ax.minX, ax.maxY, ax.minZ);
		tessellator.addVertex(ax.maxX, ax.minY, ax.minZ);
		tessellator.addVertex(ax.maxX, ax.maxY, ax.minZ);
		tessellator.addVertex(ax.maxX, ax.minY, ax.maxZ);
		tessellator.addVertex(ax.maxX, ax.maxY, ax.maxZ);
		tessellator.addVertex(ax.minX, ax.minY, ax.maxZ);
		tessellator.addVertex(ax.minX, ax.maxY, ax.maxZ);
		tessellator.addVertex(ax.maxX, ax.minY, ax.minZ);
		tessellator.addVertex(ax.maxX, ax.maxY, ax.maxZ);
		tessellator.draw();
		GL11.glLineWidth(1.8F);
		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.minX, ax.minY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.maxZ);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.minX, ax.minY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.maxZ);
		GL11.glVertex3d(ax.minX, ax.minY, ax.minZ);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public static void drawBox(double x, double y, double z, float width) {
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, -width);
		drawRect(-width, 1.0f, width, 0f);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, width);
		drawRect(-width, 1.0f, width, 0f);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(width, 0, 0);
		GL11.glRotatef(90, 0, 1, 0);
		drawRect(-width, 1.0f, width, 0f);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(-width, 0, 0);
		GL11.glRotatef(90, 0, 1, 0);
		drawRect(-width, 1.0f, width, 0f);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, -width);
		GL11.glRotatef(90, 1, 0, 0);
		drawRect(-width, width * 2, width, 0f);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.0, -width);
		GL11.glRotatef(90, 1, 0, 0);
		drawRect(-width, width * 2, width, 0f);
		GL11.glPopMatrix();
	}

	public static void drawRect(float x, float y, float x1, float y1) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x1, y);
		GL11.glVertex2f(x1, y1);
		GL11.glVertex2f(x, y1);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y1);
		GL11.glVertex2f(x1, y1);
		GL11.glVertex2f(x1, y);
		GL11.glVertex2f(x, y);
		GL11.glEnd();
	}
}
