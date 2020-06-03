package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MarioGame;

public class StartScreen implements Screen {
	private MarioGame game;
	
	public Stage stage;
	
	TextButton newGame;
	TextButton commandes;
	TextButton exit;
	

	public StartScreen(MarioGame game) {
		this.game = game;
		stage = new Stage(new ScreenViewport());
		Table table = new Table();
		
		Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
		bgPixmap.setColor(Color.DARK_GRAY);
		bgPixmap.fill();
		TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

		table.setBackground(textureRegionDrawableBg);
		
		table.setFillParent(true);
		table.setDebug(false);
		stage.addActor(table);
		
		Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		 
		newGame = new TextButton("Nouvelle partie", skin);
		commandes = new TextButton("Commandes", skin);
		exit = new TextButton("Quitter", skin);
		
		table.add(newGame).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(commandes).fillX().uniformX();
		table.row();
		table.add(exit).fillX().uniformX();
	}
	
	
	public void handleInput(float delta) {

	}
	
	@Override
	public void show() {
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();				
			}
		});
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(MarioGame.choice);	
				stage.unfocusAll();
				dispose();
			}
		});
	}

	@Override
	public void render(float delta) {
		handleInput(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}


	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dispose() {
		stage.dispose();
		
	}
	
	
}
