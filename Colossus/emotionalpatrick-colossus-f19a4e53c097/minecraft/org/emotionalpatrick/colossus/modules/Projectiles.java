package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3;

import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.rendering.GL11Helper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Projectiles extends Module {

	public double pX = -9000, pY = -9000, pZ = -9000;

	public Projectiles() {
		super("Projectiles", ".projectiles", "Prediction lines for thrown items", "Emotional Patrick", 0x63D1F4, Keyboard.KEY_M, "World");
	}

	@Override
	public void onGlobalRender(float f) {
		ItemStack is = Wrapper.getMinecraft().thePlayer.inventory
				.getCurrentItem();
		if (is != null) {
			int id = is.itemID;
			boolean drawPrediction = true;
			float var4 = 0.05F;
			float var5 = 3.2F;
			float var6 = 0.0F;
			boolean var7 = false;

			int FUCKYOUEGGS = 344;
			// akemi rat sniglet lelele xd xd
			
			if (id == 261) {
				var4 = 0.05F;
				var5 = 3.2F;
			} else if (id == 368) {
				var4 = 0.03F;
				var5 = 1.5F;
			} else if (id == 332) {
				var4 = 0.03F;
				var5 = 1.5F;
			} else if (id == 346) {
				var5 = 1.5F;
			} else if (id == FUCKYOUEGGS) {
				var4 = 0.03F;
				var5 = 1.5F;
			} else if ((is.getItemDamage() & 0x4000) > 0) {
				// haruko was here
				var4 = 0.05F;
				var5 = 0.5F;
				var6 = -20F;
			} else {
				drawPrediction = false;
			}

			if (drawPrediction) {
				AxisAlignedBB var9 = AxisAlignedBB.getBoundingBox(0.0D, 0.0D,
						0.0D, 0.0D, 0.0D, 0.0D);
				float var10 = (float) Wrapper.getMinecraft().thePlayer.posX;
				float var11 = (float) Wrapper.getMinecraft().thePlayer.posY;
				float var12 = (float) Wrapper.getMinecraft().thePlayer.posZ;
				float var13 = Wrapper.getMinecraft().thePlayer.rotationYaw;
				float var14 = Wrapper.getMinecraft().thePlayer.rotationPitch;
				float var15 = var13 / 180.0F * (float) Math.PI;
				float var16 = MathHelper.sin(var15);
				float var17 = MathHelper.cos(var15);
				float var18 = var14 / 180.0F * (float) Math.PI;
				float var19 = MathHelper.cos(var18);
				float var20 = (var14 + var6) / 180.0F * (float) Math.PI;
				float var21 = MathHelper.sin(var20);
				float var22 = var10 - var17 * 0.16F;
				float var23 = var11 - 0.1F;
				float var24 = var12 - var16 * 0.16F;
				float var25 = -var16 * var19 * 0.4F;
				float var26 = -var21 * 0.4F;
				float var27 = var17 * var19 * 0.4F;
				float var28 = MathHelper.sqrt_double(var25 * var25
						+ var26 * var26 + var27 * var27);
				var25 /= var28;
				var26 /= var28;
				var27 /= var28;
				var25 *= var5;
				var26 *= var5;
				var27 *= var5;
				float var29 = var22;
				float var30 = var23;
				float var31 = var24;
				GL11.glPushMatrix();
				GL11.glColor3f(0.27F, 0.70F, 0.92F);
				GL11Helper.enableDefaults();
				this.beginGLLines();
				int var32 = 0;
				boolean var33 = true;
				while (var33) {
					++var32;
					var29 += var25;
					var30 += var26;
					var31 += var27;
					Vec3 var34 = Vec3.createVectorHelper(var22,
							var23, var24);
					Vec3 var35 = Vec3.createVectorHelper(var29,
							var30, var31);
					MovingObjectPosition var8 = Wrapper.getMinecraft().theWorld
							.rayTraceBlocks(var34, var35);
					if (var8 != null) {
						var22 = (float) var8.hitVec.xCoord;
						var23 = (float) var8.hitVec.yCoord;
						var24 = (float) var8.hitVec.zCoord;
						var33 = false;
					} else if (var32 > 200) {
						var33 = false;
					} else {
						this.drawLine3D(var22 - var10, var23 - var11, var24
								- var12, var29 - var10, var30 - var11, var31
								- var12);
						if (var7) {
							var9.setBounds(var22 - 0.125F,
									var23, var24 - 0.125F,
									var22 + 0.125F,
									var23 + 0.25F,
									var24 + 0.125F);
							var4 = 0.0F;
							float var37;
							for (int var36 = 0; var36 < 5; ++var36) {
								var37 = (float) (var9.minY + (var9.maxY - var9.minY)
										* (var36) / 5.0D);
								float var38 = (float) (var9.minY + (var9.maxY - var9.minY)
										* (var36 + 1) / 5.0D);
								AxisAlignedBB var39 = AxisAlignedBB
										.getBoundingBox(var9.minX,
												var37, var9.minZ,
												var9.maxX, var38,
												var9.maxZ);
								if (Wrapper.getMinecraft().theWorld
										.isAABBInMaterial(var39, Material.water)) {
									var4 += 0.2F;
								}
							}
							float var40 = var4 * 2.0F - 1.0F;
							var26 += 0.04F * var40;
							var37 = 0.92F;

							if (var4 > 0.0F) {
								var37 *= 0.9F;
								var26 *= 0.8F;
							}

							var25 *= var37;
							var26 *= var37;
							var27 *= var37;
						} else {
							var25 *= 0.99F;
							var26 *= 0.99F;
							var27 *= 0.99F;
							var26 -= var4;
						}
						var22 = var29;
						var23 = var30;
						var24 = var31;
					}

					if (!var33) {
						pX = var22;
						pY = var23;
						pZ = var24;
						GL11.glVertex3d(pX - var10 - 0.5F, pY - var11, pZ - var12 - 0.5F);
						GL11.glVertex3d(pX - var10 - 0.5F, pY - var11, pZ - var12 + 0.5F);
						GL11.glVertex3d(pX - var10 + 0.5F, pY - var11, pZ - var12 + 0.5F);
						GL11.glVertex3d(pX - var10 + 0.5F, pY - var11, pZ - var12 - 0.5F);
						GL11.glVertex3d(pX - var10 + 0.5F, pY - var11, pZ - var12 + 0.5F);
						GL11.glVertex3d(pX - var10 - 0.5F, pY - var11, pZ - var12 + 0.5F);
						GL11.glVertex3d(pX - var10 - 0.5F, pY - var11, pZ - var12 - 0.5F);
						GL11.glVertex3d(pX - var10 + 0.5F, pY - var11, pZ - var12 - 0.5F);
						GL11.glVertex3d(pX - var10 - 0.5F, pY - var11, pZ - var12 - 0.5F);
						GL11.glVertex3d(pX - var10 + 0.5F, pY - var11, pZ - var12 + 0.5F);
						GL11.glVertex3d(pX - var10 - 0.5F, pY - var11, pZ - var12 + 0.5F);
						GL11.glVertex3d(pX - var10 + 0.5F, pY - var11, pZ - var12 - 0.5F);
					} else {
					}
				}
				this.endGLLines();
				GL11Helper.disableDefaults();
				GL11.glPopMatrix();
			}
		}
	}

	public void drawLine3D(float var1, float var2, float var3, float var4,
			float var5, float var6) {
		GL11.glVertex3d(var1, var2, var3);
		GL11.glVertex3d(var4, var5, var6);
	}

	public void beginGLLines() {
		GL11.glBegin(GL11.GL_LINES);
	}

	public void endGLLines() {
		GL11.glEnd();
	}
}
