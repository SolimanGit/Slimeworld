package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MarioGame;
import Sprites.Slime;
import Tools.B2WorldCreator2;
import Tools.WorldContactListener;

public class ChoiceScreen implements Screen{

	public MarioGame game;
	public boolean pauseGame = false;
	// Camera de jeu
	private OrthographicCamera gameCam;
	
	// Ajusteur de ratio d'écran
	public Viewport gamePort;
	
	// Map
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private Parameters parameters;
	
	//box 2D
	private World world;
	private Box2DDebugRenderer b2dr;
	private Vector3 camPosition;
	public Slime player;

	private TextureAtlas atlas;
	
	
	
	public ChoiceScreen(MarioGame game) {
		
		this.atlas = new TextureAtlas("slime_vert.txt");
		this.game = game;
		this.gameCam = new OrthographicCamera();
		this.gamePort = new FitViewport(MarioGame.V_WIDTH / MarioGame.PPM, MarioGame.V_HEIGHT / MarioGame.PPM , gameCam);
		
		this.mapLoader = new TmxMapLoader();
		this.parameters = new Parameters();
		parameters.textureMinFilter = TextureFilter.Linear;
		parameters.textureMagFilter = TextureFilter.Nearest;
		this.map = mapLoader.load("choix.tmx",parameters);
		this.renderer = new OrthogonalTiledMapRenderer(this.map, 1/ MarioGame.PPM);
		this.gameCam.position.set(gamePort.getWorldWidth(),gamePort.getWorldHeight()*2, 0);
		
		this.world = new World(new Vector2(0,0), true);	//créer un "monde" avec une gravité
		b2dr = new Box2DDebugRenderer();
		
		new B2WorldCreator2(this.world,this.map, this);
		player = new Slime(this.world, this);
		world.setContactListener(new WorldContactListener(game,this));
		
	}
	
	public TextureAtlas getAtlas() {
		return this.atlas;
	}

	@Override
	public void show() {
		
		
	}
	
	
	public void handleInput(float time) {
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
				player.b2body.setLinearVelocity(0, 1.8f);
		}
		else if(!Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			player.b2body.setLinearVelocity(0,0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
			player.direction=Slime.right;
			player.b2body.setLinearVelocity(1.8f,0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
			player.direction=Slime.left;
			player.b2body.setLinearVelocity(-1.8f,0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player.b2body.setLinearVelocity(0,-1.8f);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			pauseGame = pauseGame == true ? false : true;
		}
		
	}
	
	public void update(float time) {
		player.update(time);
		camUpdate();		
		world.step(1/60f, 6, 2);
		gameCam.update();
		renderer.setView(gameCam);
	}

	@Override
	public void render(float delta) {
		handleInput(delta);
		if(!pauseGame) {
			update(delta);
		}
			
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render();
		//b2dr.render(world, gameCam.combined);
		game.batch.setProjectionMatrix(gameCam.combined);
		this.game.batch.begin();
		player.draw(game.batch);
		this.game.batch.end();
		if(pauseGame) {
			game.batch.setProjectionMatrix(MarioGame.pause.stage.getCamera().combined);
			MarioGame.pause.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
			MarioGame.pause.stage.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);		
		
	}
	
	public void camUpdate() {
		camPosition = this.gameCam.position;
		camPosition.x += (player.b2body.getPosition().x - camPosition.x) * 0.05;
		camPosition.y += (player.b2body.getPosition().y - camPosition.y) * 0.05;
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		b2dr.dispose();
		world.dispose();
	}
			
}
