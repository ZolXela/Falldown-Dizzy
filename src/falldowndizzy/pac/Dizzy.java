package falldowndizzy.pac;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
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
	
	private Body DizzyBody;
	public boolean jumping;
	
	PhysicsWorld pPhysicsWorld;
	
	public Dizzy(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld mPhysicsWorld) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.setSettings();
		pPhysicsWorld = mPhysicsWorld;

//		DizzyBody = PhysicsFactory.createCircleBody(pPhysicsWorld, this, BodyType.DynamicBody, CIRCLE_FIXTURE_DEF);
//		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody, true, false));	
//		this.registerUpdateHandler(pPhysicsWorld);
//		contact = new Contact(pPhysicsWorld, DizzyBody);

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
				// TODO Auto-generated method stub
				
			}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	
	public void Stay(){		
		setCurrentTileIndex(6);
//		setAnimation(6, 8);
		this.DizzyBody.setLinearVelocity(0, 0);
//		stopAnimation(6);	
	}
	
	public void GoLeft(Vector2 velocity){		
//		setAnimation(9, 11);
		this.DizzyBody.setLinearVelocity(velocity);	
	}
	
	public void GoRight(Vector2 velocity){	
//		setAnimation(3, 5);
		this.DizzyBody.setLinearVelocity(velocity);		
	}
	
	public void JumpLeft(Vector2 velocity){
		setCurrentTileIndex(9);
//		setAnimation(9, 11);
		DizzyBody.applyLinearImpulse(this.setImpulse(velocity), DizzyBody.getWorldCenter());	
		DizzyBody.setAngularDamping(1.5f);
		DizzyBody.setLinearDamping(1.5f); //to decrease velocity slowly. no linear no floaty 
		jumping = true;
	}
	
	public void JumpRight(Vector2 velocity){
		setCurrentTileIndex(3);
//		setAnimation(3, 5);
		DizzyBody.applyLinearImpulse(this.setImpulse(velocity), DizzyBody.getWorldCenter());	
		DizzyBody.setAngularDamping(2.5f);
		DizzyBody.setLinearDamping(2.5f); //to decrease velocity slowly. no linear no floaty 
		jumping = true;
	}
	
	public Vector2 setImpulse(Vector2 velocity){	
		return new Vector2(velocity.x * 16, velocity.y * 20);
	}
	
	public void setSettings(){	
		this.setScaleCenterY(GfxAssets.mPlayer.getHeight());
		this.setScale(2);		
	}
	
	public void setAnimation(int begNum, int endNum){
		this.animate(new long[]{300, 300, 300}, begNum, endNum, true);    
	}
	

}

