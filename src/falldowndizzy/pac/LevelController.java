package falldowndizzy.pac;

import java.util.ArrayList;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.shape.Shape;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

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
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public void setmPhysicsWorld(PhysicsWorld mPhysicsWorld) {
		this.mPhysicsWorld = mPhysicsWorld;
	}
	
	public void createFrame(){
		
		//	205-175-149 	cdaf95
		final IAreaShape ground = new Rectangle(0, mCameraHeight - 2, mCameraWidth, 2, new VertexBufferObjectManager());
		ground.setColor(0.7f, 0.5f, 0.3f);
		
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);

	}
	public void loadLevel(int levelId){
		isGameFinished = false;
		this.setCurrentLevel(levelId);

	}
	
	public void setCurrentLevel(int levelId){
		this.levelId = levelId;
	}
	
}
