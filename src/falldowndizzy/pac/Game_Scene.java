package falldowndizzy.pac;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.color.Color;

import android.hardware.SensorManager;
import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Game_Scene extends CameraScene {
	
	// ===========================================================
	// Constants
	// ===========================================================

	/* The categories. */
	public static final short CATEGORYBIT_WALL = 1;
	public static final short CATEGORYBIT_PLATO = 2;
	public static final short CATEGORYBIT_PLAYER = 4;

	/* And what should collide with what. */
	public static final short MASKBITS_WALL = CATEGORYBIT_WALL |  CATEGORYBIT_PLAYER;
	public static final short MASKBITS_PLATO = CATEGORYBIT_PLAYER;  // Missing: CATEGORYBIT_PLAYER
	public static final short MASKBITS_PLAYER = CATEGORYBIT_WALL  |  CATEGORYBIT_PLATO;// Missing: CATEGORYBIT_PLATO

	/* FixtureDefs for Dizzy, borders and obstacles */
	public static final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.1f, 0.0f, 1.0f, false, CATEGORYBIT_WALL, MASKBITS_WALL, (short)0);
	public static final FixtureDef PLATO_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.2f, 0.0f, 1.0f, false, CATEGORYBIT_PLATO, MASKBITS_PLATO, (short)0);
	public static final FixtureDef PLAYER_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 0.8f, false, CATEGORYBIT_PLAYER, MASKBITS_PLAYER, (short)0);
	
	/* Basic fields */
	public PhysicsWorld gamePhysicsWorld;
	private Dizzy gamePlayer;
	Vector2 velocity;
	
	private boolean gameLoaded = false;
	
	/**
	 * Screen borders
	 */
	public static Rectangle bottomOuter;
	public static Rectangle topOuter;
	public static Rectangle leftOuter;
	public static Rectangle rightOuter;
	
	/**
	 * Fields implementing Dizzy's moving ability
	 */
	public float GlobalX = 30;
	public float GlobalY;
	
	private int finger = 0;
	float currentX = 0;
	private float jumpHeight = -18;
	private float goStep = 5;

	/* Fields for Dizzy's score */
	private Text _score;
	private final int maxScore = 10;
	private int hitCount;
	
	public Game_Scene(){
		super(GameActivity._Camera);
		this.setGamePhysicsWorld();
				
		setBackground(this.LoadAutoParallaxBg());		
		this.initBorders();
		this.CreateDizzy(30, 50);

		attachChild(gamePlayer);
		gamePlayer.Stay();
		this.setTouchAreaBindingOnActionDownEnabled(true);
		gameLoaded = true;
	}

	public void Show(){
		setVisible(true);
		setIgnoreUpdate(false);
	}
	
	public void Hide(){
		setVisible(false);
		setIgnoreUpdate(true);
	}
	
	private void setGamePhysicsWorld(){

		gamePhysicsWorld = new FixedStepPhysicsWorld(100, new Vector2(0, SensorManager.GRAVITY_EARTH * 5), true, 8, 1);	
		this.registerUpdateHandler(gamePhysicsWorld);
		
	}
	
	public AutoParallaxBackgroundXY LoadAutoParallaxBg(){
		
		final AutoParallaxBackgroundXY autoParallaxBackgroundXY = new AutoParallaxBackgroundXY(0, 0, 0, 5);
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerMountains, GameActivity.mVertexBufferObjectManager)));
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(5.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerCloud, GameActivity.mVertexBufferObjectManager)));	
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerTreesBg, GameActivity.mVertexBufferObjectManager)));
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerTrees, GameActivity.mVertexBufferObjectManager)));
//		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerTrees, GameActivity.mVertexBufferObjectManager)));
		return autoParallaxBackgroundXY;	
	}
	
	public void CreateDizzy(float pX, float pY){
		gamePlayer = new Dizzy(pX, pY, 
				GfxAssets.mPlayerTextureRegion, GameActivity.mVertexBufferObjectManager, this.gamePhysicsWorld);
	}
		
	private boolean isJumping(Dizzy player){	
		return player.jumping;	
	}
	
	private void initBorders() {

		bottomOuter = new Rectangle(0, GameActivity.CAMERA_HEIGHT, GameActivity.CAMERA_WIDTH, 2, GameActivity.mVertexBufferObjectManager);
		topOuter = new Rectangle(0, 0, GameActivity.CAMERA_WIDTH, 2, GameActivity.mVertexBufferObjectManager);
		leftOuter = new Rectangle(0, 0, 2, GameActivity.CAMERA_HEIGHT, GameActivity.mVertexBufferObjectManager);
		rightOuter = new Rectangle(GameActivity.CAMERA_WIDTH - 2, 0, 2, GameActivity.CAMERA_HEIGHT, GameActivity.mVertexBufferObjectManager);
				
		PhysicsFactory.createBoxBody(this.gamePhysicsWorld, bottomOuter, BodyType.StaticBody, PLATO_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(this.gamePhysicsWorld, topOuter, BodyType.StaticBody, PLATO_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(this.gamePhysicsWorld, leftOuter, BodyType.StaticBody, WALL_FIXTURE_DEF);
		PhysicsFactory.createBoxBody(this.gamePhysicsWorld, rightOuter, BodyType.StaticBody, WALL_FIXTURE_DEF);
			
		bottomOuter.setColor(Color.BLACK);
		topOuter.setColor(Color.BLACK);
		leftOuter.setColor(Color.BLACK);
		rightOuter.setColor(Color.BLACK);
		
		this.attachChild(bottomOuter);
		this.attachChild(topOuter);
		this.attachChild(leftOuter);
		this.attachChild(rightOuter);
				
		this.addObstacle(0, GameActivity.CAMERA_HEIGHT / 2, GfxAssets.mPlatformTextureRegion1, "plat1.xml");
		this.showScore();
		
	}
	
	private void playerController() {	

		float currentPosY = gamePlayer.getY();
		if(GlobalY >= (currentPosY + jumpHeight * 5) && finger == 1) {
			float currentPosX = gamePlayer.getX();
				if((currentPosX) > GlobalX) {			
						velocity = Vector2Pool.obtain((-1) * goStep, 0);
							currentX = velocity.x;
								gamePlayer.GoLeft(velocity);
				}
				else if((currentPosX + gamePlayer.getWidth()) < GlobalX){
					velocity = Vector2Pool.obtain(goStep, 0);
							currentX = velocity.x;
								gamePlayer.GoRight(velocity);
				}
		} else if(GlobalY < (currentPosY + jumpHeight * 3)) {
			velocity = Vector2Pool.obtain(currentX, jumpHeight);
				gamePlayer.Jump(velocity);
					GlobalY = GameActivity.CAMERA_HEIGHT;
		}
		else gamePlayer.Stay();
	}	
			
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		final TouchEvent curTouchEvent = pSceneTouchEvent;
		if(gameLoaded && finger <= 2){
			switch(pSceneTouchEvent.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(finger == 0) {
						GlobalX = curTouchEvent.getX();
					}
					GlobalY = curTouchEvent.getY();
						finger++;
							if(!isJumping(gamePlayer)){
								this.playerController();
							}
					break;
				case MotionEvent.ACTION_UP:
					finger = (finger > 0) ? finger-1 : 0;
						if (finger <= 1) {
							currentX = 0;
							if(!isJumping(gamePlayer))
								this.playerController();						
						}
						else
							gamePlayer.Stay();	
					break;
				default:
					if(!isJumping(gamePlayer) && finger > 0)
							this.playerController();
					break;
			}
			return true;
			}
		return false;

	}

	private void addObstacle(final float pX, final float pY, ITextureRegion pTextureRegion, String xmlFile) {
	
		final Obstacle _obstacle = new Obstacle(pX, pY, pTextureRegion, GameActivity.mVertexBufferObjectManager, this.gamePhysicsWorld, xmlFile);
		this.attachChild(_obstacle);

	}
	
	private void addEnemies(final float pX, final float pY, ITiledTextureRegion pTextureRegion, String xmlFile) {

		final SpiderEnemy _spiderEnemy = new SpiderEnemy(pX, pY, pTextureRegion, GameActivity.mVertexBufferObjectManager, this.gamePhysicsWorld, xmlFile);
		this.attachChild(_spiderEnemy);

	}    
	
	private void showScore(){
		
		this._score = new Text(0, 0, GfxAssets.mFont, String.valueOf(maxScore), GameActivity._main.getVertexBufferObjectManager());
		// repositioning the score later so we can use the score.getWidth()
		System.out.println("************* Est' ili net??? " + this._score.getWidth());
		this._score.setPosition(GameActivity.CAMERA_WIDTH - this._score.getWidth() - 5, 5);

//		createSpriteSpawnTimeHandler();
		this.attachChild(_score);
//		this.registerUpdateHandler(detect);

//		restart();
	}
	
	IUpdateHandler detect = new IUpdateHandler() {
		@Override
		public void reset() {
		}

		@Override
		public void onUpdate(float pSecondsElapsed) {

		}
	};

	/** to restart the game and clear the whole screen */
	public void restart() {

		GameActivity._main.runOnUpdateThread(new Runnable() {

			@Override
			// to safely detach and re-attach the sprites
			public void run() {
				Game_Scene.this.detachChildren();
				Game_Scene.this.attachChild(gamePlayer);
				Game_Scene.this.attachChild(Game_Scene.this._score);
			}
		});

		// resetting everything
		hitCount = 0;
		this._score.setText(String.valueOf(hitCount));

	}
//	private void addFlare(final float pX, final float pY) {
//		final AnimatedSprite flare = new AnimatedSprite(pX, pY, GfxAssets.mFlare, GameActivity._main.getVertexBufferObjectManager());
//		
//		flare.animate(new long[]{200, 200, 200, 200}, 0, 3, true);
//
//		this.attachChild(flare);
//	}
	
}


