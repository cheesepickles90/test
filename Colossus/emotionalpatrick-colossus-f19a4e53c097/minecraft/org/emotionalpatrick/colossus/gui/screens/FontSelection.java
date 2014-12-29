package org.emotionalpatrick.colossus.gui.screens;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.StringTranslate;
import org.emotionalpatrick.colossus.betterfonts.FontHandler;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.utilities.color.ChatColor;
import org.lwjgl.input.Keyboard;

public class FontSelection extends GuiScreen {

	private GuiScreen parentScreen;
	
	private GuiTextField fontName;
	private GuiTextField fontSize;
	
	public FontSelection(GuiScreen guiscreen) {
		parentScreen = guiscreen;
	}

	@Override
	public void updateScreen() {
		fontName.updateCursorCounter();
		fontSize.updateCursorCounter();
	}

	@Override
	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, 98, 20, "Select Font"));
		controlList.add(new GuiButton(2, width / 2 + 2, height / 4 + 96 + 12, 98, 20, "Global TTF"));
		controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, "Back"));
		
		fontName = new GuiTextField(fontRenderer, width / 2 - 100, (height / 4 - 10) + 20, 200, 20);
		fontSize = new GuiTextField(fontRenderer, width / 2 - 100, (height / 4 - 10) + 50 + 12, 200, 20);
		
		fontName.setMaxStringLength(228);
		fontSize.setMaxStringLength(228);
		
		fontName.setText(FontHandler.getInstance().getFontName());
		fontSize.setText("" + FontHandler.getInstance().getFontSize());
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if (!guibutton.enabled) {
			return;
		}

		if (guibutton.id == 2) {
			try {
				FontHandler.getInstance().globalTTF = !FontHandler.getInstance().globalTTF;
				Helper.addConsole(FontHandler.getInstance().globalTTF ? "Global TTF toggled on." : "Global TTF toggled off.");

				mc.initializeFontRenderer();
				mc.displayGuiScreen(this);
				FontHandler.getInstance().saveConfig();
			} catch (Exception err) {
				err.printStackTrace();
			}
		} else
			if (guibutton.id == 1) {
				mc.displayGuiScreen(parentScreen);
				FontHandler.getInstance().saveConfig();
			} else if (guibutton.id == 0) {
				try {
					int size = Integer.parseInt(fontSize.getText());

					FontHandler.getInstance().setFontName(fontName.getText());
					FontHandler.getInstance().setFontSize(size);

					mc.initializeFontRenderer();
					mc.displayGuiScreen(this);
					FontHandler.getInstance().saveConfig();
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
	}

	@Override
	protected void keyTyped(char c, int i) {
		if (c == '\r') {
			actionPerformed((GuiButton) controlList.get(0));
		}
		
		fontName.textboxKeyTyped(c, i);
		fontSize.textboxKeyTyped(c, i);
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		
		fontName.mouseClicked(i, j, k);
		fontSize.mouseClicked(i, j, k);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "Font Selection",
				width / 2, (height / 4 - 60) + 20, 0xffffff);
		fontRenderer.drawString("Font Name", width / 2 - 100,
				(height / 4 - 10) + 10, 10526880);
		fontRenderer.drawString("Font Size", width / 2 - 100,
				(height / 4 - 10) + 52, 10526880);
		fontRenderer.drawString("Current Font: " + FontHandler.getInstance().getFontName() + " - Current Size: " + FontHandler.getInstance().getFontSize(), width / 2 - 100,
				(height / 4 - 10) + 92 + 12, 10526880);
		
		String globalTTF = FontHandler.getInstance().isGlobalTTF() ? ChatColor.DARK_GREEN + "Enabled" : ChatColor.RED + "Disabled";
		fontRenderer.drawString("Global TTF: " + globalTTF, width / 2 - 100,
				(height / 4 - 10) + 92, 10526880);
		
		fontSize.drawTextBox();
		fontName.drawTextBox();
		
		super.drawScreen(i, j, f);
	}
}
