package falldowndizzy.pac;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Dizzy extends AnimatedSprite {
	
	public Body DizzyBody;
	public boolean jumping = false;
	
	PhysicsWorld pPhysicsWorld;
	
	public Dizzy(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld mPhysicsWorld) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.setSettings();
		pPhysicsWorld = mPhysicsWorld;

		DizzyBody = PhysicsFactory.createCircleBody(pPhysicsWorld, this, BodyType.DynamicBody, Game_Scene.PLAYER_FIXTURE_DEF);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody, true, false));	
		pPhysicsWorld.setContactListener(new ContactListener(){
			@Override
			public void beginContact(Contact contact) {
				jumping = false;
			}
			@Override
			public void endContact(Contact contact)
			{
				jumping = true;
			}
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				
				
			}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				jumping = contact.isEnabled() ? false : true;				
			}
		});

	}
	
	public void Stay(){		
		setAnimation(6, 8);
		this.DizzyBody.setLinearVelocity(0, 0);	
	}
	
	public void GoLeft(Vector2 velocity){		
		setAnimation(9, 11);
		this.DizzyBody.setLinearVelocity(velocity);	
		Vector2Pool.recycle(velocity);
	}
	
	public void GoRight(Vector2 velocity){	
		setAnimation(3, 5);
		this.DizzyBody.setLinearVelocity(velocity);	
		Vector2Pool.recycle(velocity);
	}
	
	public void Jump(Vector2 velocity){
		setAnimation(6, 8);
		this.DizzyBody.setLinearVelocity(velocity);	
		Vector2Pool.recycle(velocity);
	}
	
	public void setSettings(){	
		this.setScaleCenterY(GfxAssets.mPlayer.getHeight());
		this.setScale(2);		
	}
	
	public void setAnimation(int begNum, int endNum){
		this.animate(new long[]{300, 300, 300}, begNum, endNum, true);    
	}
	

}

