package org.emotionalpatrick.colossus.gui.screens;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.ImageBufferDownload;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.Tessellator;

import org.emotionalpatrick.colossus.gui.components.PasswordField;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.threads.ThreadAltDatabaseLogin;
import org.emotionalpatrick.colossus.utilities.Encryption;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.net.URL;
import java.net.URLEncoder;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

public class AccountLogin extends GuiScreen {

	private String state = "N/A";
	private int color = 0xffffff;

	private int colGreen = 0x37F216;
	private int colRed = 0xCF0A0A;

	private GuiScreen parentScreen;
	private GuiTextField field_22111_h;
	private GuiTextField fieldNick;
	private PasswordField fieldPass;

	public AccountLogin(GuiScreen guiscreen) {
		parentScreen = guiscreen;
	}

	@Override
	public void updateScreen() {
		fieldNick.updateCursorCounter();
		fieldPass.updateCursorCounter();
	}

	@Override
	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12,
				"Login"));
		controlList.add(new GuiButton(1, width / 2 - 100,
				height / 4 + 120 + 12, "Back"));
		String s = mc.session.username;
		((GuiButton) controlList.get(0)).enabled = s.length() > 0;
		fieldNick = new GuiTextField(fontRenderer, width / 2 - 100,
				(height / 4 - 10) + 20, 200, 20);
		fieldPass = new PasswordField(fontRenderer, width / 2 - 100,
				(height / 4 - 10) + 50 + 12, 200, 20);
		
		this.controlList.add(new GuiButton(2, 2, 2, 100, 20, "Random Alt"));

		fieldNick.setMaxStringLength(228);
		fieldNick.setText(mc.session.username);

		fieldPass.setMaxStringLength(228);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if (!guibutton.enabled) {
			return;
		}
		if (guibutton.id == 2) {
			mc.displayGuiScreen(new RandomAlt(this));
		} else
		if (guibutton.id == 1) {
			mc.displayGuiScreen(parentScreen);
		} else if (guibutton.id == 0) {
			if (fieldPass.getText().length() > 0) {
				try {
					login(fieldNick.getText(), fieldPass.getText());
				} catch (UnsupportedEncodingException e) {
				}
				fieldPass.setText("");
			} else {
				String s = fieldNick.getText().trim();
				s = s.replaceAll("&", "\247");
				mc.session.username = s;
				mc.session.sessionId = "-";
				state = "Logged in as offline name \"" + s + "\".";
			}
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		fieldNick.textboxKeyTyped(c, i);
		if (c == '\r') {
			actionPerformed((GuiButton) controlList.get(0));
		}
		((GuiButton) controlList.get(0)).enabled = fieldNick.getText().length() > 0;
		fieldPass.textboxKeyTyped(c, i);
	}

	public void login(String username, String password)
			throws UnsupportedEncodingException {
		String result;
		result = excutePost(
				"https://login.minecraft.net/",
				(new StringBuilder("user="))
						.append(URLEncoder.encode(fieldNick.getText(), "UTF-8"))
						.append("&password=")
						.append(URLEncoder.encode(fieldPass.getText(), "UTF-8"))
						.append("&version=").append(13).toString());
		if (result == null) {
			state = "Can't connect to minecraft.net";
			return;
		}
		if (!result.contains(":")) {
			if (result.trim().equals("Bad login"))
				state = "Login failed";
			else if (result.trim().equals("Old version")) {
				state = "Outdated launcher";
			} else {
				state = result;
			}
			return;
		}
		try {
			String[] values = result.split(":");
			mc.session.username = values[2].trim();
			mc.session.sessionId = values[3].trim();
			state = "Logged in as \"" + mc.session.username + "\".";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		fieldNick.mouseClicked(i, j, k);
		fieldPass.mouseClicked(i, j, k);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer,
				stringtranslate.translateKey("Account Login"),
				width / 2, (height / 4 - 60) + 20, 0xffffff);
		fontRenderer.drawString("Username", width / 2 - 100,
				(height / 4 - 10) + 10, 0xffffff);
		fieldNick.drawTextBox();
		fontRenderer.drawString("Password", width / 2 - 100,
				(height / 4 - 10) + 52, 0xffffff);
		fieldPass.drawTextBox();

		boolean status = false;
		if (mc.session.sessionId != "-") {
			status = true;
		}
		String Prem;
		if (status) {
			Prem = "\2472Connected as a premium user.";
		} else {
			Prem = "Connected as an offline user.";
		}
		{
			fontRenderer.drawString(Prem, width / 2 - 100,
					(height / 4 - 10) + 92 + 12, 0xff0000);
		}
		fontRenderer.drawString("Status: \247f" + state, width / 2 - 100,
				(height / 4 - 10) + 92, 0xffffff);

		String username = Wrapper.getMinecraft().session.username;
		renderFace(width / 2 - 10, (height / 4 - 60) + 40);

		super.drawScreen(i, j, f);
	}

	public void renderFace(int x, int y) {
		float zLevel = 0;
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.obtainImageData(
				"http://s3.amazonaws.com/MinecraftSkins/" + mc.session.username
						+ ".png", new ImageBufferDownload());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,
				mc.renderEngine
						.getTextureForDownloadableImage(
								"http://s3.amazonaws.com/MinecraftSkins/"
										+ mc.session.username + ".png",
								"/mob/char.png"));
		float f1 = 3f;
		int f = 3;
		int x2 = x + 184;
		GL11.glScalef(f1, f1, f1);
		drawTexturedModalRect(x / f, y / f, 8, 8, 8, 8, zLevel);
		drawTexturedModalRect(x / f, y / f, 40, 8, 8, 8, zLevel);
		GL11.glPopMatrix();
	}

	public void drawTexturedModalRect(int i, int j, int k, int i1, int j1,
			int k1, float zLevel) {
		float f = 0.015625F;
		float f1 = 0.03125F;
		Tessellator tesselator = Tessellator.instance;
		tesselator.startDrawingQuads();
		tesselator.addVertexWithUV(i + 0, j + k1,
				zLevel, (k + 0) * f,
				(i1 + k1) * f1);
		tesselator.addVertexWithUV(i + j1, j + k1,
				zLevel, (k + j1) * f,
				(i1 + k1) * f1);
		tesselator.addVertexWithUV(i + j1, j + 0,
				zLevel, (k + j1) * f,
				(i1 + 0) * f1);
		tesselator.addVertexWithUV(i + 0, j + 0,
				zLevel, (k + 0) * f,
				(i1 + 0) * f1);
		tesselator.draw();
	}
	
	public static String excutePost(String targetURL, String data) {
		HttpsURLConnection httpsurlconnection = null;
		try {
			try {
				URL url = new URL(targetURL);
				httpsurlconnection = (HttpsURLConnection) url.openConnection();
				httpsurlconnection.setRequestMethod("POST");
				httpsurlconnection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				httpsurlconnection.setRequestProperty("Content-Type",
						Integer.toString(data.getBytes().length));
				httpsurlconnection.setRequestProperty("Content-Language",
						"en-US");
				httpsurlconnection.setUseCaches(false);
				httpsurlconnection.setDoInput(true);
				httpsurlconnection.setDoOutput(true);
				httpsurlconnection.connect();
				DataOutputStream dataoutputstream = new DataOutputStream(
						httpsurlconnection.getOutputStream());
				dataoutputstream.writeBytes(data);
				dataoutputstream.flush();
				dataoutputstream.close();
				InputStream inputstream = httpsurlconnection.getInputStream();
				BufferedReader bufferedreader = new BufferedReader(
						new InputStreamReader(inputstream));
				StringBuffer stringbuffer = new StringBuffer();
				String s2;

				while ((s2 = bufferedreader.readLine()) != null) {
					stringbuffer.append(s2);
					stringbuffer.append('\r');
				}

				bufferedreader.close();
				String s3 = stringbuffer.toString();
				String s4 = s3;
				return s3;
			} catch (Exception exception) {
				exception.printStackTrace();
			}

			return null;
		} finally {
			if (httpsurlconnection != null) {
				httpsurlconnection.disconnect();
			}
		}
	}
}