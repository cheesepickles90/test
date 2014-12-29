package org.emotionalpatrick.colossus.utilities;

import java.util.Random;

public class Encryption {

	private static String key = "asd";

	public String decryptString(String text) {
		long finalKey = 0;

		for (int i = 0; i < key.length(); i++) {
			long tempKey = key.charAt(i);
			tempKey *= 128;
			finalKey += tempKey;
		}

		Random generator = new Random(finalKey);
		String returnString = "";

		for (int i = 0; i < text.length(); i++) {
			int temp = (int) text.charAt(i);
			temp -= generator.nextInt(69);

			if (temp < 36) {
				temp += 95;
			} else if (temp > 126) {
				temp -= 95;
			}

			returnString += (char) temp;
		}

		return returnString;
	}
	
	public static String decryptString2(String text) {
		long finalKey = 0;

		for (int i = 0; i < key.length(); i++) {
			long tempKey = key.charAt(i);
			tempKey *= 128;
			finalKey += tempKey;
		}

		Random generator = new Random(finalKey);
		String returnString = "";

		for (int i = 0; i < text.length(); i++) {
			int temp = (int) text.charAt(i);
			temp -= generator.nextInt(94);

			if (temp < 36) {
				temp += 95;
			} else if (temp > 126) {
				temp -= 95;
			}

			returnString += (char) temp;
		}

		return returnString;
	}
}
