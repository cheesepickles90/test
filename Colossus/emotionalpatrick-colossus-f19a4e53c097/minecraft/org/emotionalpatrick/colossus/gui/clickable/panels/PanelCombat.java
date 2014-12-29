package org.emotionalpatrick.colossus.gui.clickable.panels;

import java.util.Iterator;

import org.emotionalpatrick.colossus.gui.clickable.Button;
import org.emotionalpatrick.colossus.gui.clickable.PanelBase;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.modules.Module;

public class PanelCombat extends PanelBase {

	public PanelCombat(String s, int x, int y, int w, int h) {
		super(s, x, y, w, h);
	}

	public void init() {
		height = 0;
		width = 106;
		
		int Y = 0;
		Iterator i = Colossus.getManager().getModules().iterator();
		while (i.hasNext()) {
			Module m = (Module) i.next();
			if (!m.category.equalsIgnoreCase("Kill Aura")) {
				continue;
			} else {
				add(new Button(m, 2, Y += 18, 41, 11, this));
				Helper.addConsole("Added " + m.getName()
						+ " into category " + m.getCategory());
				height = Y + 9;
			}
		}
	}
}
