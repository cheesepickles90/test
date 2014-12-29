package org.emotionalpatrick.colossus.gui.screens;

import java.util.ArrayList;
import java.util.Iterator;

import org.emotionalpatrick.colossus.gui.clickable.ColossusButton;
import org.emotionalpatrick.colossus.gui.clickable.ColossusPanelBase;

import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;

public class ColossusGuiHub extends GuiScreen {

	public static ArrayList menus;

	public ArrayList<ColossusPanelBase> draggables = ColossusPanelBase.listOfDraggables;

	public ColossusGuiHub() {
	}
	
	@Override
	public void updateScreen() {
		Iterator i = draggables.iterator();
		while (i.hasNext()) {
			ColossusPanelBase d = (ColossusPanelBase) i.next();
			d.updateScreen();
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		super.drawScreen(i, j, f);
		for (int x = draggables.size() - 1; x >= 0; x--) {
			ColossusPanelBase d = draggables.get(x);
			try {
				d.draw(i, j);
			} catch (Exception e) { }
		}
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		for (int x = 0; x < draggables.size(); x++) {
			ColossusPanelBase d = draggables.get(x);
			try {
				d.mouseClick(i, j);
			} catch (Exception e) {
			}
			if (d.hasBeenActivated(i, j)) {
				draggables.remove(d);
				draggables.add(0, d);
				return;
			}
		}
	}

	@Override
	public void mouseMovedOrUp(int i, int j, int k) {
		super.mouseMovedOrUp(i, j, k);
	}

	@Override
	public void onGuiClosed() {
		ColossusPanelBase.listOfDraggables = draggables;
		ColossusPanelBase.savePanels();
	}
}
