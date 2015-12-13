package com.thecherno.ld34.maths;

public class Vector3f {

	public float x, y, z;

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f multiply(Vector3f other) {
		return new Vector3f(x * other.x, y * other.y, z * other.z);
	}
	
	public Vector3f multiply(float value) {
		return new Vector3f(x * value, y * value, z * value);
	}

	public Vector3f add(Vector3f other) {
		return new Vector3f(x + other.x, y + other.y, z + other.z);
	}

}
