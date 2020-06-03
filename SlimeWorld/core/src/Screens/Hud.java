package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MarioGame;

public class Hud {
	private Viewport viewport;
	public Stage stage;
	private Integer worldTimer;
	private float timeCount;
	public Integer score;
	public Integer keyCount;
	
	Label countDownLabel;
	public Label scoreLabel;
	Label timeLabel;
	Label levelLabel;
	Label keyLabel;
	Label slimeLabel;
	
	public Hud(SpriteBatch batch, int lvl) {
		worldTimer = 300;
		timeCount = 0;
		score = 0;
		keyCount = 0;
		
		this.viewport = new FitViewport(MarioGame.V_WIDTH*1.2f, MarioGame.V_HEIGHT*1.2f, new OrthographicCamera());
		
		stage = new Stage(viewport, batch);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		countDownLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		keyLabel = new Label(String.format("%01d",keyCount), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel = new Label("TEMPS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		levelLabel = new Label("NIVEAU "+ lvl, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		slimeLabel = new Label("SLIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		table.add(slimeLabel).expandX();
		table.add(scoreLabel).expandX();
		table.add(timeLabel).expandX();
		table.row();
		table.add(levelLabel);
		table.add(keyLabel);
		table.add(countDownLabel);
		
		stage.addActor(table);
		
	}
	
	public void update(float time) {
		timeCount += time;
		if(timeCount >= 1) {
			worldTimer --;
			countDownLabel.setText(String.format("%03d",worldTimer));
			timeCount = 0;
		}
		
		scoreLabel.setText(score);
		keyLabel.setText(keyCount);
	}
	
	public void dispose() {
		stage.dispose();
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public void addKey() {
		this.keyCount += 1;
	}
	
}	