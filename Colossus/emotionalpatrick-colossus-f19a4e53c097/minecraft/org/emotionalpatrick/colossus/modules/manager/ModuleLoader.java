package org.emotionalpatrick.colossus.modules.manager;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import org.emotionalpatrick.colossus.files.FileManagerImpl;
import org.emotionalpatrick.colossus.main.Colossus;
import org.emotionalpatrick.colossus.main.ColossusHelper;
import org.emotionalpatrick.colossus.modules.Module;

// dat is not reflection -haruko
//@above hard-core fail-core, bro. That's obviously reflection. ~Ram
public class ModuleLoader {
	
	private ArrayList <Module> externalModules = new ArrayList<Module>();

	public void loadExternalModules() {
		try {
			externalModules.clear();
			loadModulesFromJar();
			for (Module module : externalModules) {
				ColossusHelper.addConsole("Loaded external module \"" + module.getName() + "\" by \"" + module.getAuthor() +  "\".");
			}
			ColossusHelper.addConsole(externalModules.size() + " external module(s) loaded.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadModulesFromJar() throws Exception {
		for(File container : getPluginContainers()) {
			try {
				ColossusHelper.addConsole("External Module Path: " + container.getAbsolutePath());
				JarFile moduleContainer = new JarFile(container);
				Enumeration<JarEntry> entries = moduleContainer.entries();
				JarInputStream jarInput = new JarInputStream(new FileInputStream(container.getPath()));
				while(true) {
					JarEntry entry = jarInput.getNextJarEntry();
					if(entry == null) { break; }
					if(!entry.getName().endsWith(".class")) { continue; }

					ColossusHelper.addConsole("Loading class: " + entry.getName());
					System.err.println(entry.getName());
					
					Module module = getModuleFromJar(container, entry.getName().replace(".class", ""));
					if(module == null) { ColossusHelper.addConsole("Error! Could not load class: " + entry.getName()); continue; }
					externalModules.add(module);
				}
				moduleContainer.close();
				jarInput.close();
			}catch (Exception e) { e.printStackTrace(); }
		}
		ColossusHelper.addConsole(externalModules.size() + " external module(s) found.");
	}
	
	private Module getModuleFromJar(File dir, String name) throws Exception {
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{dir.toURI().toURL()});
		Class<?> initialClass = classLoader.loadClass(name.replace("/", "."));
		if(!initialClass.getSuperclass().equals(Module.class)) { return null; }
		Class<? extends Module> moduleClass = initialClass.asSubclass(Module.class);
		Constructor<? extends Module> moduleConstructor = moduleClass.getConstructor();
		Module module = moduleConstructor.newInstance();
		return module;
	}

	private List<File> getPluginContainers() {
		ArrayList<File> result = new ArrayList<File>();
		ColossusHelper.addConsole("Plugins folder: " + FileManagerImpl.getPluginsDir().getAbsolutePath());
		for (File file : FileManagerImpl.getPluginsDir().listFiles()) {
			ColossusHelper.addConsole("Plugins Container: " + file.getName());
			if (getFileExtension(file.getName()).equals("jar")) {
				result.add(file);
			}
		}
		return result;
	}

	private String getFileExtension(String fileName){
		int lastIndex = fileName.lastIndexOf(".");
		return fileName.substring(lastIndex+1, fileName.length());
	}

	public ArrayList<Module> getExternalModules() {
		return externalModules;
	}
}