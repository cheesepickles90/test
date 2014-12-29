package org.emotionalpatrick.colossus.gui.clickable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;

import org.emotionalpatrick.colossus.files.FileManagerImpl;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.modules.Module;
import org.emotionalpatrick.colossus.utilities.TTFRenderer;
import org.emotionalpatrick.colossus.utilities.gui.GuiUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class PanelBase {

	public int mx;
	public int my;
	private int sx;
	private int sy;
	public int x, y, width, height;
    private int windowClick;
	
	public String name;
	
	private TTFRenderer cf;
	public Module c;
	
	public boolean dragging = false, show = true;

	public ArrayList buttonList = new ArrayList();
	public static ArrayList<PanelBase> listOfDraggables = new ArrayList<PanelBase>();
	
	public Slider sl = null;
	
	/**
	 * @param s - Name of the draggable
	 * @param x - Original x coordinate of the draggable
	 * @param y - Original y coordinate of the draggable
	 * @param w - Original width of the draggable
	 * @param h - Original height of the draggable
	 * */
	public PanelBase(String s, int x, int y, int w, int h) {
		windowClick = 0;
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		name = s;
		init();
		listOfDraggables.add(this);
		loadPanels();
	}
	
	public void draw(int mx, int my){
		dragMe(mx, my);
	
		GuiUtils.drawGradientRect(x, y , x + width, y + 11,0xFF95928C, 0xFF6B6965);
	
		if (cf == null) {
			cf = new TTFRenderer(Wrapper.getMinecraft(), "Tahoma", 17);
		}
	
		Wrapper.getFontRenderer().drawTabTittleString(cf, name, x + 6, y - 1, 0x212121);
		if(!show)
			return;
	
		GuiUtils.drawBorderedRect(x - 0.5f, y - 0.5f , x + width + 0.5f, y + height + 12, 0xff000000,0x8F252525);
		GuiUtils.drawGradientRect(x, y , x + width, y + 11, 0xFF95928C, 0xFF6B6965);
		
		Wrapper.getFontRenderer().drawTabTittleString(cf,name, x + 6, y - 1, 0x212121);
		  
		drawExtras();
	
		for (Object o : buttonList) {
			if (o instanceof Button) {
				Button b = (Button) o;
				b.drawButton(mx, my);
			}
			if (o instanceof Slider) {
				Slider s = (Slider) o;
	
				if (sl == null)
					sl = s;
	
				if (s.getWidth() > sl.getWidth())
					sl = s;
	
//				if (s.getHeight() > height)
//					height = s.getHeight();
	
				s.draw(x, y, mx, my);
	
				if (sl.getWidth() > width)
					width = s.getWidth();
			}
		}
	}

	public void mouseClick(int mx, int my) {
		for (Object o : buttonList) {
			if (o instanceof Button) {
				Button b = (Button) o;
				if (show)
					b.mouseClicked(mx, my);
			}
			if (o instanceof Slider) {
				Slider s = (Slider) o;
				if (show)
					s.mouseClicked(x, y, mx, my);
			}
		}
		
		boolean show = mx > x + width - 16 && mx < x + width - 2 && my > y + 1
				&& my < y + 14;
		
		if (show) {
			this.show = !this.show;
			Wrapper.getMinecraft().sndManager.playSoundFX("random.click", 1f, 1f);
		}

		if (isMouseAround(mx, my)) {
			sx = x;
			sy = y;
			this.mx = mx;
			this.my = my;
			dragging = true;
			if (this.windowClick > 0 && this.windowClick < 40) {
				this.show = !this.show;
			} else {
				this.windowClick = 8;
			}
			Wrapper.getMinecraft().sndManager.playSoundFX("random.click", 1f,
					1f);
		} else
			dragging = false;
	}
	
	public void updateScreen() {
		if (this.windowClick > 0) {
			--this.windowClick;
		}
	}

	public void dragMe(int mx, int my) {
		if (dragging && Mouse.isButtonDown(0)) {
			x += ((mx - x) - (this.mx - sx));
			y += ((my - y) - (this.my - sy));
		} else
			dragging = false;
	}
	
	public boolean isMouseAround(int mx, int my){
		return mx > x && mx < x + width && my > y && my < y + 14;
	}
	
	public void init(){ }
	public void drawExtras(){ }
	
	public void add(Object o){
		buttonList.add(o);
	}
	
	public boolean hasBeenActivated(int i, int j) {
		if (show)
			return i > x && i < x + width && j > y && j < y + 13;
		return i > x && i < x + width && j > y && j < y + height;
	}

	static GuiUtils m;
	
	public void drawReliantButtonOff(int par1, int par2, int par3, int par4) {
		m.drawFHorizontalLine(par1 + 0.5F, par2 + 20F, par3 + 9.4F, 0x8F000000);
		m.drawFVerticalLine(par1 + 21.4F, par2 - 0.5F, par3 + 9F,0x8F000000);
		m.drawFVerticalLine(par1 + 21F, par2 + 8F, par3 + 9.5F,0x8F000000);
		m.drawFVerticalLine(par1 + 20.5F, par2 + 9F, par3 + 9.5F,0x8F000000);
		drawRoundedRect(par1 - 1, par2 - 1, par3+22, par4+10F, 0xFF555555);
		drawRoundedRect(par1, par2, par3+22, par4+10, 0xFF2E2E2E);
		drawRoundedRect(par1 - 1, par2, par3+13, par4 + 9, 0xFF4C4C4C);
		drawRoundedRect(par1, par2+9, par3+21, par4 + 9, 0xFF454545);
		drawRoundedRect(par1, par2+9, par3+21, par4 + 9, 0xFF2E2E2E);
		m.drawGradientRect(par1, par2, par3 + 21, par4 + 9, isMouseAround(mx,my) ? 0xFF3A3A3A : 0xFF404040, isMouseAround(mx,my)  ? 0xFF242424 : 0xFF282828);
	}
   
	public void drawReliantButtonOn(int par1, int par2, int par3, int par4) {
		m.drawFHorizontalLine(par1 + 0.5F, par2 + 20F, par3 + 9.4F, 0x8F000000);
		m.drawFVerticalLine(par1 + 21.4F, par2 - 0.5F, par3 + 9F,0x8F000000);
		m.drawFVerticalLine(par1 + 21F, par2 + 8F, par3 + 9.5F,0x8F000000);
		m.drawFVerticalLine(par1 + 20.5F, par2 + 9F, par3 + 9.5F,0x8F000000);
		drawRoundedRect(par1 - 1, par2 - 1, par3+22, par4+10, 0xFF1B5F8D);
		drawRoundedRect(par1, par2, par3+22, par4+10, 0xFF093B5B);
		drawRoundedRect(par1 - 1, par2, par3+13, par4 + 9, 0xFF1A587E);
		drawRoundedRect(par1, par2+9, par3+21, par4 + 9, 0xFF083553);
		m.drawGradientRect(par1, par2, par3 + 21, par4 + 9, isMouseAround(mx,my)  ? 0xFF014571 : 0xFF014D7E, isMouseAround(mx,my)  ? 0xFF012B47 : 0xFF01304F);
	}
	
	public void drawRoundedRect(float par1, float par2, float par3, float par4, int par5) {
   		par1 *= 2; par3 *= 2; par2 *= 2; par4 *= 2;
   		GL11.glScalef(0.5F, 0.5F, 0.5F);
   		float x = par1; float y = par2; float x1 = par3; float y1 = par4; int borderC = par5;
        m.drawFVerticalLine(x, y + 1, y1 -2, borderC);
        m.drawFVerticalLine(x1 - 1, y + 1, y1 - 2, borderC);
        m.drawFHorizontalLine(x + 2, x1 - 3, y, borderC);
        m.drawFHorizontalLine(x + 2, x1 - 3, y1 -1, borderC);
        m.drawFHorizontalLine(x + 1, x + 1, y + 1, borderC);
        m.drawFHorizontalLine(x1 - 2, x1 - 2, y + 1, borderC);
        m.drawFHorizontalLine(x1 - 2, x1 - 2, y1 - 2, borderC);
        m.drawFHorizontalLine(x + 1, x + 1, y1 - 2, borderC);
        m.drawFRect(x + 1, y + 1, x1 - 1, y1 - 1, par5);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
   	}

	public static void savePanels() {
		try {
			File file = new File(FileManagerImpl.getColossusDir(),
					"panels.txt");
			PrintWriter printwriter = new PrintWriter(new FileWriter(file));
			
			printwriter.println("#Colosus Panel Config" + "\r");
			for (PanelBase d : listOfDraggables) {
				printwriter.println(d.name + ":" + d.x + ":" + d.y);

			}
			printwriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadPanels() {
		try {
			File file = new File(FileManagerImpl.getColossusDir(),
					"panels.txt");
			if (!file.exists())
				return;
			BufferedReader bufferedreader = new BufferedReader(new FileReader(
					file));
			for (String s = ""; (s = bufferedreader.readLine()) != null;) {
				try {
					for (int x = 0; x < listOfDraggables.size(); x++) {
						if (s.split(":")[0]
								.startsWith(listOfDraggables.get(x).name)) {
							listOfDraggables.get(x).x = Integer.parseInt(s
									.split(":")[1]);
							listOfDraggables.get(x).y = Integer.parseInt(s
									.split(":")[2]);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			bufferedreader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
