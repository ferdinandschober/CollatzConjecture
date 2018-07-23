package com.base.tests;
public class speedtest
{
    public static void testandvsmodulo()
    {
        long startTime = System.nanoTime();
        boolean n;
        for(long i = 0; i < 1000000000; i++)
        {
            n = (i & 1)==0;
        }
        
        System.out.println((float)(System.nanoTime()-startTime)/1000000000);
        startTime = System.nanoTime();
        for(long i = 0; i < 1000000000; i++)
        {
            n = (i % 2)==0;
        }
        System.out.println((float)(System.nanoTime()-startTime)/1000000000);
    }
    
    public static void testdivisionvsshifting()
    {
        long startTime = System.nanoTime();
        long n;
        for(long i = 0; i < 1000000000; i++)
        {
            n = i/2;
        }
        
        System.out.println((float)(System.nanoTime()-startTime)/1000000000);
        startTime = System.nanoTime();
        for(long i = 0; i < 1000000000; i++)
        {
            n = i>>1;
        }
        System.out.println((float)(System.nanoTime()-startTime)/1000000000);
    }
    
    public static void testmultiplikationvsshifting()
    {
        long startTime = System.nanoTime();
        long n;
        for(long i = 0; i < 1000000000; i++)
        {
            n = i*3;
        }
        
        System.out.println((float)(System.nanoTime()-startTime)/1000000000);
        startTime = System.nanoTime();
        for(long i = 0; i < 1000000000; i++)
        {
            n = (i<<1)+i+1;
        }
        System.out.println((float)(System.nanoTime()-startTime)/1000000000);
    }
    
    
    public static void asdf()
    {
        System.out.println((1<<1)+1+1);
    }
}