package org.emotionalpatrick.colossus.gui.clickable.panels;

import java.util.Iterator;

import org.emotionalpatrick.colossus.gui.clickable.ColossusPanelBase;
import org.emotionalpatrick.colossus.gui.clickable.ColossusButton;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.ColossusHelper;
import org.emotionalpatrick.colossus.modules.Module;

public class ColossusPanelWorld extends ColossusPanelBase {

	public ColossusPanelWorld(String s, int x, int y, int w, int h) {
		super(s, x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	public void init() {
		height = 0;
		width = 95;

		int Y = 0;
		Iterator i = Colossus.getManager().getModules().iterator();
		while (i.hasNext()) {
			Module m = (Module) i.next();
			if (!m.category.equalsIgnoreCase("World")) {
				continue;
			} else {
				add(new ColossusButton(m, 2, Y += 18, 41, 11, this));
				ColossusHelper.addConsole("Added " + m.getName()
						+ " into category " + m.getCategory());
				height = Y + 9;
			}
		}
	}
}
