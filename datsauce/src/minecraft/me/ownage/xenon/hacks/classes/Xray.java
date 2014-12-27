package me.ownage.xenon.hacks.classes;

import java.util.ArrayList;



import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.EnumGuiCategory;
import me.ownage.xenon.util.Value;
import net.minecraft.src.EntityPlayerSP;

import org.lwjgl.input.Keyboard;




public class Xray extends XenonMod
{
	public static ArrayList<Integer> blocks = new ArrayList<Integer>();
	public static Value opacity = new Value("Opacity");
	
	public Xray()
	{
		super("Xray", "Allows you to see through the ground and find ores.", Keyboard.KEY_X, 0x666666, EnumGuiCategory.WORLD);
	}
	
	public void fastReRender() {
		if(mc.thePlayer != null && mc.theWorld != null) {
	        int var0 = (int)mc.thePlayer.posX;
	        int var1 = (int)mc.thePlayer.posY;
	        int var2 = (int)mc.thePlayer.posZ;
	        mc.renderGlobal.markBlockRangeForRenderUpdate(var0 - 200, var1 - 200, var2 - 200, var0 + 200, var1 + 200, var2 + 200);
		}
	}
	
	@Override
	public void onEnable() {
		mc.renderGlobal.loadRenderers();
		fastReRender();
	}
	
	@Override
	public void onDisable() {
		mc.renderGlobal.loadRenderers();
		fastReRender();
	}
	
	public static boolean isXrayBlock(int id)
	{
		return blocks.contains(id);
	}
}
