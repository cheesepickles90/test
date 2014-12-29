package org.emotionalpatrick.colossus.modules.manager;

import java.util.ArrayList;
import java.util.List;

import org.emotionalpatrick.colossus.modules.AntiVelocity;
import org.emotionalpatrick.colossus.modules.AutoSoup;
import org.emotionalpatrick.colossus.modules.AutoSwim;
import org.emotionalpatrick.colossus.modules.Beacon;
import org.emotionalpatrick.colossus.modules.Bind;
import org.emotionalpatrick.colossus.modules.Breadcrumb;
import org.emotionalpatrick.colossus.modules.Bright;
import org.emotionalpatrick.colossus.modules.Chat;
import org.emotionalpatrick.colossus.modules.ConsoleSpam;
import org.emotionalpatrick.colossus.modules.FastPlace;
import org.emotionalpatrick.colossus.modules.Flight;
import org.emotionalpatrick.colossus.modules.Gui;
import org.emotionalpatrick.colossus.modules.Help;
import org.emotionalpatrick.colossus.modules.Hide;
import org.emotionalpatrick.colossus.modules.InsultGenerator;
import org.emotionalpatrick.colossus.modules.Jesus;
import org.emotionalpatrick.colossus.modules.KillAura;
import org.emotionalpatrick.colossus.modules.Module;
import org.emotionalpatrick.colossus.modules.NoFall;
import org.emotionalpatrick.colossus.modules.Nuker;
import org.emotionalpatrick.colossus.modules.OpAura;
import org.emotionalpatrick.colossus.modules.OpKill;
import org.emotionalpatrick.colossus.modules.Protect;
import org.emotionalpatrick.colossus.modules.Retard;
import org.emotionalpatrick.colossus.modules.Say;
import org.emotionalpatrick.colossus.modules.Search;
import org.emotionalpatrick.colossus.modules.Sneak;
import org.emotionalpatrick.colossus.modules.Speed;
import org.emotionalpatrick.colossus.modules.Speedmine;
import org.emotionalpatrick.colossus.modules.Step;
import org.emotionalpatrick.colossus.modules.Toggle;
import org.emotionalpatrick.colossus.modules.Tracers;
import org.emotionalpatrick.colossus.modules.UserDump;

public class ModuleManagerImpl implements ModuleManager {

	private static final List<Module> modules = new ArrayList<Module>();
	
	public void initializeModules() {
		modules.add(new Hide());
		modules.add(new Chat());
		modules.add(new ConsoleSpam());
		modules.add(new Search());
		modules.add(new Bright());
		modules.add(new Beacon());
		modules.add(new Retard());
		modules.add(new FastPlace());
		modules.add(new Flight());
		modules.add(new Jesus());
		modules.add(new OpAura());
		modules.add(new KillAura());
		modules.add(new AntiVelocity());
		modules.add(new AutoSoup());
		modules.add(new NoFall());
		modules.add(new Nuker());
		modules.add(new Sneak());
		modules.add(new Speed());
		modules.add(new Speedmine());
		modules.add(new Step());
		modules.add(new Tracers());
		modules.add(new Protect());
		modules.add(new InsultGenerator());
		modules.add(new UserDump());
		modules.add(new OpKill());
		modules.add(new Say());
		modules.add(new Breadcrumb());
		modules.add(new Toggle());
		modules.add(new Bind());
		modules.add(new Help());
	}

	public Module getModuleByName(String par1Str) {
		for (Module module : getModules())
			if (module.getName().toLowerCase().replace("_", "").replace(" ", "").replace("-", "").equalsIgnoreCase(par1Str.toLowerCase()))
				return module;
		return null;
	}

	public List<Module> getModules() {
		return modules;
	}
}
