package com.base;

public class Stats
{
	double fps;
	long lastTime = System.nanoTime();
	long frameTime;
	boolean logFramerate = false;
	int updateRate = 10;
	int frames;

	String stats;

	public void update()
	{
		frames++;
		String s = Double.toString(fps);
		if (s.length() > 4)
			s = s.substring(0, 5);
		if (frames % updateRate == 0)
		{
			frameTime = System.nanoTime() - lastTime;
			fps = updateRate * (1.0 / (frameTime / (double) 1000000000));
			lastTime = System.nanoTime();
			if (logFramerate)
			{
				System.out.print(" " + s + " fps");
			}
		}
		stats = s + " fps";
	}
	
	public String getStats() { return stats; }
}
