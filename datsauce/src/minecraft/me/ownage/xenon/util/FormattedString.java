package me.ownage.xenon.util;

import java.awt.Color;

public class FormattedString {
	private static final int WHITE_NO_ALPHA = 0xFFFFFF;
	private static final int WHITE = 0xFFFFFFFF;
	
	private int hexColor;
	private String text;
	private Color toColor;
	
	public FormattedString(String str) {
		this.text = str;
		this.hexColor = WHITE;
		this.toColor = Color.WHITE;
	}
	
	public FormattedString(String str, int hexColor) {
		this.text = str;
		this.hexColor = hexColor;
		this.toColor = new Color(hexColor);
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof FormattedString)) {
			return false;
		}
		
		FormattedString fs = (FormattedString) o;
		
		if(fs.hexColor != this.hexColor) return false;
		if(fs.text != this.text) return false;
		
		return true;
	}
	
	public int[] toRGB() {
		return new int[] {
				this.toColor.getRed(),
				this.toColor.getBlue(),
				this.toColor.getGreen()
		};
	}
	
	public int[] toRGBA() {
		return new int[] {
				this.toColor.getRed(),
				this.toColor.getBlue(),
				this.toColor.getGreen(),
				this.toColor.getAlpha()
		};
	}
	
	public int[] toScaledRGB() {
		return new int[] {
				this.toColor.getRed() / 255,
				this.toColor.getBlue() / 255,
				this.toColor.getGreen() / 255
		};
	}
	
	public int[] toScaledRGBA() {
		return new int[] {
				this.toColor.getRed() / 255,
				this.toColor.getBlue() / 255,
				this.toColor.getGreen() / 255,
				this.toColor.getAlpha() / 255
		};
	}
	
	public Color toColor() {
		return this.toColor;
	}
	
	public int getHTMLColor() {
		return this.hexColor;
	}
	
	public String getString() {
		return this.text;
	}
	
	public String toString() {
		return this.text;
	}
}
