package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MarioGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "SlimeWorld";
		cfg.foregroundFPS = 60;
		cfg.pauseWhenBackground = true;
		cfg.pauseWhenMinimized = true;
		cfg.vSyncEnabled = true;
		cfg.width = 1280;
		cfg.height = 720;
		new LwjglApplication(new MarioGame(), cfg);
	}
}
