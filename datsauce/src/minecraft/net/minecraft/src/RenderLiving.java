package net.minecraft.src;

import java.util.Random;

import me.ownage.xenon.hacks.classes.NameTags;
import me.ownage.xenon.hacks.classes.PlayerESP;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.Friend;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderLiving extends Render
{
    protected ModelBase mainModel;

    /** The model to be used during the render passes. */
    protected ModelBase renderPassModel;

    public RenderLiving(ModelBase par1ModelBase, float par2)
    {
        this.mainModel = par1ModelBase;
        this.shadowSize = par2;
    }

    /**
     * Sets the model to be used in the current render pass (the first render pass is done after the primary model is
     * rendered) Args: model
     */
    public void setRenderPassModel(ModelBase par1ModelBase)
    {
        this.renderPassModel = par1ModelBase;
    }

    /**
     * Returns a rotation angle that is inbetween two other rotation angles. par1 and par2 are the angles between which
     * to interpolate, par3 is probably a float between 0.0 and 1.0 that tells us where "between" the two angles we are.
     * Example: par1 = 30, par2 = 50, par3 = 0.5, then return = 40
     */
    private float interpolateRotation(float par1, float par2, float par3)
    {
        float var4;

        for (var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F)
        {
            ;
        }

        while (var4 >= 180.0F)
        {
            var4 -= 360.0F;
        }

        return par1 + par3 * var4;
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        this.mainModel.onGround = this.renderSwingProgress(par1EntityLiving, par9);

        if (this.renderPassModel != null)
        {
            this.renderPassModel.onGround = this.mainModel.onGround;
        }

        this.mainModel.isRiding = par1EntityLiving.isRiding();

        if (this.renderPassModel != null)
        {
            this.renderPassModel.isRiding = this.mainModel.isRiding;
        }

        this.mainModel.isChild = par1EntityLiving.isChild();

        if (this.renderPassModel != null)
        {
            this.renderPassModel.isChild = this.mainModel.isChild;
        }

        try
        {
            float var10 = this.interpolateRotation(par1EntityLiving.prevRenderYawOffset, par1EntityLiving.renderYawOffset, par9);
            float var11 = this.interpolateRotation(par1EntityLiving.prevRotationYawHead, par1EntityLiving.rotationYawHead, par9);
            float var12 = par1EntityLiving.prevRotationPitch + (par1EntityLiving.rotationPitch - par1EntityLiving.prevRotationPitch) * par9;
            this.renderLivingAt(par1EntityLiving, par2, par4, par6);
            float var13 = this.handleRotationFloat(par1EntityLiving, par9);
            this.rotateCorpse(par1EntityLiving, var13, var10, par9);
            float var14 = 0.0625F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
            this.preRenderCallback(par1EntityLiving, par9);
            GL11.glTranslatef(0.0F, -24.0F * var14 - 0.0078125F, 0.0F);
            float var15 = par1EntityLiving.prevLimbYaw + (par1EntityLiving.limbYaw - par1EntityLiving.prevLimbYaw) * par9;
            float var16 = par1EntityLiving.limbSwing - par1EntityLiving.limbYaw * (1.0F - par9);

            if (par1EntityLiving.isChild())
            {
                var16 *= 3.0F;
            }

            if (var15 > 1.0F)
            {
                var15 = 1.0F;
            }

            GL11.glEnable(GL11.GL_ALPHA_TEST);
            this.mainModel.setLivingAnimations(par1EntityLiving, var16, var15, par9);
            this.renderModel(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
            float var19;
            int var18;
            float var20;
            float var22;

            for (int var17 = 0; var17 < 4; ++var17)
            {
                var18 = this.shouldRenderPass(par1EntityLiving, var17, par9);

                if (var18 > 0)
                {
                    this.renderPassModel.setLivingAnimations(par1EntityLiving, var16, var15, par9);
                    this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);

                    if ((var18 & 240) == 16)
                    {
                        this.func_82408_c(par1EntityLiving, var17, par9);
                        this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                    }

                    if ((var18 & 15) == 15)
                    {
                        var19 = (float)par1EntityLiving.ticksExisted + par9;
                        this.loadTexture("%blur%/misc/glint.png");
                        GL11.glEnable(GL11.GL_BLEND);
                        var20 = 0.5F;
                        GL11.glColor4f(var20, var20, var20, 1.0F);
                        GL11.glDepthFunc(GL11.GL_EQUAL);
                        GL11.glDepthMask(false);

                        for (int var21 = 0; var21 < 2; ++var21)
                        {
                            GL11.glDisable(GL11.GL_LIGHTING);
                            var22 = 0.76F;
                            GL11.glColor4f(0.5F * var22, 0.25F * var22, 0.8F * var22, 1.0F);
                            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                            GL11.glMatrixMode(GL11.GL_TEXTURE);
                            GL11.glLoadIdentity();
                            float var23 = var19 * (0.001F + (float)var21 * 0.003F) * 20.0F;
                            float var24 = 0.33333334F;
                            GL11.glScalef(var24, var24, var24);
                            GL11.glRotatef(30.0F - (float)var21 * 60.0F, 0.0F, 0.0F, 1.0F);
                            GL11.glTranslatef(0.0F, var23, 0.0F);
                            GL11.glMatrixMode(GL11.GL_MODELVIEW);
                            this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                        }

                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glMatrixMode(GL11.GL_TEXTURE);
                        GL11.glDepthMask(true);
                        GL11.glLoadIdentity();
                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
                        GL11.glEnable(GL11.GL_LIGHTING);
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glDepthFunc(GL11.GL_LEQUAL);
                    }

                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_ALPHA_TEST);
                }
            }

            GL11.glDepthMask(true);
            this.renderEquippedItems(par1EntityLiving, par9);
            float var26 = par1EntityLiving.getBrightness(par9);
            var18 = this.getColorMultiplier(par1EntityLiving, var26, par9);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

            if ((var18 >> 24 & 255) > 0 || par1EntityLiving.hurtTime > 0 || par1EntityLiving.deathTime > 0)
            {
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDepthFunc(GL11.GL_EQUAL);

                if (par1EntityLiving.hurtTime > 0 || par1EntityLiving.deathTime > 0)
                {
                    GL11.glColor4f(var26, 0.0F, 0.0F, 0.4F);
                    this.mainModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);

                    for (int var27 = 0; var27 < 4; ++var27)
                    {
                        if (this.inheritRenderPass(par1EntityLiving, var27, par9) >= 0)
                        {
                            GL11.glColor4f(var26, 0.0F, 0.0F, 0.4F);
                            this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                        }
                    }
                }

                if ((var18 >> 24 & 255) > 0)
                {
                    var19 = (float)(var18 >> 16 & 255) / 255.0F;
                    var20 = (float)(var18 >> 8 & 255) / 255.0F;
                    float var29 = (float)(var18 & 255) / 255.0F;
                    var22 = (float)(var18 >> 24 & 255) / 255.0F;
                    GL11.glColor4f(var19, var20, var29, var22);
                    this.mainModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);

                    for (int var28 = 0; var28 < 4; ++var28)
                    {
                        if (this.inheritRenderPass(par1EntityLiving, var28, par9) >= 0)
                        {
                            GL11.glColor4f(var19, var20, var29, var22);
                            this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                        }
                    }
                }

                GL11.glDepthFunc(GL11.GL_LEQUAL);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        catch (Exception var25)
        {
            var25.printStackTrace();
        }

        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
        this.passSpecialRender(par1EntityLiving, par2, par4, par6);
    }

    /**
     * Renders the model in RenderLiving
     */
    protected void renderModel(EntityLiving par1EntityLiving, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.func_98190_a(par1EntityLiving);

        if (!par1EntityLiving.getHasActivePotion())
        {
            this.mainModel.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
        }
        else if (!par1EntityLiving.func_98034_c(Minecraft.getMinecraft().thePlayer))
        {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
            this.mainModel.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
        }
        else
        {
            this.mainModel.setRotationAngles(par2, par3, par4, par5, par6, par7, par1EntityLiving);
        }
    }

    protected void func_98190_a(EntityLiving par1EntityLiving)
    {
        this.loadTexture(par1EntityLiving.getTexture());
    }

    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    protected void renderLivingAt(EntityLiving par1EntityLiving, double par2, double par4, double par6)
    {
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
    }

    protected void rotateCorpse(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
        GL11.glRotatef(180.0F - par3, 0.0F, 1.0F, 0.0F);

        if (par1EntityLiving.deathTime > 0)
        {
            float var5 = ((float)par1EntityLiving.deathTime + par4 - 1.0F) / 20.0F * 1.6F;
            var5 = MathHelper.sqrt_float(var5);

            if (var5 > 1.0F)
            {
                var5 = 1.0F;
            }

            GL11.glRotatef(var5 * this.getDeathMaxRotation(par1EntityLiving), 0.0F, 0.0F, 1.0F);
        }
    }

    protected float renderSwingProgress(EntityLiving par1EntityLiving, float par2)
    {
        return par1EntityLiving.getSwingProgress(par2);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLiving par1EntityLiving, float par2)
    {
        return (float)par1EntityLiving.ticksExisted + par2;
    }

    protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2) {}

    /**
     * renders arrows the Entity has been attacked with, attached to it
     */
    protected void renderArrowsStuckInEntity(EntityLiving par1EntityLiving, float par2)
    {
        int var3 = par1EntityLiving.getArrowCountInEntity();

        if (var3 > 0)
        {
            EntityArrow var4 = new EntityArrow(par1EntityLiving.worldObj, par1EntityLiving.posX, par1EntityLiving.posY, par1EntityLiving.posZ);
            Random var5 = new Random((long)par1EntityLiving.entityId);
            RenderHelper.disableStandardItemLighting();

            for (int var6 = 0; var6 < var3; ++var6)
            {
                GL11.glPushMatrix();
                ModelRenderer var7 = this.mainModel.getRandomModelBox(var5);
                ModelBox var8 = (ModelBox)var7.cubeList.get(var5.nextInt(var7.cubeList.size()));
                var7.postRender(0.0625F);
                float var9 = var5.nextFloat();
                float var10 = var5.nextFloat();
                float var11 = var5.nextFloat();
                float var12 = (var8.posX1 + (var8.posX2 - var8.posX1) * var9) / 16.0F;
                float var13 = (var8.posY1 + (var8.posY2 - var8.posY1) * var10) / 16.0F;
                float var14 = (var8.posZ1 + (var8.posZ2 - var8.posZ1) * var11) / 16.0F;
                GL11.glTranslatef(var12, var13, var14);
                var9 = var9 * 2.0F - 1.0F;
                var10 = var10 * 2.0F - 1.0F;
                var11 = var11 * 2.0F - 1.0F;
                var9 *= -1.0F;
                var10 *= -1.0F;
                var11 *= -1.0F;
                float var15 = MathHelper.sqrt_float(var9 * var9 + var11 * var11);
                var4.prevRotationYaw = var4.rotationYaw = (float)(Math.atan2((double)var9, (double)var11) * 180.0D / Math.PI);
                var4.prevRotationPitch = var4.rotationPitch = (float)(Math.atan2((double)var10, (double)var15) * 180.0D / Math.PI);
                double var16 = 0.0D;
                double var18 = 0.0D;
                double var20 = 0.0D;
                float var22 = 0.0F;
                this.renderManager.renderEntityWithPosYaw(var4, var16, var18, var20, var22, par2);
                GL11.glPopMatrix();
            }

            RenderHelper.enableStandardItemLighting();
        }
    }

    protected int inheritRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return this.shouldRenderPass(par1EntityLiving, par2, par3);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return -1;
    }

    protected void func_82408_c(EntityLiving par1EntityLiving, int par2, float par3) {}

    protected float getDeathMaxRotation(EntityLiving par1EntityLiving)
    {
        return 90.0F;
    }

    /**
     * Returns an ARGB int color back. Args: entityLiving, lightBrightness, partialTickTime
     */
    protected int getColorMultiplier(EntityLiving par1EntityLiving, float par2, float par3)
    {
        return 0;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving par1EntityLiving, float par2) {}

    /**
     * Passes the specialRender and renders it
     */
    protected void passSpecialRender(EntityLiving par1EntityLiving, double par2, double par4, double par6)
    {
        if (Minecraft.isGuiEnabled() && par1EntityLiving != this.renderManager.livingPlayer && !par1EntityLiving.func_98034_c(Minecraft.getMinecraft().thePlayer) && (par1EntityLiving.func_94059_bO() || par1EntityLiving.func_94056_bM() && par1EntityLiving == this.renderManager.field_96451_i))
        {
            float var8 = 1.6F;
            float var9 = 0.016666668F * var8;
            double var10 = par1EntityLiving.getDistanceSqToEntity(this.renderManager.livingPlayer);
            float var12 = par1EntityLiving.isSneaking() ? 256.0F : 256.0F;

            if (var10 < (double)(var12 * var12))
            {
                String var13 = par1EntityLiving.func_96090_ax();

                this.func_96449_a(par1EntityLiving, par2, par4, par6, var13, var9, var10);
            }
        }
    }

    protected void func_96449_a(EntityLiving par1EntityLiving, double par2, double par4, double par6, String par8Str, float par9, double par10)
    {
        if (par1EntityLiving.isPlayerSleeping())
        {
            this.renderLivingLabel(par1EntityLiving, par8Str, par2, par4 - 1.5D, par6, 64);
        }
        else
        {
            this.renderLivingLabel(par1EntityLiving, par8Str, par2, par4, par6, 64);
        }
    }

    /**
     * Draws the debug or playername text above a living
     */
    protected void renderLivingLabel(EntityLiving par1EntityLiving, String par2Str, double par3, double par5, double par7, int par9)
    {
    	 for(Friend friend: Xenon.getFriends().friendsList)
		 {
			 par2Str = par2Str.replace(friend.getName(), friend.getAlias());
		 }

		 double var10 = par1EntityLiving.getDistanceToEntity(this.renderManager.livingPlayer);

		 if (var10 <= 256)
		 {
			 if(Hacks.findMod(NameTags.class).isEnabled()) {
				 par2Str = par2Str + " \247f[\247e" + (int)var10 + "\247f]";
			 }

			 FontRenderer var12 = this.getFontRendererFromRenderManager();
			 float var13 = 1.6F;
			 float var14 = (float) (0.016666668F * var13 * (Hacks.findMod(NameTags.class).isEnabled() ? (par1EntityLiving.getDistanceToEntity(this.renderManager.livingPlayer) >= 10 ? (var10 / 10) : 1) : 1));
			 GL11.glPushMatrix();
			 GL11.glTranslatef((float)par3 + 0.0F, (float)par5 + 2.3F, (float)par7);
			 GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			 GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
			 GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
			 GL11.glScalef(-var14, -var14, var14);
			 GL11.glDisable(GL11.GL_LIGHTING);
			 GL11.glDepthMask(false);
			 GL11.glDisable(GL11.GL_DEPTH_TEST);
			 GL11.glEnable(GL11.GL_BLEND);
			 GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			 Tessellator var15 = Tessellator.instance;
			 byte var16 = 0;

			 if (par2Str.equals("deadmau5"))
			 {
				 var16 = -10;
			 }

			 GL11.glDisable(GL11.GL_TEXTURE_2D);
			 var15.startDrawingQuads();
			 int var17 = (var12.getStringWidth(par2Str) / 2) + 1;
			 var15.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.35F);
			 if(par1EntityLiving instanceof EntityPlayer) {
				 if(Xenon.getFriends().isFriend(((EntityPlayer)par1EntityLiving).username))
				 {
					 var15.setColorRGBA_F(0.0F, 1.0F, 0.0F, 0.5F);
				 }
			 }
			 var15.addVertex((double)(-var17 - 1), (double)(-1 + var16), 0.0D);
			 var15.addVertex((double)(-var17 - 1), (double)(9 + var16), 0.0D);
			 var15.addVertex((double)(var17 + 1), (double)(9 + var16), 0.0D);
			 var15.addVertex((double)(var17 + 1), (double)(-1 + var16), 0.0D);
			 var15.draw();

			 if(par1EntityLiving != null && par1EntityLiving instanceof EntityPlayer && Hacks.findMod(PlayerESP.class).isEnabled())
			 {
				 GL11.glEnable(GL11.GL_TEXTURE_2D);
				 GL11.glEnable(32826);
				 RenderHelper.enableGUIStandardItemLighting();
				 GL11.glDisable(GL11.GL_BLEND);
				 EntityPlayer player = ((EntityPlayer) par1EntityLiving);

				 if(player.inventory.getCurrentItem() != null) // Item Held
				 {
					 boolean enc = player.inventory.getCurrentItem().hasEffect();
					 int usingId = player.inventory.getCurrentItem().itemID;
					 int usingDamage = player.inventory.getCurrentItem().getItemDamage();
					 (new RenderItem()).renderItemIntoGUI(Xenon.getMinecraft().fontRenderer, Xenon.getMinecraft().renderEngine, player.inventory.getCurrentItem(), -45, var17 - 50);
					 if(enc)
					 {
						 GL11.glScalef(0.5F, 0.5F, 0.5F);
						 RenderHelper.disableStandardItemLighting();
						 var12.drawStringWithShadow("enc", -72, var17 - 52, 0xFFFFFF);
						 RenderHelper.enableGUIStandardItemLighting();
						 GL11.glScalef(2F, 2F, 2F);
					 }
				 }
				 if(player.inventory.armorInventory[3] != null)
				 {
					 boolean enc = player.inventory.armorInventory[3].hasEffect();
					 int usingId = player.inventory.armorInventory[3].itemID;
					 int usingDamage = player.inventory.armorInventory[3].getItemDamage();
					 (new RenderItem()).renderItemIntoGUI(Xenon.getMinecraft().fontRenderer, Xenon.getMinecraft().renderEngine, player.inventory.armorInventory[3], -25, var17 - 47);
					 if(enc)
					 {
						 GL11.glScalef(0.5F, 0.5F, 0.5F);
						 RenderHelper.disableStandardItemLighting();
						 var12.drawStringWithShadow("enc", -43, var17 - 30, 0xFFFFFF);
						 RenderHelper.enableGUIStandardItemLighting();
						 GL11.glScalef(2F, 2F, 2F);
					 }
				 }
				 if(player.inventory.armorInventory[2] != null)
				 {
					 boolean enc = player.inventory.armorInventory[2].hasEffect();
					 int usingId = player.inventory.armorInventory[2].itemID;
					 int usingDamage = player.inventory.armorInventory[2].getItemDamage();
					 (new RenderItem()).renderItemIntoGUI(Xenon.getMinecraft().fontRenderer, Xenon.getMinecraft().renderEngine, player.inventory.armorInventory[2], -10, var17 - 47);
					 if(enc)
					 {
						 GL11.glScalef(0.5F, 0.5F, 0.5F);
						 RenderHelper.disableStandardItemLighting();
						 var12.drawStringWithShadow("enc", -13, var17 - 39, 0xFFFFFF);
						 RenderHelper.enableGUIStandardItemLighting();
						 GL11.glScalef(2F, 2F, 2F);
					 }
				 }
				 if(player.inventory.armorInventory[1] != null)
				 {
					 boolean enc = player.inventory.armorInventory[1].hasEffect();
					 int usingId = player.inventory.armorInventory[1].itemID;
					 int usingDamage = player.inventory.armorInventory[1].getItemDamage();
					 (new RenderItem()).renderItemIntoGUI(Xenon.getMinecraft().fontRenderer, Xenon.getMinecraft().renderEngine, player.inventory.armorInventory[1], 5, var17 - 47);
					 if(enc)
					 {
						 GL11.glScalef(0.5F, 0.5F, 0.5F);
						 RenderHelper.disableStandardItemLighting();
						 var12.drawStringWithShadow("enc", 17, var17 - 39, 0xFFFFFF);
						 RenderHelper.enableGUIStandardItemLighting();
						 GL11.glScalef(2F, 2F, 2F);
					 }
				 }
				 if(player.inventory.armorInventory[0] != null)
				 {
					 boolean enc = player.inventory.armorInventory[0].hasEffect();
					 int usingId = player.inventory.armorInventory[0].itemID;
					 int usingDamage = player.inventory.armorInventory[0].getItemDamage();
					 (new RenderItem()).renderItemIntoGUI(Xenon.getMinecraft().fontRenderer, Xenon.getMinecraft().renderEngine, player.inventory.armorInventory[0], 20, var17 - 45);
					 if(enc)
					 {
						 GL11.glScalef(0.5F, 0.5F, 0.5F);
						 RenderHelper.disableStandardItemLighting();
						 var12.drawStringWithShadow("enc", 47, var17 - 32, 0xFFFFFF);
						 RenderHelper.enableGUIStandardItemLighting();
						 GL11.glScalef(2F, 2F, 2F);
					 }
				 }
				 GL11.glEnable(GL11.GL_BLEND);
				 RenderHelper.disableStandardItemLighting();
				 GL11.glDisable(32826);
				 GL11.glDisable(GL11.GL_TEXTURE_2D);
			 }

			 GL11.glDisable(GL11.GL_BLEND);
			 GL11.glEnable(GL11.GL_TEXTURE_2D);
			 var12.drawStringWithShadow(par2Str, -var12.getStringWidth(par2Str) / 2, var16, par1EntityLiving.isSneaking() ? 0xFF0000 : 0xFFFFFF);
			 GL11.glEnable(GL11.GL_DEPTH_TEST);
			 GL11.glDepthMask(true);
			 var12.drawStringWithShadow(par2Str, -var12.getStringWidth(par2Str) / 2, var16, par1EntityLiving.isSneaking() ? 0xFF0000 : 0xFFFFFF);
			 GL11.glEnable(GL11.GL_BLEND);
			 GL11.glEnable(GL11.GL_LIGHTING);
			 GL11.glDisable(GL11.GL_BLEND);
			 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			 GL11.glPopMatrix();
		 }
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRenderLiving((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
    }
}
