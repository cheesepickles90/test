package me.ownage.xenon.util;

import java.net.URL;
import java.util.Scanner;




import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.Friend;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.StringUtils;

public class ThreadGoldName extends Thread implements Runnable
{
	private EntityOtherPlayerMP thePlayer;
	public boolean isDonator;
	
	public ThreadGoldName(EntityOtherPlayerMP targetPlayer)
	{
		this.thePlayer = targetPlayer;
	}
	
	public void run()
	{
		try
		{
			String s = downloadPage("http://xenon.ownagedev.com/capes/goldname.php?user=".concat(StringUtils.stripControlCodes(thePlayer.username)));
			try{
				if(s.trim().equalsIgnoreCase("true"))
				{
					isDonator = true;
					for(Friend f: Xenon.getFriends().friendsList)
					{
						if(f.getName().equalsIgnoreCase(thePlayer.username))
						{
							return;
						}
					}
					this.thePlayer.username = "\2476".concat(this.thePlayer.username);
				}
			}catch(Exception e){
				System.out.println("[Xenon] Failed to retrieve cape.");
				e.printStackTrace();
			}
		}catch(Exception e) {e.printStackTrace();}
	}
	

	public String downloadPage(String url)
    {
        try
        {
            URL pageURL = new URL(url);
            StringBuilder text = new StringBuilder();
            Scanner scanner = new Scanner(pageURL.openStream(), "utf-8");
            try
            {
                while (scanner.hasNextLine())
                {
                    text.append(scanner.nextLine() + "\n");
                }
            }finally
            {
                scanner.close();
            }
            return text.toString();
        }catch(Exception ex)
        {
            return null;
        }
    }
}
