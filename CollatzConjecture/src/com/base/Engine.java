package com.base;

import com.base.display.Display;

public class Engine implements Runnable {
	Display display = new Display();
	double fps;
	long lastTime;
	long frameTime;
	boolean logFramerate = false;
	int statsUpdateRate = 100;
	int frames;

	private static int steps;
	long counter = 1;
	private static int type = 'f';
	private int scrollspeed = 1000; //needs to be a factor of mouseWheelSensitivity
	long mostSteps;
	private long longestSequence;

	int mouseWheelSensitivity = 100;

	public Engine() {
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		lastTime = System.nanoTime();
		while (true) {
			try {
				if (!display.getInput().getMousePressed(1)) {
					update();
					draw();
				} else
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void draw() {
		display.drawFrameBuffer();
		display.drawText(stats(), 10, 20);
		display.drawText("number with longest sequence: " + longestSequence + " (" + mostSteps + " steps )", 10, 40);
		display.drawText("operations per frame: " + scrollspeed, 10, 60);
		display.draw();
	}

	public void update() {
		if (frames == 0)
			System.out.println('\f');
		display.shiftImage(scrollspeed);
		long stepcount = 0;
		for (int i = scrollspeed; i > 0; i--) {
			stepcount = CollatzSteps(counter);
			if (stepcount > mostSteps) {
				System.out.println();
				System.out.print(counter + ": " + stepcount + " steps");
				longestSequence = counter;
				mostSteps = stepcount;
			}
			if (type == 'f') {
				for (int j = display.getHeight() - 1; j >= display.getHeight() - 1 - stepcount; j--)
					display.draw(display.getWidth() - i, j, 255, 255, 255);
			} else
				display.draw(display.getWidth() - i, display.getHeight() - 1 - (int) stepcount, 255, 255, 255);
			counter++;
		}
		if (display.getInput().getResized()) {
			display.getInput().setResized(false);
			display.updateSize();
		}
		if (display.getInput().getMouseButtonsClicked(3)) {
			display.getInput().setMouseButtonsClicked(3, false);
			switch (type) {
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

		if (scrollspeed == 10)
			mouseWheelSensitivity = mouseWheelRotation > 0 ? 10 : 1;
		if (scrollspeed == 100)
			mouseWheelSensitivity = mouseWheelRotation > 0 ? 100 : 10;
		if (scrollspeed == 1000)
			mouseWheelSensitivity = mouseWheelRotation > 0 ? 1000 : 100;
		if (scrollspeed == 10000)
			mouseWheelSensitivity = mouseWheelRotation > 0 ? 10000 : 1000;
		scrollspeed += mouseWheelRotation * mouseWheelSensitivity;

		if (scrollspeed < 0)
			scrollspeed = 0;
	}

	public String stats() {
		frames++;
		String s = Double.toString(fps);
		if (s.length() > 4)
			s = s.substring(0, 5);
		if (frames % statsUpdateRate == 0) {
			frameTime = System.nanoTime() - lastTime;
			fps = statsUpdateRate * (1.0 / (frameTime / (double) 1000000000));
			lastTime = System.nanoTime();
			if (logFramerate) {
				System.out.print(" " + s + " fps");
			}
		}
		return s + " fps" + " (" + (int) (fps * scrollspeed) + " operations per second" + ")";
	}

	public static long Collatz(long n) {
		steps++;
		return (n == 1 ? 1 : (n & 1) == 0 ? Collatz(n >> 1) : Collatz(n * 3 + 1));
	}

	public static long iCollatz(long n) {
		while (n > 1) {
			steps++;
			n = ((n & 1) == 0) ? n >> 1 : (n << 1) + n + 1;
		}
		return n;
	}

	public static void testAll() {
		long startTime = System.nanoTime();
		for (long i = 1; i < 10000000; i++) {
			CollatzSteps(i);
			// System.out.println(i+": "+CollatzSteps(i)+" steps");
		}
		System.out.println((float) (System.nanoTime() - startTime) / 1000000000f);
	}

	public static int CollatzSteps(long n) {
		steps = 0;
		iCollatz(n);
		return steps;
	}
}
