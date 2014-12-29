package org.emotionalpatrick.colossus.utilities;

// Thanks Haruko!
public class OperationTimer {
	
	private long startTime;
	private long endTime;

	public void start() {
		this.startTime = nanoTime() / 1000000;
	}

	public void stop() {
		this.endTime = nanoTime() / 1000000 - this.startTime;
	}

	public void reset() {
		this.startTime = 0;
		this.endTime = 0;
	}

	private long nanoTime() {
		return System.nanoTime() / 1000000;
	}

	public long getElapsedTime() {
		return this.endTime;
	}

	public String toString() {
		return this.endTime + "ms";
	}
}
