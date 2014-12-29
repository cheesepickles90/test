package org.emotionalpatrick.colossus.modules.manager;

import java.util.ArrayList;

import org.emotionalpatrick.colossus.modules.AntiAFK;
import org.emotionalpatrick.colossus.modules.AntiVelocity;
import org.emotionalpatrick.colossus.modules.AutoBlock;
import org.emotionalpatrick.colossus.modules.AutoFish;
import org.emotionalpatrick.colossus.modules.AutoRespawn;
import org.emotionalpatrick.colossus.modules.AutoSoup;
import org.emotionalpatrick.colossus.modules.AutoSwim;
import org.emotionalpatrick.colossus.modules.AutoTool;
import org.emotionalpatrick.colossus.modules.AutoAccept;
import org.emotionalpatrick.colossus.modules.Beacon;
import org.emotionalpatrick.colossus.modules.Bind;
import org.emotionalpatrick.colossus.modules.BowAimbot;
import org.emotionalpatrick.colossus.modules.Breadcrumb;
import org.emotionalpatrick.colossus.modules.Bright;
import org.emotionalpatrick.colossus.modules.Chat;
import org.emotionalpatrick.colossus.modules.ConsoleSpam;
import org.emotionalpatrick.colossus.modules.CopyCat;
import org.emotionalpatrick.colossus.modules.FastPlace;
import org.emotionalpatrick.colossus.modules.Filter;
import org.emotionalpatrick.colossus.modules.Flight;
import org.emotionalpatrick.colossus.modules.Freecam;
import org.emotionalpatrick.colossus.modules.Glide;
import org.emotionalpatrick.colossus.modules.God;
import org.emotionalpatrick.colossus.modules.Gui;
import org.emotionalpatrick.colossus.modules.Help;
import org.emotionalpatrick.colossus.modules.Hide;
import org.emotionalpatrick.colossus.modules.InsultGenerator;
import org.emotionalpatrick.colossus.modules.Allah;
import org.emotionalpatrick.colossus.modules.KillAura;
import org.emotionalpatrick.colossus.modules.Killstreaks;
import org.emotionalpatrick.colossus.modules.LastLoginDecrypter;
import org.emotionalpatrick.colossus.modules.Module;
import org.emotionalpatrick.colossus.modules.NoFall;
import org.emotionalpatrick.colossus.modules.IgnoreItems;
import org.emotionalpatrick.colossus.modules.Nuker;
import org.emotionalpatrick.colossus.modules.OpAura;
import org.emotionalpatrick.colossus.modules.OpKill;
import org.emotionalpatrick.colossus.modules.OpSudo;
import org.emotionalpatrick.colossus.modules.NameProtect;
import org.emotionalpatrick.colossus.modules.Parkour;
import org.emotionalpatrick.colossus.modules.Projectiles;
import org.emotionalpatrick.colossus.modules.PvPTimer;
import org.emotionalpatrick.colossus.modules.Retard;
import org.emotionalpatrick.colossus.modules.ReverseNuker;
import org.emotionalpatrick.colossus.modules.SafeWalk;
import org.emotionalpatrick.colossus.modules.Say;
import org.emotionalpatrick.colossus.modules.Search;
import org.emotionalpatrick.colossus.modules.Sneak;
import org.emotionalpatrick.colossus.modules.SongSpammer;
import org.emotionalpatrick.colossus.modules.Spam;
import org.emotionalpatrick.colossus.modules.Speed;
import org.emotionalpatrick.colossus.modules.Speedmine;
import org.emotionalpatrick.colossus.modules.Step;
import org.emotionalpatrick.colossus.modules.Templates;
import org.emotionalpatrick.colossus.modules.TimeWarp;
import org.emotionalpatrick.colossus.modules.Toggle;
import org.emotionalpatrick.colossus.modules.Tracers;
import org.emotionalpatrick.colossus.modules.UserDump;
import org.emotionalpatrick.colossus.modules.Wallhack;
import org.emotionalpatrick.colossus.modules.Waypoints;
import org.emotionalpatrick.colossus.modules.WorldAbuse;

public class ModuleManager {

	public static final ArrayList<Module> modules = new ArrayList<Module>();

	public CopyCat copyCat = new CopyCat();
	public Waypoints waypoints = new Waypoints();
	public Hide hide = new Hide();
	public Chat customChat = new Chat();
	public AntiAFK antiAFK = new AntiAFK();
	public Search search = new Search();
	public Killstreaks killStreaks = new Killstreaks();
	public Projectiles projectiles = new Projectiles();
	public Parkour parkour = new Parkour();
	public Bright bright = new Bright();
	public Beacon beacon = new Beacon();
	public Retard retard = new Retard();
	public BowAimbot bowAimbot = new BowAimbot();
	public FastPlace fastPlace = new FastPlace();
	public Freecam freecam = new Freecam();
	public God god = new God();
	public Flight flight = new Flight();
	public Gui gui = new Gui();
	public Glide glide = new Glide();
	public AutoTool autoTool = new AutoTool();
	public AutoSwim autoSwim = new AutoSwim();
	public Allah allah = new Allah();
	public OpAura opAura = new OpAura();
	public KillAura killAura = new KillAura();
	public AntiVelocity antiVelocity = new AntiVelocity();
	public AutoSoup autoSoup = new AutoSoup();
	public NoFall noFall = new NoFall();
	public PvPTimer pvpTimer = new PvPTimer();
	public Nuker nuker = new Nuker();
	public ReverseNuker reverseNuker = new ReverseNuker();
	public Speed speed = new Speed();
	public SafeWalk safeWalk = new SafeWalk();
	public Filter filter = new Filter();
	public LastLoginDecrypter lastLoginDecrypter = new LastLoginDecrypter();
	public TimeWarp timeWarp = new TimeWarp();
	public Templates templates = new Templates();
	public Sneak sneak = new Sneak();
	public Speedmine speedMine = new Speedmine();
	public Spam spam = new Spam();
	public SongSpammer songSpammer = new SongSpammer();
	public AutoRespawn autoRespawn = new AutoRespawn();
	public Step step = new Step();
	public AutoBlock autoBlock = new AutoBlock();
	public IgnoreItems ignoreItems = new IgnoreItems();
	public AutoFish autoFish = new AutoFish();
	public ConsoleSpam consoleSpam = new ConsoleSpam();
	public Tracers tracers = new Tracers();
	public AutoAccept tpaccept = new AutoAccept();
	public NameProtect nameProtect = new NameProtect();
	public InsultGenerator insultGenerator = new InsultGenerator();
	public UserDump userDump = new UserDump();
	public Wallhack wallhack = new Wallhack();
	public WorldAbuse worldAbuse = new WorldAbuse();
	public OpSudo opSudo = new OpSudo();
	public OpKill opKill = new OpKill();
	public Say say = new Say();
	public Breadcrumb breadcrumb = new Breadcrumb();
	public Toggle toggle = new Toggle();
	public Bind bind = new Bind();
	public Help help = new Help();

	public static final Module getModuleByName(String par1Str) {
		for (Module module : getModules())
			if (module.getName().equalsIgnoreCase(par1Str))
				return module;
		return null;
	}

	public static final ArrayList<Module> getModules() {
		return modules;
	}
}
