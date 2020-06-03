package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends NonInteractiveTileObject {

	public Ground(World world, TiledMap map, Rectangle bounds) {
		super(world, map, bounds);
		fixture.setUserData(this);
	}

	@Override
	public void onFeetHit(Slime player) {
		player.hitGround = true;
		
	}

	@Override
	public void onHeadHit() {

	}

}
