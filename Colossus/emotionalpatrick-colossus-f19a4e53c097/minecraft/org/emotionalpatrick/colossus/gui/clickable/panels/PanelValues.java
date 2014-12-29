package org.emotionalpatrick.colossus.gui.clickable.panels;

import org.emotionalpatrick.colossus.gui.clickable.PanelBase;
import org.emotionalpatrick.colossus.gui.clickable.Slider;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.utilities.values.Values;

public class PanelValues extends PanelBase {

	public PanelValues(String s, int x, int y, int w, int h) {
		super(s, x, y, w, h);
	}
	
	public void init() {
		width = 111;
		int Y = 26;
		add(new Slider(Values.auraRange, 2, Y, 98, 12, 10F, "#.#", this));
		add(new Slider(Values.auraSpeed, 2, Y += 20, 98, 12, 15F, "#.#", this));
		add(new Slider(Values.worldAlpha, 2, Y += 20, 98, 12, 255F, "#.#", this));
		add(new Slider(Values.stepHeight, 2, Y += 20, 98, 12, 10F, "#.#", this));
		add(new Slider(Values.mineSpeed, 2, Y += 20, 98, 12, 10F, "#.#", this));
		add(new Slider(Values.speedHack, 2, Y += 20, 98, 12, 10F, "#.#", this));
		add(new Slider(Values.fastPlace, 2, Y += 20, 98, 12, 5F, "#.#", this));
		add(new Slider(Values.flightSpeed, 2, Y += 20, 98, 12, 1F, "#.#", this));
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
