package com.base;
import java.util.Random;

import com.base.display.CanvasDisplay;

import java.util.ArrayList;

public class CanvasEngine implements Runnable {
    CanvasDisplay display = new CanvasDisplay();
    double fps;
    long lastTime;
    long frameTime;
    boolean logFramerate = true;
    int statsUpdateRate = 1000;
    int frames;

    private static int steps;
    long counter = 1;
    private static int type = 'f';
    private long startTime;
    private int scrollspeed = 2000;
    long maxstepcount;
    
    public CanvasEngine()
    {
        Thread t = new Thread(this);
        t.start();
    }

    public void run()
    {
        lastTime = System.nanoTime();
        while (true) {
            try {
                update();      
                draw();
                stats();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void draw()
    {
    	display.drawFrameBuffer();
    	display.drawText(stats(), 10, 10);
    	display.draw();
    }

    public void update() {

        if(true){
            if(frames == 0)
                System.out.println('\f');
            display.shiftImage(scrollspeed);
            long stepcount = 0;
            for(int i = scrollspeed; i > 0;i--)
            {
                stepcount = CollatzSteps(counter);
                if(stepcount > maxstepcount){
                    System.out.println();
                    System.out.print(counter+": "+stepcount+" steps");
                    maxstepcount = stepcount;
                }
                if(type == 'f')
                {
                    for(int j = display.HEIGHT-1; j >= display.HEIGHT-1-stepcount; j--)
                        display.draw(display.WIDTH-i,j,255,255,255);
                }
                else
                    display.draw(display.WIDTH-i,display.HEIGHT-1-(int)stepcount,255,255,255);
                counter++;
            }
            //System.out.println(counter+": "+stepcount);
        }

    }

    public String stats() {
        frames++;
        String s = Double.toString(fps);
        if(s.length()>4)
            s = s.substring(0,5);
        if (frames % statsUpdateRate == 0) 
        {
            frameTime = System.nanoTime() - lastTime;
            fps = statsUpdateRate*(1.0 / (frameTime / (double) 1000000000));
            lastTime = System.nanoTime();
            if(logFramerate){
                System.out.print(" "+s+" fps");
            }
        }
        return s+" fps";
    }

    public static long Collatz(long n)
    {
        steps++;
        return(n==1?1:(n&1)==0?Collatz(n>>1):Collatz(n*3+1));
    }

    public static long iCollatz(long n)
    {
        while (n > 1){
            steps++; n=((n&1)==0)?n>>1:(n<<1)+n+1;
        }
        return n;
    }

    public static void testAll()
    {
        long startTime = System.nanoTime();
        for(long i = 1; i < 10000000; i++)
        {
            CollatzSteps(i);
            //System.out.println(i+": "+CollatzSteps(i)+" steps");
        }
        System.out.println((float)(System.nanoTime()-startTime)/1000000000f);
    }

    public static int CollatzSteps(long n)
    {
        steps = 0;
        iCollatz(n);
        return steps;
    }
}
