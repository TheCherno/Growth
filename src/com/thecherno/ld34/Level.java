package com.thecherno.ld34;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.thecherno.ld34.maths.Vector2f;

public class Level {

	private Camera camera;

	private int selectionContext;
	private Vector2f[] points = new Vector2f[4];

	public List<Wall> walls = new ArrayList<Wall>();
	
	private Vector2f[] rects = new Vector2f[22 * 2];

	private List<Entity> entities = new ArrayList<Entity>();

	private int time = 0;

	private Random random = new Random();
	private int force = 0;
	private int state = 0;
	private int score = 0;
	
	private int moveIndex = 0;
	
	private int tier = 0;
	
	private List<StringThing> tutorial = new ArrayList<StringThing>();

	public Level() {
		camera = new Camera(0, -1000);

		points[0] = new Vector2f(500, 400);
		points[1] = new Vector2f(600, 200);
		points[2] = new Vector2f(500, 50);
		points[3] = new Vector2f(400, -500);

		int i = 0;
		for (int y = 0; y < 22; y++) {
			rects[i++] = new Vector2f(20, -y * 50);
			rects[i++] = new Vector2f(480, -y * 50);
		}
		
		help();
	}
	
	private void help() {
		tutorial.add(createMessage("You are a plant.", 140, -1050));
		tutorial.add(createMessage("You are growing.", 130, -1250));
		tutorial.add(createMessage("Use the left and right", 100, -1450));
		tutorial.add(createMessage("arrow keys to move.", 110, -1410));
		tutorial.add(createMessage("Try not to hit any walls.", 90, -1650));
		tutorial.add(createMessage("Grow as tall and strong", 80, -1850));
		tutorial.add(createMessage("as you can!", 170, -1810));
		
		tutorial.add(createMessage("Made by Yan Chernikov", 90, -2450));
		tutorial.add(createMessage("For Ludum Dare 34", 125, -2410));
	}
	
	private StringThing createMessage(String text, int x, int y) {
		StringThing result = new StringThing(text, new Vector2f(x, y));
		result.font = new Font("Helvetica", Font.BOLD, 32);
		result.fixed = true;
		result.color = new Color(0x267F44);
		return result;
	}

	public void add(Entity entity) {
		entities.add(entity);
	}

	public void update() {
		if (state == 0) {
			for (int i = 0; i < walls.size(); i++) {
				if (walls.get(i).isDestroyed())
					walls.remove(i--);
			}

			for (int i = 0; i < entities.size(); i++) {
				if (entities.get(i).isRemoved()) {
					entities.remove(i--);
					continue;
				}
				entities.get(i).update();
			}

			time++;

			if (Input.isKeyPressed(KeyEvent.VK_1))
				selectionContext = 0;
			if (Input.isKeyPressed(KeyEvent.VK_2))
				selectionContext = 1;
			if (Input.isKeyPressed(KeyEvent.VK_3))
				selectionContext = 2;
			if (Input.isKeyPressed(KeyEvent.VK_4))
				selectionContext = 3;

			if (Input.isMouseButtonPressed(MouseEvent.BUTTON1)) {
				points[selectionContext] = Input.getMousePosition();
			}

			int speed = 5;

			if (Input.isKeyPressed(KeyEvent.VK_LEFT))
				points[3].x -= speed;
			if (Input.isKeyPressed(KeyEvent.VK_RIGHT))
				points[3].x += speed;

			int cameraSpeed = 2;
			if (camera.y < -100000) {
				if (tier != 6) {
					speedUpMessage();
				}
				tier = 6;
				cameraSpeed = 8;
			}
			else if (camera.y < -80000) {
				if (tier != 5) {
					speedUpMessage();
				}
				tier = 5;
				cameraSpeed = 7;
			}
			else if (camera.y < -60000) {
				if (tier != 4) {
					speedUpMessage();
				}
				tier = 4;
				cameraSpeed = 6;
			}
			else if (camera.y < -40000) {
				if (tier != 3) {
					speedUpMessage();
				}
				tier = 3;
				cameraSpeed = 5;
			}
			else if (camera.y < -20000) {
				if (tier != 2) {
					speedUpMessage();
				}
				tier = 2;				
				cameraSpeed = 4;
			}
			else if (camera.y < -10000) {
				if (tier != 1) {
					speedUpMessage();
				}
				tier = 1;
				cameraSpeed = 3;
			}
			
			for (int i = 0; i < cameraSpeed; i++) {
				camera.y--;
				if (camera.y % 50 == 0) {
					rects[moveIndex++ % rects.length].y -= 22 * 50;
					rects[moveIndex++ % rects.length].y -= 22 * 50;
				}
			}
			
			
			score += cameraSpeed;

			points[0] = new Vector2f((float) Math.sin(time / 50.0) * 100.0f + 280, camera.y + 1000);
			points[1] = new Vector2f((float) Math.sin(time / 40.0) * 200.0f + 270, camera.y + 900);
			points[2] = new Vector2f((float) (Math.sin(time / 20.0) * 180.0) + 250,
					(float) Math.sin(time / 50.0) * 100.0f + camera.y + 600);
			points[3].y -= cameraSpeed;

			if (time % (random.nextInt(100) + 100) == 0) {
				force = random.nextInt(9) - 4;
				generate();
			}
			points[3].x += force;
		} else if (state == 1) {
			if (Input.isKeyPressed(KeyEvent.VK_LEFT) && Input.isKeyPressed(KeyEvent.VK_RIGHT))
				Game.restart();
		}
	}

