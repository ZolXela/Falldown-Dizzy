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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Game_Scene extends CameraScene {
	
	// ===========================================================
	// Constants
	// ===========================================================

	/* The categories. */
	public static final short CATEGORYBIT_WALL = 1;
	public static final short CATEGORYBIT_BOX = 2;
	public static final short CATEGORYBIT_CIRCLE = 4;

	/* And what should collide with what. */
	public static final short MASKBITS_WALL = CATEGORYBIT_WALL + CATEGORYBIT_BOX + CATEGORYBIT_CIRCLE;

	public static final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f, false, CATEGORYBIT_WALL, MASKBITS_WALL, (short)0);
	
	public PhysicsWorld goPhysicsWorld;
	private Dizzy myPlayer;
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
		this.setGoPhysicsWorld();
				
		setBackground(this.LoadAutoParallaxBg());
		
		this.initBorders();
		this.CreateDizzy(30, 50);
		attachChild(myPlayer);
		
		
		
		
		
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
	
	private void setGoPhysicsWorld(){

		goPhysicsWorld = new FixedStepPhysicsWorld(30, new Vector2(0, SensorManager.GRAVITY_EARTH), true, 8, 1);
		
		this.registerUpdateHandler(goPhysicsWorld);
	}
	
	public AutoParallaxBackgroundXY LoadAutoParallaxBg(){
		
		final AutoParallaxBackgroundXY autoParallaxBackgroundXY = new AutoParallaxBackgroundXY(0, 0, 0, 5);
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerBack, GameActivity.mVertexBufferObjectManager)));
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(5.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerCloud, GameActivity.mVertexBufferObjectManager)));	
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerTrees, GameActivity.mVertexBufferObjectManager)));
		
		return autoParallaxBackgroundXY;	
	}
	
	public void CreateDizzy(float pX, float pY){
		myPlayer = new Dizzy(pX, pY, 
				GfxAssets.mPlayer, GameActivity.mVertexBufferObjectManager, this.goPhysicsWorld){
			
			@Override
			public void beginContact(Contact contact) {
			    jumping = false; //you touched ground so you aren't jumping anymore	
			}
			
			@Override
			public void endContact(Contact contact) {
				jumping = true; //you leave ground so you're jumping
			}
		};
	}
	
	private void initOnScreenControls() {
		final DigitalOnScreenControl analogOnScreenControl = new DigitalOnScreenControl(
				(GameActivity.CAMERA_WIDTH - GfxAssets.mOnScreenControlBaseTextureRegion.getWidth())/2, 
					GameActivity.CAMERA_HEIGHT - GfxAssets.mOnScreenControlBaseTextureRegion.getHeight(), 
					this.mCamera, GfxAssets.mOnScreenControlBaseTextureRegion, 
						GfxAssets.mOnScreenControlKnobTextureRegion, 0.1f,
							GameActivity.mVertexBufferObjectManager, 
								new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, 
											final float pValueX, 
												final float pValueY) {
				
				if (pValueY == -1.0f && isJumping(myPlayer)){
					velocity = Vector2Pool.obtain(oldX * 2, pValueY * 2);
					if (oldX < 0)
						myPlayer.JumpLeft(velocity);
					else myPlayer.JumpRight(velocity);
					
				}
				else {
					if (pValueX < 0) {
					velocity = Vector2Pool.obtain(pValueX, 0);
					myPlayer.GoLeft(velocity);		
					oldX = pValueX;
					}				
					else if (pValueX > 0){
						velocity = Vector2Pool.obtain(pValueX, 0);
						myPlayer.GoRight(velocity);		
						oldX = pValueX;
					}
					else myPlayer.Stay();
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
				
		PhysicsFactory.createBoxBody(this.goPhysicsWorld, bottomOuter, BodyType.StaticBody, WALL_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(this.goPhysicsWorld, topOuter, BodyType.StaticBody, WALL_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(this.goPhysicsWorld, leftOuter, BodyType.StaticBody, WALL_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(this.goPhysicsWorld, rightOuter, BodyType.StaticBody, WALL_FIXTURE_DEF);
			

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
			loader.load(GameActivity._main, this.goPhysicsWorld, "plat1.xml", platform,
					false, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//		final Body boxBody = Game_Scene.createObstacleBody(this.goPhysicsWorld, platform, BodyType.StaticBody, WALL_FIXTURE_DEF);
//		boxBody.setLinearDamping(10);
//		boxBody.setAngularDamping(10);

//		this.goPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(platform, boxBody, true, true));

		
	}
		
//    public static Body createObstacleBody(final PhysicsWorld pPhysicsWorld, final IAreaShape pAreaShape, final BodyType pBodyType, final FixtureDef pFixtureDef) {
//                
//        final float point1x = 66 / PIXEL_TO_METER_RATIO_DEFAULT;
//        final float point1y = -10 / PIXEL_TO_METER_RATIO_DEFAULT;
//        
//        final float point2x = 24 / PIXEL_TO_METER_RATIO_DEFAULT;
//        final float point2y = -10 / PIXEL_TO_METER_RATIO_DEFAULT;
//        
//        final float point3x = -37 / PIXEL_TO_METER_RATIO_DEFAULT;
//        final float point3y = -6 / PIXEL_TO_METER_RATIO_DEFAULT;
//        
//        final float point4x = -67 / PIXEL_TO_METER_RATIO_DEFAULT;
//        final float point4y = -4 / PIXEL_TO_METER_RATIO_DEFAULT;
//        
//        final float point5x = -71 / PIXEL_TO_METER_RATIO_DEFAULT;
//        final float point5y = 0 / PIXEL_TO_METER_RATIO_DEFAULT;
//        
//        final float point6x = -72 / PIXEL_TO_METER_RATIO_DEFAULT;
//        final float point6y = 11 / PIXEL_TO_METER_RATIO_DEFAULT;
//        
//        final float point7x = 0 / PIXEL_TO_METER_RATIO_DEFAULT;
//        final float point7y = 7 / PIXEL_TO_METER_RATIO_DEFAULT;
//        
//        final float point8x = 73 / PIXEL_TO_METER_RATIO_DEFAULT;
//        final float point8y = 2 / PIXEL_TO_METER_RATIO_DEFAULT;
//        
//        
//        /* Remember that the vertices are relative to the center-coordinates of the Shape. */
//        final float halfWidth = pAreaShape.getWidthScaled() * 0.5f / PIXEL_TO_METER_RATIO_DEFAULT;
//        final float halfHeight = pAreaShape.getHeightScaled() * 0.5f / PIXEL_TO_METER_RATIO_DEFAULT;        
//        
//        
//        final Vector2[] vertices = {
//                        new Vector2(point1x, point1y),
//                        new Vector2(point2x, point2y),
//                        new Vector2(point3x, point3y),
//                        new Vector2(point4x, point4y),
//                        new Vector2(point5x, point5y),
//                        new Vector2(point6x, point6y),
//                        new Vector2(point7x, point7y),
//                        new Vector2(point8x, point8y)
//        };
//
//        return PhysicsFactory.createPolygonBody(pPhysicsWorld, pAreaShape, vertices, pBodyType, pFixtureDef);
//}
    
//	private void addFlare(final float pX, final float pY) {
//		final AnimatedSprite flare = new AnimatedSprite(pX, pY, GfxAssets.mFlare, GameActivity._main.getVertexBufferObjectManager());
//		
//		flare.animate(new long[]{200, 200, 200, 200}, 0, 3, true);
//
//		this.attachChild(flare);
//	}
	
}


