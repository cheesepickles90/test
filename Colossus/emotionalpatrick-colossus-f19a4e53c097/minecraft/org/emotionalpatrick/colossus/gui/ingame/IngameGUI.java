package org.emotionalpatrick.colossus.gui.ingame;

import org.emotionalpatrick.colossus.hooks.HookManager;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.modules.Module;
import net.minecraft.src.FontRenderer;

public class IngameGUI {
	
	/** Called in GuiIngame */
	public static void renderIGGUI() {
		if (!Colossus.getManager().getModuleByName("Hide").isEnabled()) {
			FontRenderer fr = Wrapper.getFontRenderer();
			HookManager.onRenderIGGUI(fr);
			renderTag();
			renderCoordinates();
			renderModules();
			if (Helper.isDebugMode())
			renderFPS();
		}
	}
	
	public static void renderTag() {
		FontRenderer fr = Wrapper.getFontRenderer();
		fr.drawStringWithShadow(Colossus.getClientTitle(), 2, 2, 0xffffff);
	}

	public static void renderModules() {
		int yPosition = 2;
		for (Module m : Colossus.getManager().getModules()) {
			if (m.isShown() && m.isEnabled()) {
				String name = m.getName();
				int color = m.getColor();
				int xPosition = Wrapper.getScaledResolution().getScaledWidth()
						- (Wrapper.getFontRenderer().getStringWidth(name) + 2);
				Wrapper.getFontRenderer().drawStringWithShadow(name,
						xPosition, yPosition, color);
				yPosition += 10;
			}
		}
	}
	
	public static void renderCoordinates() {
		FontRenderer fr = Wrapper.getFontRenderer();
		String x = "X: " + (int)Wrapper.getPlayer().posX;
	    String y = "Y: " + (int)Wrapper.getPlayer().posY;
	    String z = "Z: " + (int)Wrapper.getPlayer().posZ;
		fr.drawStringWithShadow(x, Wrapper.getScaledResolution().getScaledWidth() - fr.getStringWidth(x) - 2, Wrapper.getScaledResolution().getScaledHeight() - 30, 0xffffff);
		fr.drawStringWithShadow(y, Wrapper.getScaledResolution().getScaledWidth() - fr.getStringWidth(y) - 2, Wrapper.getScaledResolution().getScaledHeight() - 20, 0xffffff);
		fr.drawStringWithShadow(z, Wrapper.getScaledResolution().getScaledWidth() - fr.getStringWidth(z) - 2, Wrapper.getScaledResolution().getScaledHeight() - 10, 0xffffff);
	}
	
	public static void renderFPS() {
		FontRenderer fr = Wrapper.getFontRenderer();
        String args[] = Wrapper.getMinecraft().debug.split(",");
        String fps = args[0];
		fr.drawStringWithShadow("DEBUG (" + fps + ")", 2, Wrapper.getScaledResolution().getScaledHeight() - 10, 0xffffff);
	}
}
