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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MarioGame;
import Sprites.Bullet;
import Sprites.Coin;
import Sprites.Door;
import Sprites.Enemy;
import Sprites.Key;
import Sprites.Slime;
import Sprites.Slime.State;
import Sprites.Weapon;
import Tools.B2WorldCreator;
import Tools.WorldContactListener;

public class PlayScreen implements Screen {
	public MarioGame game;
	private boolean showHud = false;
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
	
	//Coins
	public Array<Coin> coins = new Array<>();
	
	//Monsters
	public Array<Enemy> monsterList = new Array<>();
	
	//Keys
	public Array<Key> keys = new Array<>();
	
	//Weapon
	public Weapon weapon;
	
	//Door
	public Door door;
	
	//HUD
	public Hud hud;
	
	//LVL nbr
	public int lvl;
	private float timer;
	private float timeCount = 0;
	
	public PlayScreen(MarioGame game, int lvl) {
		this.atlas = new TextureAtlas("slime_vert.txt");
		this.game = game;
		this.gameCam = new OrthographicCamera();
		this.gamePort = new FitViewport(MarioGame.V_WIDTH / MarioGame.PPM, MarioGame.V_HEIGHT / MarioGame.PPM , gameCam);
		this.lvl = lvl;
		this.hud = new Hud(game.batch,lvl);
		this.mapLoader = new TmxMapLoader();
		this.parameters = new Parameters();
		parameters.textureMinFilter = TextureFilter.Linear;
		parameters.textureMagFilter = TextureFilter.Nearest;
		String maplvl = "map" + lvl + "/map.tmx";
		this.map = mapLoader.load(maplvl,parameters);
		this.renderer = new OrthogonalTiledMapRenderer(this.map, 1/ MarioGame.PPM);
		this.gameCam.position.set(gamePort.getWorldWidth(),gamePort.getWorldHeight()*2, 0);
		
		this.world = new World(new Vector2(0,-10), true);	//créer un "monde" avec une gravité
		b2dr = new Box2DDebugRenderer();
		
		new B2WorldCreator(this.world,this.map, this);
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
		
		if(player.getState() != State.DEAD) {
			
		
			if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				if(player.hitGround == true){
					player.b2body.applyLinearImpulse(new Vector2(0,3.5f), player.b2body.getWorldCenter(), true);
					player.hitGround = false;
				}
			}
				
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
				player.direction=Slime.right;
				player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
				player.direction=Slime.left;
				player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				player.isSmashing = true;
			}
			if(!Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				player.isSmashing = false;
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				
				if(player.canShoot) {
					player.shooting = true;
					for(int i = 0; i<player.ammo.size ; i++) {
						if(player.ammo.get(i).destroyed == true) {
							player.ammo.removeIndex(i);
							player.ammo.add(new Bullet(this.world,player,player.direction));
							break;
						}
					}
				}
						
			}
		}
	
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			pauseGame = pauseGame == true ? false : true;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.TAB)) {
			this.showHud = true;
		}
		else {
			this.showHud = false;
		}
		
	}
	
	public void update(float time) {
		if(player.isDead) {
			timeCount += time;
			if(timeCount >= 0.7f) {
				game.setScreen(MarioGame.dead);
				timeCount = 0;
			}
			
			
		}
			player.update(time);
			hud.update(time);
			door.update(time,player.nbr_cles);
			weapon.update(time);
			camUpdate();
			
			
			if(weapon.destroyed == true && weapon.isDestroyed == false) {
				world.destroyBody(weapon.body);
				weapon.isDestroyed = true;
			}
		
		for(int i = 0; i<coins.size; i++) {
			if(coins.get(i).destroyed == true) {
				world.destroyBody(coins.get(i).body);
				player.score += 1;						//COINS
				System.out.println(player.score);
				hud.addScore(50);
				coins.removeIndex(i);
			}
			else {
				coins.get(i).update(time);
			}
		}
		
		for(int i = 0; i<keys.size; i++) {
			if(keys.get(i).destroyed == true) {
				world.destroyBody(keys.get(i).body);
				player.nbr_cles += 1;					//CLES
				hud.addKey();
				keys.removeIndex(i);
			}
			else {
				keys.get(i).update(time);
			}
		}
		
		
		
		
		for(int i = 0; i<monsterList.size; i++) {
			if(monsterList.get(i).destroyed == true) {
			//	player.b2body.applyLinearImpulse(new Vector2(0,2.5f), player.b2body.getWorldCenter(), true);
				world.destroyBody(monsterList.get(i).body);
				monsterList.removeIndex(i);		
				hud.addScore(200);
														//MONSTRES
			}
			else {	
				monsterList.get(i).update();
			}
		}
		
		
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
		door.draw(game.batch);
		if(!weapon.isDestroyed) {
			weapon.draw(game.batch);
		}
		
		player.draw(game.batch);
		
		for(Bullet bullet : player.ammo) {
			bullet.draw(game.batch);
		}
		for(Coin coin : coins) {
			coin.draw(game.batch);
		}
		for(Key key : keys) {
			key.draw(game.batch);
		}
		for(Enemy enemy : monsterList) {
			enemy.draw(game.batch);
		}
		
		
		this.game.batch.end();
		if(showHud) {
			game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
			hud.stage.draw();
		}
		if(pauseGame) {
			game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
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
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        System.out.println("disposé");
	}
		
}
