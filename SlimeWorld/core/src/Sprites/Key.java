package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MarioGame;
import Screens.PlayScreen;

public class Key extends Sprite{
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	public Body body;
	protected Fixture fixture;
	private PlayScreen screen;
	private Texture texture;
	private Animation<TextureRegion> animation;
	private TextureRegion region;
	private float stateTimer;
	public boolean destroyed;
	
	public Key(World world, TiledMap map, Rectangle bounds, PlayScreen screen) {
		this.screen = screen;
		destroyed = false;
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set(((float)bounds.getX()+ (float)bounds.getWidth() / 2)/ MarioGame.PPM, ((float)bounds.getY() + (float)bounds.getHeight() / 2)/ MarioGame.PPM );
		body = world.createBody(bdef);
		shape.setAsBox((float)bounds.getWidth() / 2 / MarioGame.PPM,(float) bounds.getHeight() / 2 / MarioGame.PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = MarioGame.COIN_BIT;
		fdef.filter.maskBits = MarioGame.PLAYER_BIT;
		fdef.isSensor = true;
		fixture = body.createFixture(fdef);
		fixture.setUserData("key");
		
		this.texture = new Texture("key.png");
		
		Array<TextureRegion> frames = new Array<>();
		for(int i = 0; i<1; i++) {
			frames.add(new TextureRegion(texture,i*200, 0, 200,200));
		}
		
		animation = new Animation<TextureRegion>(0.1f, frames, Animation.PlayMode.NORMAL);
		
		region = (TextureRegion) animation.getKeyFrame(stateTimer, true);
		setRegion(region);
		setBounds(0,0,16/MarioGame.PPM, 16/MarioGame.PPM);
		setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
		this.screen.keys.add(this);
	}	
	
	public void update(float time) {
		if(fixture.getFilterData().categoryBits == MarioGame.DESTROYED_BIT && destroyed == false){
			destroyed = true;
		}
			stateTimer += Gdx.graphics.getDeltaTime();
			region = (TextureRegion) animation.getKeyFrame(stateTimer, true);
			setRegion(region);
	}
}
