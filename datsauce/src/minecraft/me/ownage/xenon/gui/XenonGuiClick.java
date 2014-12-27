package me.ownage.xenon.gui;

import java.util.ArrayList;




import me.ownage.xenon.gui.elements.XWindow;
import me.ownage.xenon.gui.windows.*;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.XenonUtils;
import net.minecraft.src.GuiScreen;

public class XenonGuiClick extends GuiScreen
{
	public static ArrayList<XWindow> windows = new ArrayList<XWindow>();
	public static ArrayList<XWindow> unFocusedWindows = new ArrayList<XWindow>();
	public static boolean isDark = false;
	
	public XWindow guiHub = new WindowHub();
	public XWindow player = new WindowPlayer().init();
	public XWindow world = new WindowWorld().init();
	public XWindow esp = new WindowRender().init();
	public XWindow aura = new WindowAura().init();
	public XWindow values = new WindowValues().init();
	public XWindow info = new WindowInfo();
	public XWindow radar = new WindowRadar();
	
	public void initGui()
	{
		Xenon.getFileManager().loadGuiSettings();
		guiHub.setOpen(true);
	}
	
	public void onGuiClosed()
	{
		Xenon.getFileManager().saveGuiSettings();
	}
	
	public static void sendPanelToFront(XWindow window)
	{
		if(windows.contains(window))
		{
			int panelIndex = windows.indexOf(window);
			windows.remove(panelIndex);
			windows.add(windows.size(), window);
		}
	}
	
	public static XWindow getFocusedPanel()
	{
		return windows.get(windows.size() - 1);
	}
	
	public void drawScreen(int x, int y, float f)
	{
		for(XWindow window: windows)
		{
			window.draw(x, y);
		}
		
		/*XenonUtils.drawGradientBorderedRect(1, height - 10, 25, height - 1, 0.5F, 0xFF666666, 0xFF999999, 0xFF777777);
		XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, isDark ? "Light" : "Dark", isDark ? 3 : 3.5, height - 11.5, 0xFFFFFF);*/
	}
	
	public void mouseClicked(int x, int y, int button)
	{
		try
		{
			for(XWindow window: windows)
			{
				window.mouseClicked(x, y, button);
			}
		}catch(Exception e) {}
		
		/*if(x >= 1 && y >= height - 10 && x <= 25 && y <= height - 1) {
			mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			isDark = !isDark;
		}*/
	}
	
	public void mouseMovedOrUp(int x, int y, int button)
	{
		for(XWindow window: windows)
		{
			window.mouseMovedOrUp(x, y, button);
		}
	}
	
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
