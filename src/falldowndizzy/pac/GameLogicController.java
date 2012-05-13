package falldowndizzy.pac;

import org.andengine.engine.Engine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;

import org.andengine.ui.activity.SimpleBaseGameActivity;

import falldowndizzy.pac.AutoParallaxBackgroundXY;

import android.view.KeyEvent;

public class GameLogicController extends SimpleBaseGameActivity {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 320;
	private static final int CAMERA_HEIGHT = 480;	
	
	// ===========================================================
	// BackGround Area
	// ===========================================================
		
	private BitmapTextureAtlas mAutoParallaxBackgroundTexture;

	private ITextureRegion mParallaxLayerBack;
	private ITextureRegion mParallaxLayerTrees;
	private ITextureRegion mParallaxLayerCloud;
	
	
	public static Camera _Camera;
	
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

		_Camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		final EngineOptions opt = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), _Camera);
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
		curEngine.registerUpdateHandler(new FPSLogger());
		
		_MainScene = new MainScene();		
		
		final AutoParallaxBackgroundXY autoParallaxBackgroundXY = new AutoParallaxBackgroundXY(0, 0, 0, 5);
		final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
		autoParallaxBackgroundXY.attachParallaxEntityXY(new falldowndizzy.pac.AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, this.mParallaxLayerBack, vertexBufferObjectManager)));
		autoParallaxBackgroundXY.attachParallaxEntityXY(new falldowndizzy.pac.AutoParallaxBackgroundXY.ParallaxEntityXY(-5.0f, 0.0f, new Sprite(0, 0, this.mParallaxLayerCloud, vertexBufferObjectManager)));	
		autoParallaxBackgroundXY.attachParallaxEntityXY(new falldowndizzy.pac.AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, this.mParallaxLayerTrees, vertexBufferObjectManager)));
		
		_MainScene.setBackground(autoParallaxBackgroundXY);
		
		_GameLoaded = true;
        return _MainScene;
	}
    
	@Override
	protected void onCreateResources() {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024);
		this.mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "dizzy_parallax_background_layer_back.png", 0, 0);
		this.mParallaxLayerCloud = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "clouds_bg.png", 321, 0);
		this.mParallaxLayerTrees = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "dizzy_parallax_background_layer_trees.png", 0, 481);
		this.mAutoParallaxBackgroundTexture.load();
	}
}
