package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.ownage.xenon.hacks.classes.TTFChat;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.Friend;
import me.ownage.xenon.util.XenonUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiNewChat extends Gui
{
	private int startY = 0;

	public int dragX = 0, dragY = 0, lastDragX = 0, lastDragY = 0;
	public boolean dragging = false;

	public void chatDragged(int x, int y)
	{
		dragX = x - lastDragX;
		dragY = y - lastDragY;
	}

	/** The Minecraft instance. */
	private final Minecraft mc;

	/** A list of messages previously sent through the chat GUI */
	private final List sentMessages = new ArrayList();

	/** Chat lines to be displayed in the chat box */
	private final List chatLines = new ArrayList();
	private final List field_96134_d = new ArrayList();
	private int field_73768_d = 0;
	private boolean field_73769_e = false;

	public GuiNewChat(Minecraft par1Minecraft)
	{
		this.mc = par1Minecraft;
	}

	public void drawChat(int par1)
	{
		ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		int width  = sr.getScaledWidth();
		int height = sr.getScaledHeight();

		if (this.mc.gameSettings.chatVisibility != 2)
		{
			byte var2 = 10;
			boolean var3 = false;
			int var4 = 0;
			int var5 = Xenon.ircDisplayToggle ? Xenon.ircLines.size() : this.chatLines.size();
			float var6 = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;

			if (var5 > 0)
			{
				if (this.getChatOpen())
				{
					var2 = 20;
					var3 = true;
				}

				int var7;
				int var9;
				int var12 = 0;

				for (var7 = 0; var7 + this.field_73768_d < (Xenon.ircDisplayToggle ? Xenon.ircLines.size() : this.chatLines.size()) && var7 < var2; ++var7)
				{
					ChatLine var8 = Xenon.ircDisplayToggle ? Xenon.ircLines.get(var7+this.field_73768_d) : (ChatLine)this.chatLines.get(var7 + this.field_73768_d);


					if (var8 != null)
					{
						var9 = par1 - var8.getUpdatedCounter();

						if (var9 < 200 || var3)
						{
							double var10 = (double)var9 / 200.0D;
							var10 = 1.0D - var10;
							var10 *= 10.0D;

							if (var10 < 0.0D)
							{
								var10 = 0.0D;
							}

							if (var10 > 1.0D)
							{
								var10 = 1.0D;
							}

							var10 *= var10;
							var12 = (int)(255.0D * (Hacks.findMod(TTFChat.class).isEnabled() ? 1 : var10));

							if (var3)
							{
								var12 = 255;
							}

							var12 = (int)((float)var12 * var6);
							++var4;

							if (var12 > 3)
							{
								if(mc.thePlayer.getTotalArmorValue() > 0 && !mc.playerController.isInCreativeMode()) GL11.glTranslatef(0.0F, -10F, 0.0F);
								byte var13 = 3;
								int var14 = -var7 * 9;
								startY = var14 - 1;
								String var15 = var8.getChatLineString();
								if(!Hacks.findMod(TTFChat.class).isEnabled())
								{
									drawRect(var13, var14 - 1, var13 + 320 + 4, var14 + 8, var12 / 2 << 24);
								}
								GL11.glEnable(GL11.GL_BLEND);

								if (!this.mc.gameSettings.chatColours)
								{
									var15 = StringUtils.stripControlCodes(var15);
								}

								if(!Hacks.findMod(TTFChat.class).isEnabled())
								{
									this.mc.fontRenderer.drawStringWithShadow(var15, var13 + 1, var14, 16777215 + (var12 << 24));
								}
								if(mc.thePlayer.getTotalArmorValue() > 0 && !mc.playerController.isInCreativeMode()) GL11.glTranslatef(0.0F, 10F, 0.0F);
							}
						}
					}
				}

				if(var12 > 0 && Hacks.findMod(TTFChat.class).isEnabled()) {
					if(getChatOpen()) {
						XenonUtils.drawBorderedRect(3 + dragX, startY - 15 + dragY, 337 + dragX, startY - 3 + dragY, 0xFF000000, 0x7F000000);
						XenonUtils.drawTTFStringWithShadow(Xenon.chatFont, Xenon.ircDisplayToggle ? "IRC" : "Chat", 5 + dragX, startY - 16F + dragY, 0xFFFFFF);
					}
					XenonUtils.drawBorderedRect(3 + dragX, startY - 2 + dragY, 337 + dragX, 11 + dragY, 0xFF000000, 0x7F000000);
				}

				if(Hacks.findMod(TTFChat.class).isEnabled()) {
					for (var7 = 0; var7 + this.field_73768_d < (Xenon.ircDisplayToggle ? Xenon.ircLines.size() : this.chatLines.size()) && var7 < var2; ++var7)
					{
						ChatLine var8 = Xenon.ircDisplayToggle ? Xenon.ircLines.get(var7+this.field_73768_d) : (ChatLine)this.chatLines.get(var7 + this.field_73768_d);

						if (var8 != null)
						{
							var9 = par1 - var8.getUpdatedCounter();

							if (var9 < 200 || var3)
							{
								double var10 = (double)var9 / 200.0D;
								var10 = 1.0D - var10;
								var10 *= 10.0D;

								if (var10 < 0.0D)
								{
									var10 = 0.0D;
								}

								if (var10 > 1.0D)
								{
									var10 = 1.0D;
								}

								var10 *= var10;
								var12 = (int)(255.0D);

								if (var3)
								{
									var12 = 255;
								}

								var12 = (int)((float)var12 * var6);
								++var4;

								if (var12 > 3)
								{
									String var15 = var8.getChatLineString();
									byte var13 = 3;
									int var14 = -var7 * 9;
									startY = var14 - 1;
									GL11.glEnable(GL11.GL_BLEND);

									if (!this.mc.gameSettings.chatColours)
									{
										var15 = StringUtils.stripControlCodes(var15);
									}

									XenonUtils.drawTTFStringWithShadow(Xenon.chatFont, var15, var13 + 3F + dragX, var14 - 3F + dragY, 0xFFFFFF);
								}
							}
						}
					}
				}

				if (var3)
				{
					var7 = this.mc.fontRenderer.FONT_HEIGHT;
					GL11.glTranslatef(0.0F, (float)var7, 0.0F);
					int var16 = var5 * var7 + var5;
					var9 = var4 * var7 + var4;
					int var17 = this.field_73768_d * var9 / var5;
					int var11 = var9 * var9 / var16;

					if (var16 != var9)
					{
						var12 = var17 > 0 ? 170 : 96;
						int var18 = this.field_73769_e ? 13382451 : 3355562;
						drawRect(0, -var17, 2, -var17 - var11, var18 + (var12 << 24));
						drawRect(2, -var17, 1, -var17 - var11, 13421772 + (var12 << 24));
					}
				}
			}
		}
	}

	/**
	 * Clears the chat.
	 */
	public void clearChatMessages()
	{
		this.field_96134_d.clear();
		this.chatLines.clear();
		this.sentMessages.clear();
	}

	/**
	 * takes a String and prints it to chat
	 */
	public void printChatMessage(String par1Str)
	{
		this.printChatMessageWithOptionalDeletion(par1Str, 0);
	}

	/**
	 * prints the String to Chat. If the ID is not 0, deletes an existing Chat Line of that ID from the GUI
	 */
	public void printChatMessageWithOptionalDeletion(String par1Str, int par2)
	{
		par1Str = par1Str.replace("\247\247", "§").replace("\247r", "\247f");
		for(Friend friend: Xenon.getFriends().friendsList)
		{
			par1Str = par1Str.replace(friend.getName(), "\247a" + friend.getAlias() + "\247f");
		}
		List messages = mc.fontRenderer.listFormattedStringToWidth(par1Str, 320);
		for(Object o : messages) {
			this.func_96129_a((String)o, par2, this.mc.ingameGUI.getUpdateCounter(), false);
			/*ChatLine cLine = new ChatLine(mc.ingameGUI.getUpdateCounter(), (String)o, 0);
			chatLines.add(0, cLine);*/
		}
		System.out.println(par1Str);
		//this.func_96129_a(par1Str, par2, this.mc.ingameGUI.getUpdateCounter(), false);
	}

	public void mouseClicked(int x, int y, int b)
	{
		ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		position[0] = x;
		position[1] = y;
		if(b == 0 && Hacks.findMod(TTFChat.class).isEnabled())
		{
			if(x >= 3 + dragX && y >= startY - 15 + sr.getScaledHeight() - 48 + dragY && x <= 327 + dragX && y <= startY - 3 + sr.getScaledHeight() - 48 + dragY)
			{
				//System.out.println("WAT");
				lastDragX = x - dragX;
				lastDragY = y - dragY;
				dragging = true;
			}
		}
	}

	private int[] position = new int[2];
	public void mouseMovedOrUp(int x, int y, int b)
	{
		position[0] = x;
		position[1] = y;
		//this.mc.fontRenderer.drawString("X: " + position[0] + " Y: " + position[1], 2, 12, 0xFFFFFF);
		//System.out.println(x);
		//System.out.println(y);
		if(b == 0)
		{
			dragging = false;
		}
	}

	private void func_96129_a(String par1Str, int par2, int par3, boolean par4)
	{
		boolean var5 = this.getChatOpen();
		boolean var6 = true;

		if (par2 != 0)
		{
			this.deleteChatLine(par2);
		}

		Iterator var7 = this.mc.fontRenderer.listFormattedStringToWidth(par1Str, MathHelper.floor_float((float)this.func_96126_f() / this.func_96131_h())).iterator();

		while (var7.hasNext())
		{
			String var8 = (String)var7.next();

			if (var5 && this.field_73768_d > 0)
			{
				this.field_73769_e = true;
				this.scroll(1);
			}

			if (!var6)
			{
				var8 = " " + var8;
			}

			var6 = false;
			this.field_96134_d.add(0, new ChatLine(par3, var8, par2));
		}

		while (this.field_96134_d.size() > 100)
		{
			this.field_96134_d.remove(this.field_96134_d.size() - 1);
		}

		if (!par4)
		{
			this.chatLines.add(0, new ChatLine(par3, par1Str.trim(), par2));

			while (this.chatLines.size() > 100)
			{
				this.chatLines.remove(this.chatLines.size() - 1);
			}
		}
	}

	public void func_96132_b()
	{
		this.field_96134_d.clear();
		this.resetScroll();

		for (int var1 = this.chatLines.size() - 1; var1 >= 0; --var1)
		{
			ChatLine var2 = (ChatLine)this.chatLines.get(var1);
			this.func_96129_a(var2.getChatLineString(), var2.getChatLineID(), var2.getUpdatedCounter(), true);
		}
	}

	/**
	 * Gets the list of messages previously sent through the chat GUI
	 */
	public List getSentMessages()
	{
		return this.sentMessages;
	}

	/**
	 * Adds this string to the list of sent messages, for recall using the up/down arrow keys
	 */
	public void addToSentMessages(String par1Str)
	{
		if (this.sentMessages.isEmpty() || !((String)this.sentMessages.get(this.sentMessages.size() - 1)).equals(par1Str))
		{
			this.sentMessages.add(par1Str);
		}
	}

	/**
	 * Resets the chat scroll (executed when the GUI is closed)
	 */
	public void resetScroll()
	{
		this.field_73768_d = 0;
		this.field_73769_e = false;
	}

	/**
	 * Scrolls the chat by the given number of lines.
	 */
	public void scroll(int par1)
	{
		this.field_73768_d += par1;
		int var2 = this.field_96134_d.size();

		if (this.field_73768_d > var2 - this.func_96127_i())
		{
			this.field_73768_d = var2 - this.func_96127_i();
		}

		if (this.field_73768_d <= 0)
		{
			this.field_73768_d = 0;
			this.field_73769_e = false;
		}
	}

	public ChatClickData func_73766_a(int par1, int par2)
	{
		if (!this.getChatOpen())
		{
			return null;
		}
		else
		{
			ScaledResolution var3 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
			int var4 = var3.getScaleFactor();
			float var5 = this.func_96131_h();
			int var6 = par1 / var4 - 3;
			int var7 = par2 / var4 - 25;
			var6 = MathHelper.floor_float((float)var6 / var5);
			var7 = MathHelper.floor_float((float)var7 / var5);

			if (var6 >= 0 && var7 >= 0)
			{
				int var8 = Math.min(this.func_96127_i(), this.field_96134_d.size());

				if (var6 <= MathHelper.floor_float((float)this.func_96126_f() / this.func_96131_h()) && var7 < this.mc.fontRenderer.FONT_HEIGHT * var8 + var8)
				{
					int var9 = var7 / (this.mc.fontRenderer.FONT_HEIGHT + 1) + this.field_73768_d;
					return new ChatClickData(this.mc.fontRenderer, (ChatLine)this.field_96134_d.get(var9), var6, var7 - (var9 - this.field_73768_d) * this.mc.fontRenderer.FONT_HEIGHT + var9);
				}
				else
				{
					return null;
				}
			}
			else
			{
				return null;
			}
		}
	}

	/**
	 * Adds a message to the chat after translating to the client's locale.
	 */
	public void addTranslatedMessage(String par1Str, Object ... par2ArrayOfObj)
	{
		this.printChatMessage(StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj));
	}

	/**
	 * @return {@code true} if the chat GUI is open
	 */
	public boolean getChatOpen()
	{
		return this.mc.currentScreen instanceof GuiChat;
	}

	/**
	 * finds and deletes a Chat line by ID
	 */
	public void deleteChatLine(int par1)
	{
		Iterator var2 = this.field_96134_d.iterator();
		ChatLine var3;

		do
		{
			if (!var2.hasNext())
			{
				var2 = this.chatLines.iterator();

				do
				{
					if (!var2.hasNext())
					{
						return;
					}

					var3 = (ChatLine)var2.next();
				}
				while (var3.getChatLineID() != par1);

				var2.remove();
				return;
			}

			var3 = (ChatLine)var2.next();
		}
		while (var3.getChatLineID() != par1);

		var2.remove();
	}

    public int func_96126_f()
    {
        return func_96128_a(this.mc.gameSettings.chatWidth);
    }

    public int func_96133_g()
    {
        return func_96130_b(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
    }

    public float func_96131_h()
    {
        return this.mc.gameSettings.chatScale;
    }

    public static final int func_96128_a(float par0)
    {
        short var1 = 320;
        byte var2 = 40;
        return MathHelper.floor_float(par0 * (float)(var1 - var2) + (float)var2);
    }

    public static final int func_96130_b(float par0)
    {
        short var1 = 180;
        byte var2 = 20;
        return MathHelper.floor_float(par0 * (float)(var1 - var2) + (float)var2);
    }

    public int func_96127_i()
    {
        return this.func_96133_g() / 9;
    }
}
