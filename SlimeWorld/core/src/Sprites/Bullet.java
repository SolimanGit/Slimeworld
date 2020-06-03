package Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.MarioGame;


public class Bullet extends Sprite implements Disposable {
	public World world;
	public Body b2body;
	public Slime player;
	public Fixture fixture;
	public Texture texture;
	public TextureRegion region;
	public TextureRegion dead;
	public boolean destroyed = false;
	
	public Bullet(World world, Slime player, String direction) {
		this.world = world;
		destroyed = false;
		texture = new Texture("bullet.png");
		region = new TextureRegion(texture,0,0,200,200);
		if(direction == "LEFT") {
			region.flip(true,false);
		}
		dead = new TextureRegion(texture,400,0,200,200);
		setBounds(0,0,4/MarioGame.PPM, 4/MarioGame.PPM);
		setRegion(region);
		defineBullet(player, direction);
	}
	
	public void defineBullet(Slime player, String direction){
		BodyDef bdef = new BodyDef();
		if(direction == "LEFT") {
			bdef.position.set( player.b2body.getPosition().x - 5/MarioGame.PPM, player.b2body.getPosition().y + 4/MarioGame.PPM);
		}
		else {
			bdef.position.set( player.b2body.getPosition().x + 5/MarioGame.PPM, player.b2body.getPosition().y + 4/MarioGame.PPM);
		}
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(1 / MarioGame.PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = MarioGame.BULLET_BIT;
		fdef.filter.maskBits = MarioGame.DEFAULT_BIT | MarioGame.PLAYER_BIT | MarioGame.ENEMY_BIT | MarioGame.WALL_BIT | MarioGame.BULLET_BIT;
		fixture = b2body.createFixture(fdef);
		fixture.setUserData("bullet");
		if(direction == "RIGHT")
		b2body.applyLinearImpulse(new Vector2(4, 0), b2body.getWorldCenter(), true);
		if(direction == "LEFT")
		b2body.applyLinearImpulse(new Vector2(-4, 0), b2body.getWorldCenter(), true);
	}

	public void update() {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getWidth() / 2);
		if(fixture.getFilterData().categoryBits == MarioGame.DESTROYED_BIT && destroyed == false) {
			destroyed = true;
			world.destroyBody(b2body);
			setRegion(dead);
			
		}
	}
	
	@Override
	public void dispose() {
	}
	
	public void setCategoryFilter(short filterBit) {
		Filter filter = new Filter();
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
	}
}