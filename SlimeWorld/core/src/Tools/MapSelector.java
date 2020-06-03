package Tools;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MarioGame;

import Screens.ChoiceScreen;

public class MapSelector {
	public int number;
	public ChoiceScreen screen;
	public Body body;
	protected Fixture fixture;
	
	public MapSelector(World world, TiledMap map, Rectangle bounds, ChoiceScreen screen) {
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
		fixture.setUserData(this);
	}
	
	public void setLevel(int x) {
		this.number = x;
	}
	
	public int getLevel() {
		System.out.println(number);
		return this.number;
	}
}
