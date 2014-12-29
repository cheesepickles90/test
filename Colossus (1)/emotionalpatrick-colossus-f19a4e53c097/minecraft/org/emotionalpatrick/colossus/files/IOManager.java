package org.emotionalpatrick.colossus.files;

public interface IOManager {
	
	/**
	 * Setup file writing.
	 */
	public void startWriting();
	
	/**
	 * Setup file reading
	 */
	public void startReading();
	
	/**
	 * Write to a file
	 * @param par1
	 */
	public void write(String par1);
	
	/**
	 * Read a line
	 * @return
	 */
	public String readLine();
	
	/**
	 * Close file writing
	 */
	public void stopWriting();
	
	/**
	 * Close file reading
	 */
	public void stopReading();

}
