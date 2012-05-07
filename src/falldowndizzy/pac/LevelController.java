package falldowndizzy.pac;

import java.util.ArrayList;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.shape.Shape;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.FixtureDef;

import falldowndizzy.pac.GameLogicController;
import falldowndizzy.pac.Dizzy;
import falldowndizzy.pac.SoundManager;

public class LevelController {

	public Scene scene;
	private PhysicsWorld mPhysicsWorld;
	private GameLogicController gameLogicController;
	private SoundManager soundManager;
	
	
    public GameLogicController getGameLogicController() {
		return gameLogicController;
	}

	Dizzy mPlayer;
	
	private ArrayList<Shape> enemyList;
	private ArrayList<Shape> goodsList;
	private ArrayList<Shape> endPointList;
	
	int levelId;
	
	boolean isGameFinished = false;
	
	protected int mCameraWidth;
	public int getCameraWidth() {
		return mCameraWidth;
	}
	public ArrayList<Shape> getEndPointList(){return endPointList;}
	public ArrayList<Shape> getGoodsList(){return goodsList;}

	public int getCameraHeight() {
		return mCameraHeight;
	}
	final FixtureDef wallFixtureDefF = PhysicsFactory.createFixtureDef(0, 0.0f, 0.0f);
	protected int mCameraHeight;
	
	public LevelController(GameLogicController gameLogicController) {
		levelId = 0;
		soundManager = new SoundManager();
		
		
		this.gameLogicController = gameLogicController;
	}
	
	public void addSoundManager(Sound s1, Sound s2){
		soundManager.SetSound(s1, s2);
	}	
	
}
