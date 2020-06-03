package Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.MarioGame;

import Screens.ChoiceScreen;
import Screens.PlayScreen;
import Sprites.Enemy;
import Sprites.InteractiveTileObject;
import Sprites.NonInteractiveTileObject;

public class WorldContactListener implements ContactListener{
	public PlayScreen ecran;
	public ChoiceScreen ecran2;
	public MarioGame game;
	public WorldContactListener(MarioGame game, PlayScreen ecran) {
		this.ecran = ecran;
		this.game = game;
	}
	
	public WorldContactListener(MarioGame game, ChoiceScreen ecran) {
		this.ecran2 = ecran;
		this.game = game;
	}
	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		if(fixA.getUserData() == "head" || fixB.getUserData() == "head") {
			Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
			Fixture object = head == fixA ? fixB : fixA;
			
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				((InteractiveTileObject)object.getUserData()).onHeadHit(ecran.player);
			}
			if(object.getUserData() != null && MapSelector.class.isAssignableFrom(object.getUserData().getClass())) {
				
				MarioGame.screen = new PlayScreen(game, ((MapSelector)object.getUserData()).getLevel());
				game.setScreen(MarioGame.screen);
				
			}
			
			if(object.getUserData() == "coin") {
				Filter filter = new Filter();
				filter.categoryBits = MarioGame.DESTROYED_BIT;
				object.setFilterData(filter);
			}
			if(object.getUserData() == "weapon") {
				Filter filter = new Filter();
				filter.categoryBits = MarioGame.DESTROYED_BIT;
				object.setFilterData(filter);
			}
			
			if(object.getUserData() == "key") {
				Filter filter = new Filter();
				filter.categoryBits = MarioGame.DESTROYED_BIT;
				object.setFilterData(filter);
			}
			if(object.getUserData() == "door") {
				if(ecran.player.nbr_cles == 3) {
					MarioGame.screen.door.isOpen = true;
					game.setScreen(MarioGame.choice);
					//MarioGame.screen.dispose();
					
				}
			}
			
		}
		if(fixA.getUserData() == "feet" || fixB.getUserData() == "feet") {
			Fixture feet = fixA.getUserData() == "feet" ? fixA : fixB;
			Fixture object = feet == fixA ? fixB : fixA;
			
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				((InteractiveTileObject)object.getUserData()).onFeetHit(ecran.player);
			}
			if(object.getUserData() != null && NonInteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				if(this.ecran != null) {
					((NonInteractiveTileObject)object.getUserData()).onFeetHit(ecran.player);
				}
				
			}
			if(object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass())) {
				((Enemy)object.getUserData()).setCategoryFilter(MarioGame.DESTROYED_BIT);
			}
			
			if(object.getUserData() == "coin") {
				Filter filter = new Filter();
				filter.categoryBits = MarioGame.DESTROYED_BIT;
				object.setFilterData(filter);
			}
			if(object.getUserData() == "key") {
				Filter filter = new Filter();
				filter.categoryBits = MarioGame.DESTROYED_BIT;
				object.setFilterData(filter);
			}
			
		}
		if(fixA.getUserData() == "right" || fixB.getUserData() == "right") {
			Fixture right = fixA.getUserData() == "right" ? fixA : fixB;
			Fixture object = right == fixA ? fixB : fixA;
			
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				((InteractiveTileObject)object.getUserData()).onSideHit(ecran.player);
			}
			
			if(object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass())) {
				((Enemy)object.getUserData()).onSideHit(ecran.player);
			}
		}
		if(fixA.getUserData() == "left" || fixB.getUserData() == "left") {
			Fixture left = fixA.getUserData() == "left" ? fixA : fixB;
			Fixture object = left == fixA ? fixB : fixA;
			
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				((InteractiveTileObject)object.getUserData()).onSideHit(ecran.player);
			}
			if(object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass())) {
				((Enemy)object.getUserData()).onSideHit(ecran.player);
			}
		}
		
		if(fixA.getUserData() == "bullet" || fixB.getUserData() == "bullet") {
			Fixture bullet = fixA.getUserData() == "bullet" ? fixA : fixB;
			Fixture object = bullet == fixA ? fixB : fixA;
			
			
			if(object.getUserData() != null && Enemy.class.isAssignableFrom(object.getUserData().getClass())) {
				((Enemy)object.getUserData()).setCategoryFilter(MarioGame.DESTROYED_BIT);
				Filter filter = new Filter();
				filter.categoryBits = MarioGame.DESTROYED_BIT;
				bullet.setFilterData(filter);
			}
			if(object.getUserData() != null && NonInteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				Filter filter = new Filter();
				filter.categoryBits = MarioGame.DESTROYED_BIT;
				bullet.setFilterData(filter);
			}
			
		}
		
		if(fixA.getUserData() == "bullet2" || fixB.getUserData() == "bullet2") {
			Fixture bullet2 = fixA.getUserData() == "bullet2" ? fixA : fixB;
			Filter filter = new Filter();
			filter.categoryBits = MarioGame.DESTROYED_BIT;
			bullet2.setFilterData(filter);
		}
		
		
		
	}

	
	
	@Override
	public void endContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
    
        if(fixA.getUserData() == "right" || fixB.getUserData() == "right") {
        	Fixture right = fixA.getUserData() == "right" ? fixA : fixB;
			Fixture object = right == fixA ? fixB : fixA;
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				((InteractiveTileObject)object.getUserData()).onContactLoss(ecran.player);
			}
        }
        
        if(fixA.getUserData() == "left" || fixB.getUserData() == "left") {
        	Fixture left = fixA.getUserData() == "left" ? fixA : fixB;
			Fixture object = left == fixA ? fixB : fixA;
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				((InteractiveTileObject)object.getUserData()).onContactLoss(ecran.player);
			}
        }
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}
