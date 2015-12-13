package com.thecherno.ld34;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import com.thecherno.ld34.maths.Vector2f;

public class Input implements KeyListener, MouseMotionListener, MouseListener {
	
	private static boolean[] keys = new boolean[65536];
	private static boolean[] mbuttons = new boolean[16];
	private static int mx, my;
	
	private static List<Integer> pressed = new ArrayList<Integer>();

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
		mbuttons[e.getButton()] = true;
	}

	public void mouseReleased(MouseEvent e) {
		mbuttons[e.getButton()] = false;
	}

	public void mouseDragged(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		pressed.remove(new Integer(e.getKeyCode()));
	}

	public void keyTyped(KeyEvent arg0) {
	}
	
	public static boolean isMouseButtonPressed(int button) {
		return mbuttons[button];
	}

	public static boolean isKeyPressed(int key) {
		return keys[key];
	}
	
	public static int getMouseX() {
		return mx;
	}
	
	public static int getMouseY() {
		return my;
	}
	
	public static Vector2f getMousePosition() {
		return new Vector2f(mx, my);
	}

	public static boolean isKeyTyped(int key) {
		if (!keys[key] || pressed.contains(key))
			return false;
		
		pressed.add(key);
		return true;
	}
}
