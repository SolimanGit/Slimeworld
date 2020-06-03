package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MarioGame;

public abstract class NonInteractiveTileObject {
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	protected Polygon bounds2;
	protected Body body;
	protected Fixture fixture;
	
	public NonInteractiveTileObject( World world, TiledMap map, Rectangle bounds) {
		this.world = world;
		this.map = map;
		this.bounds = bounds;
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set(((float)bounds.getX()+ (float)bounds.getWidth() / 2)/ MarioGame.PPM, ((float)bounds.getY() + (float)bounds.getHeight() / 2)/ MarioGame.PPM );
		body = world.createBody(bdef);
		shape.setAsBox((float)bounds.getWidth() / 2 / MarioGame.PPM,(float) bounds.getHeight() / 2 / MarioGame.PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = MarioGame.DEFAULT_BIT;
		fixture = body.createFixture(fdef);
	}
	
	public NonInteractiveTileObject( World world, TiledMap map, Polygon bounds) {
		System.out.println("enter");
		this.world = world;
		this.map = map;
		this.bounds2 = bounds;
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set(0,0);
		float[] vertices = bounds.getTransformedVertices();
        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] / MarioGame.PPM;
        }
    
		body = world.createBody(bdef);
		shape.set(worldVertices);
		fdef.shape = shape;
		fdef.filter.categoryBits = MarioGame.DEFAULT_BIT;
		fixture = body.createFixture(fdef);
		
	}
	
	public abstract void onHeadHit();
	public abstract void onFeetHit(Slime player);
	public void setCategoryFilter(short filterBit) {
		Filter filter = new Filter();
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
		
	}
		
}
