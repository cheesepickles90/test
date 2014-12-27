package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.EnumGuiCategory;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.Packet14BlockDig;

import org.lwjgl.input.Keyboard;




public class Nuker extends XenonMod
{
	private int nukeID;
	private boolean shouldNuke;
	
	public Nuker()
	{
		super("Nuker", "Destroys nearby blocks.", Keyboard.KEY_COMMA, 0x808080, EnumGuiCategory.WORLD);
	}
	
	@Override
	public void onBlockClicked(int x, int y, int z)
	{
		if(isEnabled() && mc.theWorld.getBlockId(x, y, z) != 0)
		{
			nukeID = mc.theWorld.getBlockId(x, y, z);
			shouldNuke = true;
			Xenon.addChatMessage("Now nuking: " + nukeID);
		}
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player)
	{
		if(isEnabled())
		{
			int maxH = 5;
			int minH = -5;
			int maxW = 3;
			int minW = -3;

			for(int y = maxH; y >= minH; y--)
			{
				for(int z = minW; z <= maxW; z++)
				{
					for(int x = minW; x <= maxW; x++)
					{
						int xPos = (int) Math.round(mc.thePlayer.posX + x);
						int yPos = (int) Math.round(mc.thePlayer.posY + y);
						int zPos = (int) Math.round(mc.thePlayer.posZ + z);

						int id = mc.theWorld.getBlockId(xPos, yPos, zPos);

						if(mc.playerController.isInCreativeMode() ? id != 0 : id == nukeID)
						{
							mc.getSendQueue().addToSendQueue(new Packet14BlockDig(0, xPos, yPos, zPos, 1));
							mc.getSendQueue().addToSendQueue(new Packet14BlockDig(2, xPos, yPos, zPos, 1));
						}
					}
				}
			}
		}
	}
}
