package Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MarioGame;

import Screens.ChoiceScreen;
import Screens.PlayScreen;

public class Slime extends Sprite {
	public World world;
	public Body b2body;
	public boolean jumping = false;
	public static String left = "LEFT";
	public static String right = "RIGHT";
	public String direction = "RIGHT";
	public boolean hitGround;
	public boolean hitWall;
	public boolean shooting;
	public boolean isDead = false;
	public int score;
	public int nbr_cles = 0;
	public boolean isChoice;
	public boolean canShoot = false;
	
	
	public enum State { FALLING , JUMPING, STANDING, RUNNING, SLIDING, SHOOTING, SMASHING, GOINGUP, GOINGDOWN, DEAD };
	public State currentState;
	public State previousState;
	private Animation<TextureRegion> slimeRun;
	private Animation<TextureRegion> slimeJump;
	private Animation<TextureRegion> slimeSlide;
	private Animation<TextureRegion> slimeShoot;
	private Animation<TextureRegion> slimeUp;
	private Animation<TextureRegion> slimeDown;
	private TextureRegion slimeAFK;
	private TextureRegion slimeFall;
	private TextureRegion slimeSmash;
	public boolean isSmashing;
	private int magazine = 4;
	
	public Array<Bullet> ammo;
	
	private float stateTimer;
	private boolean runningRight;
	
	public Slime(World world, PlayScreen screen) {
		super(screen.getAtlas().findRegion("Perso"));
		ammo = new Array<Bullet>();
		isSmashing = false;
		isChoice = false;
		this.world = world;
		hitGround = true;
		hitWall = false;
		shooting = false;
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;
		
		Array<TextureRegion> frames = new Array<>();
		for(int i = 2; i<4; i++) {
			frames.add(new TextureRegion(getTexture(),i*200, 0, 200,200));
		}
		slimeRun = new Animation<TextureRegion>(0.2f,frames);
		frames.clear();
		
		frames.add(new TextureRegion(getTexture(),8*200, 0, 200,200));
		slimeJump = new Animation<TextureRegion>(0.1f,frames);
		frames.clear();
		
		frames.add(new TextureRegion(getTexture(),2000, 0, 200,200));
		slimeSlide = new Animation<TextureRegion>(0.2f,frames);
		frames.clear();
		
		frames.add(new TextureRegion(getTexture(),600, 0, 200,200));
		slimeShoot = new Animation<TextureRegion>(0.02f,frames);
		frames.clear();

		slimeAFK = new TextureRegion(getTexture(),0,0,200,200);
		
		slimeFall = new TextureRegion(getTexture(),1800,0,200,200);
		slimeSmash = new TextureRegion(getTexture(),12*200,0,200,200);
		setBounds(0,0,16/MarioGame.PPM, 16/MarioGame.PPM);
		
		
		defineHero();
		
	}
	
