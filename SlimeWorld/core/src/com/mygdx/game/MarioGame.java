package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Screens.ChoiceScreen;
import Screens.DeadScreen;
import Screens.PauseScreen;
import Screens.PlayScreen;
import Screens.StartScreen;
import Tools.MapSelector;

public class MarioGame extends Game {
	public static PlayScreen screen;
	public static StartScreen start;
	public static PauseScreen pause;
	public static ChoiceScreen choice;
	public static DeadScreen dead;
	
	public static final float V_WIDTH = 400;
	public static final float V_HEIGHT = 250;
	public static final float PPM = 100;
	
	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short ENEMY_BIT = 4;
	public static final short BULLET_BIT = 8;
	public static final short WALL_BIT = 16;
	public static final short COIN_BIT = 32;
	public static final short DOOR_BIT = 64;
	public static final short DESTROYED_BIT = 128;
	
	public SpriteBatch batch;
	
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		pause = new PauseScreen(this,batch);
		screen = new PlayScreen(this,1);
		dead = new DeadScreen(this);
		start = new StartScreen(this);
		choice = new ChoiceScreen(this);
		Gdx.input.setInputProcessor(new InputMultiplexer(pause.stage,start.stage));
		setScreen(start);
		
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
