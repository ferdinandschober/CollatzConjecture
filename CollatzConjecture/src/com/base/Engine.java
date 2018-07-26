package com.base;

import java.awt.Color;
import java.util.ArrayList;

import com.base.display.Display;

public class Engine implements Runnable
{
	Display display = new Display();
	Calculator calculator = new Calculator();
	Stats stats = new Stats();
	
	private long targetTime;
	
	{
		calculator.setResultBufferlength(display.getWidth());
	}

	private static char graphType = 'f';

	public Engine()
	{
		Thread t = new Thread(this);
		t.start();
	}

	public void run()
	{
		calculator.setPriority(5);
		calculator.start();
		while (true)
		{
			targetTime = 16666666;
			update();
			draw();
			try
			{
				double frameTime = 1.0/stats.fps;
				frameTime *= 1000000000;
				long delta = (targetTime-(long)frameTime)/1000000;
				System.out.println(delta);
				Thread.sleep(delta > 0 ? delta : 0);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			try
			{
				Thread.sleep(10);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	public void draw()
	{
		display.clearFrameBuffer();
		ArrayList<Integer> results = calculator.getResults();
		for (int x = 0; x < calculator.getResultBufferlength(); x++)
			switch (graphType)
			{
			case 'f':
			{
				for (int y = 0; y <= results.get(x); y++)
				{
					display.draw(x, display.getHeight() - y, Color.WHITE);
				}
				break;
			}
			case 'd':
			{
				display.draw(x, display.getHeight() - results.get(x),Color.WHITE);
				break;
			}
			}

		display.drawFrameBuffer();
		display.drawText(stats.getStats(), 10, 20,Color.GREEN);
		display.drawText("delay between calculations: " + calculator.getTargetCps() + " ms", 10, 40, Color.GREEN);
		display.drawText("number with longest sequence: " + calculator.getLongestSequence() + " ("
				+ calculator.getMostSteps() + " steps )", 10, 60, Color.GREEN);
		display.drawText("current number: " + calculator.getCounter(), 10, 80, Color.GREEN);
		display.draw();
	}

	public void update()
	{
		if (display.getInput().getResized())
		{
			display.getInput().setResized(false);
			display.updateSize();
			calculator.setResultBufferlength(display.getWidth());
		}
		if (display.getInput().getMouseButtonsClicked(3))
		{
			display.getInput().setMouseButtonsClicked(3, false);
			switch (graphType)
			{
			case 'f':
				graphType = 'd';
				break;
			default:
				graphType = 'f';
				break;
			}
		}

		if (display.getInput().getMousePressed(1))
		{
			if (calculator.isRunning())
				calculator.pause_();
		} else if (!calculator.isRunning())
			calculator.resume_();

		int mouseWheelRotation = display.getInput().getMouseWheelRotation();
				display.getInput().setMouseWheelRotation(0);	
				calculator.setTargetCps(calculator.getTargetCps()+mouseWheelRotation * getMouseWheelSensitivity(calculator.getTargetCps(),mouseWheelRotation));

		stats.update();
	}

	private int getMouseWheelSensitivity(double targetCps,int mouseWheelRotation)
	{
		if(mouseWheelRotation > 0)
		{
			return targetCps>=100000?100000:targetCps>=10000?10000:targetCps>=1000?1000:targetCps>=100?100:targetCps>=10?10:1;
		}
		else 
		{
			return targetCps>100000?100000:targetCps>10000?10000:targetCps>1000?1000:targetCps>100?100:targetCps>10?10:1;
		}
	}
}