	public Slime(World world, ChoiceScreen screen) {
		super(screen.getAtlas().findRegion("Perso"));
		ammo = new Array<Bullet>();
		isSmashing = false;
		isChoice = true;
		this.world = world;
		hitGround = true;
		hitWall = false;
		shooting = false;
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;
		
		Array<TextureRegion> frames = new Array<>();
		
		for(int i = 2; i<4; i++) {
			frames.add(new TextureRegion(getTexture(),i*200, 0, 200,200));
		}
		slimeRun = new Animation<TextureRegion>(0.2f,frames);
		frames.clear();
		
		
		for(int i = 4; i<6; i++) {
			frames.add(new TextureRegion(getTexture(),i*200, 0, 200,200));
		}
		slimeUp = new Animation<TextureRegion>(0.2f,frames);
		frames.clear();
		
		for(int i = 0; i<2; i++) {
			frames.add(new TextureRegion(getTexture(),i*200, 0, 200,200));
		}
		slimeDown = new Animation<TextureRegion>(0.2f,frames);
		frames.clear();
		
		slimeAFK = new TextureRegion(getTexture(),0,0,200,200);
		
		setBounds(0,0,32/MarioGame.PPM, 32/MarioGame.PPM);
		
		
		defineHero();
		
	}
	
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() / 2 );
		setRegion(getFrame(dt));
		for(Bullet bullet : ammo) {
			bullet.update();
		}
	}
	
	public TextureRegion getFrame(float dt) {
		currentState = getState();
		TextureRegion region;
		switch(currentState) {
			case SMASHING:
				region = slimeSmash;
				break;
			case SHOOTING:
				region = (TextureRegion) slimeShoot.getKeyFrame(stateTimer);
				break;
			case JUMPING:
				region = (TextureRegion) slimeJump.getKeyFrame(stateTimer);
				break;
			case RUNNING:
				region = (TextureRegion) slimeRun.getKeyFrame(stateTimer, true);
				break;
			case SLIDING:
				region = (TextureRegion) slimeSlide.getKeyFrame(stateTimer);
				break;
			case GOINGUP:
				region = (TextureRegion) slimeUp.getKeyFrame(stateTimer, true);
				break;
			case GOINGDOWN:
				region = (TextureRegion) slimeDown.getKeyFrame(stateTimer, true);
				break;
			case FALLING:	
				region = slimeFall;
				break;
			case STANDING:
			default:
				region = slimeAFK;
				break;
		}
		shooting = false;
		if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		}
		else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}
		
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;
	}
	
	
	public State getState() {
		
		if(!isChoice) {
			if(hitWall == true)
				return State.SLIDING;
			else if(isSmashing) {
				return State.SMASHING;
			}
			else if(isDead)
				return State.DEAD;
			else if(shooting)
				return State.SHOOTING;
			else if(b2body.getLinearVelocity().y > 0)
				return State.JUMPING;
			else if(!hitGround)
				return State.FALLING;
			else if(b2body.getLinearVelocity().x != 0)
				return State.RUNNING;
		}
		else {
			if(b2body.getLinearVelocity().y > 0)
				return State.GOINGUP;
			else if(b2body.getLinearVelocity().y < 0)
				return State.GOINGDOWN;
			else if(b2body.getLinearVelocity().x != 0)
				return State.RUNNING;
		}
		
		return State.STANDING;
	}
	
	public void defineHero() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(280 / MarioGame.PPM, 400 / MarioGame.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		for(int i = 0; i<magazine; i++) {
			ammo.add(new Bullet(world, this, direction));
		}
	
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(5 / MarioGame.PPM);
		
		fdef.filter.categoryBits = MarioGame.PLAYER_BIT;
		fdef.filter.maskBits = MarioGame.DEFAULT_BIT | MarioGame.ENEMY_BIT | MarioGame.WALL_BIT | MarioGame.BULLET_BIT | MarioGame.COIN_BIT | MarioGame.DOOR_BIT;
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
		
		EdgeShape head = new EdgeShape();
		head.set(new Vector2(-2 / MarioGame.PPM, 5 / MarioGame.PPM), new Vector2(2 / MarioGame.PPM, 5 / MarioGame.PPM));
		fdef.shape = head;
		fdef.isSensor = true;
		b2body.createFixture(fdef).setUserData("head");
		
		EdgeShape feet = new EdgeShape();
		feet.set(new Vector2(-2 / MarioGame.PPM, -5 / MarioGame.PPM), new Vector2(2 / MarioGame.PPM, -5 / MarioGame.PPM));
		fdef.shape = feet;
		fdef.isSensor = true;
		b2body.createFixture(fdef).setUserData("feet");
		
		EdgeShape left = new EdgeShape();
		left.set(new Vector2(-5 / MarioGame.PPM, 2 / MarioGame.PPM), new Vector2(-5 / MarioGame.PPM, -2 / MarioGame.PPM));
		fdef.shape = left;
		fdef.isSensor = true;
		b2body.createFixture(fdef).setUserData("left");
		
		EdgeShape right = new EdgeShape();
		right.set(new Vector2(5 / MarioGame.PPM, -2 / MarioGame.PPM), new Vector2(5 / MarioGame.PPM, 2 / MarioGame.PPM));
		fdef.shape = right;
		fdef.isSensor = true;
		b2body.createFixture(fdef).setUserData("right");
		
	}
	
}
