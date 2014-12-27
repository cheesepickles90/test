package me.ownage.xenon.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.hacks.classes.*;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.util.XenonUtils;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

public class XModManager extends GuiScreen {
	private GuiScreen parent;
    private int fadeProgress = 0;
    private int offset = 0;
    private int scrollEnd = 0;
	
	private XenonMod selectedMod;
	
	public XModManager(GuiScreen scr) {
		this.parent = scr;
	}
	
	public void initGui() {
		controlList.add(new GuiButton(0, width / 2 - 100, height - 28, "Back"));
	}
	
	public void actionPerformed(GuiButton button) {
		if(button.id == 0) {
			mc.displayGuiScreen(parent);
		}
	}
	
    public void updateScreen() {
    	
    }
	
    public void updateScroll() {
        while(Mouse.next()) {
            int mouseScroll = Mouse.getEventDWheel();
            if (mouseScroll != 0) {
                if (mouseScroll > 0) {
                    mouseScroll = -2;
                } else if (mouseScroll < 0) {
                    mouseScroll = 2;
                }

                this.offset -= (float)(mouseScroll * 12);
            }
        }
        if(offset < scrollEnd + height)
            offset = scrollEnd + height;
        if(offset > 0)
        	offset = 0;
    }
      
	
	public void drawScreen(int x, int y, float f) {
		this.drawDefaultBackground();
		
		drawCenteredString(fontRenderer, "Modules", width / 2, 10, 0xAAAAAA);
		
        int i = 0;
        for(XenonMod mod : Hacks.hackList) {
        	if(!(mod instanceof TTFChat) && !(mod instanceof MainMenu)) {
	            int i2 = i + offset;
	            drawHorizontalLine(0, 85, i2 + 2, 0xFF000000);
	            if(selectedMod == mod) {
	            	XenonUtils.drawBorderedRect(2, i2 + 3, 7 + fontRenderer.getStringWidth(mod.getName()), i2 + 16, 0xFF777777, 0xff000000);
	            }
	            boolean hover = x >= 2 && y >= i2 + 3 && x <= 85 && y <= i2 + 15;
	            GL11.glTranslatef(0.0F, 0.5F, 0.0F);
	            fontRenderer.drawStringWithShadow(mod.getName(), 5, i2 + 5, hover ? 0xF3F781 : 0xffffff);
	            GL11.glTranslatef(0.0F, -0.5F, 0.0F);
	            i += 14;
        	}
        }
        this.scrollEnd = i;
        this.scrollEnd = -scrollEnd - 3;
        if(!Mouse.isButtonDown(0)) {
        	updateScroll();
        }
        
        drawVerticalLine(85, 0, height, 0xFF000000);
		
        if(selectedMod != null) {
        	drawCenteredString(fontRenderer, "Name: " + selectedMod.getName(), width / 2, height / 2 - 32, 0xFFFFFF);
        	drawCenteredString(fontRenderer, "Key: " + Keyboard.getKeyName(selectedMod.getKey()), width / 2, height / 2 - 20, 0xFFFFFF);
        	drawCenteredString(fontRenderer, "\247fColor:\247r " + (selectedMod.getColor() == 0 ? "N/A" : Integer.toHexString(selectedMod.getColor()).toUpperCase()), width / 2, height / 2 - 8, (selectedMod.getColor() == 0 ? 0xFFFFFF : selectedMod.getColor()));
        	drawCenteredString(fontRenderer, "Enabled: " + (selectedMod.isEnabled() ? "Yes" : "No"), width / 2, height / 2 + 4, 0xFFFFFF);
        }

		super.drawScreen(x, y, f);
	}
	
	@Override
	public void mouseClicked(int x, int y, int b) {
        int i = 4;
        for(XenonMod mod : Hacks.hackList) {
        	if(!(mod instanceof TTFChat) && !(mod instanceof MainMenu)) {
	            int i2 = i + offset;
	            if(x >= 2 && y >= i2 + 3 && x <= 85 && y <= i2 + 15) {
	            	mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
		            this.selectedMod = mod;	
	            } 
	            i += 14;
        	}
        }
        super.mouseClicked(x, y, b);
	}
}
