package org.emotionalpatrick.colossus.commands;

import java.util.ArrayList;

import net.minecraft.src.GuiNewChat;
import net.minecraft.src.Packet11PlayerPosition;

import org.emotionalpatrick.colossus.gui.screens.CGuiNewChat;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.modules.Module;
import org.emotionalpatrick.colossus.modules.manager.ModuleManager;
import org.emotionalpatrick.colossus.utilities.Encryption;

public class CommandManagerImpl implements CommandManager {

	@Override
	public void runCommands(String cmd) {
		for (Module m : Colossus.getManager().getModules()) {
			m.externalCommand(cmd);
		}
		
		if (cmd.startsWith(".")) {
			if (cmd.startsWith(".ytp")) {
				String s1[] = cmd.split(" ");
				try {
					int height = Integer.parseInt(s1[1]);
					for (int o = 0; o < height; o++)
						Helper.sendPacket(new Packet11PlayerPosition(Wrapper.getPlayer().posX, 
								Wrapper.getPlayer().boundingBox.minY + 1f,
								Wrapper.getPlayer().posY + 1f,
								Wrapper.getPlayer().posZ, 
								Wrapper.getPlayer().onGround));
					Wrapper.getPlayer().setPosition(
							Wrapper.getPlayer().posX,
							Wrapper.getPlayer().posY + height,
							Wrapper.getPlayer().posZ);
					Helper.addChat("Teleported (" + height + ") block(s) on the Y-axis.");
				} catch (Exception err) {
					Helper.addChat("Invalid syntax, .ytp (amount)");
				}
			}
			
			if (cmd.equalsIgnoreCase(".cc")) {
				GuiNewChat.chatLines.clear();
				CGuiNewChat.chatLines.clear();
			}
			
			if (cmd.equalsIgnoreCase(".tc")) {
				for (int i = 0; i < 16; i++) {
					Helper.addChat("\247" + Integer.toHexString(i) + "Testing colors!");
				}
			}
			
			for (Module m : Colossus.getManager().getModules()) {
				if (cmd.equalsIgnoreCase(".toggle \"" + m.getName() + "\"")) {
					m.onToggle();
					Helper.addChat("Module \"" + m.getName() + "\"" + (m.isEnabled() ? " toggled on." : " toggled off."));
				}
				if (cmd.equalsIgnoreCase(m.getCommand())) {
					m.runCommand(cmd);
				}
			}
		}
	}
}
