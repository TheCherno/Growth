package com.thecherno.ld34;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.thecherno.ld34.maths.Vector2f;

public class BrokenWall extends Entity {

	private static SpriteSheet sheet = new SpriteSheet(new Texture("res/RockFragmentsSpriteSheet.png"), 3, 12);
	private static AnimatedSprite[] animatedSprites = new AnimatedSprite[sheet.getTextures().length / 3];
	private static Vector2f[] offsets = new Vector2f[12];
	private static Random random = new Random();
	
	private int time = 0;
	
	class Animation {
		List<Vector2f> position = new ArrayList<Vector2f>();
	}
	private Animation[] pieces = new Animation[12];

	public BrokenWall(int x, int y) {
		super(x, y);
		if (animatedSprites[0] == null) {
			for (int i = 0; i < animatedSprites.length; i++) {
				Texture[] textures = new Texture[3];
				for (int a = 0; a < 3; a++)
					textures[a] = sheet.getTextures()[a + i * 3];
				animatedSprites[i] = new AnimatedSprite(textures);
			}

			offsets[0] = new Vector2f(-15, 15);
			offsets[1] = new Vector2f(-26, 0);
			offsets[2] = new Vector2f(-26, -19);
			offsets[3] = new Vector2f(-8, -21);
			offsets[4] = new Vector2f(12, -21);
			offsets[5] = new Vector2f(16, -7);
			offsets[6] = new Vector2f(26, 5);
			offsets[7] = new Vector2f(23, 14);
			offsets[8] = new Vector2f(-8, -1);
			offsets[9] = new Vector2f(2, -3);
			offsets[10] = new Vector2f(6, 5);
			offsets[11] = new Vector2f(5, 17);
			
		}
		
		for (int i = 0; i < pieces.length; i++) {
			pieces[i] = new Animation();
		}
		for (int i = 0; i < 12; i++) {
			pieces[i].position.clear();
			pieces[i].position.add(new Vector2f(x, y));
			for (int a = 1; a < 24; a++) {
				Vector2f position = pieces[i].position.get(a - 1);
				Vector2f result = position.add(offsets[i].multiply(0.4f));
				result = result.add(new Vector2f(0, a * 4));
				pieces[i].position.add(result);
			}
		}
		
	}

	public void update() {
		time++;
		for (int i = 0; i < animatedSprites.length; i++) {
			animatedSprites[i].update();
		}
		if (time % 3 == 0)
			frame++;
		
		if (frame > 23) {
			frame = 23;
			remove();
		}
	}

	int frame = 0;
	public void render(Screen screen) {
		for (int i = 0; i < animatedSprites.length; i++) {
			Vector2f position = pieces[i].position.get(frame);
			screen.drawTexture((int)(position.x + offsets[i].x), (int)(position.y + offsets[i].y), animatedSprites[i].get());
			
		}
	}

}
