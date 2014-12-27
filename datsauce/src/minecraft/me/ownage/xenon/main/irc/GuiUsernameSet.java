package me.ownage.xenon.main.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import org.lwjgl.input.Keyboard;

import me.ownage.xenon.altmanager.GuiPasswordField;
import me.ownage.xenon.irc.IRCHandler;
import me.ownage.xenon.main.Xenon;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.Session;

public class GuiUsernameSet extends GuiScreen
{
	public GuiTextField usernameBox;
	public GuiPasswordField passwordBox;
	
	public static File dir = new File(Minecraft.getAppDir("minecraft") + File.separator + "Xenon" + File.separator + "irclogin.txt");
	
	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		controlList.add(new GuiButton(1, width / 2 - 100, this.height / 6 + 158, "Set"));
		usernameBox = new GuiTextField(fontRenderer, width / 2 - 100, 96 - 20, 200, 20);
		passwordBox = new GuiPasswordField(fontRenderer, width / 2 - 100, 136 - 20, 200, 20);
		usernameBox.setMaxStringLength(200);
		passwordBox.setMaxStringLength(128);
	}
	
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	public void updateScreen()
	{
		usernameBox.updateCursorCounter();
		passwordBox.updateCursorCounter();
	}
	
	public void mouseClicked(int x, int y, int b)
	{
		usernameBox.mouseClicked(x, y, b);
		passwordBox.mouseClicked(x, y, b);
		super.mouseClicked(x, y, b);
	}
	
	public void actionPerformed(GuiButton button)
	{
		if(button.id == 1)
		{
			try
			{
				BufferedWriter out = new BufferedWriter(new FileWriter(dir));
				out.write(usernameBox.getText() + ":" + passwordBox.getText());
				out.close();
				mc.displayGuiScreen(null);
				String usernm = getSavedUsername();
				String passwd = getSavedPassword();
				
				Xenon.irc = new IRCHandler(Xenon.ircServer, Xenon.ircPort, Xenon.ircChannel, usernm, passwd, Xenon.rch, Xenon.mh);
				Xenon.irc.connect();
			}catch(Exception err) {}
		}
	}
	
	public static String getSavedUsername() throws IOException
	{
	    BufferedReader reader = new BufferedReader(new FileReader(dir));
	    String line = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    String ls = System.getProperty("line.separator");
	    while((line = reader.readLine()) != null )
	    {
	        stringBuilder.append(line);
	        stringBuilder.append(ls);
	    }
	    
	    return stringBuilder.toString().split(":")[0];
	}
	
	public static String getSavedPassword() throws IOException
	{
	    BufferedReader reader = new BufferedReader(new FileReader(dir));
	    String line = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    String ls = System.getProperty("line.separator");
	    while((line = reader.readLine()) != null )
	    {
	        stringBuilder.append(line);
	        stringBuilder.append(ls);
	    }
	    
	    return stringBuilder.toString().split(":")[1];
	}
	
	protected void keyTyped(char c, int i)
	{
		usernameBox.textboxKeyTyped(c, i);
		passwordBox.textboxKeyTyped(c, i);
		if(c == '\t')
		{
			if(usernameBox.isFocused())
			{
				usernameBox.setFocused(false);
				passwordBox.isFocused = true;
			}else
			if(passwordBox.isFocused())
			{
				usernameBox.setFocused(false);
				passwordBox.isFocused = false;
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
		drawCenteredString(fontRenderer, "Set desired username and password for IRC", width / 2, 12, 0xCCCCCC);
		drawCenteredString(fontRenderer, "One time only.", width / 2, 22, 0xCCCCCC);
		drawString(fontRenderer, "Username", width / 2 - 100, 83 - 20, 0xA0A0A0);
		drawString(fontRenderer, "Password (only if registered)", width / 2 - 100, 124 - 20, 0xA0A0A0);
		try{
		usernameBox.drawTextBox();
		passwordBox.drawTextBox();
		}catch(Exception err)
		{
			err.printStackTrace();
		}
		super.drawScreen(x, y, f);
	}
}
