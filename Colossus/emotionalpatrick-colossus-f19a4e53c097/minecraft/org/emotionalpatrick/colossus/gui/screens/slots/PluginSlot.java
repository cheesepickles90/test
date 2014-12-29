package org.emotionalpatrick.colossus.gui.screens.slots;

import java.awt.Color;
import java.util.Random;

import org.emotionalpatrick.colossus.gui.screens.ModuleManager;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.modules.Module;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class PluginSlot extends GuiSlot {

	private ModuleManager pModules;
	
	private int selected;
	private int updateCounter, modKey, herp;
	
	private boolean keyTyped;
    final ModuleManager parentGui;
        
	public float r, g, b;
	Random rand = new Random();

	public PluginSlot(ModuleManager mList) {
		super(Wrapper.getMinecraft(), mList.width, mList.height, 32,
				mList.height - 59, 36);
		this.pModules = mList;
		this.selected = 0;
        this.parentGui = mList;
		for (Module pm : Colossus.getManager().getModules()) {
			randomColor();
		}
	}

	public void randomColor() {
		this.r = rand.nextFloat();
		this.g = rand.nextFloat();
		this.b = rand.nextFloat();
	}
	
	@Override
	protected int getSize() {
		return Colossus.getManager().getModules().size();
	}

	@Override
	protected int getContentHeight() {
		return getSize() * 36;
	}

	@Override
	protected void drawBackground() {
		pModules.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator) {
		
		updateCounter++;
		herp = i;
		String var1 = Colossus.getManager().getModules().get(i).getName() + " by " + Colossus.getManager().getModules().get(i).getAuthor();
		String var2 = Colossus.getManager().getModules().get(i).getDescription();
		
		int key = Colossus.getManager().getModules().get(i).getKey();
		String keyStr = new StringBuilder().append((key > 0)? Keyboard.getKeyName(key) : "NONE").toString();
		String var3 = "Command: " + Colossus.getManager().getModules().get(i).getCommand() + " / Key: " + keyStr;
		
		int color = Colossus.getManager().getModules().get(i).getColor();
		
		for (Module pm : Colossus.getManager().getModules()) {
			Color c = new Color(r, g, b);
			if (color == 0xffffff)
				color = c.getRGB();
		}
		pModules.drawString(Wrapper.getFontRenderer(), var1, j, k + 1,
				color);
		pModules.drawString(Wrapper.getFontRenderer(), var2, j, k + 12,
				0x80808080);
		pModules.drawString(Wrapper.getFontRenderer(), var3, j, k + 22,
				0x30303030);
	}
	
	@Override
	protected void elementClicked(int i, boolean flag) {
		selected = i;
		keyTyped = false;
	}
	
	protected String getSlotName() {
		return Colossus.getManager().getModules().get(getSelected()).getName();
	}

	public Module getSlotModule() {
		return (Module) Colossus.getManager().getModules().get(getSelected());
	}

	@Override
	protected boolean isSelected(int i) {
		return selected == i;
	}

	protected int getSelected() {
		return selected;
	}
}