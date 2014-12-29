package org.emotionalpatrick.colossus.modules;

public class Retard extends Module {
		
	public static float fakeYaw;
	public static float fakePitch;
	
	public Retard() {
		super("Retard", ".retard", "Head in body retard", "Emotional Patrick", "Modes");
	}
	
	@Override
	public void onTick() {
        setFakePitch(-180.0F);
	}

	public static void setFakeYaw(float fakeYaw) {
		Retard.fakeYaw = fakeYaw;
	}

	public static void setFakePitch(float fakePitch) {
		Retard.fakePitch = fakePitch;
	}
	
	public static float getFakeYaw() {
		return fakeYaw;
	}

	public static float getFakePitch() {
		return fakePitch;
	}
}
