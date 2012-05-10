package falldowndizzy.pac;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.view.KeyEvent;

public class MainScene extends Scene {

	public static MainMenu_Scene _MainMenu_Scene = new MainMenu_Scene();
	public static GameScene_Scene _GameScene_Scene = new GameScene_Scene();
	public static LevelSelect_Scene _LevelSelect_Scene = new LevelSelect_Scene();
	
/**
 * Scene identifier - defines the scene is shown now
 */
	private static int _SceneState;
	
/**
 * Scene identifier's possible values
 */
	private static final int MAIN_MENU_STATE = 0;
	private static final int LEVEL_SELECT_STATE = 1;
	private static final int GAME_RUNNING_STATE = 2;
	
	
	
	public MainScene(){
		
		attachChild(_MainMenu_Scene);
		attachChild(_GameScene_Scene);
		_MainMenu_Scene.Show();
		
	}
	
	
	public static void ShowGameScene(int levelID){
		_MainMenu_Scene.Hide();
		_GameScene_Scene.Show(levelID);
		_LevelSelect_Scene.Hide();
		_SceneState = GAME_RUNNING_STATE;
	}
	
	public static void ShowMenuScene(){
		_MainMenu_Scene.Show();
		_GameScene_Scene.Hide();
		_LevelSelect_Scene.Hide();
		_SceneState = MAIN_MENU_STATE;
	}
	
	public static void ShowLevelSelectScene(){
		_MainMenu_Scene.Hide();
		_GameScene_Scene.Hide();
		_LevelSelect_Scene.Show();
		_SceneState = LEVEL_SELECT_STATE;
	}
	
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent){
		
		switch(_SceneState){
		
			case MAIN_MENU_STATE: {
				_MainMenu_Scene.onSceneTouchEvent(pSceneTouchEvent);
				break;
			}
			case GAME_RUNNING_STATE: {
				_GameScene_Scene.onSceneTouchEvent(pSceneTouchEvent);
				break;
			}
			case LEVEL_SELECT_STATE: {
				_LevelSelect_Scene.onSceneTouchEvent(pSceneTouchEvent);
				break;
			}
		}		
		return super.onSceneTouchEvent(pSceneTouchEvent);
	}
	

	public void KeyPressed(int keyCode, KeyEvent event){
		
		switch(_SceneState){
		
			case MAIN_MENU_STATE: {
				GameLogicController.gameLogicController.onDestroy();
				break;
			}
			case GAME_RUNNING_STATE: {
				ShowMenuScene();
				break;
			}
			case LEVEL_SELECT_STATE: {
				ShowMenuScene();
				break;
			}
		}		

	}





}


