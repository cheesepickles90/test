// TODO: Colossus
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ScreenShotHelper extends Thread
{
	private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

	/**
	 * Takes a screenshot and saves it to the screenshots directory. Returns the filename of the screenshot.
	 */
	public static String saveScreenshot(File par0File, int par1, int par2)
	{
		return func_35879_a(par0File, null, par1, par2);
	}

	public static String func_35879_a(File par0File, String par1Str, int width, int height)
	{
		try
		{
			File file = new File(par0File, "screenshots");
			file.mkdir();

			String s = (new StringBuilder()).append("").append(dateFormat.format(new Date())).toString();
			File file1;

			if (par1Str == null)
			{
				for (int i = 1; (file1 = new File(file, (new StringBuilder()).append(s).append(i != 1 ? (new StringBuilder()).append("_").append(i).toString() : "").append(".png").toString())).exists(); i++) { }
			}
			else
			{
				file1 = new File(file, par1Str);
			}
			GL11.glReadBuffer(GL11.GL_FRONT);
			ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
			GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer );
			(new ScreenShotHelper(buffer,width,height,file1)).start();
			return (new StringBuilder()).append("Saved screenshot as ").append(file1.getName()).toString();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			return (new StringBuilder()).append("Failed to save: ").append(exception).toString();
		}
	}

	//Object stuff
	private ByteBuffer buffer;
	private int width;
	private int height;
	private File file;

	private ScreenShotHelper(ByteBuffer buffer, int width, int height, File file)
	{
		this.buffer = buffer;
		this.width = width;
		this.height = height;
		this.file = file;
	}

	public void run()
	{
		BufferedImage bufferedimage = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		int[] bArray = ((DataBufferInt)bufferedimage.getRaster().getDataBuffer()).getData();
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
			{
				int color = buffer.getInt((x+(width*y)) * 4);
				color = ((color & 0xFF00) + ((color & 0xFF) << 16) + ((color & 0xFF0000) >> 16)); //Herp derp Java images are stored BGR internally.
				bArray[x+(width*(height-y-1))] = color;
			}
		try {
			ImageIO.write(bufferedimage, "png", file);
		} catch (IOException ex) {}
	}
}