package com.base.display;
import java.applet.Applet;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.base.Engine;

public class Display extends Applet implements Runnable {

    public static final int width = 1280;
    public static final int height = 720;
    int displayscaling = 1;
    boolean logFramerate = true;

    BufferedImage localBufferedImage = new BufferedImage(width,height,1);
    int[] imageData = ((DataBufferInt) localBufferedImage.getRaster().getDataBuffer()).getData();
    Engine engine = new Engine();
    double fps;
    long lastTime;
    long frameTime;

    int frames;
    int statsUpdateRate = 1000;

    public void run() {
        lastTime = System.nanoTime();
        while (true) {
            try {
                update();
                if (!this.isActive()) 
                    return;      
                draw();
                stats();
                //Thread.sleep(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void warten(long ns)
    {
        try {
            Thread.sleep(ns);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void stats() {
        frames++;

        if (frames % statsUpdateRate == 0) 
        {
            frameTime = System.nanoTime() - lastTime;
            fps = statsUpdateRate*(1.0 / (frameTime / (double) 1000000000));
            lastTime = System.nanoTime();
            if(logFramerate)System.out.println(fps+" "+frames);
        }
    }

    private void update() {
        engine.update(this,frames);
    }

    private void draw() {
        this.getGraphics().drawImage(localBufferedImage, 0, 0, width*displayscaling, height*displayscaling, null);
    }

    public void shiftImage(int a)
    {
        for(int i = 0; i < imageData.length/width;i++)
            for(int j = a; j < imageData.length/height; j++)
                imageData[i*width+j-a] = imageData[i*width+j];
        for(int k = 0; k < height; k++)
            for(int l = a; l > 0; l--)
                draw(width-l,k,0,0,0);
    }

    @Override
    public void start() {
        new Thread(this).start();
    }

    public void draw(int x, int y,int color) {
        if(y*width+x<imageData.length && x>=0 && y>=0)
            imageData[y*width+x] = color;
    }

    public void draw(int x, int y,int r, int g, int b)
    {
        draw(x,y,r<<16|g<<8|b);
    }

    public int getPixel(int x, int y) {
        return imageData[y*width+x];
    }
}
