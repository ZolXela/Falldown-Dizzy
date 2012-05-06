package falldowndizzy.pac;

import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import falldowndizzy.pac.GameLogicController;
import falldowndizzy.pac.Player;
import falldowndizzy.pac.SoundManager;

public class LevelController {

	public Scene scene;
	private PhysicsWorld mPhysicsWorld;
	private GameLogicController gameLogicController;
	private SoundManager soundManager;
	
	
    public GameLogicController getGameLogicController() {
		return gameLogicController;
	}

	Player mPlayer;
	
	
	
}
