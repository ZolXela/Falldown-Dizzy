package falldowndizzy.pac;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Dizzy extends AnimatedSprite{
	
	final private FixtureDef MyFixtureDef;
	int frameCount;
	
	public Dizzy(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		MyFixtureDef = PhysicsFactory.createFixtureDef(0.8f, 0.1f, 0.9f, true);
	}
	
	public void FallDown(PhysicsWorld mPhysicsWorld){
		
		Body DizzyBody = PhysicsFactory.createCircleBody(mPhysicsWorld, this, BodyType.DynamicBody, MyFixtureDef);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody));
		setSettings(6, 8);
		GameActivity._main.enableAccelerationSensor(GameActivity._main);		
		this.registerUpdateHandler(mPhysicsWorld);
		
	}
	
	public void Go(PhysicsWorld mPhysicsWorld){
		
		Body DizzyBody = PhysicsFactory.createCircleBody(mPhysicsWorld, this, BodyType.KinematicBody, MyFixtureDef);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody));
		setSettings(3, 5);
		GameActivity._main.enableAccelerationSensor(GameActivity._main);		
		this.registerUpdateHandler(mPhysicsWorld);
		
	}
	
	public void setSettings(int begNum, int endNum){
		
		this.setScaleCenterY(GfxAssets.mPlayer.getHeight());
		this.setScale(2);
		this.animate(new long[]{200, 200, 200}, begNum, endNum, true);     
		
	}
	
}

