package org.emotionalpatrick.colossus.gui.screens;

import java.util.ArrayList;
import java.util.Iterator;

import org.emotionalpatrick.colossus.gui.clickable.Button;
import org.emotionalpatrick.colossus.gui.clickable.PanelBase;

import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;

public class GuiHub extends GuiScreen {

	public static ArrayList menus;

	public ArrayList<PanelBase> panels = PanelBase.listOfDraggables;

	public GuiHub() {
	}

	@Override
	public void updateScreen() {
		Iterator i = panels.iterator();
		while (i.hasNext()) {
			PanelBase d = (PanelBase) i.next();
			d.updateScreen();
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		super.drawScreen(i, j, f);
		for (int x = panels.size() - 1; x >= 0; x--) {
			PanelBase d = panels.get(x);
			try {
				d.draw(i, j);
			} catch (Exception e) { }
		}
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		for (int x = 0; x < panels.size(); x++) {
			PanelBase d = panels.get(x);
			try {
				d.mouseClick(i, j);
			} catch (Exception e) {
			}
			if (d.hasBeenActivated(i, j)) {
				panels.remove(d);
				panels.add(0, d);
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
		PanelBase.listOfDraggables = panels;
		PanelBase.savePanels();
	}
}
