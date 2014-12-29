package org.emotionalpatrick.colossus.modules;

import net.minecraft.src.Packet3Chat;
import net.minecraft.src.StringUtils;

import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.modules.manager.ModuleManager;

public class AutoAccept extends Module {

	public AutoAccept() {
		super("AutoAccept", ".tpac", "Accepts TPA's from friends", "Emotional Patrick");
		this.enabled = true;
	}

	@Override
	public boolean onRecieveChat(Packet3Chat c) {
		Colossus.getManager();
		if (ModuleManager.getModuleByName("Name Protect").isEnabled()) {
			String m = StringUtils.stripControlCodes(c.message);
			for (int protectCheck = 0; protectCheck < NameProtect.originalNames.size(); protectCheck++) {
				String oName = "" + NameProtect.originalNames.get(protectCheck);
				String pName = "" + NameProtect.protectedNames.get(protectCheck);
				if (m.contains(pName) || m.contains(oName)) {
					if(m.contains("has requested to teleport to you.")) {
						Helper.sendChat("/tpaccept");
					}
				}
			}
		}
		return true;
	}
}
