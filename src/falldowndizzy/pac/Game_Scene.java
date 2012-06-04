package falldowndizzy.pac;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.engine.handler.IUpdateHandler;
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
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.color.Color;

import android.hardware.SensorManager;
import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Game_Scene extends CameraScene {
	
	public static Game_Scene _curGameScene;
	// ===========================================================
	// Constants
	// ===========================================================

	/* The categories. */
	public static final short CATEGORYBIT_WALL = 1;
	public static final short CATEGORYBIT_PLATO = 2;
	public static final short CATEGORYBIT_PLAYER = 4;
	

	/* And what should collide with what. */
	public static final short MASKBITS_WALL = CATEGORYBIT_WALL | CATEGORYBIT_PLAYER;
	public static final short MASKBITS_PLATO = CATEGORYBIT_PLAYER;  // Missing: CATEGORYBIT_PLAYER
	public static final short MASKBITS_PLAYER = CATEGORYBIT_WALL | CATEGORYBIT_PLATO;// Missing: CATEGORYBIT_PLATO

	/* FixtureDefs for Dizzy, borders and obstacles */
	public static final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.1f, 0.0f, 1.0f, false, CATEGORYBIT_WALL, MASKBITS_WALL, (short)0);
	public static final FixtureDef PLATO_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.2f, 0.0f, 1.0f, false, CATEGORYBIT_PLATO, MASKBITS_PLATO, (short)0);
	public static final FixtureDef PLAYER_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 0.8f, false, CATEGORYBIT_PLAYER, MASKBITS_PLAYER, (short)0);
	
	/* Basic fields */
	public PhysicsWorld gamePhysicsWorld;
	public Dizzy gamePlayer;
	Vector2 velocity;
	
	private boolean gameLoaded = false;
	private boolean isGameFinished = false;
	
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
	private float goStep = 7;

	/* Fields for Dizzy's score */
	public static Text _score;
	private final static int maxScore = 30;
	public static int curScore = maxScore;
	
	public static LinkedList<SpiderEnemy> spiderLL;
	private LinkedList<Obstacle> platformLL;
	public static LinkedList<GoodFruit> goodsLL;
	
	private int obstacleQuantity = 3;
	private int fruitsQuantity = 3;
	private int spidersQuantity = 2;
	
	
	
	public Game_Scene(){
	
		super(GameActivity._Camera);
		this.setGamePhysicsWorld();
				
		setBackground(this.LoadAutoParallaxBg());	
		
		platformLL = new LinkedList<Obstacle>();
		spiderLL = new LinkedList<SpiderEnemy>();
		goodsLL = new LinkedList<GoodFruit>();
		
		this.initBorders();
		this.CreateDizzy(30, 50);
		attachChild(gamePlayer);
		gamePlayer.Stay();
		this.setTouchAreaBindingOnActionDownEnabled(true);

		gameLoaded = true;
		this.registerUpdateHandler(gamePhysicsWorld);
		this.registerUpdateHandler(new IUpdateHandler(){

			@Override
			public void onUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub
				
			
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		
		});
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
		
	}
	
	public AutoParallaxBackgroundXY LoadAutoParallaxBg(){
		
		final AutoParallaxBackgroundXY autoParallaxBackgroundXY = new AutoParallaxBackgroundXY(0, 0, 0, 5);
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerMountains, GameActivity.mVertexBufferObjectManager)));
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(5.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerCloud, GameActivity.mVertexBufferObjectManager)));	
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerTreesBg, GameActivity.mVertexBufferObjectManager)));
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerTrees, GameActivity.mVertexBufferObjectManager)));
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
				
		this.addObstacles(GfxAssets.mPlatformTextureRegion1, "plat1.xml", 1);
//		this.addEnemies(100, -10, GfxAssets.mSpiderTextureRegion, "enemy.xml", 0, spidersQuantity);
		addGoods(GameActivity.CAMERA_HEIGHT, GameActivity.CAMERA_WIDTH, GfxAssets.mGoodsArray, fruitsQuantity + 3);
		
		this.showScore();
