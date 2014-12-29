package org.emotionalpatrick.colossus.utilities;

import java.io.*;
import java.util.*;

import net.minecraft.client.Minecraft;

public class ModuleValues {
	
	public String name;
	
	public float value;
	public float minimal;
	public float safety, safetyMinimal;

	public static ArrayList<ModuleValues> list = new ArrayList<ModuleValues>();

	/**
	 * Usage :
	 * 			Colossus.<hackValueName>.getValue();
	 * */
	
	
	public ModuleValues(String s, float v, float m, float s1, float s2) {
		value = v;
		name = s;
		minimal = m;
		safety = s2;
		safetyMinimal = s1;
		list.add(this);
		//loadValues();
	}

	public ModuleValues(String s, float v, float m) {
		value = v;
		name = s;
		minimal = m;
		safety = -1f;
		safetyMinimal = -1f;
		list.add(this);
		//loadValues();
	}

	public float getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setValue(float f) {
		value = f;
		/*
		 * if(f > safety && Variables.safetyMode.Enabled() && safety != -1f)
		 * value = safety; if(f < safetyMinimal &&
		 * Variables.safetyMode.Enabled() && safetyMinimal != -1f) value =
		 * safetyMinimal;
		 */
	}

	public float getMinimal() {
		// if(Variables.safetyMode.Enabled() && safetyMinimal != -1f)
		// return safetyMinimal;
		return minimal;
	}

//	public static void saveValues() {
//
//		try {
//			File file = new File(Minecraft.getMinecraftDir(), "Values.txt");
//
//			PrintWriter printwriter = new PrintWriter(new FileWriter(file));
//
//			for (HackValue v : list) {
//				printwriter.println(v.getName() + ":" + v.getValue());
//
//			}
//			printwriter.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void loadValues() {
//		try {
//			File file = new File(Minecraft.getMinecraftDir(), "Values.txt");
//
//			if (!file.exists())
//				return;
//
//			BufferedReader bufferedreader = new BufferedReader(new FileReader(
//					file));
//
//			for (String s = ""; (s = bufferedreader.readLine()) != null;) {
//				try {
//					for (int x = 0; x < list.size(); x++) {
//						if (s.split(":")[0].startsWith(list.get(x).name)) {
//							list.get(x).setValue(
//									Float.parseFloat(s.split(":")[1]));
//							// System.out.println(s.split(":")[0] +
//							// " loaded as " + s.split(":")[1]);
//						}
//
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//
//			bufferedreader.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
