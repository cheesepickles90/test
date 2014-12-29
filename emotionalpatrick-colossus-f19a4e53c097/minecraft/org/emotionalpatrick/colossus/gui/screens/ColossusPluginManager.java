package org.emotionalpatrick.colossus.gui.screens;

import java.awt.Desktop;
import java.io.IOException;

import org.emotionalpatrick.colossus.files.FileManagerImpl;
import org.emotionalpatrick.colossus.gui.components.PluginSlot;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.ColossusWrapper;
import org.emotionalpatrick.colossus.utilities.ChatColor;
import org.emotionalpatrick.colossus.utilities.HexColor;
import org.lwjgl.Sys;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiMultiplayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.LoadingScreenRenderer;

public class ColossusPluginManager extends GuiScreen {

	private PluginSlot pModuleSlot;
    private String helpTooltip = null;
    
	@Override
	public void initGui() {	
		pModuleSlot = new PluginSlot(this);
		pModuleSlot.registerScrollButtons(controlList, 7, 8);
		
		controlList.clear();
		this.controlList.add(new GuiButton(1, 2, 2, 100, 20, "Open Folder"));
		controlList.add(new GuiButton(3, width / 2 - 100, height - 26, 95, 20, "Back"));
		controlList.add(new GuiButton(4, width / 2 + 6, height - 26, 95, 20, "Refresh"));
		controlList.add(new GuiButton(5, width / 2 - 100, height - 48, "Reload Plugins"));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 0) {
			} if(button.id == 1) {
				boolean isMac = false;
				try {
					Desktop.getDesktop().browse(
							FileManagerImpl.getPluginsDir().toURI());
				} catch (IOException var4) {
					isMac = true;
				}
				if (isMac) {
					Sys.openURL("file://"
							+ FileManagerImpl.getPluginsDir().toURI()
									.toString());
				}
			} if(button.id == 2) {
			} if(button.id == 3) {
				mc.displayGuiScreen(new GuiMainMenu());
			} if(button.id == 4) {
                this.mc.displayGuiScreen(new ColossusPluginManager());
			} if(button.id == 5) {
				Colossus.init(ColossusWrapper.getMinecraft());
			} else {
				pModuleSlot.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		pModuleSlot.drawScreen(I1, I2, I3);

		drawCenteredString(fontRenderer, "Colossus Plugin Manager - "  + "(" + ChatColor.GOLD + Colossus.getManager().getModules().size() + ChatColor.RESET + ")", width / 2, 10,
				0xFFFFFFFF);
        drawCenteredString(fontRenderer, ChatColor.ITALIC + "Easily manage your plugins with a few clicks", width / 2, 20, HexColor.GRAY);
		super.drawScreen(I1, I2, I3);
		helpTooltip = pModuleSlot.getSlotModule().getDescription();
	}
}
