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
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Dizzy extends AnimatedSprite{
	
	final private FixtureDef MyFixtureDef;
	PhysicsWorld pPhysicsWorld;
	public Body DizzyBody;
	
	public Dizzy(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld mPhysicsWorld) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.setSettings();
		pPhysicsWorld = mPhysicsWorld;
		MyFixtureDef = PhysicsFactory.createFixtureDef(1.0f, 0.1f, 0.9f, true);
		DizzyBody = PhysicsFactory.createCircleBody(pPhysicsWorld, this, BodyType.DynamicBody, MyFixtureDef);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody, true, false));	
		this.registerUpdateHandler(pPhysicsWorld);
	}
	
	public void Stay(){
		this.DizzyBody.setLinearVelocity(0, 0);
		this.setCurrentTileIndex(6);
		this.registerUpdateHandler(pPhysicsWorld);
	}
	
	public void FallDown(PhysicsWorld pPhysicsWorld){
		
		setAnimation(6, 8);
		GameActivity._main.enableAccelerationSensor(GameActivity._main);		
		
	}
	
	public void GoLeft(Vector2 velocity, PhysicsWorld pPhysicsWorld){
		
		setAnimation(9, 11);
		this.setCurrentTileIndex(9);
		this.DizzyBody.setLinearVelocity(velocity);
		
	}
	
	public void GoRight(Vector2 velocity, PhysicsWorld pPhysicsWorld){
		
		setAnimation(3, 5);
		this.setCurrentTileIndex(3);
		this.DizzyBody.setLinearVelocity(velocity);
		
	}
	
	public void setSettings(){
		
		this.setScaleCenterY(GfxAssets.mPlayer.getHeight());
		this.setScale(2);
			
	}
	
	public void setAnimation(int begNum, int endNum){
		this.animate(new long[]{200, 200, 200}, begNum, endNum, true);    
	}
	
	
	private boolean onBeforePositionChanged(){
		
		if(this.collidesWith(Game_Scene.bottomOuter) || 
				this.collidesWith(Game_Scene.leftOuter) ||
					this.collidesWith(Game_Scene.rightOuter) ||
						this.collidesWith(Game_Scene.topOuter))
		{
			this.callbackCollisionBorder();
			return false;
		}
		
		return true;
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		onBeforePositionChanged();
	}
	
	public void callbackCollisionBorder(){
		this.Stay();
	}
	
}

