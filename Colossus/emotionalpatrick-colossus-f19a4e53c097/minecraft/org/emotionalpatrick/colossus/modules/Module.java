package org.emotionalpatrick.colossus.modules;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.modules.manager.ModuleManager;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Packet28EntityVelocity;
import net.minecraft.src.Packet3Chat;

public class Module {

	/** Should be shown in the ArrayList */
	public boolean shown;
	
	/** Checks if module is enabled */
	public boolean enabled;
	
	/** The module's name */
	public String name;
	
	/** The module's command */
	public String command;
	
	/** The module's description */
	public String desc;
	
	/** The module's author */
	public String author;
	
	/** The module's category */
	public String category = "";
	
	/** The module's color in the ArrayList */
	public int color;
	
	/** The module's keybind */
	public int key;

	/** First ColossusModule constructor
	 * 
	 * @param n - Module name
	 * @param c - Module command
	 * @param d - Module description
	 * @param a - Module author
	 * @param co - Module color
	 * @param k - Default module keybind
	 * @param ca - Module category
	 */
	public Module(String n, String c, String d, String a, int co, int k, String ca) {
		ModuleManager.getModules().add(this);
		name = n;
		command = c;
		desc = d;
		author = a;
		color = co;
		key = k;
	    category = ca;
	    Helper.addConsole("Placed module \"" + name  + "\" into module category \"" + ca + "\".");
		shown = true;
	}
	
	/** Second ColossusModule constructor
	 * 
	 * @param n - Module name
	 * @param c - Module command
	 * @param d - Module description
	 * @param a - Module author
	 * @param co - Module color
	 * @param k - Default module keybind
	 */
	public Module(String n, String c, String d, String a, int co, int k) {
		ModuleManager.getModules().add(this);
		name = n;
		command = c;
		desc = d;
		author = a;
		color = co;
		key = k;
		shown = true;
	}

	/** Third ColossusModule constructor
	 * 
	 * @param n - Module name
	 * @param c - Module command
	 * @param d - Module description
	 * @param a - Module author
	 */
	public Module(String n, String c, String d, String a) {
		ModuleManager.getModules().add(this);
		name = n;
		command = c;
		desc = d;
		author = a;
		color = 0xffffff;
		key = -1;
		shown = false;
	}
	
	/** Fourth ColossusModule constructor
	 * 
	 * @param n - Module name
	 * @param c - Module command
	 * @param d - Module description
	 * @param a - Module author
	 * @param ca - Module category
	 */
	public Module(String n, String c, String d, String a, String ca) {
		ModuleManager.getModules().add(this);
		name = n;
		command = c;
		desc = d;
		author = a;
		color = 0xffffff;
		category = ca;
	    Helper.addConsole("Placed module \"" + name  + "\" into module category \"" + ca + "\".");
		shown = false;
	}

	/** Called when a module's command is ran */
	public void runCommand(String cmd) {
		onToggle();
		Helper.addChat(name + (enabled ? " toggled on." : " toggled off."));
	}

	/** Called when a module is toggled */
	public void onToggle() {
		toggle();
	}
	
	/** Called for external commands */
	public void externalCommand(String s) {
	}

	/** Called on enabled */
	public void onEnable() {
	}

	/** Called on disabled */
	public void onDisable() {
	}

	/** Returns module name */
	public String getName() {
		return name;
	}

	/** Returns module description */
	public String getDescription() {
		return desc;
	}

	/** Returns module toggle command */
	public String getCommand() {
		return command;
	}
	
	/** Returns module author */
	public String getAuthor() {
		return author;
	}

	/** Returns module color */
	public int getColor() {
		return color;
	}

	/** Returns module keybind */
	public int getKey() {
		return key;
	}

	/** Sets module keybind */
	public void setKey(int k) {
		key = k;
	}
	
	/** Returns module category */
	public String getCategory() {
		return category;
	}

	/** Returns module status */
	public boolean isEnabled() {
		return this.enabled;
	}
	
	/** Returns if the module is shown */
	public boolean isShown() {
		return this.shown;
	}

	public void toggle() {
		enabled = !enabled;
		if (enabled) {
			onEnable();
		} else {
			onDisable();
		}
	}

	/** Called on every in-game tick */
	public void onTick() {
	}

	/** Called before the motion update */
	public void preMotionUpdate() {
	}

	/** Called after the motion update */
	public void postMotionUpdate() {
	}

	/** Called when attacking an entity */
	public void onAttackEntity(EntityPlayer par1EntityPlayer, Entity par2Entity) {
	}

	/** Called before attacking an entity */
	public void preAttackEntity() {
	}

	/** Called after attacking an entity */
	public void postAttackEntity() {
	}

	/** Called when a block is clicked */
	public void onClickBlock(int i) {
	}

	/** Called when the world renders */
	public void onGlobalRender(float f) {
	}

	/** Called when right clicking */
	public int onRightClick(int i) {
		return i;
	}
	
	/** Called when breaking a block */
	public int onBlockHitDelay(int i) {
		return i;
	}

	/** Called once you've hit a block */
	public void onClickBlock(int I1, int I2, int I3, int I4) {
	}

	/** Called when you enter water */
	public AxisAlignedBB onFluidBoundingBoxCreate(Block b, int i, int j, int k) {
		return null;
	}
	
	/** Called when you attempt to break a block is set */
	public float setCurBlockDamage(Block b, int i, int j, int k, float l) {
		return b.getPlayerRelativeBlockHardness(Wrapper.getPlayer(),
				Wrapper.getPlayer().worldObj, i, j, k);
	}

	/** Called when your player receives velocity */
	public boolean entityVelocity(Packet28EntityVelocity var1) {
		return true;
	}

	/** Called on every chat message */
	public void onChatLine(String s) {
		
	}
	
	/** Called upon player respawn */
	public void onRespawn() {
		
	}
	
	/** Called in the in-game GUI */
	public void onRenderIngame(FontRenderer fr) {
		
	}
	
	/** Called when block brightness is set */
	public float setBlockBrightness(float f) {
		return f;
	}

	/** Called when the block is rendered */
	public int onRenderBlockPass(int var1, Block b) {
		return var1;
	}

	/** Called when the block is rendered */
	public boolean onRenderAllFaces(Block b, int i, int j, int k) {
		return false;
	}
	
	/** Called when the block opacity is set */
	public int setBlockOpacity() {
		return 255;
	}
	
	/** Called when the block opacity is set */
	public float setGammaSetting(float f) {
		return f;
	}

	/** Called when block brightness is set */
	public int setBlockBrightnessInteger(int var1) {
		return var1;
	}
	
	/** Called when the chat is rendered */
	public String onChatMessage(String msg) {
		return msg;
	}
	
	/** Called when the player recieves a chat message */
	public boolean onRecieveChat(Packet3Chat c) {
		return true;
	}
	
	/** Called when the player disconnects from a server */
	public void onDisconnect() {
		
	}
	
	/** Called when the player performs a middle click */
	public void onMiddleClick() {
		
	}

	/** Called when the player dies */
	public void onDeath() {
		
	}
}
