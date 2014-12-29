package org.emotionalpatrick.colossus.gui.ingame;

import org.emotionalpatrick.colossus.gui.clickable.ColossusPanelBase;
import org.emotionalpatrick.colossus.gui.clickable.panels.ColossusPanelModes;
import org.emotionalpatrick.colossus.hooks.ColossusHook;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.ColossusWrapper;
import org.emotionalpatrick.colossus.modules.Module;
import org.emotionalpatrick.colossus.modules.manager.ModuleManager;

import net.minecraft.src.FontRenderer;

public class ColossusIGGUI {
	
	/** Called in GuiIngame */
	public static void renderIGGUI() {
		if (!Colossus.getManager().getModuleByName("Hide").isEnabled()) {
			FontRenderer fr = ColossusWrapper.getFontRenderer();
			ColossusHook.onRenderIGGUI(fr);
			renderColossusTag();
			renderCoordinates();
			renderModules();
		}
	}
	
	public static void renderColossusTag() {
		FontRenderer fr = ColossusWrapper.getFontRenderer();
		fr.drawStringWithShadow(Colossus.getClientTitle(), 2, 2, 0xffffff);
	}

	public static void renderModules() {
		int yPosition = 2;
		for (Module m : ModuleManager.getModules()) {
			if (m.shown && m.isEnabled()) {
				String name = m.getName();
				int color = m.getColor();
				int xPosition = ColossusWrapper.getScaledResolution().getScaledWidth()
						- (ColossusWrapper.getFontRenderer().getStringWidth(name) + 2);
				ColossusWrapper.getFontRenderer().drawStringWithShadow(name,
						xPosition, yPosition, color);
				yPosition += 10;
			}
		}
	}
	
	public static void renderCoordinates() {
		FontRenderer fr = ColossusWrapper.getFontRenderer();
		String x = "X: " + (int)ColossusWrapper.getPlayer().posX;
	    String y = "Y: " + (int)ColossusWrapper.getPlayer().posY;
	    String z = "Z: " + (int)ColossusWrapper.getPlayer().posZ;
		fr.drawStringWithShadow(x, ColossusWrapper.getScaledResolution().getScaledWidth() - fr.getStringWidth(x) - 2, ColossusWrapper.getScaledResolution().getScaledHeight() - 30, 0xffffff);
		fr.drawStringWithShadow(y, ColossusWrapper.getScaledResolution().getScaledWidth() - fr.getStringWidth(y) - 2, ColossusWrapper.getScaledResolution().getScaledHeight() - 20, 0xffffff);
		fr.drawStringWithShadow(z, ColossusWrapper.getScaledResolution().getScaledWidth() - fr.getStringWidth(z) - 2, ColossusWrapper.getScaledResolution().getScaledHeight() - 10, 0xffffff);
	}
}
