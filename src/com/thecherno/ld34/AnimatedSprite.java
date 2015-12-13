package com.thecherno.ld34;

public class AnimatedSprite {
	
	private Texture currentTexture;
	private Texture[] textures;
	private int counter = 0;
	private int time = 0;
	private int rate = 6;
	
	public AnimatedSprite(Texture[] textures) {
		this.textures = textures;
		currentTexture = textures[0];
	}
	
	public void update() {
		if (time++ % rate == 0) {
			counter = ++counter % textures.length;
			currentTexture = textures[counter];
		}
	}
	
	public Texture get() {
		return currentTexture;
	}
	
	public void setRate(int rate) {
		this.rate = rate;
	}
	
}
