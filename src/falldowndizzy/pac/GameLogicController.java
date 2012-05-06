package falldowndizzy.pac;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.ui.activity.BaseGameActivity;

import falldowndizzy.pac.GameLogicController;
//import falldowndizzy.pac.LevelController;
//import falldowndizzy.pac.PlayerProfileManager;

import com.badlogic.gdx.math.Vector2;

public class GameLogicController extends BaseGameActivity implements IAccelerationListener{

//	PlayerProfileManager playerProfileManager;
	
//	public LevelController levelController;

	private Camera camera;
	protected PhysicsWorld mPhysicsWorld;

	public Texture mTexture;
	public TextureRegion enemyTextureRegion;
	
	public Texture mFinishLineTexture;
	public TextureRegion mFinishLineTextureRegion;
	
	public Texture mBlockTexture;
	public TextureRegion mBlockTextureRegion;
	
	private float mGravityX;
	private float mGravityY;
	private final Vector2 mTempVector = new Vector2();

	public TiledTextureRegion mCircleFaceTextureRegion;
	
	public Texture mEnemyTexture;
	
	public Texture mDiamantTexture;
	public TextureRegion mDiamantTextureRegion;
	
	private RepeatingSpriteBackground mGrassBackground;
	private RepeatingSpriteBackground mMenuBackground;
	
	public Texture mBackgroundTexture;
	public TextureRegion mBackgroundTextureRegion;

	private Sound mGameOverSound;
	private Sound mMunchSound;
	
	public Texture mLevelMenuTexture;
	public TextureRegion mLevelTextureRegion;
	
	static GameLogicController gameLogicController;
	
	public static GameLogicController getInstance(){return gameLogicController;}
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub
		
	}

}
