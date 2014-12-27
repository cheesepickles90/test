package net.minecraft.src;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.ownage.xenon.hooks.XGuiIngame;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.main.irc.GuiUsernameSet;
import me.ownage.xenon.util.XenonUtils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiChat extends GuiScreen
{
    private String field_73898_b = "";

    /**
     * keeps position of which chat message you will select when you press up, (does not increase for duplicated
     * messages sent immediately after each other)
     */
    private int sentHistoryCursor = -1;
    private boolean field_73897_d = false;
    private boolean field_73905_m = false;
    private int field_73903_n = 0;
    private List field_73904_o = new ArrayList();

    /** used to pass around the URI to various dialogues and to the host os */
    private URI clickedURI = null;

    /** Chat entry field */
    protected GuiTextField inputField;

    /**
     * is the text that appears when you press the chat key and the input box appears pre-filled
     */
    private String defaultInputFieldText = "";

    public GuiChat() {}

    public GuiChat(String par1Str)
    {
        this.defaultInputFieldText = par1Str;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        this.inputField = new GuiTextField(this.fontRenderer, 4, this.height - 12, this.width - 4, 12);
        this.inputField.setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        this.mc.ingameGUI.getChatGUI().resetScroll();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.inputField.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.field_73905_m = false;

        if (par2 == 15)
        {
            this.completePlayerName();
        }
        else
        {
            this.field_73897_d = false;
        }

        if (par2 == 1)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (par2 == 28)
        {
            String var3 = this.inputField.getText().trim();

            if (var3.length() > 0)
            {
                this.mc.ingameGUI.getChatGUI().addToSentMessages(var3);

                if(Xenon.ircDisplayToggle) 
                {
                	if(var3.startsWith("."))
                	{
                		Xenon.getCommandManager().runCommands(var3);
                	}else if(!var3.startsWith("/"))
                	{
                		try {
	                		if(Xenon.irc.isChatReady() && !var3.trim().startsWith("!")) {
	                			Xenon.irc.sendMessage(Xenon.ircChannel, var3);
	                			
	                			Xenon.addIRCMessage(Xenon.irc.getNick(), var3, false);
	                		}
                		}catch(Exception error) {
                			error.printStackTrace();
                		}
                	}
                }else
                if (!this.mc.handleClientCommand(var3))
                {
                    this.mc.thePlayer.sendChatMessage(var3);
                }
            }

            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (par2 == 200)
        {
            this.getSentHistory(-1);
        }
        else if (par2 == 208)
        {
            this.getSentHistory(1);
        }
        else if (par2 == 201)
        {
            this.mc.ingameGUI.getChatGUI().scroll(19);
        }
        else if (par2 == 209)
        {
            this.mc.ingameGUI.getChatGUI().scroll(-19);
        }
        else
        {
            this.inputField.textboxKeyTyped(par1, par2);
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();

        if (var1 != 0)
        {
            if (var1 > 1)
            {
                var1 = 1;
            }

            if (var1 < -1)
            {
                var1 = -1;
            }

            if (!isShiftKeyDown())
            {
                var1 *= 7;
            }

            this.mc.ingameGUI.getChatGUI().scroll(var1);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
    	mc.ingameGUI.getChatGUI().mouseClicked(par1, par2, par3);
        if (par3 == 0 && this.mc.gameSettings.chatLinks)
        {
            ChatClickData var4 = this.mc.ingameGUI.getChatGUI().func_73766_a(Mouse.getX(), Mouse.getY());

            if (var4 != null)
            {
                URI var5 = var4.getURI();

                if (var5 != null)
                {
                    if (this.mc.gameSettings.chatLinksPrompt)
                    {
                        this.clickedURI = var5;
                        this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, var4.getClickedUrl(), 0));
                    }
                    else
                    {
                        this.func_73896_a(var5);
                    }

                    return;
                }
            }
        }
        
        int x = par1;
        int y = par2;
        if(x >= 3 && y >= height - 30 && x <= 25 && y <= height - 18)
        {
        	mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
        	Xenon.ircDisplayToggle = false;
        }
        if(x >= 26 && y >= height - 30 && x <= 42 && y <= height - 18)
        {
        	mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
        	Xenon.ircDisplayToggle = true;
        }
        if(x >= 45 && y >= height - 30 && x <= 72 && y <= height - 18)
        {
        	mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
        	mc.thePlayer.sendChatMessage("/home");
        }
        if(x >= 75 && y >= height - 30 && x <= 104 && y <= height - 18)
        {
        	mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
        	mc.thePlayer.sendChatMessage("/spawn");
        }

        this.inputField.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }

    public void mouseMovedOrUp(int par1, int par2, int par3)
    {
    	mc.ingameGUI.getChatGUI().mouseMovedOrUp(par1, par2, par3);
    }
    
    public void confirmClicked(boolean par1, int par2)
    {
        if (par2 == 0)
        {
            if (par1)
            {
                this.func_73896_a(this.clickedURI);
            }

            this.clickedURI = null;
            this.mc.displayGuiScreen(this);
        }
    }

    private void func_73896_a(URI par1URI)
    {
        try
        {
            Class var2 = Class.forName("java.awt.Desktop");
            Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            var2.getMethod("browse", new Class[] {URI.class}).invoke(var3, new Object[] {par1URI});
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
        }
    }

    /**
     * Autocompletes player name
     */
    public void completePlayerName()
    {
        String var3;

        if (this.field_73897_d)
        {
            this.inputField.deleteFromCursor(this.inputField.func_73798_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());

            if (this.field_73903_n >= this.field_73904_o.size())
            {
                this.field_73903_n = 0;
            }
        }
        else
        {
            int var1 = this.inputField.func_73798_a(-1, this.inputField.getCursorPosition(), false);
            this.field_73904_o.clear();
            this.field_73903_n = 0;
            String var2 = this.inputField.getText().substring(var1).toLowerCase();
            var3 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
            this.func_73893_a(var3, var2);

            if (this.field_73904_o.isEmpty())
            {
                return;
            }

            this.field_73897_d = true;
            this.inputField.deleteFromCursor(var1 - this.inputField.getCursorPosition());
        }

        if (this.field_73904_o.size() > 1)
        {
            StringBuilder var4 = new StringBuilder();

            for (Iterator var5 = this.field_73904_o.iterator(); var5.hasNext(); var4.append(var3))
            {
                var3 = (String)var5.next();

                if (var4.length() > 0)
                {
                    var4.append(", ");
                }
            }

            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(var4.toString(), 1);
        }

        this.inputField.writeText((String)this.field_73904_o.get(this.field_73903_n++));
    }

    private void func_73893_a(String par1Str, String par2Str)
    {
        if (par1Str.length() >= 1)
        {
            this.mc.thePlayer.sendQueue.addToSendQueue(new Packet203AutoComplete(par1Str));
            this.field_73905_m = true;
        }
    }

    /**
     * input is relative and is applied directly to the sentHistoryCursor so -1 is the previous message, 1 is the next
     * message from the current cursor position
     */
    public void getSentHistory(int par1)
    {
        int var2 = this.sentHistoryCursor + par1;
        int var3 = this.mc.ingameGUI.getChatGUI().getSentMessages().size();

        if (var2 < 0)
        {
            var2 = 0;
        }

        if (var2 > var3)
        {
            var2 = var3;
        }

        if (var2 != this.sentHistoryCursor)
        {
            if (var2 == var3)
            {
                this.sentHistoryCursor = var3;
                this.inputField.setText(this.field_73898_b);
            }
            else
            {
                if (this.sentHistoryCursor == var3)
                {
                    this.field_73898_b = this.inputField.getText();
                }

                this.inputField.setText((String)this.mc.ingameGUI.getChatGUI().getSentMessages().get(var2));
                this.sentHistoryCursor = var2;
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
    	if(mc.ingameGUI.getChatGUI().dragging)
    	{
    		mc.ingameGUI.getChatGUI().chatDragged(par1, par2);
    	}
        drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        this.inputField.drawTextBox();
    	if(!XGuiIngame.hideGui) {
	    	if(!Xenon.ircDisplayToggle) {
	    		drawRoundedRect(3, this.height - 30, 42, this.height - 18, 0xFF000000, 0x880587E6,0x8F333333, 5.5F);	
	    	} else {
	    		drawRoundedRect(3, this.height - 30, 42, this.height - 18, 0xFF000000, 0x8F333333,0x880587E6, 5.5F);
	    	}
	    	
	    	XenonUtils.drawRoundedRect(45, height - 30, 72, height - 18, 0xFF000000, 0x8F333333);
	    	XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, "Home", 47.5, height - 30, 0xFFFFFF);
	    	
	    	XenonUtils.drawRoundedRect(75, height - 30, 104, height - 18, 0xFF000000, 0x8F333333);
	    	XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, "Spawn", 76.5, height - 30, 0xFFFFFF);
	    	
	    	GL11.glScalef(0.5F, 0.5F, 0.5F);
	    	drawVerticalLine(47, this.height * 2 - 30 * 2, this.height * 2 - 18 * 2, 0xFF000000);
	    	GL11.glScalef(2F, 2F, 2F);
	    	XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, "Chat", 4, this.height - 30, 0xFFFFFF);
	    	XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, "IRC", 26, this.height - 30, 0xFFFFFF);
    	}
        super.drawScreen(par1, par2, par3);
    }
    
	public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int halfColor, int halfColor2, float add)
	{
    	x *= 2; x1 *= 2; y *= 2; y1 *= 2;
    	GL11.glScalef(0.5F, 0.5F, 0.5F);
        XenonUtils.drawRect(x + 1, y + 1, ((x1) / 2) + add, y1 - 1, halfColor);
        XenonUtils.drawRect(((x1 - 1) / 2) + add, y + 1, x1 - 1, y1 - 1, halfColor2);
        XenonUtils.drawVLine(x, y + 1, y1 -2, borderC);
        XenonUtils.drawVLine(x1 - 1, y + 1, y1 - 2, borderC);
        XenonUtils.drawHLine(x + 2, x1 - 3, y, borderC);
        XenonUtils.drawHLine(x + 2, x1 - 3, y1 -1, borderC);
        XenonUtils.drawHLine(x + 1, x + 1, y + 1, borderC);
        XenonUtils.drawHLine(x1 - 2, x1 - 2, y + 1, borderC);
        XenonUtils.drawHLine(x1 - 2, x1 - 2, y1 - 2, borderC);
        XenonUtils.drawHLine(x + 1, x + 1, y1 - 2, borderC);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

    public void func_73894_a(String[] par1ArrayOfStr)
    {
        if (this.field_73905_m)
        {
            this.field_73904_o.clear();
            String[] var2 = par1ArrayOfStr;
            int var3 = par1ArrayOfStr.length;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                String var5 = var2[var4];

                if (var5.length() > 0)
                {
                    this.field_73904_o.add(var5);
                }
            }

            if (this.field_73904_o.size() > 0)
            {
                this.field_73897_d = true;
                this.completePlayerName();
            }
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
