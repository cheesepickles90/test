package org.emotionalpatrick.colossus.modules;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.StatCollector;

import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.gui.GuiUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Beacon extends Module {
	
	private static GuiUtils g;

	public Beacon() {
		super("Beacon", ".beacon", "Beacon ESP and potion indicator", "Emotional Patrick", 0x6495ED, Keyboard.KEY_NONE, "World");
		g = new GuiUtils();
	}
	
	@Override
	public void onRenderIngame(FontRenderer fr) {
		int var1 = 1;
		int var2 = 1;
		Collection c = Wrapper.getPlayer().getActivePotionEffects();

		if (!c.isEmpty()) {
			int var5 = Wrapper.getMinecraft().renderEngine
					.getTexture("/gui/inventory.png");
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			int var6 = -16;

			for (Iterator i = Wrapper.getPlayer()
					.getActivePotionEffects().iterator(); i.hasNext(); var2 += var6) {
				PotionEffect pe = (PotionEffect) i.next();
				Potion var9 = Potion.potionTypes[pe.getPotionID()];
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				Wrapper.getMinecraft().renderEngine.bindTexture(var5);

				if (var9.hasStatusIcon()) {
					int var10 = var9.getStatusIconIndex();
					g.drawTexturedModalRect(20, var2
							+ Wrapper.getScaledResolution()
									.getScaledHeight() - 20,
							0 + var10 % 8 * 18, 198 + var10 / 8 * 18, 18, 18);
				}

				String var12 = StatCollector.translateToLocal(var9.getName());

				if (pe.getAmplifier() == 1) {
					var12 = var12 + " II";
				} else if (pe.getAmplifier() == 2) {
					var12 = var12 + " III";
				} else if (pe.getAmplifier() == 3) {
					var12 = var12 + " IV";
				}

				int var14 = Wrapper.getScaledResolution()
						.getScaledWidth()
						- Wrapper.getFontRenderer().getStringWidth(
								var12) / 2 - 21;

				drawSmallString(var12, 2, var2
						+ Wrapper.getScaledResolution()
								.getScaledHeight() - 14, 0x25ffffff);
				String var11 = Potion.getDurationString(pe);
				var14 = Wrapper.getScaledResolution().getScaledWidth()
						- Wrapper.getFontRenderer().getStringWidth(
								var11) / 2 - 21;
				drawSmallString(var11, 2, var2
						+ Wrapper.getScaledResolution()
								.getScaledHeight() - 8, 10526880);
			}
		}
	}
	
	public static void drawSmallString(String s, int x, int y, int co) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		Wrapper.getFontRenderer().drawStringWithShadow(s, x * 2, y * 2, co);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}
}
