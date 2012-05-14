package falldowndizzy.pac;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.view.KeyEvent;

public class GameActivity extends SimpleBaseGameActivity {
	
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 320;
	private static final int CAMERA_HEIGHT = 480;
	
	
	public static MainState _MainState;
	public static Camera _Camera;
	private boolean _GameLoaded = false;
	public static GameActivity _main;
	public static Engine _Engine;
	
	public static VertexBufferObjectManager mVertexBufferObjectManager = new VertexBufferObjectManager();

	@Override
	public EngineOptions onCreateEngineOptions() {
		_main = this;
		_Camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions opt = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), _Camera);
	    opt.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		return opt;
	}

	@Override
	protected void onCreateResources() {
		_Engine = this.mEngine;
		
		GfxAssets.LoadGFX();
	}

	@Override
	protected Scene onCreateScene() {
		_MainState = new MainState();
		_GameLoaded = true;
		return _MainState;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (!_GameLoaded ) return true;
			if (_MainState != null && _GameLoaded) {
				_MainState.KeyPressed(keyCode, event);
				return true;
			}
			return true;
		}
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}	

}