	private void speedUpMessage() {
		tutorial.add(createMessage("Grow Faster!", 175, camera.y - 80));
	}

	private void generate() {
		if (camera.y < -20000) {
			for (int i = 0; i < 3; i++)
				walls.add(new Wall(random.nextInt(400) + 50, camera.y - 1000));
		}
		else if (camera.y < -8000) {
			for (int i = 0; i < 2; i++)
				walls.add(new Wall(random.nextInt(400) + 50, camera.y - 1000));
		}
		else if (camera.y < -3500) {
			for (int i = 0; i < 1; i++)
				walls.add(new Wall(random.nextInt(400) + 50, camera.y - 1000));
		}
	}

	public void render(Screen screen) {
		if (state == 0) {
			screen.setCamera(camera);
			
			for (Vector2f rect : rects) {
				screen.fillRect(rect.x, rect.y, 32, 32, 0x065F14);
			}
			for (Wall wall : walls) {
				wall.render(screen);
			}
	
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).render(screen);
			}
			screen.drawCurve(points, 0xFF54, this);
			
			for (StringThing string : tutorial)
				Game.stringBuffer.add(string);
			
			StringThing scoreString = new StringThing("" + score, new Vector2f(65, 40));
			scoreString.font = new Font("Helvetica", Font.PLAIN, 32);
			scoreString.color = new Color(0x068F34);
			Game.stringBuffer.add(scoreString);
		} else if (state == 1) {
			StringThing string = new StringThing("Game Over!", new Vector2f(50, 450));
			string.font = new Font("Helvetica", Font.BOLD, 80);
			string.color = new Color(0x528F44);
			Game.stringBuffer.add(string);
			
			string = new StringThing("Press Left and Right to start again!", new Vector2f(5, 490));
			string.font = new Font("Helvetica", Font.BOLD, 32);
			string.color = new Color(0x428F44);
			Game.stringBuffer.add(string);
			
			string = new StringThing("Score: " + score, new Vector2f(170, 590));
			string.font = new Font("Helvetica", Font.PLAIN, 32);
			string.color = new Color(0x328F44);
			Game.stringBuffer.add(string);
			
			string = new StringThing("Tweet your score @TheCherno!", new Vector2f(45, 790));
			string.font = new Font("Helvetica", Font.PLAIN, 32);
			string.color = new Color(0x267F44);
			Game.stringBuffer.add(string);
		}
	}

	public void destroyWall(Wall wall) {
		if (state == 1)
			return;
		if (wall.isDestroyed())
			return;
		
		Game.sounds[random.nextInt(2) + 2].play();
		score += 1000;
		add(new BrokenWall(wall.x + 16, wall.y + 16));
		wall.destroy();
	}

	public void gameOver() {
		state = 1;
		Game.sounds[1].play();
	}

}
