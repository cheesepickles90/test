package org.emotionalpatrick.colossus.modules;

import java.util.Random;

import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.main.Wrapper;
import org.emotionalpatrick.colossus.utilities.rendering.GL11Helper;

public class Search extends Module {
	
	public SearchBlock searchBlock[];
	
	private int searchID = 6969;
	private int size = 0;
	private int refreshRate = 10;
	private int radius = 64;

	public float r, g, b;
	Random rand = new Random();

	public Search() {
		super("Search", ".s", "Search for blocks", "Emotional Patrick", "World");
		searchBlock = new SearchBlock[10000000];
		rand = new Random();
	}
	
	@Override
	public void externalCommand (String s) {
		if (s.toLowerCase().startsWith(".search")) {
			String args[] = s.split(" ");
			try {
				
				try {
					int id = Integer.parseInt(args[1]);
					setSearchID(id);
				} catch (NumberFormatException nfe) {
					int b = Helper.blockNameToID(args[1]);
					setSearchID(b);
				}
				
				int s1 = searchID;
				getRandomColor();
				
				if (!args[1].equalsIgnoreCase("clear") && !args[1].startsWith("radius")) {
					Helper.addChat("Searching for \"" + Helper.blockIDtoName(s1).toLowerCase() + "\" - (ID #" + s1 + ")");
				}
				if (args[1].equalsIgnoreCase("clear")) {
					setSearchID(1337);
					Helper.addChat("Cleared search!");
				}
				if (args[1].startsWith("radius")) {
					int radius = Integer.parseInt(args[2]);
					setRadius(radius);
					checkSearch();
					Helper.addChat("Search radius set to (" + radius + ").");
				}
			} catch (Exception err) {
				Helper.addChat("Invalid syntax, .search (block name or ID/clear/radius)");
			}
		}
	}

	@Override
	public void onGlobalRender(float f) {
		refreshRate--;
		if (refreshRate == 0) {
			checkSearch();
			refreshRate = 40;
		}
		
		double playerX = Wrapper.getPlayer().lastTickPosX
				+ (Wrapper.getPlayer().posX - Wrapper.getPlayer().lastTickPosX)
				* Wrapper.getMinecraft().timer.renderPartialTicks;
		double playerY = Wrapper.getPlayer().lastTickPosY
				+ (Wrapper.getPlayer().posY - Wrapper.getPlayer().lastTickPosY)
				* Wrapper.getMinecraft().timer.renderPartialTicks;
		double playerZ = Wrapper.getPlayer().lastTickPosZ
				+ (Wrapper.getPlayer().posZ - Wrapper.getPlayer().lastTickPosZ)
				* Wrapper.getMinecraft().timer.renderPartialTicks;
		
		for (int cur = 0; cur < size; cur++) {
			if (searchBlock[cur].id == getSearchID()) {
				GL11Helper.drawBlockESP(searchBlock[cur].posX,
						searchBlock[cur].posY, searchBlock[cur].posZ, r, g, b);
			}
		}
	}

	private void checkSearch() {
		size = 0;
		for (int y = 0; y < 256; y++) {
			for (int x = 0; x < getRadius(); x++) {
				for (int z = 0; z < getRadius(); z++) {
					int cX = (int) Wrapper.getPlayer().posX - getRadius() / 2 + x;
					int cY = y;
					int cZ = (int) Wrapper.getPlayer().posZ - getRadius() / 2 + z;
					int id = Wrapper.getWorld().getBlockId(cX, cY, cZ);
					if (id == searchID) {
						searchBlock[size++] = new SearchBlock(cX, cY, cZ, id);
					}
				}
			}
		}
	}
	
	private void getRandomColor() {
		this.r = rand.nextFloat();
		this.g = rand.nextFloat();
		this.b = rand.nextFloat();
	}
	
	public int getSearchID() {
		return searchID;
	}

	public void setSearchID(int searchID) {
		this.searchID = searchID;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	class SearchBlock {

		public int posX;
		public int posY;
		public int posZ;
		public int id;

		public SearchBlock(int x, int y, int z, int id) {
			posX = x;
			posY = y;
			posZ = z;
			this.id = id;
		}
	}
}
