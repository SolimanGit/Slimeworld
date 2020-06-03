package Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MarioGame;
import Screens.PlayScreen;

public abstract class Enemy extends Sprite{
	public World world;
	public PlayScreen screen;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	public Body body;
	public Fixture fixture;
	protected Texture texture;
	protected TextureRegion left;
	protected TextureRegion right;
	public enum State { MOVING_RIGHT, MOVING_LEFT, ATTACKING, DEAD };
	public State currentState;
	public boolean destroyed;
	
	public Enemy( World world, TiledMap map, Rectangle bounds, PlayScreen screen) {
		this.world = world;
		this.screen = screen;
		this.destroyed = false;
		this.map = map;
		this.bounds = bounds;
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(((float)bounds.getX()+ (float)bounds.getWidth() / 2)/ MarioGame.PPM, ((float)bounds.getY() + (float)bounds.getHeight() / 2)/ MarioGame.PPM );
		body = world.createBody(bdef);
		shape.setAsBox((float)bounds.getWidth() / 2 / MarioGame.PPM,(float) bounds.getHeight() / 2 / MarioGame.PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = MarioGame.ENEMY_BIT;
		fdef.filter.maskBits = MarioGame.DEFAULT_BIT | MarioGame.WALL_BIT | MarioGame.PLAYER_BIT | MarioGame.BULLET_BIT | MarioGame.ENEMY_BIT;
		fixture = body.createFixture(fdef);
		currentState = State.MOVING_RIGHT;
	
	}
	protected abstract void move();
	public abstract void update();
	public abstract void switchState();
	public abstract void onHeadHit();
	public abstract void onFeetHit(Slime player);
	public abstract void onSideHit(Slime player);
	public abstract void onContactLoss(Slime player);
	
	public void setCategoryFilter(short filterBit) {
		if(filterBit == MarioGame.DESTROYED_BIT && destroyed == false){
			destroyed = true;
		}
			Filter filter = new Filter();
			filter.categoryBits = filterBit;
			fixture.setFilterData(filter);
	}
	

		
}