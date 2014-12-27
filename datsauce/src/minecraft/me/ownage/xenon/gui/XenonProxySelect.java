package me.ownage.xenon.gui;

import org.lwjgl.input.Keyboard;
import net.minecraft.src.*;

public class XenonProxySelect extends GuiScreen
{
	private GuiScreen parentScreen;
	private GuiTextField serverTextField;

	public XenonProxySelect(GuiScreen parentscreen)
	{
		parentScreen = parentscreen;
	}

	public void updateScreen()
	{
		serverTextField.updateCursorCounter();
	}

	public void drawScreen(int i, int j, float f)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "Proxy Select", width / 2, height / 4 - 60 + 20, 16777215);
		drawCenteredString(fontRenderer, "Proxy hostname and port", width / 2 , height / 4 - 60 + 60 + 15, 10526880);
		serverTextField.drawTextBox();
		super.drawScreen(i, j, f);
	}
	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 92 + 12, "Set Proxy"));
		controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 116 + 12, "Back"));
		serverTextField = new GuiTextField(fontRenderer, width / 2 - 100, height / 4 - 10 + 26 + 15, 200, 20);
		serverTextField.setFocused(true);
		serverTextField.setMaxStringLength(128);
	}

	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton guibutton)
	{
		if(guibutton.id == 0)
		{
			mc.displayGuiScreen(parentScreen);
			if(serverTextField.getText().trim().length() == 0)
			{
				System.setProperty("socksProxyHost", "");
				System.setProperty("socksProxyPort", "");
				return;
			}

			String[] aParts = serverTextField.getText().trim().split(":");
			try
			{
			if(aParts.length < 2) return;
				System.setProperty("socksProxyHost", aParts[0]);
				System.setProperty("socksProxyPort", aParts[1]);
			}catch(Exception e)
			{
				System.out.println("Yo proxy dun broke.");
			}
		}
		if(guibutton.id == 1)
		{
			mc.displayGuiScreen(parentScreen);
		}
	}

	protected void keyTyped(char c, int i)
	{
		serverTextField.textboxKeyTyped(c, i);
		if(c == '\r') actionPerformed((GuiButton)controlList.get(0));
	}

	protected void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		serverTextField.mouseClicked(i, j, k);
	}
}