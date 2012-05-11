package falldowndizzy.pac;

import org.andengine.engine.Engine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.entity.scene.Scene;

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
	protected void onCreateResources() {
		
	}
}
