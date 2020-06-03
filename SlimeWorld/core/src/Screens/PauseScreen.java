package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MarioGame;

public class PauseScreen{
	private Viewport viewport;
	public Stage stage;
	public MarioGame game;
	
	TextButton Reprendre;
	TextButton Options;
	TextButton Quitter;
	
	public PauseScreen(final MarioGame game,SpriteBatch batch) {
		
		this.viewport = new ScreenViewport();
		
		stage = new Stage(viewport);
		this.game = game;
		Table table = new Table();
		table.center();
		table.setDebug(false);
		table.setFillParent(true);
		Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		
		Reprendre = new TextButton("Reprendre", skin);
		Options = new TextButton("Options", skin);
		Quitter = new TextButton("Quitter", skin);
		
		stage.addActor(table);
		table.add(Reprendre).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(Options).fillX().uniformX();
		table.row();
		table.add(Quitter).fillX().uniformX();
		
		Quitter.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(game.getScreen().equals(MarioGame.choice)) {
					Gdx.app.exit();
				}
				
				game.setScreen(MarioGame.choice);
			}
		});
		
		Reprendre.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				MarioGame.screen.pauseGame = false;
				
				if(game.getScreen().equals(MarioGame.choice)) {
					MarioGame.choice.pauseGame = false;
				}
				
				
				
			}
		});
		
	}
	
}	