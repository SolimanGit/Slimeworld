package Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import Screens.ChoiceScreen;
import Sprites.Ground;


public class B2WorldCreator2 {
	int x = 0;
	
	public B2WorldCreator2(World world, TiledMap map, ChoiceScreen screen) {
		
		
		for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new Ground(world,map,rect);
		}
		
		for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			x+=1;
			new MapSelector(world, map, rect, screen).setLevel(x);
		}
		
	}
}