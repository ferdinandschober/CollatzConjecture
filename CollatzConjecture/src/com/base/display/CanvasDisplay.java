package com.base.display;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
public class CanvasDisplay
{
    private Frame mainFrame;
    private Canvas canvas;
    private Graphics g;
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    BufferedImage localBufferedImage = new BufferedImage(WIDTH,HEIGHT,1);
    int[] imageData = ((DataBufferInt) localBufferedImage.getRaster().getDataBuffer()).getData();
    
    private static Font monoFont = new Font("Monospaced", Font.PLAIN, 14);
    
    public CanvasDisplay()
    {
        mainFrame = new Frame("Display");
        mainFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                    System.exit(0);
                }        
            });  
        canvas = new Canvas();
        canvas.setBounds(0,0,856,480);
        mainFrame.add(canvas);
        mainFrame.pack();
        mainFrame.setVisible(true);
        canvas.createBufferStrategy(2);
        g = canvas.getGraphics();
    }

    public void draw() {
        drawImage(localBufferedImage);   
    }

    public void shiftImage(int a)
    {
        for(int i = 0; i < imageData.length/WIDTH;i++)
            for(int j = a; j < imageData.length/HEIGHT; j++)
                imageData[i*WIDTH+j-a] = imageData[i*WIDTH+j];
        for(int k = 0; k < HEIGHT; k++)
            for(int l = a; l > 0; l--)
                draw(WIDTH-l,k,0,0,0);
    }

    public void drawImage(BufferedImage img)
    {
        g.drawImage(img,0, 0, WIDTH, HEIGHT,null);
    }

    public void draw(int x, int y,int color) {
        if(y*WIDTH+x<imageData.length && x>=0 && y>=0)
            imageData[y*WIDTH+x] = color;
    }

    public void draw(int x, int y,int r, int g, int b)
    {
        draw(x,y,r<<16|g<<8|b);
    }

    public int getPixel(int x, int y) {
        return imageData[y*WIDTH+x];
    }

    public void drawText(String s,int x,int y)
    {
        g.setColor(Color.GREEN);
        g.setFont(monoFont);  
        if(s!=null){
            g.drawString(s, x, y);
        }
    }
}