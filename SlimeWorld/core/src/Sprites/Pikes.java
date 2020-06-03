package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MarioGame;

public class Pikes extends InteractiveTileObject {
	public Pikes(World world, TiledMap map, Rectangle bounds) {
		super(world , map, bounds);
		fixture.setUserData(this);
		setCategoryFilter(MarioGame.ENEMY_BIT);
	}
	
	public void onHeadHit(Slime player) {
		player.hitGround = true;
		player.isDead = true;
		player.b2body.applyLinearImpulse(new Vector2(0,3.5f), player.b2body.getWorldCenter(), true);
	}

	@Override
	public void onFeetHit(Slime player) {
		player.hitGround = true;
		player.isDead = true;
		player.b2body.applyLinearImpulse(new Vector2(0,3.5f), player.b2body.getWorldCenter(), true);
	}

	@Override
	public void onSideHit(Slime player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onContactLoss(Slime player) {
		// TODO Auto-generated method stub
		
	}

}