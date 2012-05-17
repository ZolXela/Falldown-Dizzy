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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Dizzy extends AnimatedSprite implements ContactListener{
	
	/* The categories. */
	public static final short CATEGORYBIT_WALL = 1;
	public static final short CATEGORYBIT_BOX = 2;
	public static final short CATEGORYBIT_CIRCLE = 4;

	/* And what should collide with what. */
	public static final short MASKBITS_WALL = CATEGORYBIT_WALL + CATEGORYBIT_BOX + CATEGORYBIT_CIRCLE;
	public static final short MASKBITS_BOX = CATEGORYBIT_WALL + CATEGORYBIT_BOX; // Missing: CATEGORYBIT_CIRCLE
	public static final short MASKBITS_CIRCLE = CATEGORYBIT_WALL + CATEGORYBIT_CIRCLE; // Missing: CATEGORYBIT_BOX

	public static final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f, false, CATEGORYBIT_WALL, MASKBITS_WALL, (short)0);
	public static final FixtureDef BOX_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f, false, CATEGORYBIT_BOX, MASKBITS_BOX, (short)0);
	public static final FixtureDef CIRCLE_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f, false, CATEGORYBIT_CIRCLE, MASKBITS_CIRCLE, (short)0);

	
	//final private FixtureDef MyFixtureDef;
	PhysicsWorld pPhysicsWorld;
	private Body DizzyBody;
	public boolean jumping;
	
	public Dizzy(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld mPhysicsWorld) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.setSettings();
		pPhysicsWorld = mPhysicsWorld;
		DizzyBody = PhysicsFactory.createCircleBody(pPhysicsWorld, this, BodyType.DynamicBody, CIRCLE_FIXTURE_DEF);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody, true, false));	
		this.registerUpdateHandler(pPhysicsWorld);
//		contact = new Contact(pPhysicsWorld, DizzyBody);
	}
	
	public void Stay(){
		
		this.DizzyBody.setLinearVelocity(0, 0);
		setAnimation(6, 8);
		
	}
	
	public void GoLeft(Vector2 velocity){
		
		setAnimation(9, 11);
		this.DizzyBody.setLinearVelocity(velocity);
		
	}
	
	public void GoRight(Vector2 velocity){
		
		setAnimation(3, 5);
		this.DizzyBody.setLinearVelocity(velocity);
		
	}
	
	public void JumpLeft(Vector2 velocity){
		setAnimation(9, 11);
//		Vector2 currentPosition = DizzyBody.getPosition();
//		Vector2 newPosition = new Vector2(currentPosition.x - 0.1f, currentPosition.y);
//		DizzyBody.applyForce(velocity, newPosition);
		DizzyBody.applyLinearImpulse(velocity, DizzyBody.getPosition());
		jumping = true;
	}
	
	public void JumpRight(Vector2 velocity){
		setAnimation(3, 5);
		DizzyBody.applyForce(velocity, DizzyBody.getPosition());
		jumping = true;
	}
	
	public void setSettings(){
		
		this.setScaleCenterY(GfxAssets.mPlayer.getHeight());
		this.setScale(2);
			
	}
	
	public void setAnimation(int begNum, int endNum){
		this.animate(new long[]{150, 150, 150}, begNum, endNum, true);    
	}

	@Override
	public void beginContact(Contact contact) {
	    jumping = false; //you touched ground so you aren't jumping anymore	
	}

	@Override
	public void endContact(Contact contact) {
		jumping = true; //you leave ground so you're jumping
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
}

