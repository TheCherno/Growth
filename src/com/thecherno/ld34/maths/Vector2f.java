package com.thecherno.ld34.maths;

public class Vector2f {

	public float x, y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f multiply(Vector2f other) {
		return new Vector2f(x * other.x, y * other.y);
	}
	
	public Vector2f multiply(float value) {
		return new Vector2f(x * value, y * value);
	}

	public Vector2f add(Vector2f other) {
		return new Vector2f(x + other.x, y + other.y);
	}

	public Vector2f subtract(Vector2f other) {
		return new Vector2f(x - other.x, y - other.y);
	}
	
	public Vector2f clone() {
		return new Vector2f(x, y);
	}

	public String toString() {
		return x + ", " + y;
	}
}
