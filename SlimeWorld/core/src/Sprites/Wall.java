package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MarioGame;

public class Wall extends InteractiveTileObject {

	public Wall(World world, TiledMap map, Rectangle bounds) {
		super(world, map, bounds);
		fixture.setUserData(this);
		setCategoryFilter(MarioGame.WALL_BIT);
	}
	
	
	@Override
	public void onHeadHit(Slime player) {
		
	}

	@Override
	public void onFeetHit(Slime player) {
		player.hitGround = true;
		
	}

	@Override
	public void onSideHit(Slime player) {
		player.hitWall = true;
		player.hitGround = true;
		this.world.setGravity(new Vector2(0,-7));
	}
	
	public void onContactLoss(Slime player) {
		player.hitWall = false;
		this.world.setGravity(new Vector2(0,-10));
	}

}
