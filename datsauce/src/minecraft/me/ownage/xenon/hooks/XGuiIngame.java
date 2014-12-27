package me.ownage.xenon.hooks;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import me.ownage.xenon.gui.XenonGuiClick;
import me.ownage.xenon.gui.elements.XWindow;
import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.hacks.classes.Minimap;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.FormattedString;
import me.ownage.xenon.util.XenonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StatCollector;
import net.minecraft.src.GuiChat;

public class XGuiIngame extends GuiIngame {
	public static boolean hideGui = false;
	
	private Minecraft mc = Xenon.getMinecraft();
	private FontRenderer fr = mc.fontRenderer;

	public XGuiIngame(Minecraft par1Minecraft) {
		super(par1Minecraft);
	}

	Minimap m = new Minimap();
	
	@Override
	public void renderGameOverlay(float par1, boolean par2, int par3, int par4) {	
		super.renderGameOverlay(par1, par2, par3, par4);
		/*m.width = 128;
		m.height = 128;
		m.posX = 50;
		m.posY = 50;
		m.render(0F);*/
		if(mc.gameSettings.showDebugInfo || hideGui || mc.currentScreen instanceof GuiChat) return;
		ScaledResolution var5423 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		int width  = var5423.getScaledWidth();
		int height = var5423.getScaledHeight();
		
		if(!Xenon.csayMessage.isEmpty()) {
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			drawCenteredString(fr, Xenon.csayMessage.replace("ยง", "\247").replace("ง", "\247"), width / 4, (height / 4) - 15, 0xFFFFFF);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
		}

		fr.drawStringWithShadow(Xenon.CLIENT_STRING, 2, 2, 0xFFFFFF);

		int count = 0;
		for(XenonMod mod: Hacks.display) {
			int x = width - fr.getStringWidth(mod.getName()) - 2;
			int y = (10 * count) + 2;
			fr.drawStringWithShadow(mod.getName(), x, y, mod.getColor());
			count++;
		}
		
		for(XWindow window: XenonGuiClick.windows) {
			if(!(mc.currentScreen instanceof XenonGuiClick)) {
				if(window.isPinned()) {
					window.draw(0, 0);
				}
			}
		}
		
		ScaledResolution var13 = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);

		int var1 = 1;
		int var2 = 1;
		Collection var4 = this.mc.thePlayer.getActivePotionEffects();

		if(!var4.isEmpty()) {
			int var5 = this.mc.renderEngine.getTexture("/gui/inventory.png");
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			int var6 = -28;

			for(Iterator var7 = this.mc.thePlayer.getActivePotionEffects().iterator(); var7.hasNext(); var2 += var6) {
				PotionEffect var8 = (PotionEffect)var7.next();
				Potion var9 = Potion.potionTypes[var8.getPotionID()];
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.mc.renderEngine.bindTexture(var5);

				String var12 = StatCollector.translateToLocal(var9.getName());

				if(var8.getAmplifier() == 1) {
					var12 = var12 + " II";
				} else if (var8.getAmplifier() == 2) {
					var12 = var12 + " III";
				} else if (var8.getAmplifier() == 3) {
					var12 = var12 + " IV";
				}

				int var14 = var13.getScaledWidth() - this.fr.getStringWidth(var12) - 2;

				this.fr.drawStringWithShadow(var12, var1 + var14, var2 + var13.getScaledHeight() - 20, 16777215);
				String var11 = Potion.getDurationString(var8);
				var14 = var13.getScaledWidth() - this.fr.getStringWidth(var11) - 2;
				this.fr.drawStringWithShadow(var11, var1 + var14, var2 + var13.getScaledHeight() - 10, 8355711);
			}
		}
	}
}
