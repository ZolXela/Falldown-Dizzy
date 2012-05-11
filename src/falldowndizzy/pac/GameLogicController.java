package falldowndizzy.pac;

import org.andengine.engine.Engine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.entity.scene.Scene;
<<<<<<< HEAD
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.view.KeyEvent;

public class GameLogicController extends SimpleBaseGameActivity {

	PlayerProfileManager playerProfileManager;

	public static Camera camera;
	
	private MainScene _MainScene;
	private boolean _GameLoaded = false;
	public static Engine curEngine;
	
	 /**
     * Atlas region, where the graphic for sprite is loaded in
     */

	public static GameLogicController gameLogicController;
	public static VertexBufferObjectManager mVertexBufferObjectManager = new VertexBufferObjectManager();
	
/**
 * Activity methods
 */	
	@Override
	public EngineOptions onCreateEngineOptions() {
		gameLogicController = this;
		camera = new Camera(0, 0, 800, 460);
		final EngineOptions opt = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, 
				new RatioResolutionPolicy(camera.getWidth(), camera.getHeight()),
				camera);
		curEngine = new Engine(opt);
		return opt;	
=======
import org.andengine.ui.activity.BaseGameActivity;

import falldowndizzy.pac.GameLogicController;
import falldowndizzy.pac.PlayerProfileManager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;


public class GameLogicController extends BaseGameActivity {

	PlayerProfileManager playerProfileManager;

	public static Camera camera;
	
	private MainScene _MainScene;
	private boolean _GameLoaded;
	public static Engine curEngine;
	
	 /**
     * Atlas region, where the graphic for sprite is loaded in
     */

	static GameLogicController gameLogicController;
	public static VertexBufferObjectManager mVertexBufferObjectManager;
	
	public static GameLogicController getInstance(){
		return gameLogicController;
	}

	
/**
 * Activity methods
 */	
	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, 640, 320);
		final EngineOptions opt = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, 
				new RatioResolutionPolicy(camera.getWidth(), camera.getHeight()),
				camera);
		opt.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		return opt;	
	}
	
	public Engine onLoadEngine() {

		curEngine = this.mEngine;
		mVertexBufferObjectManager = this.getVertexBufferObjectManager();
		playerProfileManager = new PlayerProfileManager(this);
		return new Engine(onCreateEngineOptions());
	}
	
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
		
	}	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		gameLogicController = this;
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
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		// TODO Auto-generated method stub
		
>>>>>>> 54d7ba15fbb419d583fed1c0d3ec1e4cf43b01a1
	}
	
	@Override
	public Scene onCreateScene() {
        /**
         * Create new scene and save it
         * in MainScene. 
         */		
		
		_MainScene = new MainScene();
//		
		_GameLoaded = true;
        return _MainScene;
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

<<<<<<< HEAD
	@Override
	protected void onCreateResources() {
		
	}


=======
>>>>>>> 54d7ba15fbb419d583fed1c0d3ec1e4cf43b01a1
}
