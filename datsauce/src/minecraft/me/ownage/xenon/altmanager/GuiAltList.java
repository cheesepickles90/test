package me.ownage.xenon.altmanager;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.ownage.xenon.gui.XenonOptions;



import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiYesNo;
import net.minecraft.src.Session;
import net.minecraft.src.StringTranslate;

public class GuiAltList extends GuiScreen {
	public String dispErrorString = "";
	public boolean deleteMenuOpen = false;
	
	public GuiAltList() {
		Manager.loadAlts();
	}
	
	public FontRenderer getLocalFontRenderer() {
		return this.fontRenderer;
	}
	
	public void onGuiClosed() {
		Manager.saveAlts();
		super.onGuiClosed();
	}
	
	private SlotAlt tSlot;
	
	public void initGui() {
		controlList.clear();
		controlList.add(new GuiButton(1, width / 2 - 100, height - 47, 66, 20, "Add"));
		controlList.add(new GuiButton(2, width / 2 - 33, height - 47, 65, 20, "Login"));
		controlList.add(new GuiButton(3, width / 2 + 32, height - 47, 69, 20, "Remove"));
		controlList.add(new GuiButton(4, width / 2 - 100, height - 26, 99,20, "Back"));
		controlList.add(new GuiButton(5, width / 2 , height - 26, 100, 20, "Direct Login"));
		
		tSlot = new SlotAlt(this.mc, this);
		tSlot.registerScrollButtons(controlList, 7, 8);
	}
	
	@Override
	public void confirmClicked(boolean flag, int i1) {
		super.confirmClicked(flag, i1);
		if(deleteMenuOpen) {
			deleteMenuOpen = false;
			
			if(flag) {
				Manager.altList.remove(i1);
				Manager.saveAlts();
			}
			
			mc.displayGuiScreen(this);
		}
	}
	
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if(button.id == 1) {
			GuiAltAdd gaa = new GuiAltAdd(this);
			mc.displayGuiScreen(gaa);
		}
		if(button.id == 2) {
			try {
				Alt a1 = Manager.altList.get(tSlot.getSelected());
				if(a1.isPremium()) {
					try {
						String post = (new StringBuilder("user=")).append(URLEncoder.encode(a1.getUsername(), "UTF-8")).append("&password=").append(URLEncoder.encode(a1.getPassword(), "UTF-8")).append("&version=").append(13).toString();
						String returnInf = Manager.excutePost("https://login.minecraft.net/", post);
						String[] parsedInfo = returnInf.split(":");
	                    mc.session = new Session(parsedInfo[2].trim(), parsedInfo[3].trim());
	                    Manager.altScreen.dispErrorString = "";
					} catch(Exception error) {
						dispErrorString = "".concat("\247cBad Login \2477(").concat(a1.getUsername()).concat(")");
					}
				} else {
					this.mc.session = new Session(a1.getUsername(), "-");
					Manager.altScreen.dispErrorString = "";
				}
			}catch(Exception e) {}
		}
		if(button.id == 3)
		{
			try
			{
				StringTranslate stringtranslate = StringTranslate.getInstance();
				String s1 = "Are you sure you want to delete the alt: " + "\"" + Manager.altList.get(tSlot.getSelected()).getUsername() + "\"" + "?";
				String s3 = "Delete";
				String s4 = "Cancel";
				GuiYesNo guiyesno = new GuiYesNo(this, s1, "", s3, s4, tSlot.getSelected());
				deleteMenuOpen = true;
				mc.displayGuiScreen(guiyesno);
			}catch(Exception e) {}
		}
		if(button.id == 4)
		{
			mc.displayGuiScreen(new XenonOptions(this));
		}
		if(button.id == 5)
		{
			GuiDirectLogin gdl = new GuiDirectLogin(this);
			mc.displayGuiScreen(gdl);
		}
	}
	
	public void updateScreen()
	{
		super.updateScreen();
//		if(!(mc.currentScreen instanceof GuiYesNo) && deleteMenuOpen)
//		{
//			deleteMenuOpen = false;
//		}
	}
	
	public void drawScreen(int i, int j, float f)
	{
		tSlot.drawScreen(i, j, f);
		drawCenteredString(fontRenderer, "Alts: \2477" + Manager.altList.size(), width / 2, 13, 0xFFFFFF);
		fontRenderer.drawStringWithShadow("Username: \2477" + mc.session.username, 3, 3, 0xFFFFFF);
		fontRenderer.drawStringWithShadow(dispErrorString, 3, 13, 0xFFFFFF);
		super.drawScreen(i, j, f);
	}
}
