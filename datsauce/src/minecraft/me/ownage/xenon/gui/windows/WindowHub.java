package me.ownage.xenon.gui.windows;



import me.ownage.xenon.gui.XenonGuiClick;
import me.ownage.xenon.gui.elements.XWindow;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.CustomFont;
import me.ownage.xenon.util.XenonUtils;
import net.minecraft.src.FontRenderer;


public class WindowHub extends XWindow
{
	public WindowHub()
	{
		super("Gui Hub", 2, 2);
	}
	
	@Override
	public void draw(int x, int y)
	{
		super.draw(x, y);
		
		if(isExtended())
		{
			XenonUtils.drawGradientBorderedRect(2 + dragX, getY() + 14 + dragY, getX() + 90 + dragX, (XenonGuiClick.unFocusedWindows.size() * 13) - 2 + dragY, 0.5F, 0xFF666666, 0xFF999999, 0xFF777777);
			
			int size = 0;
			for(XWindow window: XenonGuiClick.unFocusedWindows)
			{
				if(!window.getTitle().equalsIgnoreCase(this.getTitle()))
				{
					int yPosition = (12 * size) + 18 + dragY;
					XenonUtils.drawGradientBorderedRect(4 + dragX, yPosition, getX() + 88 + dragX, yPosition + 10, 1.0F, 0xFF444444, !window.isOpen() ? 0xFF666666 : 0xFF555555, !window.isOpen() ? 0xFF555555 : 0xFF666666);
					drawCenteredTTFString(Xenon.guiFont, window.getTitle(), 6 + 40 + dragX, yPosition - 1, window.isOpen() ? 0xFFFFFF : 0xBBBBBB);
					size++;
				}
			}
		}
	}
	
    public void drawCenteredTTFString(CustomFont font, String par2Str, double par3, double par4, int par5)
    {
        XenonUtils.drawTTFStringWithShadow(font, par2Str, par3 - font.getStringWidth(par2Str) / 4, par4, par5);
    }
	
	@Override
	public void mouseClicked(int x, int y, int button)
	{
		super.mouseClicked(x, y, button);
		
		int size = 0;
		for(XWindow window: XenonGuiClick.unFocusedWindows)
		{
			if(!window.getTitle().equalsIgnoreCase(this.getTitle()) && this.isExtended())
			{
				int i = (12 * size) + 18;
				if(x >= 4 + dragX && y >= i + dragY && x <= getX() + 88 + dragX && y <= i + 10 + dragY)
				{
					Xenon.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
					window.setOpen(!window.isOpen());
				}
				size++;
			}
		}
	}
}
