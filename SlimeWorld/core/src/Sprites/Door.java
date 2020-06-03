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

import Screens.ChoiceScreen;
import Screens.PlayScreen;

public class Door extends Sprite{
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	public Body body;
	protected Fixture fixture;
	private PlayScreen screen;
	private Texture texture, texture1;
	private Animation<TextureRegion> animation;
	private TextureRegion close, open;
	public boolean isOpen = false;
	private float stateTimer;
	private int lvl;
	
	public Door(World world, TiledMap map, Rectangle bounds, PlayScreen screen) {
		this.screen = screen;
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set(((float)bounds.getX()+ (float)bounds.getWidth() / 2)/ MarioGame.PPM, ((float)bounds.getY() + (float)bounds.getHeight() / 2)/ MarioGame.PPM );
		body = world.createBody(bdef);
		shape.setAsBox((float)bounds.getWidth() / 2 / MarioGame.PPM,(float) bounds.getHeight() / 2 / MarioGame.PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = MarioGame.DOOR_BIT;
		fdef.filter.maskBits = MarioGame.PLAYER_BIT;
		fdef.isSensor = true;
		fixture = body.createFixture(fdef);
		fixture.setUserData("door");
		
		this.texture = new Texture("porte.png");
		this.texture1 = new Texture("porte1.png");

		close = new TextureRegion(texture,0,0,200,200);
		open = new TextureRegion(texture1,0,0,200,200);
		setRegion(close);
		setBounds(0,0,90/MarioGame.PPM, 80/MarioGame.PPM);
		setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
		screen.door = this;
	}	
	
	public void update(float time, int nb_cle) {
		if(nb_cle == 3 && isOpen == false) {
			setRegion(open);
			isOpen = true;
		}
			
	}
}