package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.util.EnumGuiCategory;
import net.minecraft.src.EntityPlayerSP;

import org.lwjgl.input.Keyboard;




public class Brightness extends XenonMod
{
	public float fakeGamma = mc.gameSettings.gammaSetting;
	private boolean isFading = false;
	private static final float FADE_INTERVAL = 1.0F;
	
	public Brightness()
	{
		super("Brightness", "Brightens the world.", Keyboard.KEY_C, 0xFFF8C6, EnumGuiCategory.WORLD);
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player)
	{
		if(!isEnabled() && fakeGamma != mc.gameSettings.gammaSetting && !isFading)
		{
			fakeGamma = mc.gameSettings.gammaSetting; 
		}
		if(isFading && isEnabled())
		{
			if(fakeGamma < 20F)
			{
				fakeGamma += FADE_INTERVAL;
			}
			if(fakeGamma > 20F)
			{
				fakeGamma = 20F;
				isFading = false;
			}
			if(fakeGamma == 20F)
			{
				isFading = false;
			}
		}
		if(isFading && !isEnabled())
		{
			if(fakeGamma > mc.gameSettings.gammaSetting)
			{
				fakeGamma -= FADE_INTERVAL;
			}
			if(fakeGamma == mc.gameSettings.gammaSetting)
			{
				isFading = false;
			}
			if(fakeGamma < mc.gameSettings.gammaSetting)
			{
				fakeGamma = mc.gameSettings.gammaSetting;
				isFading = false;
			}
		}
	}
	
	public void onEnable()
	{
		super.onEnable();
		
		isFading = true;
	}
	
	public void onDisable() 
	{
		super.onDisable();
		
		isFading = true;
	}
}
