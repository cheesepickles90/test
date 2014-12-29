package org.emotionalpatrick.colossus.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class IOManagerImpl implements IOManager {

	private File file;
	private URL url;

	private BufferedReader bufferedreader;
	private BufferedWriter bufferedwriter;

	public IOManagerImpl(File file) {
		if(file == null)
			throw new NullPointerException("EmotionalFagtrick is null!");
		
		this.file = file;
	}

	public IOManagerImpl(URL url) {
		if(url == null)
			throw new NullPointerException("Holy shit, it's null!");
		
		this.url = url;
	}

	@Override
	public void startWriting() {
		try {
			if(file != null)
				bufferedwriter = new BufferedWriter(new FileWriter(file));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startReading() {
		try {
			if(file != null)
				bufferedreader = new BufferedReader(new FileReader(file));
			else 
				bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write(String par1) {
		try {
			if(bufferedwriter != null)
				bufferedwriter.write(par1);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String readLine() {
		String value = null;

		try {
			if(bufferedreader != null)
				value = bufferedreader.readLine();
		}catch (IOException e) {
			e.printStackTrace();
		}

		return value;
	}

	@Override
	public void stopWriting() {
		try {		
			if(bufferedwriter != null) {
				bufferedwriter.flush();
				bufferedwriter.close();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stopReading() {
		try {
			if(bufferedreader != null)
				bufferedreader.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
