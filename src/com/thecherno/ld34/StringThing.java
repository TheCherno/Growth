package com.thecherno.ld34;

import java.awt.Color;
import java.awt.Font;

import com.thecherno.ld34.maths.Vector2f;

public class StringThing {
	
	public String string;
	public Vector2f position;
	public Font font = new Font("Helvetica", 0, 24);
	public boolean fixed = false;
	public Color color = Color.WHITE; 
	
	public StringThing(String string, Vector2f position) {
		this.string = string;
		this.position = position;
	}

}
