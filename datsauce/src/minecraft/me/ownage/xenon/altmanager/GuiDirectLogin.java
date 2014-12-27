package me.ownage.xenon.altmanager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.Session;

public class GuiDirectLogin extends GuiScreen
{
	public GuiScreen parent;
	public GuiTextField usernameBox;
	public GuiPasswordField passwordBox;
	public GuiTextField sessionBox;
	
	public GuiDirectLogin(GuiScreen paramScreen)
	{
		this.parent = paramScreen;
	}
	
	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96 + 12, "Login"));
		controlList.add(new GuiButton(2, width / 2 - 100, height / 4 + 96 + 36, "Back"));
		usernameBox = new GuiTextField(fontRenderer, width / 2 - 100, 76 - 25, 200, 20);
		passwordBox = new GuiPasswordField(fontRenderer, width / 2 - 100, 116 - 25, 200, 20);
		sessionBox = new GuiTextField(fontRenderer, width / 2- 100, 156 - 25, 200, 20);
		usernameBox.setMaxStringLength(200);
		passwordBox.setMaxStringLength(128);
		sessionBox.setMaxStringLength(257);
	}
	
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	public void updateScreen()
	{
		usernameBox.updateCursorCounter();
		passwordBox.updateCursorCounter();
		sessionBox.updateCursorCounter();
	}
	
	public void mouseClicked(int x, int y, int b)
	{
		usernameBox.mouseClicked(x, y, b);
		passwordBox.mouseClicked(x, y, b);
		sessionBox.mouseClicked(x, y, b);
		super.mouseClicked(x, y, b);
	}
	
	public void actionPerformed(GuiButton button)
	{
		if(button.id == 1)
		{
			if(!usernameBox.getText().trim().isEmpty() && !passwordBox.getText().trim().isEmpty())
			{
				try
				{
					String post = (new StringBuilder("user=")).append(URLEncoder.encode(usernameBox.getText().trim(), "UTF-8")).append("&password=").append(URLEncoder.encode(passwordBox.getText().trim(), "UTF-8")).append("&version=").append(13).toString();
					String returnInf = Manager.excutePost("https://login.minecraft.net/", post);
					String[] parsedInfo = returnInf.split(":");
	                mc.session = new Session(parsedInfo[2].trim(), parsedInfo[3].trim());
	                Manager.altScreen.dispErrorString = "";
				}catch(Exception error)
				{
					Manager.altScreen.dispErrorString = "".concat("\247cBad Login \2477(").concat(usernameBox.getText()).concat(")");
				}
			}else
			if(!usernameBox.getText().trim().isEmpty() && !sessionBox.getText().trim().isEmpty())
			{
                mc.session = new Session(usernameBox.getText().trim(), sessionBox.getText().trim());
                Manager.altScreen.dispErrorString = "";
			}else
			if(!usernameBox.getText().trim().isEmpty())
			{
				mc.session = new Session(usernameBox.getText().trim(), "-");
				Manager.altScreen.dispErrorString = "";
			}
            
			mc.displayGuiScreen(parent);
		}else
		if(button.id == 2)
		{
			mc.displayGuiScreen(parent);
		}
	}
	
	public static String excutePost(String s, String s1)
	{
		HttpsURLConnection httpsurlconnection = null;

		try
		{
			try
			{
				URL url = new URL(s);
				httpsurlconnection = (HttpsURLConnection)url.openConnection();
				httpsurlconnection.setRequestMethod("POST");
				httpsurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				httpsurlconnection.setRequestProperty("Content-Type", Integer.toString(s1.getBytes().length));
				httpsurlconnection.setRequestProperty("Content-Language", "en-US");
				httpsurlconnection.setUseCaches(false);
				httpsurlconnection.setDoInput(true);
				httpsurlconnection.setDoOutput(true);
				httpsurlconnection.connect();
				DataOutputStream dataoutputstream = new DataOutputStream(httpsurlconnection.getOutputStream());
				dataoutputstream.writeBytes(s1);
				dataoutputstream.flush();
				dataoutputstream.close();
				InputStream inputstream = httpsurlconnection.getInputStream();
				BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
				StringBuffer stringbuffer = new StringBuffer();
				String s2;

				while ((s2 = bufferedreader.readLine()) != null)
				{
					stringbuffer.append(s2);
					stringbuffer.append('\r');
				}

				bufferedreader.close();
				String s3 = stringbuffer.toString();
				String s4 = s3;
				return s3;
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}

			return null;
		}finally 
		{
			if (httpsurlconnection != null)
			{
				httpsurlconnection.disconnect();
			}
		}
	}
	
	protected void keyTyped(char c, int i)
	{
		usernameBox.textboxKeyTyped(c, i);
		passwordBox.textboxKeyTyped(c, i);
		sessionBox.textboxKeyTyped(c, i);
		if(c == '\t')
		{
			if(usernameBox.isFocused())
			{
				usernameBox.setFocused(false);
				passwordBox.setFocused(true);
				sessionBox.setFocused(false);
			}else
			if(passwordBox.isFocused())
			{
				usernameBox.setFocused(false);
				passwordBox.setFocused(false);
				sessionBox.setFocused(true);
			}else
			if(sessionBox.isFocused())
			{
				usernameBox.setFocused(true);
				passwordBox.setFocused(false);
				sessionBox.setFocused(false);
			}
		}
		if(c == '\r')
		{
			actionPerformed((GuiButton) controlList.get(0));
		}
	}
	
	public void drawScreen(int x, int y, float f)
	{
		drawDefaultBackground();
		drawString(fontRenderer, "Username", width / 2 - 100, 63 - 25, 0xA0A0A0);
		drawString(fontRenderer, "\2474*", width / 2 - 106, 63 - 25, 0xA0A0A0);
		drawString(fontRenderer, "Password", width / 2 - 100, 104 - 25, 0xA0A0A0);
		drawString(fontRenderer, "Session ID (Advanced)", width / 2 - 100, 144 - 25, 0xA0A0A0);
		try{
		usernameBox.drawTextBox();
		passwordBox.drawTextBox();
		sessionBox.drawTextBox();
		}catch(Exception err)
		{
			err.printStackTrace();
		}
		super.drawScreen(x, y, f);
	}
}
