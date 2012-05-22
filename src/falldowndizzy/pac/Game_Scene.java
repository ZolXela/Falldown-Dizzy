package falldowndizzy.pac;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.util.color.Color;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Game_Scene extends CameraScene {
	
	// ===========================================================
	// Constants
	// ===========================================================

	/* The categories. */
	public static final short CATEGORYBIT_WALL = 1;
	public static final short CATEGORYBIT_PLATO = 2;
	public static final short CATEGORYBIT_PLAYER = 4;

	/* And what should collide with what. */
	public static final short MASKBITS_WALL = CATEGORYBIT_WALL  |  CATEGORYBIT_PLATO  |  CATEGORYBIT_PLAYER;
	public static final short MASKBITS_PLATO = CATEGORYBIT_WALL  |  CATEGORYBIT_PLAYER;  // Missing: CATEGORYBIT_CIRCLE
	public static final short MASKBITS_PLAYER = CATEGORYBIT_WALL  |  CATEGORYBIT_PLATO;// Missing: CATEGORYBIT_BOX

	public static final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.0f, 1.0f, false, CATEGORYBIT_WALL, MASKBITS_WALL, (short)0);
	public static final FixtureDef PLATO_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.0f, 1.0f, false, CATEGORYBIT_PLATO, MASKBITS_PLATO, (short)0);
	public static final FixtureDef PLAYER_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.0f, 0.8f, false, CATEGORYBIT_PLAYER, MASKBITS_PLAYER, (short)0);
	
	public PhysicsWorld gamePhysicsWorld;
	private Dizzy gamePlayer;
	Vector2 velocity;
	int oldX = 1;

	public static Rectangle bottomOuter;
	public static Rectangle topOuter;
	public static Rectangle leftOuter;
	public static Rectangle rightOuter;
	
	public static Rectangle plato1;

	public short jedyForce = 2;
	
	/**  
	 * Arrows to control gamer's moving
	 */
	public boolean leftArrowTouched = false;
	public boolean rightArrowTouched = false;
	public boolean centralArrowTouched = false;
	
	
	public Game_Scene(){
		super(GameActivity._Camera);
		this.setGamePhysicsWorld();
				
		setBackground(this.LoadAutoParallaxBg());		
		this.initBorders();
		this.CreateDizzy(30, 50);

		attachChild(gamePlayer);
		gamePlayer.Stay();
		this.initPlayerController();

	}

	public void Show(){
		setVisible(true);
		setIgnoreUpdate(false);
	}
	
	public void Hide(){
		setVisible(false);
		setIgnoreUpdate(true);
	}
	
	private void setGamePhysicsWorld(){

		gamePhysicsWorld = new FixedStepPhysicsWorld(30, new Vector2(0, SensorManager.GRAVITY_EARTH), true, 8, 1);	
		this.registerUpdateHandler(gamePhysicsWorld);
		
	}
	
	public AutoParallaxBackgroundXY LoadAutoParallaxBg(){
		
		final AutoParallaxBackgroundXY autoParallaxBackgroundXY = new AutoParallaxBackgroundXY(0, 0, 0, 5);
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerBack, GameActivity.mVertexBufferObjectManager)));
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(5.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerCloud, GameActivity.mVertexBufferObjectManager)));	
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerTrees, GameActivity.mVertexBufferObjectManager)));
		
		return autoParallaxBackgroundXY;	
	}
	
	public void CreateDizzy(float pX, float pY){
		gamePlayer = new Dizzy(pX, pY, 
				GfxAssets.mPlayer, GameActivity.mVertexBufferObjectManager, this.gamePhysicsWorld);
	}
		
	private boolean isJumping(Dizzy player){	
		return player.jumping;	
	}
	
	private void initBorders() {

		bottomOuter = new Rectangle(0, GameActivity.CAMERA_HEIGHT - 2, GameActivity.CAMERA_WIDTH, 2, GameActivity.mVertexBufferObjectManager);
		topOuter = new Rectangle(0, 0, GameActivity.CAMERA_WIDTH, 2, GameActivity.mVertexBufferObjectManager);
		leftOuter = new Rectangle(0, 0, 2, GameActivity.CAMERA_HEIGHT, GameActivity.mVertexBufferObjectManager);
		rightOuter = new Rectangle(GameActivity.CAMERA_WIDTH - 2, 0, 2, GameActivity.CAMERA_HEIGHT, GameActivity.mVertexBufferObjectManager);
				
		PhysicsFactory.createBoxBody(this.gamePhysicsWorld, bottomOuter, BodyType.StaticBody, PLATO_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(this.gamePhysicsWorld, topOuter, BodyType.StaticBody, PLATO_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(this.gamePhysicsWorld, leftOuter, BodyType.StaticBody, WALL_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(this.gamePhysicsWorld, rightOuter, BodyType.StaticBody, WALL_FIXTURE_DEF);
			
		bottomOuter.setColor(Color.BLACK);
		topOuter.setColor(Color.BLACK);
		leftOuter.setColor(Color.BLACK);
		rightOuter.setColor(Color.BLACK);
		
		this.attachChild(bottomOuter);
		this.attachChild(topOuter);
		this.attachChild(leftOuter);
		this.attachChild(rightOuter);
				
		this.addObstacle(0, GameActivity.CAMERA_HEIGHT / 2);
//		
//		this.addFlare(GameActivity.CAMERA_WIDTH - 57, GameActivity.CAMERA_HEIGHT / 3);
		

	}
	
	
	private void initPlayerController() {
		
		
		final Sprite LeftArrow = new Sprite(
				0, GameActivity.CAMERA_HEIGHT - GfxAssets.mPlayGame.getHeight(), 
				GfxAssets.mPlayGame, GameActivity.mVertexBufferObjectManager){
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown() && !isJumping(gamePlayer)) { 			
					leftArrowTouched = true;
					System.out.println(">>>>>>> is left touched? " + leftArrowTouched);
					velocity = (centralArrowTouched == true && !isJumping(gamePlayer)) ? Vector2Pool.obtain(-2, -8) : Vector2Pool.obtain(-4, 0);	
					gamePlayer.GoLeft(velocity);
					return false;
				}
				else if(pSceneTouchEvent.isActionUp() && leftArrowTouched == true) {
					leftArrowTouched = false;
					System.out.println(">>>>>>> is left action up? " + leftArrowTouched);
					return false;
				}		
				else return true;			
			}
		};
		
		final Sprite RightArrow = new Sprite(
				GameActivity.CAMERA_WIDTH - GfxAssets.mPlayGame.getWidth(), GameActivity.CAMERA_HEIGHT - GfxAssets.mPlayGame.getHeight(), 
				GfxAssets.mPlayGame, GameActivity.mVertexBufferObjectManager){
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown() && !isJumping(gamePlayer)) { 	
					rightArrowTouched = true;
					System.out.println(">>>>>>> is right touched? " + rightArrowTouched);
					velocity = (centralArrowTouched == true && !isJumping(gamePlayer)) ? Vector2Pool.obtain(2, -8) : Vector2Pool.obtain(4, 0);
					gamePlayer.GoRight(velocity);			
					return false;
				}
				else if(pSceneTouchEvent.isActionUp() && rightArrowTouched == true) {
					rightArrowTouched = false;
					System.out.println(">>>>>>> is right action up? " + rightArrowTouched);
					return false;
				}
				else return true;	
			}
		};
		
		final Sprite CentralArrow = new Sprite(
				(GameActivity.CAMERA_WIDTH - GfxAssets.mPlayGame.getWidth()) / 2,
				GameActivity.CAMERA_HEIGHT - GfxAssets.mPlayGame.getHeight(), 
				GfxAssets.mPlayGame, GameActivity.mVertexBufferObjectManager) {
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown()) { 
					centralArrowTouched = true;
					if (leftArrowTouched == false && rightArrowTouched == false && !isJumping(gamePlayer)) {
						velocity = Vector2Pool.obtain(0, -8);
						gamePlayer.Jump(velocity);
					}
					System.out.println(">>>>>>> is central touched? " + centralArrowTouched);					
					return false;
				}
				else if(pSceneTouchEvent.isActionUp() && centralArrowTouched == true) {
					centralArrowTouched = false;
					System.out.println(">>>>>>> is central action up? " + centralArrowTouched);
					return false;
				}			
				else return true;
			}	
		};
			
		this.attachChild(LeftArrow);
		this.attachChild(RightArrow);
		this.attachChild(CentralArrow);
		
		this.registerTouchArea(LeftArrow);
		this.registerTouchArea(RightArrow);
		this.registerTouchArea(CentralArrow);

	}
	
