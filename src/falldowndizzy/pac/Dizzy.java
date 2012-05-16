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
		this.setCurrentTileIndex(6);
		MyFixtureDef = PhysicsFactory.createFixtureDef(1.0f, 0.1f, 0.9f, true);
		DizzyBody = PhysicsFactory.createCircleBody(pPhysicsWorld, this, BodyType.DynamicBody, MyFixtureDef);
	}
	
	public void Stay(){
		this.DizzyBody.setLinearVelocity(Vector2Pool.obtain(0, 0));
		this.registerUpdateHandler(pPhysicsWorld);
	}
	
	public void FallDown(PhysicsWorld pPhysicsWorld){
		
		setAnimation(6, 8);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody, true, false));
		GameActivity._main.enableAccelerationSensor(GameActivity._main);		
		this.registerUpdateHandler(pPhysicsWorld);
		
	}
	
	public void GoLeft(Vector2 velocity, PhysicsWorld pPhysicsWorld){
		
		this.DizzyBody.setLinearVelocity(velocity);
		setAnimation(9, 11);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody, true, false));
		this.registerUpdateHandler(pPhysicsWorld);
		
	}
	
	public void GoRight(Vector2 velocity, PhysicsWorld pPhysicsWorld){
		
		this.DizzyBody.setLinearVelocity(velocity);
		setAnimation(3, 5);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody, true, false));	
		this.registerUpdateHandler(pPhysicsWorld);
		
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
	
	public void callbackCollisionGoods(int i){
//		Shape goodShape = goodsList.get(i);
//		Game_Scene.removeEntity(goodShape);
//		goodsList.remove(i);
	}
	
	public void callbackCollisionBorder(){
		this.Stay();
	}
	
	public void callbackCollisionWithEndPoint(){

//		if(goodsList.size()==0)
//		{
//			if(!isGameFinished)
//			{
//				isGameFinished = true;
//				showSignCompleted();
//				soundManager.playGameOver();
//			}
//			
//			
//		}

	}
	
}

