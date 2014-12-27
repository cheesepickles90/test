package me.ownage.xenon.gui;




import me.ownage.xenon.altmanager.Manager;
import me.ownage.xenon.hooks.XMainMenu;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiControls;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;

public class XenonOptions extends GuiScreen
{
	public GuiScreen parentScreen;

	public XenonOptions(GuiScreen guiscreen)
	{
		this.parentScreen = guiscreen;
	}

	public void initGui()
	{
		controlList.add(new GuiButton(1, width / 2 - 100, height / 4, "Alts"));
		controlList.add(new GuiButton(2, width / 2 - 100, height / 4 + 24, "Proxy"));
		controlList.add(new GuiButton(3, width / 2 - 100, height / 4 + 48, "Info"));
		//controlList.add(new GuiButton(4, width / 2 - 100, height / 4 + 72, "Mod Manager"));
		controlList.add(new GuiButton(5, width / 2 - 100, height / 4 + 104, "Back"));
	}

	public void actionPerformed(GuiButton guibutton)
	{
		if(guibutton.id == 1)
		{
			mc.displayGuiScreen(Manager.altScreen);
		}
		if(guibutton.id == 2)
		{
			mc.displayGuiScreen(new XenonProxySelect(this));
		}
		if(guibutton.id == 3)
		{
			mc.displayGuiScreen(new XenonInfo(this));
		}
		if(guibutton.id == 4) {
			mc.displayGuiScreen(new XModManager(this));
		}
		if(guibutton.id == 5)
		{
			if(mc.theWorld == null)
			{
				mc.displayGuiScreen(new XMainMenu());
			}else
			{
				mc.displayGuiScreen(new GuiIngameMenu());
			}
		}
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "Xenon Options Menu", width / 2, 20, 0xFFFFFF);
		super.drawScreen(par1, par2, par3);
	}
}
