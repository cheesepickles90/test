package me.ownage.xenon.hacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;



import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.FormattedString;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.GuiScreen;


public class XenonMod
{
	protected Minecraft mc = Xenon.getMinecraft();

	private String name;
	private String description;
	public Object[] original;
	private int keyBind;
	private int arrayColor;

	private boolean isEnabled;
	private boolean isVisible;

	private EnumGuiCategory category;
	
	public XenonMod(String name, String description, EnumGuiCategory category) {
		this.name = name;
		this.setDescription(description);
		this.isVisible = false;
		this.category = category;
		this.original = new Object[] { name };
		this.keyBind = 0;
		System.out.println("[Xenon] " + name + " instantiated.");
	}

	public XenonMod(String name, String description, int keyBind, EnumGuiCategory category)
	{
		this.name = name;
		this.setDescription(description);
		this.keyBind = keyBind;
		this.isVisible = false;
		this.category = category;
		this.original = new Object[] { name };
		System.out.println("[Xenon] " + name + " instantiated.");
	}

	public XenonMod(String name, String description, int keyBind, int arrayColor, EnumGuiCategory category)
	{
		this.name = name;
		this.setDescription(description);
		this.keyBind = keyBind;
		this.arrayColor = arrayColor;
		this.isVisible = true;
		this.category = category;
		this.original = new Object[] { name, arrayColor };
		System.out.println("[Xenon] " + name + " instantiated.");
	}

	public void onEnable() {}
	public void onDisable() {}
	public void onToggled() {}
	public void beforeUpdate(EntityPlayerSP player) {}
	public void onUpdate(EntityPlayerSP player) {}
	public void onPlayerUpdate(EntityPlayerSP player) {}
	public void afterUpdate(EntityPlayerSP player) {}
	public void runTick() {}
	public void onRender() {}
	public void onBlockClicked(int x, int y, int z) {}
	
	public GuiScreen onDisplayGuiScreen(GuiScreen guiScreen)
	{ 
		return guiScreen; 
	}

	public void onKeyPressed(int key)
	{
		if(key == keyBind)
		{
			toggle();
		}
	}

	public String getName()
	{
		return name;
	}

	public int getKey()
	{
		return keyBind;
	}

	public int getColor()
	{
		return arrayColor;
	}

	public boolean isEnabled()
	{
		return isEnabled;
	}

	public boolean getVisible()
	{
		return isVisible;
	}
	
	public EnumGuiCategory getCategory()
	{
		return category;
	}

	public final void setState(boolean flag) {
		isEnabled = flag;
		if(isEnabled()) {
			onEnable();
			if(isVisible) {
				Xenon.getHacks().display.add(this);
			}
		} else {
			onDisable();
			Xenon.getHacks().display.remove(this);
		}
	}

	public void setName(String s)
	{
		name = s;
	}

	public void setKey(int i)
	{
		keyBind = i;
	}

	public void setColor(int i)
	{
		arrayColor = i;
	}

	public final void toggle()
	{
		setState(!isEnabled);
		onToggled();
		Xenon.getFileManager().saveHacks();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
