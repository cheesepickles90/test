package me.ownage.xenon.gui.windows;

import me.ownage.xenon.gui.elements.XWindow;
import me.ownage.xenon.hacks.classes.Flight;
import me.ownage.xenon.hacks.classes.KillAura;
import me.ownage.xenon.hacks.classes.SpeedMine;
import me.ownage.xenon.hacks.classes.Step;
import me.ownage.xenon.hacks.classes.Xray;

public class WindowValues extends XWindow
{
	public WindowValues()
	{
		super("Values", 186, 98 + 11);
	}
	
	public XWindow init()
	{
		addSlider(Flight.flySpeed, 0.1F, 5F, false).setValue(1.0F);
		addSlider(SpeedMine.mineSpeed, 0.1F, 1F, false).setValue(0.3F);
		addSlider(KillAura.auraSpeed, 1.0F, 20.0F, true).setValue(8.0F);
		addSlider(KillAura.auraRange, 3.0F, 10.0F, false).setValue(4.0F);
		addSlider(Xray.opacity, 0.0F, 256F, true).setValue(100F);
		return this;
	}
}
