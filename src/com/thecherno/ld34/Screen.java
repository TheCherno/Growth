package com.thecherno.ld34;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.thecherno.ld34.maths.Vector2f;

public class Screen {

	private int[] pixels;
	private int width, height;

	private int xOffset, yOffset;

	private List<Light> lights = new ArrayList<Light>();

	public Screen(int[] pixels, int width, int height) {
		this.pixels = pixels;
		this.width = width;
		this.height = height;

		lights.add(new Light(250, 450));
	}

	public void clear(int color) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = color;
		}
	}

	public void fillRect(float x, float y, float width, float height, int color) {
		fillRect((int) x, (int) y, (int) width, (int) height, color);
	}

	public void fillRect(int x, int y, int width, int height, int color) {
		x -= xOffset;
		y -= yOffset;

		for (int yy = 0; yy < height; yy++) {
			int yo = y + yy;
			if (yo < 0 || yo >= this.height)
				continue;
			for (int xx = 0; xx < width; xx++) {
				int xo = x + xx;
				if (xo < 0 || xo >= this.width)
					continue;
				pixels[xo + yo * this.width] = color;
			}
		}
	}

	public void drawTexture(int x, int y, Texture texture) {
		x -= xOffset;
		y -= yOffset;

		for (int yy = 0; yy < texture.getHeight(); yy++) {
			int yo = y + yy;
			if (yo < 0 || yo >= this.height)
				continue;
			for (int xx = 0; xx < texture.getWidth(); xx++) {
				int xo = x + xx;
				if (xo < 0 || xo >= this.width)
					continue;
				int color = texture.getPixels()[xx + yy * texture.getWidth()];
				int alpha = (color & 0xff000000) >> 24;
				if (alpha < 0)
					pixels[xo + yo * this.width] = color;
			}
		}
	}

	public void drawCurve(Vector2f[] points, int color, Level level) {
		drawCurve(points[0], points[1], points[2], points[3], color, level);
	}

	private void debugRenderCurvePoints(Vector2f point0, Vector2f point1, Vector2f point2, Vector2f point3) {
		int size = 16;
		fillRect(point0.x - size / 2, point0.y - size / 2, size / 2, size / 2, 0xff4444);
		fillRect(point1.x - size / 2, point1.y - size / 2, size / 2, size / 2, 0xff4444);
		fillRect(point2.x - size / 2, point2.y - size / 2, size / 2, size / 2, 0xff4444);
		fillRect(point3.x - size / 2, point3.y - size / 2, size / 2, size / 2, 0xff4444);
	}

	public void drawCurve(Vector2f point0, Vector2f point1, Vector2f point2, Vector2f point3, int color, Level level) {
		// debugRenderCurvePoints(point0, point1, point2, point3);

		float segmentCount = 750;
		float t = 0.0f;
		for (int i = 0; i <= segmentCount; i++) {
			t = i / (float) segmentCount;
			Vector2f pixel = calculateBezierPoint(t, point0, point1, point2, point3);
			int x = (int) pixel.x;
			int y = (int) pixel.y;
			// if (x < 0 || x >= this.width || y < 0 || y >= this.height)
			// continue;
			int yo = y - yOffset;
			int thickness = (yo) / 64 - 4;
			for (Wall wall : level.walls) {
				if (wall.collision(new Rectangle(x, y, thickness, thickness))) {
					if (i > segmentCount - 3)
						level.gameOver();
					else
						level.destroyWall(wall);
				}
			}
			
			if (i > segmentCount - 3) {
				if (x < 50 || x > 480)
					level.gameOver();
			}
			
			fillRect(x, y, thickness, thickness, color);
			// pixels[x + y * this.width] = color;
		}
	}

	public void lightPass() {
		Light light = lights.get(0);
		light.y = -400;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				float attenuation = 800.0f / getDistance(x, y, light.x, light.y);
				int color = pixels[x + y * width];
				int r = (color & 0xff0000) >> 16;
				int g = (color & 0xff00) >> 8;
				int b = (color & 0xff);
				r *= attenuation;
				g *= attenuation;
				b *= attenuation;
				if (r > 255)
					r = 255;
				if (g > 255)
					g = 255;
				if (b > 255)
					b = 255;
				pixels[x + y * width] = r << 16 | g << 8 | b;
			}
		}
	}

	private float getDistance(int x0, int y0, int x1, int y1) {
		int x = x0 - x1;
		int y = y0 - y1;
		return (float) Math.sqrt(x * x + y * y);
	}

	private Vector2f calculateBezierPoint(float t, Vector2f p0, Vector2f p1, Vector2f p2, Vector2f p3) {
		float u = 1 - t;
		float tt = t * t;
		float uu = u * u;
		float uuu = uu * u;
		float ttt = tt * t;

		Vector2f p = p0.multiply(uuu); // first term
		p = p.add(p1.multiply(3 * uu * t)); // second term
		p = p.add(p2.multiply(3 * u * tt)); // third term
		p = p.add(p3.multiply(ttt)); // fourth term

		return p;
	}

	public void setCamera(Camera camera) {
		xOffset = camera.x;
		yOffset = camera.y;
	}

	public int getXOffset() {
		return xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}
	
}