//	@Override
//	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
//		if (pSceneTouchEvent.isActionDown()){
//			if(rightArrowTouched && !leftArrowTouched){
//				System.out.println(">>>>>>> right arrow is touched! It's value is " + rightArrowTouched);
//				velocity = centralArrowTouched ? Vector2Pool.obtain(2, -8) : Vector2Pool.obtain(4, 0);
//				gamePlayer.GoRight(velocity);
//			}
//			else if(leftArrowTouched && !rightArrowTouched){
//				System.out.println(">>>>>>> right arrow is touched! It's value is " + leftArrowTouched);
//				velocity = centralArrowTouched ? Vector2Pool.obtain(2, -8) : Vector2Pool.obtain(4, 0);
//				gamePlayer.GoLeft(velocity);
//			}
//			else if(centralArrowTouched){
//				velocity = Vector2Pool.obtain(0, -8);
//				gamePlayer.Jump(velocity);
//			}
//			Vector2Pool.recycle(velocity);
//	    }
//	    else if (pSceneTouchEvent.isActionUp())
//	    {
//	        /**
//	         * Сбрасываем нажатия кнопок
//	         */
//	    	leftArrowTouched = false;
//	    	rightArrowTouched = false;
//	    	centralArrowTouched = false;
//	    }
//	    return super.onSceneTouchEvent(pSceneTouchEvent);
//	}    
	
	private void addObstacle(final float pX, final float pY) {

//		final Sprite platform = new Sprite(pX, pY, 147, 24, GfxAssets.mPlatform1, GameActivity._main.getVertexBufferObjectManager());
//
//		this.attachChild(platform);
//		
//		final PhysicsEditorLoader loader = new PhysicsEditorLoader();
//		// set base path
//		loader.setAssetBasePath("xml/");
//		
//		try {
//		     loader.load(GameActivity._main, this.gamePhysicsWorld, "plat1.xml", platform,
//					false, false);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		
		
//		final Body boxBody = Game_Scene.createObstacleBody(this.goPhysicsWorld, platform, BodyType.StaticBody, WALL_FIXTURE_DEF);
//		boxBody.setLinearDamping(10);
//		boxBody.setAngularDamping(10);

//		this.gamePhysicsWorld.registerPhysicsConnector(new PhysicsConnector(platform, boxBody, true, true));

		
		final Sprite platform = new Sprite(pX, pY, 147, 24, GfxAssets.mPlatform1, GameActivity.mVertexBufferObjectManager);
		final Body boxBody = PhysicsFactory.createBoxBody(this.gamePhysicsWorld, platform, BodyType.StaticBody, PLATO_FIXTURE_DEF);
		this.gamePhysicsWorld.registerPhysicsConnector(new PhysicsConnector(platform, boxBody, true, true));

		this.attachChild(platform);

		
	}
		
    
//	private void addFlare(final float pX, final float pY) {
//		final AnimatedSprite flare = new AnimatedSprite(pX, pY, GfxAssets.mFlare, GameActivity._main.getVertexBufferObjectManager());
//		
//		flare.animate(new long[]{200, 200, 200, 200}, 0, 3, true);
//
//		this.attachChild(flare);
//	}
	
	
	
}


