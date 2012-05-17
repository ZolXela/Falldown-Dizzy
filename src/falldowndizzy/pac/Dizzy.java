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
	
	/* The categories. */
	public static final short CATEGORYBIT_WALL = 1;
	public static final short CATEGORYBIT_BOX = 2;
	public static final short CATEGORYBIT_CIRCLE = 4;

	/* And what should collide with what. */
	public static final short MASKBITS_WALL = CATEGORYBIT_WALL + CATEGORYBIT_BOX + CATEGORYBIT_CIRCLE;
	public static final short MASKBITS_BOX = CATEGORYBIT_WALL + CATEGORYBIT_BOX; // Missing: CATEGORYBIT_CIRCLE
	public static final short MASKBITS_CIRCLE = CATEGORYBIT_WALL + CATEGORYBIT_CIRCLE; // Missing: CATEGORYBIT_BOX

	public static final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f, false, CATEGORYBIT_WALL, MASKBITS_WALL, (short)0);
	public static final FixtureDef BOX_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f, false, CATEGORYBIT_BOX, MASKBITS_BOX, (short)0);
	public static final FixtureDef CIRCLE_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f, false, CATEGORYBIT_CIRCLE, MASKBITS_CIRCLE, (short)0);

	
	//final private FixtureDef MyFixtureDef;
	PhysicsWorld pPhysicsWorld;
	public Body DizzyBody;
	
	public Dizzy(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld mPhysicsWorld) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.setSettings();
		pPhysicsWorld = mPhysicsWorld;
	//	MyFixtureDef = PhysicsFactory.createFixtureDef(1.0f, 0.5f, 0.5f, true);
		//DizzyBody = PhysicsFactory.createCircleBody(pPhysicsWorld, this, BodyType.DynamicBody, MyFixtureDef);
		DizzyBody = PhysicsFactory.createCircleBody(pPhysicsWorld, this, BodyType.DynamicBody, CIRCLE_FIXTURE_DEF);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody, true, false));	
		this.registerUpdateHandler(pPhysicsWorld);
	}
	
	public void Stay(){
		this.DizzyBody.setLinearVelocity(0, 0);
		this.setCurrentTileIndex(6);
		this.registerUpdateHandler(pPhysicsWorld);
	}
	
	public void FallDown(){	
		setAnimation(6, 8);			
	}
	
	public void GoLeft(Vector2 velocity){
		
		setAnimation(9, 11);
		this.setCurrentTileIndex(9);
		this.DizzyBody.setLinearVelocity(velocity);
		
	}
	
	public void GoRight(Vector2 velocity){
		
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

