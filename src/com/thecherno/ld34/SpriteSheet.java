package com.thecherno.ld34;

public class SpriteSheet {

	private int width, height;
	private Texture[] textures;
	
	public SpriteSheet(Texture texture, int xCount, int yCount) {
		int[] pixels = texture.getPixels();
		
		int sw = texture.getWidth() / xCount;
		int sh = texture.getHeight() / yCount;
		
		textures = new Texture[xCount * yCount];
		int index = 0;
		for (int yc = 0; yc < yCount; yc++) {
			for (int xc = 0; xc < xCount; xc++) {
				int[] sp = new int[sw * sh];
				for (int y = 0; y < sh; y++) {
					int yo = yc * sh + y;
					for (int x = 0; x < sw; x++) {
						int xo = xc * sw + x;
						sp[x + y * sw] = pixels[xo + yo * texture.getWidth()];
					}					
				}
				textures[index++] = new Texture(sp, sw, sh);
			}			
		}
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Texture[] getTextures() {
		return textures;
	}
	
}
