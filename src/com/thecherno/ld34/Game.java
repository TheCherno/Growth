package com.thecherno.ld34;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {

	public static final int WIDTH = 540;
	public static final int HEIGHT = 960;
	public static final int SCALE = 1;
	
	private boolean running = false;
	private JFrame frame;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	private Screen screen = new Screen(pixels, WIDTH, HEIGHT);
	private Input input;
	private static Level level;
	
	public static List<StringThing> stringBuffer = new ArrayList<StringThing>();
	
	public static Sound[] sounds = new Sound[5];

	public Game() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		input = new Input();
		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		
		level = new Level();
		
		loadSounds();
	}
	
	private void loadSounds() {
		sounds[0] = new Sound("res/audio/Action.wav");
		sounds[1] = new Sound("res/audio/Death.wav");
		sounds[2] = new Sound("res/audio/Explosion.wav");
		sounds[3] = new Sound("res/audio/Explosion2.wav");
		sounds[4] = new Sound("res/audio/Growth.mp3");
		
		sounds[2].setGain(0.3);
		sounds[3].setGain(0.3);
	}
	
	public void start() {
		running = true;
		new Thread(this).start();
	}
	
	private void init() {
		frame = new JFrame("Grow");
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		sounds[4].loop();
	}

	public void run() {
		init();
		
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				frames = 0;
				updates = 0;
			}
		}
	}
	
	private void update() {
		level.update();
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		screen.clear(0x267F44);
		level.render(screen);
		screen.lightPass();
		
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		for (StringThing str : stringBuffer) {
			g.setColor(Color.WHITE);
			g.setFont(str.font);
			g.setColor(str.color);
			int x = (int)str.position.x;
			int y = (int)str.position.y;
			if (str.fixed) {
				x -= screen.getXOffset();
				y -= screen.getYOffset();
			}
			g.drawString(str.string, x, y);
		}
		
		stringBuffer.clear();
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Game().start();
	}

	public static void restart() {
		level = new Level();
		sounds[0].play();
	}
}
