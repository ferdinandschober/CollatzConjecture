package com.base.display;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import javax.swing.JFrame;

import com.base.Input;
public class Display
{
    private JFrame frame;
    private Canvas canvas;
    private Graphics g;
    private int width = 856;
    private int height = 480;

	BufferedImage frameBuffer = new BufferedImage(getWidth(),getHeight(),1);
    int[] imageData = ((DataBufferInt) frameBuffer.getRaster().getDataBuffer()).getData();
    BufferStrategy bs;
    
    private static Font monoFont = new Font("Monospaced", Font.PLAIN, 14);
    
    public Input input;
    
    public Display()
    {
        frame = new JFrame("Display");
        frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                    System.exit(0);
                }        
            });  
        canvas = new Canvas();
        canvas.setBounds(0,0,getWidth(),getHeight());
        frame.add(canvas);
        frame.pack();
        updateSize();
        frame.setVisible(true);
        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();
        
        input = new Input();
        canvas.addKeyListener(input);
        canvas.addFocusListener(input);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);
        canvas.addMouseWheelListener(input);
        
        frame.addComponentListener(input);
        
        canvas.setFocusable(true);
        canvas.requestFocus();
        
    }

    public void drawFrameBuffer() {
        drawImage(frameBuffer);     
    }
    
    public void draw()
    {
    	 g.dispose();
         canvas.getBufferStrategy().show();
         g = canvas.getBufferStrategy().getDrawGraphics();
    }

    public void shiftImage(int a)
    {
        for(int i = 0; i < imageData.length/getWidth();i++)
            for(int j = a; j < imageData.length/getHeight(); j++)
                imageData[i*getWidth()+j-a] = imageData[i*getWidth()+j];
        for(int k = 0; k < getHeight(); k++)
            for(int l = a; l > 0; l--)
                draw(getWidth()-l,k,0,0,0);
    }

    public void drawImage(BufferedImage img)
    {
        g.drawImage(img,0, 0, getWidth(), getHeight(),null);
    }

    public void draw(int x, int y,int color) {
        if(y*width+x<imageData.length && x>=0 && y>=0)
            imageData[y*getWidth()+x] = color;
    }

    public void draw(int x, int y,int r, int g, int b)
    {
        draw(x,y,r<<16|g<<8|b);
    }

    public int getPixel(int x, int y) {
        return imageData[y*getWidth()+x];
    }

    public void drawText(String s,int x,int y)
    {
        g.setColor(Color.GREEN);
        g.setFont(monoFont);  
        if(s!=null){
            g.drawString(s, x, y);
        }
    }
    
    public Input getInput()
    {
    	return input;
    }
    
    public void updateSize()
    {
    	setWidth(frame.getContentPane().getWidth());
    	setHeight(frame.getContentPane().getHeight());
    	canvas.setSize(getWidth(), getHeight());
    	frameBuffer = new BufferedImage(getWidth(),getHeight(),1);
    	imageData = ((DataBufferInt) frameBuffer.getRaster().getDataBuffer()).getData();
    }
    
    public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void clearFrameBuffer()
	{
		Arrays.fill(imageData, 0);
	}
}