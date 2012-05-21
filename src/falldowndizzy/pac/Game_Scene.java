package falldowndizzy.pac;

import java.io.IOException;
import static org.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.util.color.Color;

import falldowndizzy.pac.PhysicsEditorLoader;

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

	public static final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.5f, 0.1f, false, CATEGORYBIT_WALL, MASKBITS_WALL, (short)0);
	public static final FixtureDef PLATO_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.5f, 0.1f, false, CATEGORYBIT_PLATO, MASKBITS_PLATO, (short)0);
	public static final FixtureDef PLAYER_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.0f, 0.5f, 0.8f, false, CATEGORYBIT_PLAYER, MASKBITS_PLAYER, (short)0);
	
	public PhysicsWorld gamePhysicsWorld;
	private Dizzy gamePlayer;
	Vector2 velocity;
	float oldX;

	public static Rectangle bottomOuter;
	public static Rectangle topOuter;
	public static Rectangle leftOuter;
	public static Rectangle rightOuter;
	
	public static Rectangle plato1;

	public short jedyForce = 2;

	
	
	public Game_Scene(){
		super(GameActivity._Camera);
		this.setGamePhysicsWorld();
				
		setBackground(this.LoadAutoParallaxBg());		
		this.initBorders();
		this.CreateDizzy(30, 50);

		attachChild(gamePlayer);

		this.initOnScreenControls();

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

		gamePhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, SensorManager.GRAVITY_EARTH * 8), true, 10, 1);	
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
	
	private void initOnScreenControls() {
		final DigitalOnScreenControl analogOnScreenControl = new DigitalOnScreenControl(
				(GameActivity.CAMERA_WIDTH - GfxAssets.mOnScreenControlBaseTextureRegion.getWidth())/2, 
					GameActivity.CAMERA_HEIGHT - GfxAssets.mOnScreenControlBaseTextureRegion.getHeight(), 
					this.mCamera, GfxAssets.mOnScreenControlBaseTextureRegion, 
						GfxAssets.mOnScreenControlKnobTextureRegion, 0.2f,
							GameActivity.mVertexBufferObjectManager, 
								new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, 
											final float pValueX, 
												final float pValueY) {
				
				if (pValueY == -1.0f && !isJumping(gamePlayer)){

					velocity = Vector2Pool.obtain(oldX * 2, pValueY * 2);
					if (oldX < 0)
						gamePlayer.JumpLeft(velocity);
					else gamePlayer.JumpRight(velocity);
					Vector2Pool.recycle(velocity);
				}
				else {
					if (pValueX < 0) {
					velocity = Vector2Pool.obtain(pValueX * 4, 0);
					gamePlayer.GoLeft(velocity);		
					oldX = pValueX;
					}				
					else if (pValueX > 0){
						velocity = Vector2Pool.obtain(pValueX * 4, 0);
						gamePlayer.GoRight(velocity);		
						oldX = pValueX;
					}
					else gamePlayer.Stay();
				}	
			}
			
			@Override
			public void onControlClick(
					AnalogOnScreenControl pAnalogOnScreenControl) {
				// TODO Auto-generated method stub
				
			}
		});
		analogOnScreenControl.getControlBase().setAlpha(0.5f);
		analogOnScreenControl.refreshControlKnobPosition();

		this.setChildScene(analogOnScreenControl);
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
	
	private void addObstacle(final float pX, final float pY) {


		final Sprite platform = new Sprite(pX, pY, 147, 24, GfxAssets.mPlatform1, GameActivity._main.getVertexBufferObjectManager());

		this.attachChild(platform);
		
		final PhysicsEditorLoader loader = new PhysicsEditorLoader();
		// set base path
		loader.setAssetBasePath("xml/");
		
		try {
		     loader.load(GameActivity._main, this.gamePhysicsWorld, "plat1.xml", platform,
					false, false);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
//		final Body boxBody = Game_Scene.createObstacleBody(this.goPhysicsWorld, platform, BodyType.StaticBody, WALL_FIXTURE_DEF);
//		boxBody.setLinearDamping(10);
//		boxBody.setAngularDamping(10);

//		this.goPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(platform, boxBody, true, true));

//		
//		final Sprite platform = new Sprite(pX, pY, 147, 24, GfxAssets.mPlatform1, GameActivity.mVertexBufferObjectManager);
//		final Body boxBody = PhysicsFactory.createBoxBody(this.gamePhysicsWorld, platform, BodyType.StaticBody, PLATO_FIXTURE_DEF);
//		this.gamePhysicsWorld.registerPhysicsConnector(new PhysicsConnector(platform, boxBody, true, true));


		
	}
		
    
//	private void addFlare(final float pX, final float pY) {
//		final AnimatedSprite flare = new AnimatedSprite(pX, pY, GfxAssets.mFlare, GameActivity._main.getVertexBufferObjectManager());
//		
//		flare.animate(new long[]{200, 200, 200, 200}, 0, 3, true);
//
//		this.attachChild(flare);
//	}
	
}


