package com.thecherno.ld34;

import java.awt.Rectangle;

public class Wall {

	public int x, y;
	private int size = 64;
	
	private static Texture texture = new Texture("res/Rock.png");
	
	private boolean destroyed = false;

	public Wall(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void render(Screen screen) {
		screen.drawTexture(x, y, texture);
	}
	
	public boolean collision(Rectangle rectangle) {
		return rectangle.intersects(new Rectangle(x, y, texture.getWidth(), texture.getHeight()));
	}

	public void destroy() {
		destroyed = true;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}

}
