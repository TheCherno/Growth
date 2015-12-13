package com.thecherno.ld34;

import java.io.File;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {

	static {
		new JFXPanel();
	}

	private volatile MediaPlayer sound;
	private String soundFile = "";

	public Sound(String file) {
		create(file);
	}

	private void create(final String file) {
		final File soundFile = new File(file);
		if (!soundFile.exists()) {
			System.err.println("Sound File \"" + file + "\" not found!");
			return;
		}
		String[] strings = file.split("/");
		this.soundFile = strings[strings.length - 1];
		try {
			sound = new MediaPlayer(new Media(soundFile.toURI().toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		new Thread("Sound") {
			public void run() {
				sound.stop();
				sound.setCycleCount(0);
				sound.play();
			}
		}.start();
	}

	public void loop() {
		new Thread("Sound: " + soundFile) {
			public void run() {
				sound.stop();
				sound.setCycleCount(MediaPlayer.INDEFINITE);
				sound.play();
			}
		}.start();
	}

	public void stop() {
		sound.stop();
	}
	
	public void setGain(double value) {
		sound.setVolume(value);
	}
}