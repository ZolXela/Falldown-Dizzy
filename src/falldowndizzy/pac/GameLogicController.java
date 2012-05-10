package falldowndizzy.pac;

import java.io.IOException;
import java.util.Date;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.engine.Engine;
import org.andengine.engine.options.AudioOptions;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.MusicOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

import falldowndizzy.pac.GameLogicController;
import falldowndizzy.pac.LevelController;
import falldowndizzy.pac.PlayerProfileManager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;

public class GameLogicController extends BaseGameActivity implements IAccelerationListener{

	PlayerProfileManager playerProfileManager;
	
	public LevelController levelController;

	public static Camera camera;

	private RatioResolutionPolicy resolution;
	protected PhysicsWorld mPhysicsWorld;
	
	public Texture mCloudTexture;
	public TextureRegion mCloudTextureRegion;
	
	public Texture mDizzyTexture;
	public TextureRegion mDizzyTextureRegion;

	public TiledTextureRegion mCircleFaceTextureRegion;
	
	private MainScene _MainScene;
	private boolean _GameLoaded;
	public static Engine curEngine;
	
	 /**
     * Atlas region, where the graphic for sprite is loaded in
     */

	public Texture mLevelTexture;

	public Texture mDiamantTexture;
	public TextureRegion mDiamantTextureRegion;
	
	private RepeatingSpriteBackground mMenuBackground;	
	private RepeatingSpriteBackground mSky; 

	
	public Texture mBackgroundTexture;
	public TextureRegion mBackgroundTextureRegion;
	
//  Audio *****************************************	

	private Sound jumpingSound; 
	private Sound getGoodsSound;
	private Sound mGameOverSound;
	private Music backgroundMenuMusic;
    private Music backgroundGameMusic;
    
//  Audio *****************************************	
    
    
	public Texture mLevelMenuTexture;
	public TextureRegion mLevelTextureRegion;
	
	static GameLogicController gameLogicController;
	public static VertexBufferObjectManager mVertexBufferObjectManager;
	public static GameLogicController getInstance(){
		return gameLogicController;
	}
	
	int currentPage;

	
/**
 * Activity methods
 */	
	
	
	public Engine onLoadEngine() {
			
		currentPage = 0;
		curEngine = this.mEngine;
		mVertexBufferObjectManager = this.getVertexBufferObjectManager();
		playerProfileManager = new PlayerProfileManager(this);

		gameLogicController = this;
		levelController = new LevelController(this);

		levelController.mCameraWidth = 640;
		levelController.mCameraHeight = 320;

		camera = new Camera(0, 0, levelController.mCameraWidth, levelController.mCameraHeight);
		resolution = new RatioResolutionPolicy(levelController.mCameraWidth, levelController.mCameraHeight);
		return new Engine(new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, resolution, camera));
		
	}
	
//	public Scene newGameLevelScene(int levelId){
//		
//		Scene scene = new Scene();
//		this.mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, 0), false);
//		levelController.setScene(scene);
//		levelController.setmPhysicsWorld(mPhysicsWorld);
//		levelController.createFrame();
//		levelController.loadLevel(levelId);
//		this.enableAccelerationSensor(this);
//		scene.registerUpdateHandler(this.mPhysicsWorld);
//		return scene;
//	}

	
	public Scene onLoadScene() {
        /**
         * Create new scene and save it
         * in MainScene. 
         */		
		_MainScene = new MainScene();
		_GameLoaded = true;
        return _MainScene;
	}


	public void onLoadResources(){
        
		SoundFactory.setAssetBasePath("mfx/");
		try {
			jumpingSound = SoundFactory.createSoundFromAsset(
					mEngine.getSoundManager(), this, "pew_pew_lei.wav");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SoundFactory.setAssetBasePath("mfx/");
		try {
			getGoodsSound = SoundFactory.createSoundFromAsset(
					mEngine.getSoundManager(), this, "pew_pew_lei.wav");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SoundFactory.setAssetBasePath("mfx/");
		try {
			mGameOverSound = SoundFactory.createSoundFromAsset(
					mEngine.getSoundManager(), this, "pew_pew_lei.wav");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		

		MusicFactory.setAssetBasePath("mfx/");
		try {
			backgroundMenuMusic = MusicFactory.createMusicFromAsset(
					mEngine.getMusicManager(), this, "background_music.wav");
			backgroundMenuMusic.setLooping(true);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			backgroundGameMusic = MusicFactory.createMusicFromAsset(
					mEngine.getMusicManager(), this, "background_music.wav");
			backgroundGameMusic.setLooping(true);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		
	}	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

    	return super.onTouchEvent(event);
 
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	
    	if (keyCode == KeyEvent.KEYCODE_BACK){
    		if(!_GameLoaded) return true;
    		 if(_MainScene != null && _GameLoaded){
    			 _MainScene.KeyPressed(keyCode, event);
    			 return true;
    		 }
    		return true; 
    	}
    	
    	return super.onKeyDown(keyCode, event);
    }
    
    
	@Override
	protected void onDestroy(){
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
    
	
	
	
	
	
	
	
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
