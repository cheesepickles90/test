package org.emotionalpatrick.colossus.hooks;

import java.util.Iterator;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Packet28EntityVelocity;

import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.ColossusWrapper;
import org.emotionalpatrick.colossus.modules.Module;
import org.emotionalpatrick.colossus.modules.manager.ModuleManager;

public class ColossusHook {

	public static final void onTickIngame() {
		for (Module m : ModuleManager.getModules()) {
			if (m.isEnabled()) {
				m.onTick();
			}
		}
	}

	public static final void onPreMotionUpdate() {
		for (Module m : ModuleManager.getModules()) {
			if (m.isEnabled()) {
				m.preMotionUpdate();
			}
		}
	}

	public static final void onPostMotionUpdate() {
		for (Module m : ModuleManager.getModules()) {
			if (m.isEnabled()) {
				m.postMotionUpdate();
			}
		}
	}

	public static final void onPlayerClick(int i) {
		for (Module m : ModuleManager.getModules()) {
			if (m.isEnabled()) {
				m.onClickBlock(i);
			}
		}
	}

	public static final void onPlayerRespawn() {
		for (Module m : ModuleManager.getModules()) {
			if (m.isEnabled()) {
				m.onRespawn();
			}
		}
	}

	public static final void onGlobalRender(float f) {
		for (Module m : ModuleManager.getModules()) {
			if (m.isEnabled()) {
				m.onGlobalRender(f);
			}
		}
	}

	public static final void onPreAttackEntity() {
		for (Module m : ModuleManager.getModules()) {
			if (m.isEnabled()) {
				m.preAttackEntity();
			}
		}
	}

	public static final void onPostAttackEntity() {
		for (Module m : ModuleManager.getModules()) {
			if (m.isEnabled()) {
				m.postAttackEntity();
			}
		}
	}
	
	public static final void onRenderIGGUI(FontRenderer fr) {
		for (Module m : ModuleManager.getModules()) {
			if (m.isEnabled()) {
				m.onRenderIngame(fr);
			}
		}
	}

	public static final void onAttackEntity(EntityPlayer par1EntityPlayer,
			Entity par2Entity) {
		for (Module m : ModuleManager.getModules()) {
			if (m.isEnabled()) {
				m.onAttackEntity(par1EntityPlayer, par2Entity);
			}
		}
	}
	
	public static final void onChatLine(String s) {
		for (Module m : ModuleManager.getModules()) {
			if (m.isEnabled()) {
				m.onChatLine(s);
			}
		}
	}

	public static final int onRightClick(int i) {
		Iterator it = ModuleManager.getModules().iterator();
		Module hb;
		do {
			if (!it.hasNext()) {
				return i;
			}
			hb = (Module) it.next();
		} while (!hb.enabled || hb.onRightClick(i) == i);
		return hb.onRightClick(i);
	}
	
	public static final int onOpacitySet() {
		Iterator it = ModuleManager.getModules().iterator();
		Module m;
		do {
			if (!it.hasNext()) {
				return 255;
			}
			m = (Module) it.next();
		} while (!m.isEnabled() || m.setBlockOpacity() == 255);
		return m.setBlockOpacity();
	}
	
	public static final int onBlockBrightnessIntegerSet(int i) {
		Iterator it = ModuleManager.getModules().iterator();
		Module m;
		do {
			if (!it.hasNext()) {
				return i;
			}

			m = (Module) it.next();
		} while (!m.enabled || m.setBlockBrightnessInteger(i) == i);
		return m.setBlockBrightnessInteger(i);
	}
	
	public static final float onBlockBrightnessSet(float i) {
		Iterator it = ModuleManager.getModules().iterator();
		Module m;
		do {
			if (!it.hasNext()) {
				return i;
			}
			m = (Module) it.next();
		} while (!m.enabled || m.setBlockBrightness(i) == i);
		return m.setBlockBrightness(i);
	}
	
	public static final float onGammaSet(float i) {
		Iterator it = ModuleManager.getModules().iterator();
		Module m;
		do {
			if (!it.hasNext()) {
				return i;
			}
			m = (Module) it.next();
		} while (!m.enabled || m.setGammaSetting(i) == i);
		return m.setGammaSetting(i);
	}

	public static final int onBlockRenderPass(int i, Block b) {
		Iterator it = ModuleManager.getModules().iterator();
		Module m;
		do {
			if (!it.hasNext()) {
				return i;
			}
			m = (Module) it.next();
		} while (!m.enabled || m.onRenderBlockPass(i, b) == i);
		return m.onRenderBlockPass(i, b);
	}
    
	public static final boolean onRenderAllFaces(Block b, int i, int j,
			int k) {
		Iterator var4 = ModuleManager.getModules().iterator();
		Module m;
		do {
			if (!var4.hasNext()) {
				return false;
			}

			m = (Module) var4.next();
		} while (!m.isEnabled() || !m.onRenderAllFaces(b, i, j, k));
		return true;
	}
    
	public static final int onBlockHitDelay(int i) {
		Iterator it = ModuleManager.getModules().iterator();
		Module hb;
		do {
			if (!it.hasNext()) {
				return i;
			}
			hb = (Module) it.next();
		} while (!hb.enabled || hb.onBlockHitDelay(i) == i);
		return hb.onBlockHitDelay(i);
	}

	public static final void onClickBlock(int i, int j, int k, int f) {
		for (Module m : ModuleManager.getModules()) {
			if (m.isEnabled()) {
				m.onClickBlock(i, j, k, f);
			}
		}
	}

	public static final AxisAlignedBB fluidBoundingBoxCreationHook(Block b, int i,
			int j, int k) {
		AxisAlignedBB aabb = null;
		for (Module m : ModuleManager.getModules())
			if (m.enabled) {
				AxisAlignedBB aabb1 = m.onFluidBoundingBoxCreate(b, i, j, k);
				if (aabb1 != null) {
					aabb = aabb1;
				}
			}
		return aabb;
	}

	public static final boolean entityVelocityHook(Packet28EntityVelocity ev) {
		Iterator i = ModuleManager.getModules().iterator();
		while (i.hasNext()) {
			Module hb = (Module) i.next();
			if (hb.enabled) {
				boolean velocity = hb.entityVelocity(ev);
				if (!velocity) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static final float curBlockDamageSet(Block b, int i1, int i2, int i3,
			float i4) {
		float d = b.getPlayerRelativeBlockHardness(ColossusWrapper.getPlayer(),
				ColossusWrapper.getWorld(), i1, i2, i3);
		Iterator i = Colossus.getManager().getModules().iterator();
		Module m;
		do {
			if (!i.hasNext()) {
				return d;
			}
			m = (Module) i.next();
		} while (!m.enabled || m.setCurBlockDamage(b, i1, i2, i3, i4) == d);
		return m.setCurBlockDamage(b, i1, i2, i3, i4);
	}
	
	public static final String onChatLineRender(String s) {
		Iterator it = Colossus.getManager().getModules().iterator();
		while (it.hasNext()) {
			Module m = (Module) it.next();
			if (m.isEnabled()) {
				String msg = m.onChatMessage(s);
				if (!s.equalsIgnoreCase(msg)) {
					return msg;
				}
			}
		}
		return s;
	}
}
