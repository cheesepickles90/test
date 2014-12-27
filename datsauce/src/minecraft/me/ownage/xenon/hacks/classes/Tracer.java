package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.FreecamEntity;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;




public class Tracer extends XenonMod
{
	public Tracer()
	{
		super("Tracer", "Draws a line to nearby players.", Keyboard.KEY_O, 0xFF5000, EnumGuiCategory.ESP);
	}
	
	@Override
	public void onRender()
	{
		if(isEnabled())
		{
			renderTracers();
		}
	}
	
	public void renderTracers()
	{
		try
		{
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glDepthMask(false);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glLineWidth(1.5F);
	    	for(Object entities: mc.theWorld.loadedEntityList)
	    	{
	    		if (entities != mc.thePlayer && entities != null)
	    		{
	    			if (entities instanceof EntityPlayer && !(entities instanceof FreecamEntity))
	    			{
	    				EntityPlayer entity = (EntityPlayer)entities;
	    				float distance = mc.renderViewEntity.getDistanceToEntity(entity);
	    				double posX = ((entity.lastTickPosX + (entity.posX - entity.lastTickPosX) - RenderManager.instance.renderPosX));
	    				double posY = ((entity.lastTickPosY + 1.4 + (entity.posY - entity.lastTickPosY) - RenderManager.instance.renderPosY));
	    				double posZ = ((entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) - RenderManager.instance.renderPosZ));

	    				if(Xenon.getFriends().isFriend(entity.username))
	    				{
	    					GL11.glColor3f(0.0F, 1.0F, 0.0F);
	    				}else
	    				{
	    					if (distance <= 6F)
	    					{
	    						GL11.glColor3f(1.0F, 0.0F, 0.0F);
	    					}else if (distance <= 96F)
	    					{
	    						GL11.glColor3f(1.0F, (distance / 100F), 0.0F);
	    					}else if (distance > 96F)
	    					{
	    						GL11.glColor3f(0.1F, 0.6F, 255.0F);
	    					}
	    				}

	    				GL11.glBegin(GL11.GL_LINE_LOOP);
	    				GL11.glVertex3d(0, 0, 0);
	    				GL11.glVertex3d(posX, posY, posZ);
	    				GL11.glEnd();
	    			}
	    		}
	    	}
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glDepthMask(true);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        GL11.glDisable(GL11.GL_LINE_SMOOTH);
	        GL11.glPopMatrix();
		}catch(Exception e) {}
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
    
    private AxisAlignedBB getEntityBoundingBox(EntityLiving entity) 
    {
        double scale = 1.5D;
        double x = (entity.posX - RenderManager.renderPosX), y = (entity.posY - RenderManager.renderPosY), z = (entity.posZ - RenderManager.renderPosZ);
        double width = entity.width/scale, height = entity.height, yOffset = entity.yOffset;
        return new AxisAlignedBB(x - width, y - yOffset, z - width, x + width, y + height, z + width);
    }
}
