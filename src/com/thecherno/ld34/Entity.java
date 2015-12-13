package com.thecherno.ld34;

public class Entity {

	protected int x, y;
	protected boolean removed = false;

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void update() {
	}

	public void render(Screen screen) {
	}
}
