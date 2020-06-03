package Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MarioGame;
import Screens.PlayScreen;

public class Monster extends Enemy {

	
	public Monster(World world, TiledMap map, Rectangle bounds, PlayScreen screen) {
		super(world, map, bounds, screen);
		fixture.setUserData(this);
		this.texture = new Texture("feu.png");
		this.left = new TextureRegion(texture,0,0,200,200);
		this.right = new TextureRegion(texture,200,0,200,200);
		setBounds(0,0,16/MarioGame.PPM, 16/MarioGame.PPM);
		screen.monsterList.add(this);
	}
	
	
	protected void move() {
			if(currentState == State.MOVING_RIGHT) {
				if(body.getLinearVelocity().x == 0) {
					switchState();
					body.applyLinearImpulse(new Vector2(-0.1f, 0),body.getWorldCenter(), true);
				}
				else if(body.getLinearVelocity().x <= 1) {
					body.applyLinearImpulse(new Vector2(0.1f, 0),body.getWorldCenter(), true);
				}
				
			}
			else if(currentState == State.MOVING_LEFT) {
				if(body.getLinearVelocity().x == 0) {
					switchState();
					body.applyLinearImpulse(new Vector2(0.1f, 0),body.getWorldCenter(), true);
				}
				else if(body.getLinearVelocity().x >= -1) {
					body.applyLinearImpulse(new Vector2(-0.1f, 0),body.getWorldCenter(), true);
				}
			}
		}
	
	public void update() {
		move();
		if(currentState == State.MOVING_LEFT) {
			setRegion(left);
		}
		else {
			setRegion(right);
		}
		setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getWidth() / 2);
	}
	

	
	public void switchState() {
		if(currentState == State.MOVING_LEFT)
			currentState = State.MOVING_RIGHT;
		else if(currentState == State.MOVING_RIGHT)
			currentState = State.MOVING_LEFT;
	}
	
	@Override
	public void onHeadHit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFeetHit(Slime player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSideHit(Slime player) {
		player.isDead = true;
		player.b2body.applyLinearImpulse(new Vector2(0,3.5f), player.b2body.getWorldCenter(), true);
		
	}

	@Override
	public void onContactLoss(Slime player) {
		// TODO Auto-generated method stub
		
	}

}
