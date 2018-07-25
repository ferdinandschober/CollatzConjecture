package com.base;

import java.util.ArrayList;

public class Calculator extends Thread
{
	private int steps;
	private long counter = 1;
	private long mostSteps;
	private long longestSequence;
	
	private boolean running = true;
	
	private double delay = 0.5;

	private int resultBufferlength = 1920;

	private static ArrayList<Integer> results = new ArrayList<Integer>();

	@Override
	public void run()
	{
		while(true)
		if (running)
		{
			nextNumber();
			if(results.size() > resultBufferlength)
			results.remove(0);
		} else
			try
			{
				sleep(10);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void setResultBufferlength(int resultBufferlength)
	{
		this.resultBufferlength = resultBufferlength;
		int diff = results.size()-resultBufferlength;
		if(diff>0)
		{
			for(int i = 0; i < diff;i++)
				results.remove(0);
		}
	}

	public void pause_()
	{
		running = false;
	}
	
	public void resume_()
	{
		running = true;
	}

	public void nextNumber()
	{
		steps = CollatzSteps(counter);
		results.add(steps);
		if (steps >= mostSteps)
		{
			longestSequence = counter;
			mostSteps = steps;
		}
		counter++;
		try
		{
			sleep((int)delay,(int)( (delay-(int)delay) * 1000000));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static int CollatzSteps(long n)
	{
		int steps = 0;
		for (; n > 1; steps++)
			n = ((n & 1) == 0) ? n >> 1 : (n << 1) + n + 1;
		return steps;
	}

	public long getCounter()
	{
		return counter;
	}

	public void setCounter(long counter)
	{
		this.counter = counter;
	}

	public long getMostSteps()
	{
		return mostSteps;
	}

	public void setMostSteps(long mostSteps)
	{
		this.mostSteps = mostSteps;
	}

	public long getLongestSequence()
	{
		return longestSequence;
	}

	public void setLongestSequence(long longestSequence)
	{
		this.longestSequence = longestSequence;
	}

	public ArrayList<Integer> getResults()
	{
		return results;
	}
	
	public double getDelay()
	{
		return delay;
	}
	
	public void setDelay(double delay)
	{
		this.delay = delay;
	}

	public boolean isRunning()
	{
		return running;
	}
}
