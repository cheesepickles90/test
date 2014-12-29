package org.emotionalpatrick.colossus.files;

import java.io.File;
import java.io.IOException;

public interface FileManager {
	
	public void initSave();
	public void initLoad();
	
	public void saveKeybinds();
	public void loadKeybinds();
	
	public void saveFriends() throws IOException ;
	public void loadFriends();

}
