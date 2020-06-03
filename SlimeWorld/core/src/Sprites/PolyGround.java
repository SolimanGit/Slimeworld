package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.World;

public class PolyGround extends NonInteractiveTileObject {

	public PolyGround(World world, TiledMap map, Polygon bounds) {
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
