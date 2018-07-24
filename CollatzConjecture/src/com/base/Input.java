package com.base;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
/*
 * Copyright (c) 2014, Benny Bobaganoosh
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Stores the current state of any user input devices, and updates them with new
 * input events.
 * 
 * @author Benny Bobaganoosh (thebennybox@gmail.com)
 */
public class Input implements KeyListener, FocusListener,
		MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {
	private boolean[] keys = new boolean[65536];
	private boolean[] mouseButtonsPressed = new boolean[4];
	private boolean[] mouseButtonsClicked = new boolean[4];
	private int mouseX = 0;
	private int mouseY = 0;
	private boolean resized = false;

	private int mouseWheelRotation = 0;
	
	
	/** Updates state when the mouse is dragged */
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	/** Updates state when the mouse is moved */
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	/** Updates state when the mouse is clicked */
	public void mouseClicked(MouseEvent e) {
		int code = e.getButton();
		if (code > 0 && code < mouseButtonsClicked.length)
			mouseButtonsClicked[code] = true;
	}

	/** Updates state when the mouse enters the screen */
	public void mouseEntered(MouseEvent e) {
	}

	/** Updates state when the mouse exits the screen */
	public void mouseExited(MouseEvent e) {
	}

	/** Updates state when a mouse button is pressed */
	public void mousePressed(MouseEvent e) {
		int code = e.getButton();
		if (code > 0 && code < mouseButtonsPressed.length)
			mouseButtonsPressed[code] = true;
	}

	/** Updates state when a mouse button is released */
	public void mouseReleased(MouseEvent e) {
		int code = e.getButton();
		if (code > 0 && code < mouseButtonsPressed.length)
			mouseButtonsPressed[code] = false;
	}

	/** Updates state when the window gains focus */
	public void focusGained(FocusEvent e) {
	}

	/** Updates state when the window loses focus */
	public void focusLost(FocusEvent e) {
		for (int i = 0; i < keys.length; i++)
			keys[i] = false;
		for (int i = 0; i < mouseButtonsPressed.length; i++)
			mouseButtonsPressed[i] = false;
	}

	/** Updates state when a key is pressed */
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code > 0 && code < keys.length)
			keys[code] = true;
	}

	/** Updates state when a key is released */
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code > 0 && code < keys.length)
			keys[code] = false;
	}

	/** Updates state when a key is typed */
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * gets whether or not a particular key is currently pressed.
	 * 
	 * @param key The key to test
	 * @return Whether or not key is currently pressed.
	 */
	public boolean getKey(int key) {
		return keys[key];
	}

	/**
	 * gets whether or not a particular mouse button is currently pressed.
	 * 
	 * @param button The button to test
	 * @return Whether or not the button is currently pressed.
	 */
	public boolean getMousePressed(int button) {
		return mouseButtonsPressed[button];
	}

	/**
	 * gets the location of the mouse cursor on x, in pixels.
	 * @return The location of the mouse cursor on x, in pixels
	 */
	public int getMouseX() {
		return mouseX;
	}

	/**
	 * gets the location of the mouse cursor on y, in pixels.
	 * @return The location of the mouse cursor on y, in pixels
	 */
	public int getMouseY() {
		return mouseY;
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		resized  = true;
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean getResized()
	{
		return resized;
	}
	
	public void setResized(boolean value)
	{
		resized = value;
	}

	public boolean getMouseButtonsClicked(int button) {
		return mouseButtonsClicked[button];
	}
	
	public void setMouseButtonsClicked(int button, boolean value) {
		mouseButtonsClicked[button]=value;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.mouseWheelRotation = e.getWheelRotation();
	}

	public int getMouseWheelRotation() {
		return mouseWheelRotation;
	}

	public void setMouseWheelRotation(int mouseWheelRotation) {
		this.mouseWheelRotation = mouseWheelRotation;
	}
}
