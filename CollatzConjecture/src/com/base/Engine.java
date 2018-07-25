package com.base;

import com.base.display.Display;

public class Engine implements Runnable
{
	Display display = new Display();
	Calculator calculator = new Calculator();
	private Stats stats = new Stats();

	private static int type = 'r';

	int mouseWheelSensitivity = 1;

	public Engine()
	{
		Thread t = new Thread(this);
		t.start();
	}

	public void run()
	{
		calculator.start();
		while (true)
		{
			try
			{
				if (!display.getInput().getMousePressed(1))
				{
					update();
					draw();
					try
					{
						Thread.sleep(16);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				} else
					try
					{
						Thread.sleep(10);
					} catch (Exception e)
					{
						e.printStackTrace();
					}

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	public void draw()
	{
		display.clearFrameBuffer();
		for (int x = 0; x < display.getWidth(); x++)
			switch (type)
			{
			case 'f':
				for (int y = 0; y < calculator.getResults(x); y++)
					display.draw(x, display.getHeight() - 1 - y, 255, 255, 255);
				break;
			default:
				display.draw(x, display.getHeight() - 1 - calculator.getResults(x), 255, 255, 255);
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
		}
		if (display.getInput().getMouseButtonsClicked(3))
		{
			display.getInput().setMouseButtonsClicked(3, false);
			switch (type)
			{
			case 'f':
				type = 'r';
				break;
			default:
				type = 'f';
				break;
			}
		}
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
