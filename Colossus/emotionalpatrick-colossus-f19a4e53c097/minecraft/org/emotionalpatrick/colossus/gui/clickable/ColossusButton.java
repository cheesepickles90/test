package org.emotionalpatrick.colossus.gui.clickable;

import org.emotionalpatrick.colossus.gui.components.GuiHelper;
import org.emotionalpatrick.colossus.main.ColossusHelper;
import org.emotionalpatrick.colossus.main.ColossusWrapper;
import org.emotionalpatrick.colossus.modules.Module;
import org.emotionalpatrick.colossus.utilities.CustomFont;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.src.Gui;

public class ColossusButton {

	public int x, y, width, height;

	public String name;

	public ColossusPanelBase d;

	public Module m;

	private CustomFont cf;

	private ColossusPanelBase rel;
	
	public ColossusButton(Module h, int x, int y, int w, int he,
			ColossusPanelBase d) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = he;
		m = h;
		this.d = d;
	}
	/**
	 * Draw the button
	 * @param mx - Mouse X
	 * @param my - Mouse y
	 * */
	 public void drawButton(int mx, int my){ 
		if(m.isEnabled()) {
			GuiHelper.drawReliantButtonOn(d.x + 5, d.y + y , d.x + width - 28, d.y + (y + height)- 6);
		} else {
			GuiHelper.drawReliantButtonOff(d.x + 5, d.y + y , d.x + width - 28, d.y + (y + height)- 6);
		}
		
		if (mouseAround(mx, my) && m.isEnabled()) {
			GuiHelper.drawReliantButtonHoverEffectOn(d.x + 5, d.y + y , d.x + width - 28, d.y + (y + height)- 6);
		} else if (mouseAround(mx, my) && !m.isEnabled()) {
			GuiHelper.drawReliantButtonHoverEffectOff(d.x + 5, d.y + y , d.x + width - 28, d.y + (y + height)- 6);
		}

		if (cf == null) {
			cf = new CustomFont(ColossusWrapper.getMinecraft(), "Tahoma", 17);
		}

		ColossusWrapper.getFontRenderer().drawTTFStringWithShadow(cf,m.name, d.x + width - 2, d.y + y  , m.enabled? 0xffffff : 0x777777);

	 }
	 
	 /**
	  * Returns the width of the button (for resizing the draggable, to fit the button into it)
	  * */
	 public int getWidth(){
		 return width + ColossusWrapper.getFontRenderer().getStringWidth(m.name);
	 }
	 /**
	  * Returns the height;
	  * */
	 public int getHeight(){
		 return ( y + height);
	 }
		
	 public void mouseClicked(int mx, int my){
		 if(mouseAround(mx, my)) {
			 m.onToggle();
			 ColossusWrapper.getMinecraft().sndManager.playSoundFX("random.click", 1f, 1f);
		 }
	 }

	 public boolean mouseAround(int mx, int my){
		 return mx > d.x + x &&  mx < d.x + (x) + width - 8 && my > d.y + y - 4 && my < d.y + y + height + 4;
	 }
}