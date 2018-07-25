package com.base;

import java.util.ArrayList;

import com.base.display.Display;

public class Engine implements Runnable
{
	Display display = new Display();
	Calculator calculator = new Calculator();
	{
		calculator.setResultBufferlength(display.getWidth());
	}
	Stats stats = new Stats();

	private static char graphType = 'f';

	public Engine()
	{
		Thread t = new Thread(this);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}

	public void run()
	{
		calculator.setPriority(9);
		calculator.start();
		while (true)
		{
			update();
			draw();
			try
			{
				Thread.sleep(5);
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
		for (int x = 0; x < results.size(); x++)
			switch (graphType)
			{
			case 'f':
			{
				for (int y = 0; y <= results.get(x); y++)
				{
					display.draw(x, display.getHeight() - y, 255, 255, 255);
				}
				break;
			}
			case 'd':
			{
				display.draw(x, display.getHeight() - results.get(x), 255, 255, 255);
				break;
			}
			}

		display.drawFrameBuffer();
		display.drawText(stats.getStats(), 10, 20);
		display.drawText("delay between calculations: " + calculator.getDelay() + " ms", 10, 40);
		display.drawText("number with longest sequence: " + calculator.getLongestSequence() + " ("
				+ calculator.getMostSteps() + " steps )", 10, 60);
		display.drawText("current number: " + calculator.getCounter(), 10, 80);
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
				graphType = 'r';
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

		double delaynew = calculator.getDelay();
		if (mouseWheelRotation < 0)
			delaynew -= calculator.getDelay() > 1 ? 1 : calculator.getDelay() > 0.1 ? 0.1 : 0.01;
		else if (mouseWheelRotation > 0)
			delaynew += calculator.getDelay() >= 1 ? 1 : calculator.getDelay() >= 0.1 ? 0.1 : 0.01;

		delaynew = (double) Math.round(delaynew * 100) / 100;
		if (delaynew >= 0)
			calculator.setDelay(delaynew);
		stats.update();
	}
}
