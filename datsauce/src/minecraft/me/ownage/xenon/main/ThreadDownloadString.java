package me.ownage.xenon.main;

public class ThreadDownloadString extends Thread implements Runnable {
	private String url;	
	private String downloadedString;
	
	public ThreadDownloadString(String url) {
		this.url = url;
	}
	
	@Override
	public void run() {
		downloadedString = Xenon.downloadString(url);
	}
	
	public String getDownloadedString() {
		return downloadedString;
	}
}
