package com.base;
import java.util.Random;

import com.base.display.Display;

import java.util.ArrayList;

public class Engine {
    private static int steps;
    long counter = 1;
    private static int type = 'f';
    private long startTime;
    private int scrollspeed = 800;
    long maxstepcount;
    public void update(Display display, int frames) {

        if(true){
            if(frames == 0)
                System.out.println('\f');
            display.shiftImage(scrollspeed);
            long stepcount = 0;
            for(int i = scrollspeed; i > 0;i--)
            {
                stepcount = CollatzSteps(counter);
                if(stepcount > maxstepcount){
                    System.out.println(counter+": "+stepcount+" steps");
                    maxstepcount = stepcount;
                }
                if(type == 'f')
                {
                    for(int j = display.height-1; j >= display.height-1-stepcount; j--)
                        display.draw(display.width-i,j,255,255,255);
                }
                else
                    display.draw(display.width-i,display.height-1-(int)stepcount,255,255,255);
                counter++;
            }
            //System.out.println(counter+": "+stepcount);
        }
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
