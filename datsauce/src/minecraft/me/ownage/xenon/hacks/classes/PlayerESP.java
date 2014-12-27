package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.FreecamEntity;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;




public class PlayerESP extends XenonMod
{
	public PlayerESP()
	{
		super("Player ESP", "Draws a box around players.", Keyboard.KEY_P, 0xFFFF00, EnumGuiCategory.ESP);
	}
	
	@Override
	public void onRender() {
		if(isEnabled()) {
			for(Object o : mc.theWorld.loadedEntityList) {
				if(o instanceof EntityPlayer) {
					EntityPlayer ep = (EntityPlayer) o;
					double d = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * (double)mc.timer.renderPartialTicks;
					double d1 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * (double)mc.timer.renderPartialTicks;
					double d2 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * (double)mc.timer.renderPartialTicks;
					drawPlayerESP(d - RenderManager.renderPosX, d1 - RenderManager.renderPosY, d2 - RenderManager.renderPosZ, ep, ep.height - 0.1, ep.width - 0.12);
				}
			}
		}
	}
	
	public void drawPlayerESP(double d, double d1, double d2, EntityPlayer ep, double e, double f)
	{
		if(!(ep instanceof FreecamEntity) && !(ep instanceof EntityPlayerSP))
		{
	        GL11.glPushMatrix();
	        GL11.glEnable(3042);
	        GL11.glColor4f(0.27F, 0.70F, 0.92F, 0.15F);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_DEPTH_TEST);
	        GL11.glDepthMask(false);
	        GL11.glLineWidth(1.8F);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glEnable(GL11.GL_LINE_SMOOTH);
	        drawBoundingBox(new AxisAlignedBB(d - f, d1 + 0.1, d2 - f, d + f, d1 + e + 0.25, d2 + f));
	        GL11.glColor4f(0.27F, 0.70F, 0.92F, 1.0F);
	        drawOutlinedBoundingBox(new AxisAlignedBB(d - f, d1 + 0.1, d2 - f, d + f, d1 + e + 0.25, d2 + f));
	        GL11.glDepthMask(true);
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glEnable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_LINE_SMOOTH);
	        GL11.glDisable(3042);
	        GL11.glPopMatrix();
		}
    }

    private void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB)
    {
        Tessellator var2 = Tessellator.instance;
        var2.startDrawing(3);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.draw();
        var2.startDrawing(3);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.draw();
        var2.startDrawing(1);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        var2.draw();
    }
    
    public static void drawBoundingBox(AxisAlignedBB axisalignedbb)
	{
		Tessellator tessellator = Tessellator.instance;
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
	    tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
	    tessellator.draw();
	}
}
