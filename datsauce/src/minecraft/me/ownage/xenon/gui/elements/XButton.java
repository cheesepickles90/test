package me.ownage.xenon.gui.elements;

import me.ownage.xenon.gui.XenonGuiClick;
import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.XenonUtils;

import org.lwjgl.input.Mouse;

public class XButton {
	private XWindow window;
	private XenonMod mod;
	private int xPos;
	private int yPos;
	
	public boolean isOverButton;
	
	public XButton(XWindow window, XenonMod mod, int xPos, int yPos) {
		this.window = window;
		this.mod = mod;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void draw() {
		XenonUtils.drawGradientBorderedRect(getX() + 0.5 + window.dragX, getY() + 0.5 + window.dragY, getX() + 22 + window.dragX, getY() + 9.5 + window.dragY, 0.5F, mod.isEnabled() ? 0xFF3073D6 : 0xFF666666, mod.isEnabled() ? isOverButton ? 0xFF44AAFF : 0xFF3399FF : isOverButton ? 0xFF888888 : 0xFF777777, mod.isEnabled() ? isOverButton ? 0xFF0060FF : 0xFF0055FF : isOverButton ? 0xFF666666 : 0xFF555555);
		if(Mouse.isButtonDown(0) && isOverButton) {
			XenonUtils.drawGradientBorderedRect(getX() + 0.5 + window.dragX, getY() + 0.5 + window.dragY, getX() + 22 + window.dragX, getY() + 9.5 + window.dragY, 0.5F, mod.isEnabled() ? 0xFF3073D6 : 0xFF666666, mod.isEnabled() ? 0xFF4488FF : 0xFF666666, mod.isEnabled() ? 0xFF0044FF : 0xFF444444);
		}
		XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, mod.getName(), getX() + 24 + window.dragX, getY() - 1 + window.dragY, mod.isEnabled() ? 0xFFFFFF : 0xDDDDDD);
	}
	
	public void mouseClicked(int x, int y, int button) {
		if(x >= getX() + window.dragX && y >= getY() + window.dragY && x <= getX() + 22 + window.dragX && y <= getY() + 9 + window.dragY && button == 0 && window.isOpen() && window.isExtended()) {
			XenonGuiClick.sendPanelToFront(window);
			Xenon.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			mod.toggle();
		}
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}
}