//		spiderLL.add(object)
	}
	
	private boolean rightSet = false;
	private boolean leftSet = false;
	private boolean upSet = false;
	
	private void setRight(){
		gamePlayer.setAnimation(0, 7);
		rightSet = true;
	}
	
	private void setLeft(){
		gamePlayer.setAnimation(8, 15);
		leftSet = true;
	}

	private void setUp(){
		gamePlayer.stopAnimation();
	}

	
	private void playerController() {	

		float currentPosY = gamePlayer.getY();
		if(GlobalY >= (currentPosY + jumpHeight * 5) && finger == 1) {
			float currentPosX = gamePlayer.getX();
				if((currentPosX) > GlobalX) {			
						velocity = Vector2Pool.obtain((-1) * goStep, 0);
							currentX = velocity.x;
							if(!leftSet) setLeft();
								gamePlayer.GoLeft(velocity);
								rightSet = false;
								upSet = false;
				}
				else if((currentPosX + gamePlayer.getWidth()) < GlobalX){
					velocity = Vector2Pool.obtain(goStep, 0);
							currentX = velocity.x;
							if(!rightSet) setRight();
								gamePlayer.GoRight(velocity);
								leftSet = false;
								upSet = false;
				}
		} else if(GlobalY < (currentPosY + jumpHeight * 3)) {
			if(!upSet) setUp();
			velocity = Vector2Pool.obtain(currentX, jumpHeight);
				gamePlayer.Jump(velocity);
					GlobalY = GameActivity.CAMERA_HEIGHT;
					rightSet = false;
					leftSet = false;
		}

	}	
			
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		_curGameScene = this;
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
					currentX = 0;
						if (finger == 1) {						
							if(!isJumping(gamePlayer))
								this.playerController();						
						}
						else {
							gamePlayer.Stay();	
							rightSet = false;
							leftSet = false;
						}
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

	private void addObstacles(ITextureRegion pTextureRegion, String xmlFile, int amount) {
		float pX, pY;
		Obstacle _obstacle;
		while(amount > 0) {
			pX = 0;
			pY = GameActivity.CAMERA_HEIGHT / 2;
			_obstacle = new Obstacle(pX, pY, pTextureRegion, GameActivity.mVertexBufferObjectManager, this.gamePhysicsWorld, xmlFile);
			this.attachChild(_obstacle);
			platformLL.add(_obstacle);
			this.addGoods(pY, _obstacle.getWidth(), GfxAssets.mGoodsArray, fruitsQuantity);
			amount--;
		}

	}
	
	private void addEnemies(final float pX, final float pY, ITiledTextureRegion pTextureRegion, String xmlFile, float pos, int amount) {

		SpiderEnemy _spiderEnemy;
		while(amount > 0) {
			_spiderEnemy = new SpiderEnemy(pX, pY, pTextureRegion, GameActivity.mVertexBufferObjectManager, this.gamePhysicsWorld, xmlFile, pos);
			this.attachChild(_spiderEnemy);
			spiderLL.add(_spiderEnemy);
			amount--;
		}

	}    
	
	private void addGoods(float pY, final float obstWidth, ArrayList<TextureRegion> mGoodTextureRegion, int amount) {
		float pX;
		Random random = new Random();
		GoodFruit _goodFruit;
		TextureRegion curFruitTR;
		while(amount > 0) {
			curFruitTR = mGoodTextureRegion.get(random.nextInt(mGoodTextureRegion.size()));
			pX = (obstWidth - curFruitTR.getWidth()) * random.nextFloat();
			_goodFruit = new GoodFruit(pX, pY - curFruitTR.getHeight(), curFruitTR, GameActivity.mVertexBufferObjectManager, this.gamePhysicsWorld);
			this.attachChild(_goodFruit);
			goodsLL.add(_goodFruit);
			amount--;
		}

	}   
	
	
	private void showScore(){
		
		_score = new Text(0, 0, GfxAssets.mFont, String.valueOf(maxScore), GameActivity._main.getVertexBufferObjectManager());
		_score.setPosition(GameActivity.CAMERA_WIDTH - _score.getWidth() - 70, 20);

		_score.setScale(2);
		_score.setScaleCenterY(0);
		this.attachChild(_score);
	}

	/** to restart the game and clear the whole screen */
	public void restart() {

		GameActivity._main.runOnUpdateThread(new Runnable() {

			@Override
			// to safely detach and re-attach the sprites
			public void run() {
				Game_Scene.this.detachChildren();
				Game_Scene.this.attachChild(gamePlayer);
				Game_Scene.this.attachChild(Game_Scene._score);
			}
		});

		// resetting everything
	}
	
//	private void addFlare(final float pX, final float pY) {
//		final AnimatedSprite flare = new AnimatedSprite(pX, pY, GfxAssets.mFlare, GameActivity._main.getVertexBufferObjectManager());
//		
//		flare.animate(new long[]{200, 200, 200, 200}, 0, 3, true);
//
//		this.attachChild(flare);
//	}
		
	public void callbackCollisionGoods(int i){
		goodsLL.get(i).setCollision();
		this.detachChild(goodsLL.get(i));
		_score.setText(String.valueOf(--Game_Scene.curScore));
		goodsLL.remove(i);
		
	}
	
	public void callbackCollisionEnemy(){
		//restart 

		this.isGameFinished = true;
	}
	
}


