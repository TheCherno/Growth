package com.thecherno.ld34;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {

	private int width, height;
	private int[] pixels;

	public Texture(String path) {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Texture(int[] pixels, int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		 return height;
	}
	
	public int[] getPixels() {
		return pixels;
	}

}
