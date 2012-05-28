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
import org.andengine.util.color.Color;

import android.hardware.SensorManager;
import android.view.MotionEvent;

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
	public static final short MASKBITS_WALL = CATEGORYBIT_WALL |  CATEGORYBIT_PLAYER;
	public static final short MASKBITS_PLATO = CATEGORYBIT_PLAYER;  // Missing: CATEGORYBIT_PLAYER
	public static final short MASKBITS_PLAYER = CATEGORYBIT_WALL  |  CATEGORYBIT_PLATO;// Missing: CATEGORYBIT_PLATO

	public static final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.1f, 0.0f, 1.0f, false, CATEGORYBIT_WALL, MASKBITS_WALL, (short)0);
	public static final FixtureDef PLATO_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.2f, 0.0f, 1.0f, false, CATEGORYBIT_PLATO, MASKBITS_PLATO, (short)0);
	public static final FixtureDef PLAYER_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 0.8f, false, CATEGORYBIT_PLAYER, MASKBITS_PLAYER, (short)0);
	
	public PhysicsWorld gamePhysicsWorld;
	private Dizzy gamePlayer;
	Vector2 velocity;
	float currentX = 0;
	private boolean gameLoaded = false;
	
	public static Rectangle bottomOuter;
	public static Rectangle topOuter;
	public static Rectangle leftOuter;
	public static Rectangle rightOuter;
	
//	public float touchX;
//	public float touchY;
	public float GlobalX = 30;
	public float GlobalY;
	
	private int finger = 0;
	private float jumpHeight = -16;
	private float goStep = 4;
	
	public Game_Scene(){
		super(GameActivity._Camera);
		this.setGamePhysicsWorld();
				
		setBackground(this.LoadAutoParallaxBg());		
		this.initBorders();
		this.CreateDizzy(30, 50);

		attachChild(gamePlayer);
		gamePlayer.Stay();
		
		this.setTouchAreaBindingOnActionMoveEnabled(true);
		gameLoaded = true;
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

		gamePhysicsWorld = new FixedStepPhysicsWorld(100, new Vector2(0, SensorManager.GRAVITY_EARTH * 5), true, 8, 1);	
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

		bottomOuter = new Rectangle(0, GameActivity.CAMERA_HEIGHT, GameActivity.CAMERA_WIDTH, 2, GameActivity.mVertexBufferObjectManager);
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
		
//		this.addFlare(GameActivity.CAMERA_WIDTH - 57, GameActivity.CAMERA_HEIGHT / 3);
		

	}
	
//	private void initPlayerController() {		
//		
//		final DigitalOnScreenControl digitalOnScreenControl = new DigitalOnScreenControl(170, GameActivity.CAMERA_HEIGHT - GfxAssets.mOnScreenControlBaseTextureRegion.getHeight(), this.mCamera, GfxAssets.mOnScreenControlBaseTextureRegion, GfxAssets.mOnScreenControlKnobTextureRegion, 0.1f, GameActivity.mVertexBufferObjectManager, new IOnScreenControlListener() {
//			@Override
//			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
//				if(!isJumping(gamePlayer) && pValueX != 0){
//					velocity = Vector2Pool.obtain(4 * Math.signum(pValueX), 0); 	
//					if(pValueX > 0) gamePlayer.GoRight(velocity);
//						else if(pValueX < 0) gamePlayer.GoLeft(velocity);
//				}				
//				else gamePlayer.Stay();
//				currentX = pValueX;
//			}
//
//		});
//
//		digitalOnScreenControl.refreshControlKnobPosition();
//		this.setChildScene(digitalOnScreenControl);
//		
//		
//		final Sprite JumpBtn = new Sprite(30, GameActivity.CAMERA_HEIGHT - GfxAssets.mOnScreenControlBaseTextureRegion.getHeight(), 50, 50, GfxAssets.mPlayGame, 
//														GameActivity.mVertexBufferObjectManager){
//			@Override
//			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pLocalAreaTouchX, final float pLocalAreaTouchY){
//				if(pSceneTouchEvent.isActionDown() && !isJumping(gamePlayer)) { 
//					System.out.println("************** currentX is " + currentX);
//					velocity = Vector2Pool.obtain(6 * Math.signum(currentX), -12);
//					gamePlayer.Jump(velocity);
//					return true;
//				}
//				else return false;
//			}
//		};
//		
//		this.attachChild(JumpBtn);
//		this.registerTouchArea(JumpBtn);
//	}	
	
	private void playerController() {	

		float currentPosY = gamePlayer.getY();
		if(GlobalY >= (currentPosY - jumpHeight * 15) && finger == 1) {
				if((GameActivity.CAMERA_WIDTH / 2) > GlobalX) {			
					System.out.println("********* should move to the left");
						velocity = Vector2Pool.obtain((-1) * goStep, 0);
							currentX = velocity.x;
								gamePlayer.GoLeft(velocity);
				}
				else if((GameActivity.CAMERA_WIDTH / 2) < GlobalX){
					velocity = Vector2Pool.obtain(goStep, 0);
						System.out.println("********* should move to the right");
							currentX = velocity.x;
								gamePlayer.GoRight(velocity);
				}
		} else if((GlobalY < (currentPosY - jumpHeight * 15)) && (GlobalY > (currentPosY - jumpHeight * 30))) {
			velocity = Vector2Pool.obtain(currentX, jumpHeight);
				gamePlayer.Jump(velocity);
					GlobalY = GameActivity.CAMERA_HEIGHT;
		}
		else gamePlayer.Stay();
	}	
			
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		final TouchEvent curTouchEvent = pSceneTouchEvent;
		if(gameLoaded && finger <= 2){
			switch(pSceneTouchEvent.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(finger == 0) 
						GlobalX = curTouchEvent.getX();
					GlobalY = curTouchEvent.getY();
						finger++;
							if(!isJumping(gamePlayer)){
								this.playerController();
							}
					break;
				case MotionEvent.ACTION_UP:
					finger = (finger > 0) ? finger-1 : 0;
					if(!isJumping(gamePlayer)){
						if (finger == 1) {
							currentX = 0;
							this.playerController();						
						}
						else gamePlayer.Stay();	
					}
					break;
				default:
					if(!isJumping(gamePlayer) && finger > 0)
							this.playerController();
					break;
			}
			return true;
			}
		return false;
	}
	
	
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


