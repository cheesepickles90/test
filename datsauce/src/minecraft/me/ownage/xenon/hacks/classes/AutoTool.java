package me.ownage.xenon.hacks.classes;

import me.ownage.xenon.hacks.XenonMod;
import me.ownage.xenon.util.EnumGuiCategory;
import net.minecraft.src.*;

import org.lwjgl.input.Keyboard;




public class AutoTool extends XenonMod
{
	public AutoTool()
	{
		super("Auto Tool", "Selects correct tool based on the block that was clicked.", Keyboard.KEY_BACK, 0x009933, EnumGuiCategory.PLAYER);
	}
	
	@Override
	public void onBlockClicked(int x, int y, int z)
	{
		if(isEnabled())
		{
			bestTool(x, y, z);
		}
	}
	
	public void bestTool(int x, int y, int z)
    {
        int blockId = mc.theWorld.getBlockId(x, y, z);
        int bestSlot = 0;
        float f = 0.1F;
        for(int i1 = 36;i1 < 45;i1++)
        {
            try
            {
                ItemStack curSlot = mc.thePlayer.inventoryContainer.getSlot(i1).getStack();
                if(curSlot.getStrVsBlock(Block.blocksList[blockId]) > f)
                {
                    bestSlot = i1-36;
                    f = curSlot.getStrVsBlock(Block.blocksList[blockId]);
                }
            }catch(Exception e){}
        }
        mc.thePlayer.inventory.currentItem = bestSlot;
    }
	
	public void bestSword(Entity targetEntity)
    {
        int bestSlot = 0;
        float f = 1.0F;
        for(int i1 = 36; i1 < 45; i1++)
        {
        	if((mc.thePlayer.inventoryContainer.inventorySlots.toArray())[i1] != null && targetEntity != null)
        	{
                ItemStack curSlot = mc.thePlayer.inventoryContainer.getSlot(i1).getStack();
                
                if(curSlot != null && ((curSlot.getItem().getDamageVsEntity(targetEntity) + (curSlot.hasEffect() ? getEnchantDamageVsEntity(curSlot, targetEntity) : 0)) > f))
                {
                    bestSlot = i1 - 36;
                    f = (curSlot.getItem().getDamageVsEntity(targetEntity) + (curSlot.hasEffect() ? getEnchantDamageVsEntity(curSlot, targetEntity) : 0));
                }
        	}
        }

        if(f > 1.0F)
        {
        	mc.thePlayer.inventory.currentItem = bestSlot;
        }
    }
	
	public int getEnchantDamageVsEntity(ItemStack i, Entity e)
	{
		if(e instanceof EntityZombie || e instanceof EntityPigZombie || e instanceof EntitySkeleton)
		{
			return EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, i) + EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, i);
		}else
		if(e instanceof EntitySpider)
		{
			return EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, i) + EnchantmentHelper.getEnchantmentLevel(Enchantment.baneOfArthropods.effectId, i);
		}else
		{
			return EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, i);
		}
	}
}
