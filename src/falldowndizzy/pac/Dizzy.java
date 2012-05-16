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
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Dizzy extends AnimatedSprite{
	
	final private FixtureDef MyFixtureDef;
	public Body DizzyBody;
	
	public Dizzy(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.setSettings();
		MyFixtureDef = PhysicsFactory.createFixtureDef(0.8f, 0.1f, 0.9f, true);
	}
	
	public void FallDown(PhysicsWorld pPhysicsWorld){
		
		DizzyBody = PhysicsFactory.createCircleBody(pPhysicsWorld, this, BodyType.DynamicBody, MyFixtureDef);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody));
		setAnimation(6, 8);
		GameActivity._main.enableAccelerationSensor(GameActivity._main);		
		this.registerUpdateHandler(pPhysicsWorld);
		
	}
	
	public void GoLeft(Vector2 velocity, PhysicsWorld pPhysicsWorld){
		
		DizzyBody = PhysicsFactory.createCircleBody(pPhysicsWorld, this, BodyType.DynamicBody, MyFixtureDef);
		this.DizzyBody.setLinearVelocity(velocity);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody));
		setAnimation(9, 11);	
		this.registerUpdateHandler(pPhysicsWorld);
		
	}
	
	public void GoRight(Vector2 velocity, PhysicsWorld pPhysicsWorld){
		
		DizzyBody = PhysicsFactory.createBoxBody(pPhysicsWorld, this, BodyType.KinematicBody, MyFixtureDef);
		this.DizzyBody.setLinearVelocity(velocity);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody));
		setAnimation(3, 5);	
		this.registerUpdateHandler(pPhysicsWorld);
		
	}
	
	public void setSettings(){
		
		this.setScaleCenterY(GfxAssets.mPlayer.getHeight());
		this.setScale(2);
			
	}
	
	public void setAnimation(int begNum, int endNum){
		this.animate(new long[]{200, 200, 200}, begNum, endNum, true);    
	}
}

