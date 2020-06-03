package Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import Screens.PlayScreen;
import Sprites.Coin;
import Sprites.Door;
import Sprites.Ground;
import Sprites.Key;
import Sprites.Monster;
import Sprites.Pikes;
import Sprites.PolyGround;
import Sprites.Wall;
import Sprites.Weapon;


public class B2WorldCreator {
	public B2WorldCreator(World world, TiledMap map, PlayScreen screen) {
		
		
		for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new Ground(world,map,rect);
		}
		for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new Pikes(world, map, rect);
		}
		
		for(MapObject object : map.getLayers().get(4).getObjects().getByType(PolygonMapObject.class)){
			Polygon poly = ((PolygonMapObject) object).getPolygon();
			new PolyGround(world,map,poly);
		}
		for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new Wall(world, map, rect);
		}
		for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new Coin(world, map, rect, screen);
		}
		for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new Key(world, map, rect, screen);
		}
		
		for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new Door(world, map, rect, screen);
		}
		for(MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new Weapon(world, map, rect, screen);
		}
		
		
		for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new Monster(world, map, rect, screen);
		}
		
	}
}