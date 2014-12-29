package org.emotionalpatrick.colossus.gui.clickable.panels;

import org.emotionalpatrick.colossus.gui.clickable.ColossusPanelBase;
import org.emotionalpatrick.colossus.gui.clickable.ColossusSlider;
import org.emotionalpatrick.colossus.gui.components.ValuesHelper;
import org.emotionalpatrick.colossus.main.Colossus;

public class ColossusPanelValues extends ColossusPanelBase {

	public ColossusPanelValues(String s, int x, int y, int w, int h) {
		super(s, x, y, w, h);
	}
	
	public void init() {
		width = 111;
		int Y = 26;
		add(new ColossusSlider(ValuesHelper.auraRange, 2, Y, 98, 12, 10F, "#.#", this));
		add(new ColossusSlider(ValuesHelper.auraSpeed, 2, Y += 20, 98, 12, 15F, "#.#", this));
		add(new ColossusSlider(ValuesHelper.worldAlpha, 2, Y += 20, 98, 12, 255F, "#.#", this));
		add(new ColossusSlider(ValuesHelper.stepHeight, 2, Y += 20, 98, 12, 10F, "#.#", this));
		add(new ColossusSlider(ValuesHelper.mineSpeed, 2, Y += 20, 98, 12, 10F, "#.#", this));
		add(new ColossusSlider(ValuesHelper.speedHack, 2, Y += 20, 98, 12, 10F, "#.#", this));
		add(new ColossusSlider(ValuesHelper.fastPlace, 2, Y += 20, 98, 12, 5F, "#.#", this));
		add(new ColossusSlider(ValuesHelper.flightSpeed, 2, Y += 20, 98, 12, 1F, "#.#", this));
		height = Y + 3;
		// add(new DraggableSlider(Exon.auraReach, 2, 46, 98, 12, 10F, "#.##",
		// this));
		// add(new DraggableSlider(Exon.mineSpeed, 2, 66, 98, 12, 1F, "#.##",
		// this));
		// add(new DraggableSlider(Exon.flightSpeed, 2, 86, 98, 12, 2F, "#.##",
		// this));
		// add(new DraggableSlider(Exon.timer, 2, 106, 98, 12, 10F,"#.#",
		// this));
		// add(new DraggableSlider(Exon.sprintSpeed, 2, 126, 98, 12, 1F,"#.#",
		// this));
		// add(new DraggableSlider(Exon.bright, 2, 146, 98,12 , 3.0F,
		// "#.#",this));
		// add(new DraggableSlider(Exon.nukeSpeed,2,166,98,12, 200f,"#",this));
	}
}
