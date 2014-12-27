package me.ownage.xenon.gui.elements;

import java.text.DecimalFormat;

import me.ownage.xenon.hacks.classes.Xray;
import me.ownage.xenon.main.Hacks;
import me.ownage.xenon.main.Xenon;
import me.ownage.xenon.util.Value;
import me.ownage.xenon.util.XenonUtils;

public class XSlider {
	private XWindow window;
	private Value sliderValue;
	private int xPos;
	private int yPos;
	private float maxValue;
	private float minValue;
	
	private boolean shouldRound;
	
	public boolean dragging;
	public float dragX, lastDragX;
	
	private int drawSliderWidth;
	private int sliderWidth;
	
	public void dragSlider(int x) {
		dragX = x - lastDragX;
	}
	
	public XSlider(XWindow window, Value value, int x, int y) {
		this.window = window;
		this.sliderValue = value;
		this.xPos = x;
		this.yPos = y;
		this.maxValue = 10.0F;
		this.minValue = 0.0F;
		this.drawSliderWidth = 85;
		this.sliderWidth = this.drawSliderWidth - 5;
	}
	
	public XSlider(XWindow window, Value value, int x, int y, float maxValue) {
		this.window = window;
		this.sliderValue = value;
		this.xPos = x;
		this.yPos = y;
		this.maxValue = maxValue;
		this.minValue = 0.0F;
		this.drawSliderWidth = 85;
		this.sliderWidth = this.drawSliderWidth - 5;
	}
	
	public XSlider(XWindow window, Value value, int x, int y, float minValue, float maxValue, boolean shouldRound) {
		this.window = window;
		this.sliderValue = value;
		this.xPos = x;
		this.yPos = y;
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.drawSliderWidth = 85;
		this.shouldRound = shouldRound;
		this.sliderWidth = this.drawSliderWidth - 5;
	}
	
	public void draw(int x) {
		if(Hacks.findMod(Xray.class).isEnabled() && sliderValue == Xray.opacity && dragging) {
	        Xenon.getMinecraft().renderGlobal.loadRenderers();
		}
		if(dragging) {
			dragSlider(x);
		}
		if(dragX < 0) {
			dragX = 0;
		}
		if(dragX > this.sliderWidth) {
			dragX = this.sliderWidth;
		}
		
		DecimalFormat format = new DecimalFormat(shouldRound ? "0" : "0.0");
		
		XenonUtils.drawTTFStringWithShadow(Xenon.guiFont, sliderValue.getName() + ": " + format.format(sliderValue.getValue()), xPos + window.dragX, yPos - 3 + window.dragY, 0xFFFFFF);
		XenonUtils.drawHLine(xPos + window.dragX, xPos + this.drawSliderWidth + window.dragX, yPos + 12 + window.dragY, 0xFFAAAAAA);
		XenonUtils.drawHLine(xPos + window.dragX, xPos + dragX + window.dragX, yPos + 12 + window.dragY, 0xFFDDDDDD);
		XenonUtils.drawGradientBorderedRect(xPos + window.dragX + (int)dragX, yPos + 9 + window.dragY, xPos + 6 + window.dragX + (int)dragX, yPos + 15.5F + window.dragY, 0.5F, 0xFF555555, 0xFF777777, 0xFF555555);
		
		float fraction = this.sliderWidth / (maxValue - minValue);
		
		sliderValue.setValue(sliderValue.getName().equals("Step") ? ((dragX / fraction) + minValue) + 0.1F : shouldRound ? (int)(dragX / fraction) + minValue : (dragX / fraction) + minValue);
	}
	
	public float getValue() {
		return Float.parseFloat((new DecimalFormat("0.0")).format(this.sliderValue.getValue()));
	}
	
	public void setValue(float value) {
		value -= minValue;
		
		float fraction = 80 / (maxValue - minValue);
		sliderValue.setValue(value);
		dragX = fraction * value;
	}
	
	public void mouseClicked(int x, int y, int button) {
		if(button == 0) {
			if(x >= xPos + window.dragX + dragX && y >= yPos + 9 + window.dragY && x <= xPos + 6 + window.dragX + dragX && y <= yPos + 15.5F + window.dragY) {
				lastDragX = x - dragX;
				dragging = true;
			}
		}
	}
	
	public void mouseMovedOrUp(int x, int y, int b) {
		if(b == 0) {
			dragging = false;
		}
	}
}
