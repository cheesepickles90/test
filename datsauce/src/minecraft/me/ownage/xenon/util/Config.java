package me.ownage.xenon.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Config {
	private File fileConfig;
	private Map<String, String> mapConfig = new HashMap<String, String>();
	private Map<String, String> mapDefaults = new HashMap<String, String>();
	
	private static final String CRLF = new String(new char[]{ '\r', '\n' });
	
	public Config(String path, Map<String, String> defaults) throws IOException {
		this.fileConfig = new File(path);
		this.mapDefaults = defaults;
		
		reloadConfig();
	}
	
	public Config(File path, Map<String, String> defaults) throws IOException {
		this.fileConfig = path;
		this.mapDefaults = defaults;
		
		reloadConfig();
	}
	
	public Config(String path) throws IOException {
		this.fileConfig = new File(path);
		
		reloadConfig();
	}
	
	public Config(File path) throws IOException {
		this.fileConfig = path;
		
		reloadConfig();
	}
	
	public void reloadConfig() throws IOException {
		mapConfig.clear();
		
		if(!fileConfig.exists()) {
			fileConfig.getParentFile().mkdirs();
			fileConfig.createNewFile();
			
			if(this.mapDefaults != null) {
				mapConfig.putAll(mapDefaults);
				writeConfig();
			}
			return;
		}
		
		DataInputStream stream = new DataInputStream(new FileInputStream(fileConfig));
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		String readLine;
		while((readLine = reader.readLine()) != null) {
			String line = readLine;
			
			if(!line.startsWith("#")) {
				mapConfig.put(line.split("=")[0].trim().toLowerCase(), line.split("=")[1].trim());
			}
		}
		
		stream.close();
		
		writeConfig();
	}
	
	public void writeConfig() throws IOException {
		FileWriter stream = new FileWriter(fileConfig);
		BufferedWriter out = new BufferedWriter(stream);
		boolean flag = false;
		for(String key: mapConfig.keySet()) {
			if(flag) {
				out.write(CRLF);
			}
			
			out.write(key);
			out.write('=');
			out.write(mapConfig.get(key));
			
			flag = true;
		}
		
		if(mapDefaults != null) {
			for(String key: mapDefaults.keySet()) {
				if(!hasKey(key)) {
					if(flag) {
						out.write(CRLF);
					}
					
					out.write(key);
					out.write('=');
					out.write(mapDefaults.get(key));
					
					flag = true;
				}
			}
		}
		
		out.close();
	}
	
	public String getKeyValue(String key) {
		if(!hasKey(key)) {
			addKeyValue(key.trim().toLowerCase(), "NULL");
		}
		
		return mapConfig.get(key.trim().toLowerCase());
	}
	
	public void addKeyValue(String key, String value) {
		if(!hasKey(key)) {
			mapConfig.put(key.trim().toLowerCase(), value.trim());
		}
	}
	
	public void changeKeyValue(String key, String value) {
		if(!hasKey(key)) {
			addKeyValue(key, value);
			return;
		} else {
			mapConfig.remove(key);
			mapConfig.put(key.trim().toLowerCase(), value.trim());
		}
	}
	
	public boolean hasKey(String key) {
		return mapConfig.containsKey(key.toLowerCase().trim());
	}
}
