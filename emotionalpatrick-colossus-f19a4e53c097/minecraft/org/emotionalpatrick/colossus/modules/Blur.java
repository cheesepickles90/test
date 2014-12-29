package org.emotionalpatrick.colossus.modules;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Blur extends Module {

	public Blur() {
		super("Blur", ".blur", "Motion blur", "Emotional Patrick", 0xD3D3D3, Keyboard.KEY_NONE, "World");
	}

	public void onGlobalRender(float f) {
		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		// GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glTexCoord2f(0.0f, 1.0f); 
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(1.0f, 1.0f); 
		GL11.glVertex2f(800, 0);
		GL11.glTexCoord2f(1.0f, 0.0f); 
		GL11.glVertex2f(800,  600);
		GL11.glTexCoord2f(0.0f, 0.0f); 
		GL11.glVertex2f(0,  600);
		GL11.glEnd();
		GL11.glPopMatrix();
	}
}
