package falldowndizzy.pac;

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;

import android.hardware.SensorManager;
import android.opengl.GLES20;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Game_Scene extends CameraScene {
	
	public static PhysicsWorld fallPhysicsWorld;
	public static PhysicsWorld goPhysicsWorld;
	private Dizzy myPlayer;
	
	public Game_Scene(){
		super(GameActivity._Camera);
		this.setFallPhysicsWorld();
		this.setGoPhysicsWorld();
		
		final AutoParallaxBackgroundXY autoParallaxBackgroundXY = new AutoParallaxBackgroundXY(0, 0, 0, 5);
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerBack, GameActivity.mVertexBufferObjectManager)));
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(5.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerCloud, GameActivity.mVertexBufferObjectManager)));	
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerTrees, GameActivity.mVertexBufferObjectManager)));
				
		setBackground(autoParallaxBackgroundXY);
		
		this.CreateDizzy(50, 30);
		attachChild(myPlayer);
		this.initOnScreenControls();
//		myPlayer.FallDown(fallPhysicsWorld);

	}

	public void Show(){
		setVisible(true);
		setIgnoreUpdate(false);
	}
	
	public void Hide(){
		setVisible(false);
		setIgnoreUpdate(true);
	}
	
	private void setFallPhysicsWorld(){
		fallPhysicsWorld = new PhysicsWorld(
				new Vector2(0, SensorManager.GRAVITY_EARTH/20), false);
		this.registerUpdateHandler(fallPhysicsWorld);
	}
	
	private void setGoPhysicsWorld(){
		goPhysicsWorld = new FixedStepPhysicsWorld(30, new Vector2(0, 0), false, 8, 1);
		this.registerUpdateHandler(goPhysicsWorld);
	}
	
	public void CreateDizzy(float pX, float pY){
		myPlayer = new Dizzy(pX, pY, 
				GfxAssets.mPlayer, GameActivity.mVertexBufferObjectManager);
	}
	
	private void initOnScreenControls() {
		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(
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
//				final Body playerBody = myPlayer.DizzyBody;

				final Vector2 velocity = Vector2Pool.obtain(pValueX * 5, pValueY * 5);
				myPlayer.GoRight(velocity, goPhysicsWorld);
//				myPlayer.DizzyBody.setLinearVelocity(velocity);
				Vector2Pool.recycle(velocity);
			}

			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				/* Nothing. */
			}
		});
		analogOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.5f);
		analogOnScreenControl.refreshControlKnobPosition();

		this.setChildScene(analogOnScreenControl);
	}
}
