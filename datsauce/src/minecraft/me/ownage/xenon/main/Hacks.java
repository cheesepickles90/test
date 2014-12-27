package me.ownage.xenon.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.hacks.classes.*;
import me.ownage.xenon.util.FormattedString;




public class Hacks
{
	public static ArrayList<XenonMod> display = new ArrayList<XenonMod>();
	
	/** All mods stored in this list **/
	public static List<XenonMod> hackList = Arrays.asList(new XenonMod[] {
			new Brightness(),
			new Flight(),
			new KillAura(),
			new Nuker(),
			new Sprint(),
			new Step(),
			new Xray(),
			new Tracer(),
			new PlayerESP(),
			new Crits(),
			new AutoBlock(),
			new GUI(),
			new ChestESP(),
			new AuraPlayer(),
			new AuraMob(),
			new NoFall(),
			new AutoTool(),
			new Freecam(),
			new SpeedMine(),
			new MainMenu(),
			new AimBot(),
			new TTFChat(),
			new AutoSoup(),
			new Sneak(),
			new AntiVelocity(),
			new Breadcrumb(),
			new Waypoint(),
			new Dolphin(),
			new FastPlace(),
			new LSD(),
			new NameTags(),
			new God(),
			new MagicCarpet(),
			new SafeWalk(),
			new Trajectories(),
			new Search()
	});

	public static XenonMod[] getEnabledHacks()
	{
		ArrayList<XenonMod> enabledMods = new ArrayList<XenonMod>();
		for(XenonMod xmod: hackList)
		{
			if(xmod.isEnabled()) 
			{
				enabledMods.add(xmod);
			}
		}
		
		return enabledMods.toArray(new XenonMod[enabledMods.size()]);
	}
	
	public static XenonMod getModByClassName(String name)
	{
		for(XenonMod xmod: hackList) 
		{
			if(xmod.getClass().getSimpleName().toLowerCase().trim().equals(name.toLowerCase().trim()))
			{
				return xmod;
			}
		}
		
		return null;
	}
	
	public static XenonMod getModByName(String name) 
	{
		for(XenonMod xmod: hackList)
		{
			if(xmod.getName().trim().equalsIgnoreCase(name.trim()) || xmod.toString().trim().equalsIgnoreCase(name.trim())) 
			{
				return xmod;
			}
		}
		
		return null;
	}
	
	public static XenonMod findMod(Class<?extends XenonMod> clazz) 
	{
		for(XenonMod xmod: hackList)
		{
			if(xmod.getClass() == clazz)
			{
				return xmod;
			}
		}
		
		return null;
	}
	
	public static XenonMod findMod(String name)
	{
		XenonMod xmod = getModByName(name);
		if(xmod != null) 
		{
			return xmod;
		}
		xmod = getModByClassName(name);
		
		return xmod;
	}
}
