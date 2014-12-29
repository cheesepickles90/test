package org.emotionalpatrick.colossus.gui.screens;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ImageBufferDownload;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Tessellator;

import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.threads.ThreadAltDatabaseLogin;
import org.emotionalpatrick.colossus.utilities.Encryption;
import org.emotionalpatrick.colossus.utilities.color.ChatColor;
import org.lwjgl.opengl.GL11;

public class RandomAlt extends GuiScreen {
	private GuiScreen parentScreen;
	private GuiButton backButton;
	private GuiButton newAltButton;

	private String screenTitle;

	public Thread altThread;

	public RandomAlt(GuiScreen g) {
		this.screenTitle = ChatColor.RED + "An error has occured.";
		this.parentScreen = g;
	}

	public RandomAlt() {
		this((GuiScreen) null);
	}

	@Override
	public void initGui() {
		this.controlList.clear();
		this.controlList.add(new GuiButton(1, this.width / 2 - 100,
				this.height / 4 + 156, "Back"));
		this.controlList.add(new GuiButton(2, this.width / 2 - 100,
				this.height / 4 + 120 + 12, "Fetch Alt"));
		this.screenTitle = ChatColor.RED
				+ "Communicating with alt database...";
		
		altThread = new Thread(new ThreadAltDatabaseLogin(
				Encryption.decryptString2(":WcV& |fzIe4WH?@AC=")),
				"Alt login thread");
		altThread.start();

		if (!altThread.isAlive()) {
			this.screenTitle = ChatColor.DARK_GREEN + "Logged in as: "
					+ mc.session.username;
		} else if (altThread.isAlive()) {
			this.screenTitle = ChatColor.RED
					+ "Communicating with alt database...";
		}
	}

	@Override
	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle,
				this.width / 2, 20, -1);
		super.drawScreen(var1, var2, var3);

		if (!altThread.isAlive()) {
			this.screenTitle = ChatColor.DARK_GREEN + "Logged in as: "
					+ mc.session.username;
		} else if (altThread.isAlive()) {
			this.screenTitle = ChatColor.RED
					+ "Communicating with alt database...";
		}

		drawAltSkin();
	}

	@Override
	protected void actionPerformed(GuiButton g) {
		if (g.id == 1) {
			this.mc.displayGuiScreen(this.parentScreen);
		} else if (g.id == 2) {
			altThread = new Thread(new ThreadAltDatabaseLogin(
					Encryption.decryptString2(":WcV& |fzIe4WH?@AC=")),
					"Alt login thread");
			altThread.start();
			if (!altThread.isAlive()) {
				this.screenTitle = ChatColor.DARK_GREEN + "Logged in as: "
						+ mc.session.username;
			} else if (altThread.isAlive()) {
				this.screenTitle = ChatColor.RED
						+ "Communicating with alt database...";
			}
		}
	}

	public void drawTexturedRect(int var1, int var2, int var3, int var4,
			int var5, int var6) {
		float var7 = 0.015625F;
		float var8 = 0.03125F;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(var1 + 0, var2 + var6, this.zLevel, (var3 + 0)
				* var7, (var4 + var6) * var8);
		var9.addVertexWithUV(var1 + var5, var2 + var6, this.zLevel,
				(var3 + var5) * var7, (var4 + var6) * var8);
		var9.addVertexWithUV(var1 + var5, var2 + 0, this.zLevel, (var3 + var5)
				* var7, (var4 + 0) * var8);
		var9.addVertexWithUV(var1 + 0, var2 + 0, this.zLevel,
				(var3 + 0) * var7, (var4 + 0) * var8);
		var9.draw();
	}

	public void drawAltSkin() {
		int var1 = this.width / 2 / 4 - 4;
		byte var2 = 10;
		ScaledResolution var3 = new ScaledResolution(this.mc.gameSettings,
				this.mc.displayWidth, this.mc.displayHeight);
		GL11.glClear(256);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, var3.getScaledWidth_double() / 4.0D,
				var3.getScaledHeight_double() / 4.0D, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.obtainImageData(
				"http://skins.minecraft.net/MinecraftSkins/"
						+ mc.session.username + ".png",
				new ImageBufferDownload());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine
				.getTextureForDownloadableImage(
						"http://skins.minecraft.net/MinecraftSkins/"
								+ Wrapper.getMinecraft().session.username
								+ ".png", "/mob/char.png"));
		this.zLevel = -90.0F;
		this.drawTexturedRect(var1, var2, 8, 8, 8, 8);
		this.drawTexturedRect(var1, var2 + 8, 20, 20, 8, 12);
		this.drawTexturedRect(var1 - 4, var2 + 8, 44, 20, 4, 12);
		this.drawTexturedRect(var1 + 8, var2 + 8, 44, 20, 4, 12);
		this.drawTexturedRect(var1, var2 + 20, 4, 20, 4, 12);
		this.drawTexturedRect(var1 + 4, var2 + 20, 4, 20, 4, 12);
		this.drawTexturedRect(var1, var2, 40, 8, 8, 8);
		GL11.glOrtho(0.0D, var3.getScaledWidth(), var3.getScaledHeight(), 0.0D,
				1000.0D, 3000.0D);
	}
}
