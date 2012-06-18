package falldowndizzy.pac;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

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
	private float jumpHeight = -20;
	private float goStep = 7;

	/* Fields for Dizzy's score */
	public static Text _score;
	private final static int maxLifes = 3;
	private int lifeS = maxLifes;
	private final static int maxScore = 30;
	public static int curScore = maxScore;
	Sprite _spriteLifes;
	
	public static LinkedList<SpiderEnemy> spiderLL;
	private LinkedList<Obstacle> platformLL;
	public static LinkedList<GoodFruit> goodsLL;
	
	
	public Game_Scene(){
	
		super(GameActivity._Camera);
		_curGameScene = this;
		this.setGamePhysicsWorld();
				
		setBackground(this.LoadAutoParallaxBg());	
		
		platformLL = new LinkedList<Obstacle>();
		spiderLL = new LinkedList<SpiderEnemy>();
		goodsLL = new LinkedList<GoodFruit>();
		
		this.initBorders();
		this.CreateDizzy(30, 50);
		attachChild(gamePlayer);
		this.initObstacles();
		
		gamePlayer.Stay();
		this.setTouchAreaBindingOnActionDownEnabled(true);

		gameLoaded = true;
		this.registerUpdateHandler(gamePhysicsWorld);

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

		gamePhysicsWorld = new FixedStepPhysicsWorld(120, new Vector2(0, SensorManager.GRAVITY_EARTH * 5), true, 8, 1);	
		
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
			
		bottomOuter.setIgnoreUpdate(true);
		topOuter.setIgnoreUpdate(true);
		leftOuter.setIgnoreUpdate(true);
		rightOuter.setIgnoreUpdate(true);
		
		bottomOuter.setColor(Color.BLACK);
		topOuter.setColor(Color.BLACK);
		leftOuter.setColor(Color.BLACK);
		rightOuter.setColor(Color.BLACK);
		
		this.attachChild(bottomOuter);
		this.attachChild(topOuter);
		this.attachChild(leftOuter);
		this.attachChild(rightOuter);
		
		this.showScore();
		this.showLifes();
	}
	
	private void initObstacles(){
		
		this.addGoodFixedPos(300, 40, GfxAssets.mBananaTextureRegion);
		this.addObstacles(0, GameActivity.CAMERA_HEIGHT / 2, GfxAssets.mPlatformTextureRegion1, "plat1.xml", 1);	
		this.addObstacles(0, 753 ,GfxAssets.mPlatformLongTextureRegion, "bridge_long.xml", 5);
		this.addObstacles(300, 650, GfxAssets.mPlatform2TextureRegion, "bridge_2.xml", 2);
		this.addObstacles(0, 550, GfxAssets.mPlatform2TextureRegion, "bridge_2.xml", 4);
		this.addObstacles(300, 450, GfxAssets.mPlatform2TextureRegion, "bridge_2.xml", 3);
		this.addEnemies(400, 450 + GfxAssets.mPlatform2TextureRegion.getHeight(), GfxAssets.mSpiderTextureRegion);
		
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
		if(GlobalY >= (currentPosY + jumpHeight * 5)) {
			if (finger == 1) {
				float currentPosX = gamePlayer.getX();
					if((currentPosX) > GlobalX) {			
							velocity = Vector2Pool.obtain((-1) * goStep, 0);
								currentX = velocity.x;
								if(!leftSet) setLeft();
									gamePlayer.GoLeft(velocity);
									rightSet = upSet = false;
					}
					else if((currentPosX + gamePlayer.getWidth()) < GlobalX){
						velocity = Vector2Pool.obtain(goStep, 0);
								currentX = velocity.x;
								if(!rightSet) setRight();
									gamePlayer.GoRight(velocity);
									leftSet = upSet = false;
					}
			} else {
				gamePlayer.Stay();
				rightSet = leftSet = upSet = false;
			}
			
		} else if(GlobalY < (currentPosY + jumpHeight * 3)) {
			if(!upSet) setUp();
			velocity = Vector2Pool.obtain(currentX, jumpHeight);
				gamePlayer.Jump(velocity);
					GlobalY = GameActivity.CAMERA_HEIGHT;
					rightSet = leftSet = false;
		}

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
					currentX = 0;
						if (finger == 1) {						
							if(!isJumping(gamePlayer))
								this.playerController();						
						}
						else {
							gamePlayer.Stay();	
							rightSet = leftSet = upSet = false;
						}
					break;
				default:
					if(!isJumping(gamePlayer) && finger > 0)
							this.playerController();
					break;
			}
			return true;
			}
		else {
			gamePlayer.Stay();
			rightSet = leftSet = upSet = false;
		}
		return false;

	}

	private void addObstacles(float pX, float pY, ITextureRegion pTextureRegion, String xmlFile, int fruitsQuantity) {
	
		Obstacle _obstacle = new Obstacle(pX, pY, pTextureRegion, GameActivity.mVertexBufferObjectManager, this.gamePhysicsWorld, xmlFile);
		platformLL.add(_obstacle);
		_obstacle.setIgnoreUpdate(true);
		float width = (_obstacle.getX() + _obstacle.getWidth() < GameActivity.CAMERA_WIDTH) ? _obstacle.getWidth() : GameActivity.CAMERA_WIDTH - _obstacle.getX();
		this.addGoods(pY, _obstacle.getX(), width, GfxAssets.mGoodsArray, fruitsQuantity);
		this.attachChild(_obstacle);
	
	}
	
	private void addEnemies(final float pX, final float pY, ITiledTextureRegion pTextureRegion) {

		SpiderEnemy _spiderEnemy = new SpiderEnemy(pX, pY, pTextureRegion, GameActivity.mVertexBufferObjectManager, this.gamePhysicsWorld);
		this.attachChild(_spiderEnemy);
		this.attachChild(_spiderEnemy.rope);
		spiderLL.add(_spiderEnemy);

	}    
	
	private void addGoods(float pY, final float obstX, final float obstWidth, ArrayList<TextureRegion> mGoodTextureRegion, int amount) {
	
		float pX;
		Random random = new Random();
		TextureRegion curFruitTR;
		while(amount > 0) {
			curFruitTR = mGoodTextureRegion.get(random.nextInt(mGoodTextureRegion.size()));
			pX = obstX + (obstWidth - curFruitTR.getWidth()) * random.nextFloat();
			this.addGoodFixedPos(pX, pY, curFruitTR);
			amount--;
		}
	
	}   
	
	private void addGoodFixedPos(float pX, float pY, TextureRegion curFruitTR) {
		
		GoodFruit _goodFruit = new GoodFruit(pX, pY - curFruitTR.getHeight(), curFruitTR, GameActivity.mVertexBufferObjectManager, this.gamePhysicsWorld);
		this.attachChild(_goodFruit);
		goodsLL.add(_goodFruit);
	
	}
	
	
	private void showScore(){		
		
		final Sprite _spriteScore = new Sprite(50, 200,
				GfxAssets.mLevelBtnTextureRegion, GameActivity.mVertexBufferObjectManager);
		_spriteScore.setPosition(GameActivity.CAMERA_WIDTH - _spriteScore.getWidth() - 70, 20);
		this.attachChild(_spriteScore);
		_score = new Text(0, 0, GfxAssets.mFont, String.valueOf(maxScore), GameActivity._main.getVertexBufferObjectManager());
		_score.setPosition((_spriteScore.getWidth() - _score.getWidth()) / 2 - 1, (_spriteScore.getHeight() - _score.getHeight()) / 2);
		_spriteScore.attachChild(_score);		 
		
	}

	private void showLifes(){		
		
		_spriteLifes = new Sprite(50, 20,
				GfxAssets.mLifesTextureRegion, GameActivity.mVertexBufferObjectManager);
		this.attachChild(_spriteLifes);
		final float indent = _spriteLifes.getWidth() / 19;
		for(int i = 0; i < lifeS; i++){
			final Sprite dizzy = new Sprite(GfxAssets.mDizzyLifesTextureRegion.getWidth() * i + indent * (i + 1), GfxAssets.mLifesTextureRegion.getHeight() / 5,
					GfxAssets.mDizzyLifesTextureRegion, GameActivity.mVertexBufferObjectManager);
			_spriteLifes.attachChild(dizzy);
		}		
		
	}
	
	/** to restart the game and clear the whole screen */
	public void restart() {

		GameActivity._main.runOnUpdateThread(new Runnable() {

			@Override
			// to safely detach and re-attach the sprites
			public void run() {
				MainState._Game_Scene.detachChild(gamePlayer);
				
				Iterator<GoodFruit> itrG = goodsLL.iterator();
				while(itrG.hasNext()){
					itrG.next().Destructor();
					itrG.remove();
				}	
				
				Iterator<Obstacle> itrP = platformLL.iterator();
				while(itrP.hasNext()){
					itrP.next().detachSelf();
					itrP.remove();
				}
				
				Iterator<SpiderEnemy> itrE = spiderLL.iterator();
				while(itrE.hasNext()){
					itrE.next().Destructor();
					itrE.remove();
				}
				
				_spriteLifes.detachChildren();
				_spriteLifes.detachSelf();
				_spriteLifes.dispose();
				System.gc();			
		
				System.out.println(">>> ls " + lifeS);
				gamePlayer.restart();
				Game_Scene.this.attachChild(gamePlayer);
				_score.setText(String.valueOf(maxScore));
				curScore = maxScore;
				Game_Scene.this.showLifes();
				Game_Scene.this.initObstacles();
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
		
	public void callbackCollisionGoods(final int i){
		
		GameActivity._main.runOnUpdateThread(new Runnable() {
		    @Override
		    public void run() {
				goodsLL.get(i).setCollision();
				MainState._Game_Scene.detachChild(goodsLL.get(i));
				_score.setText(String.valueOf(--Game_Scene.curScore));
				if(Game_Scene.curScore == 0){
					finishGame();
				}
				goodsLL.remove(i);
		    }
		});
	//	GfxAssets.mGetGoods.play();
	}
	
	private void finishGame(){
		
		MainState._Game_Scene.detachChildren();
		
		lifeS = 3;
		
		platformLL.removeAll(platformLL);
		spiderLL.removeAll(spiderLL);
		goodsLL.removeAll(goodsLL);
		
		final Sprite gameOver = new Sprite((GameActivity.CAMERA_WIDTH - GfxAssets.mMenuBtnTextureRegion.getWidth()) / 2, 
				(GameActivity.CAMERA_HEIGHT - GfxAssets.mMenuBtnTextureRegion.getHeight()) / 2 , GfxAssets.mMenuBtnTextureRegion, GameActivity.mVertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				restart();
				MainState.ShowMainScene();
				return true;
			}
		};
		
		this.attachChild(gameOver);
		this.registerTouchArea(gameOver);
		
	}
	
	public void callbackCollisionEnemy(){

		if(this.lifeS == 0){ 
			this.finishGame();
		} else {
			this.restart();
			this.lifeS--;
		}
	}
	
}


