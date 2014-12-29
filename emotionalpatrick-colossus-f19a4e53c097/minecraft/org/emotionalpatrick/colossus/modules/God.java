package org.emotionalpatrick.colossus.modules;

import java.util.Random;

import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.Packet18Animation;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;

public class God extends Module {

	private Random random;

	private long CURRENT_MS = 0L;
	private long MAX_TELE_THRESHOLD = 888L;
	private long LAST_TELE = 0L;

	public God() {
		super("God", ".god", "Invulnerabilitiy - Buggy", "HarukoXChan");
		random = new Random();
	}

	@Override
	public void onTick() {
		runTeleport();
	}

	public void runTeleport() {
		EntityClientPlayerMP player = Wrapper.getPlayer();
		if (!this.isEnabled()) return;
		CURRENT_MS = System.nanoTime() / 1000000L; 
		boolean TELE_TIMED = CURRENT_MS - LAST_TELE >= MAX_TELE_THRESHOLD;

		if (!TELE_TIMED) return;
		LAST_TELE = System.nanoTime() / 1000000L;
		
		player.setSprinting(false);
		double teleportFloat = Math.abs(random.nextDouble() + 0.55D);
		player.setPosition(player.posX + teleportFloat / 2D, player.posY, player.posZ + teleportFloat / 2D);
		player.setPosition(player.posX - teleportFloat / 7D, player.posY, player.posZ - teleportFloat / 7D);
		Helper.sendPacket(new Packet18Animation(player, 3));
		
		Helper.addConsole("Next teleportation value for invincibilty: " + (float)(teleportFloat / 2.0D * 2.0D - teleportFloat / 7.0D * 2.0D));
	}
}
