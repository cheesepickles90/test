package org.emotionalpatrick.colossus.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.emotionalpatrick.colossus.files.FileManagerImpl;
import org.emotionalpatrick.colossus.main.Helper;
import org.emotionalpatrick.colossus.utilities.color.ChatColor;

public class Templates extends Module {

    private ArrayList <TemplateObj>templates = new ArrayList<TemplateObj>();

	public Templates() {
		super("Templates", ".templates", "Customizable templates for modules", "Emotional Patrick");
		this.enabled = true;
		loadTemplates();
	}
	
	@Override
	public void runCommand (String s) {
		if (s.equalsIgnoreCase(this.getCommand())) {
			String listTemplates = ChatColor.GOLD + "Availiable Templates: " + ChatColor.WHITE;
			for (TemplateObj t : this.templates) {
				listTemplates += ("." + t.getTemplateName() + ", ");
			}
			Helper.addChat(listTemplates + " .treload" + ".");
			Helper.addChat("You have (" + templates.size() + ") template(s) loaded currently.");
		}
	}
	
	@Override
	public void externalCommand (String s) {
		if (s.equalsIgnoreCase(".treload")) {
			this.templates.clear();
			this.loadTemplates();
			Helper.addChat("Reloaded template(s).");
		}

		if (this.isEnabled()) {
			for (TemplateObj t : this.templates) {
				if (s.equalsIgnoreCase("." + t.getTemplateName())) {
					t.handleTemplate();
				}
			}
		}
    }
	
	private void loadTemplates() {
		File templateDir = new File(FileManagerImpl.getColossusDir() + "/templates");
		if (!templateDir.exists()) {
			templateDir.mkdir();
		}
        File[] savedTemplates = templateDir.listFiles();
        int i = savedTemplates.length;
		for (int m = 0; m < i; ++m) {
			File templateFile = savedTemplates[m];
			if (templateFile.getAbsolutePath().toLowerCase().endsWith(".txt")) {
				this.templates.add(new TemplateObj(templateFile));
				Helper.addConsole("Added template " + templateFile.getName() + ".");
			}
		}
		Helper.addConsole(savedTemplates.length + " file(s) in " + templateDir.getAbsolutePath() + ".");
        Helper.addConsole("Loaded " + this.templates.size() + " template(s).");
	}
	
	class TemplateObj {
		
	    public String templateName;

		private ArrayList commands = new ArrayList();
	    
		public TemplateObj(File f) {
			try {
				this.addTemplateCommands(f);
			} catch (IOException err) {
				err.printStackTrace();
			}
		}
	    
		private void addTemplateCommands(File f) throws IOException {
			try {
				setTemplateName(f.getName().toLowerCase().replaceAll(".txt", ""));
				commands.clear();
				BufferedReader br = new BufferedReader(new FileReader(f));
				for (String s1 = ""; (s1 = br.readLine()) != null;) {
					try {
						commands.add(s1.trim());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				br.close();
				Helper.addConsole("Added " + commands.size() + " command(s) from file \"" + f.getName() + "\" to the command list.");
			} catch (Exception err) {
				err.printStackTrace();
			}
		}

		public void handleTemplate() {
			Iterator i = this.commands.iterator();
			while (i.hasNext()) {
				String s = (String) i.next();
				Helper.sendChat(s);
			}
		}
		
	    public String getTemplateName() {
			return templateName;
		}
	    
		public void setTemplateName(String templateName) {
			this.templateName = templateName;
		}
	}
}
