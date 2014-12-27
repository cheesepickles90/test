package me.ownage.xenon.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import me.ownage.xenon.main.Xenon;
import net.minecraft.client.Minecraft;

import org.lwjgl.Sys;




public class XenonUpdater extends Thread implements Runnable
{
	public XenonUpdater(File mcDir)
	{
		macDir = mcDir;
	}

	private File macDir;
	private boolean notWindows = false;
	public double percentDone = 0.0F;

	public void run()
	{
		try
		{
			URL dlURL = (new URL("http://ownage.c12craft.com/xenon/XenonUpdate.jar"));
			URLConnection dlConnection = dlURL.openConnection();
			dlConnection.connect();
			double fileSize = dlConnection.getContentLength();
			BufferedInputStream in = new BufferedInputStream(dlURL.openStream());
			OutputStream out = new FileOutputStream(macDir + "/bin/XenonUpdate.jar");

			int count;
			byte data[] = new byte[1024];

			while ((count = in.read(data)) != -1)
			{
				Xenon.xu.isUpdating = true;
				out.write(data, 0, count);
				double newFileSize = (double)(new File(macDir + "/bin/XenonUpdate.jar")).length();
				percentDone = ((newFileSize / fileSize) * 100);
			}

			out.flush();
			out.close();
			in.close();
		}catch (Exception e) {}

		Xenon.xu.downloadedUpdate = true;
		String s = System.getProperty("os.name").toLowerCase();
		String os = "unknown";
		if (s.contains("win"))
		{
			os = "win";
		}
		if (s.contains("mac"))
		{
			os = "mac";
		}
		if (s.contains("linux"))
		{
			os = "linux";
		}
		if (s.contains("unix"))
		{
			os = "linux";
		}
		if(os == "win")
		{
			Sys.openURL((new StringBuilder()).append("file://").append(Minecraft.getMinecraftDir().getAbsolutePath()).append("/bin/").toString());
		}
		if(os == "mac" || os == "linux")
		{
			notWindows = true;
		}
	}
}
