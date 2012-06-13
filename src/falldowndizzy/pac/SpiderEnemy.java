package falldowndizzy.pac;

import java.io.IOException;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


public class SpiderEnemy extends AnimatedSprite {

	public Body EnemyBody;
	private PhysicsWorld mPhysicsWorld;
	public boolean killed = false;
	public float contactArea;
	public static SpiderEnemy curEnemy;
	public static float mDefaultHeight;
	
	public float dHeight = 0;
	public float minHeight;
	
	public SpiderEnemy(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld pPhysicsWorld, String xmlFile, float defaultHeight) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		mPhysicsWorld = pPhysicsWorld;
//		final PhysicsEditorLoader loader = new PhysicsEditorLoader();
//		loader.setAssetBasePath("xml/");
//		try {
//		     loader.load(GameActivity._main, mPhysicsWorld, xmlFile, this,
//					false, false);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
		mDefaultHeight = defaultHeight;
		
		EnemyBody = PhysicsFactory.createCircleBody(pPhysicsWorld, this, BodyType.StaticBody, Game_Scene.PLAYER_FIXTURE_DEF);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, EnemyBody, true, false));	
		
//		this.setPosition(pX, pY);

		this.setAnimation(0, 11);
		curEnemy = this;
//		this.registerUpdateHandler(detect);
	}

	public void setAnimation(int begNum, int endNum){
		this.animate(new long[]{200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200}, begNum, endNum, true);    
	}
	
//	IUpdateHandler detect = new IUpdateHandler() {
//		@Override
//		public void reset() {
//		}
//
//		@Override
//		public void onUpdate(float pSecondsElapsed) {
//			SpiderEnemy.curEnemy.setHeight(getNewHeight());
//			SpiderEnemy.curEnemy.createSpriteTimeHandler();
//		}
//	};
//
//	private float getNewHeight(){
//		final float height = mDefaultHeight - dHeight;
//		
//		return height;
//	}
//	
//	 private void createSpriteTimeHandler(){
//		 
//		 TimerHandler spriteTimerHandler;
//	     float mEffectSpawnDelay = 1f;
//	     spriteTimerHandler = new TimerHandler(mEffectSpawnDelay,true,new ITimerCallback(){
//	    	 @Override
//	    	 public void onTimePassed(TimerHandler pTimerHandler) {
//	    		 dHeight = (dHeight > SpiderEnemy.curEnemy.minHeight && dHeight > 0) ? dHeight * 0.9f : dHeight * (-1);
//	    				// && dHeight < SpiderEnemy.curEnemy.mDefaultHeight
//	    		 
//	    	 }
//	     });
//	     
//	     GameActivity._Engine.registerUpdateHandler(spriteTimerHandler);
//	}
	
	
}