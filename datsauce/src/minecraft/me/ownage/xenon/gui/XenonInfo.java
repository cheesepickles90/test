package me.ownage.xenon.gui;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMultiplayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSlider;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.GuiTextField;

public class XenonInfo extends GuiScreen
{
	private GuiScreen parentScreen;

	public XenonInfo(GuiScreen guiscreen)
	{
		this.parentScreen = guiscreen;
	}

	public void initGui()
	{
		controlList.add(new GuiButton(1, width / 2 - 100, height - 24, "Back"));
	}

	public void actionPerformed(GuiButton g)
	{
		if(g.id == 1)
		{
			mc.displayGuiScreen(parentScreen);
		}
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "Xenon Info and Credits", width / 2, 20, 0xFFFFFF);

		drawRect(2, 34, width - 2, height - 36, 0x8F000000);

		drawCenteredString(fontRenderer, "§2Info", width / 2, height / 4 - 15, 0xFFFFFF);
		drawCenteredString(fontRenderer, "§2*§f Keybinds ARE changeable with the .bind add/del commands.", width / 2, height / 4, 0xFFFFFF);
		drawCenteredString(fontRenderer, "  §fThey do save.", width / 2, height / 4 + 10, 0xFFFFFF);
		
		drawCenteredString(fontRenderer, "§2*§f Contact OwnageHF on Skype for any", width / 2, height / 4 + 50, 0xFFFFFF);
		drawCenteredString(fontRenderer, " questions comments or concerns.", width / 2, height / 4 + 60, 0xFFFFFF);

		drawCenteredString(fontRenderer, "§2Credits", width / 2, height / 4 + 80, 0xFFFFFF);
		drawCenteredString(fontRenderer, "§6Oliver berg §7-§f Being cool.", width / 2, height / 4 + 96, 0xFFFFFF);
		drawCenteredString(fontRenderer, "§6RogueCoder §7-§f Sexy co-dev", width / 2, height / 4 + 105, 0xFFFFFF);
		drawCenteredString(fontRenderer, "§6Ramisme §7-§f Rounded rectangle help", width / 2, height / 4 + 115, 0xFFFFFF);
		drawCenteredString(fontRenderer, "§6Ninjastylaz §7-§f CustomFont API", width / 2, height / 4 + 125, 0xFFFFFF);

		super.drawScreen(par1, par2, par3);
	}
}
