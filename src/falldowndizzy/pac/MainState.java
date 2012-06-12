package falldowndizzy.pac;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;


import android.view.KeyEvent;

public class MainState extends Scene {
	
	public static MainMenu_Scene _MainMenu_Scene = new MainMenu_Scene();
	public static Game_Scene _Game_Scene = new Game_Scene();
	public static LevelSelect_Scene _LevelSelect_Scene = new LevelSelect_Scene();
	
	private static int _GameState;
	
	private static final int MAIN_MENU_STATE = 0;
	private static final int SELECT_LEVELS_STATE = 1;
	private static final int GAME_RUNNING_STATE = 2;
	
	public MainState() {
		
		attachChild(_MainMenu_Scene);
		attachChild(_Game_Scene);
		ShowMainScene();
	}
	
	public static void ShowGameScene() {
		_MainMenu_Scene.Hide();
		_Game_Scene.Show();
		_LevelSelect_Scene.Hide();
		_GameState = GAME_RUNNING_STATE;
	}
	
	public static void ShowMainScene() {
		_MainMenu_Scene.Show();
		_Game_Scene.Hide();
		_LevelSelect_Scene.Hide();
		_GameState = MAIN_MENU_STATE;
	}
	
	public static void ShowLevelScene() {
		_LevelSelect_Scene.Show();
		_Game_Scene.Hide();
		_MainMenu_Scene.Hide();
		_GameState = SELECT_LEVELS_STATE;
	}
	
	
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		switch (_GameState)
		{
		case MAIN_MENU_STATE:
			_MainMenu_Scene.onSceneTouchEvent(pSceneTouchEvent);
			break;
		case GAME_RUNNING_STATE:
			_Game_Scene.onSceneTouchEvent(pSceneTouchEvent);
			break;
			
		}
		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

	public void KeyPressed(int keyCode, KeyEvent event) {
		switch (_GameState)
		{
		case MAIN_MENU_STATE:
			GameActivity._main.onDestroy();
			break;
		case GAME_RUNNING_STATE:
			ShowMainScene();
			break;
		}
	}

}
