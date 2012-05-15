package falldowndizzy.pac;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Game_Scene extends CameraScene {
	
	AutoParallaxBackgroundXY autoParallaxBackgroundXY;
	public static PhysicsWorld mPhysicsWorld;
	
	public Game_Scene(){
		super(GameActivity._Camera);
		
		this.setPhysicsWorld();
					
		setBackground(parallaxBG());
		
		final Rectangle _sprite = new Rectangle(20, 350, 280, 50,
				GameActivity.mVertexBufferObjectManager)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				MainState.ShowMainScene();
				return true;
			}
		};
		
		attachChild(_sprite);
		attachChild(this.CreateDizzy(0, 0));
		_sprite.setColor(Color.GREEN);
		registerTouchArea(_sprite);
	}

	public void Show(){
		setVisible(true);
		setIgnoreUpdate(false);
	}
	
	public void Hide(){
		setVisible(false);
		setIgnoreUpdate(true);
	}
	
	private AutoParallaxBackgroundXY parallaxBG(){
		
		autoParallaxBackgroundXY = new AutoParallaxBackgroundXY(0, 0, 0, 5);
		final VertexBufferObjectManager vertexBufferObjectManager = GameActivity._Engine.getVertexBufferObjectManager();
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerBack, vertexBufferObjectManager)));
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(5.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerCloud, vertexBufferObjectManager)));	
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerTrees, vertexBufferObjectManager)));
		
		return autoParallaxBackgroundXY;
	}
	
	private void setPhysicsWorld(){
		mPhysicsWorld = new PhysicsWorld(
				new Vector2(0, SensorManager.GRAVITY_EARTH/10), false);
		this.registerUpdateHandler(mPhysicsWorld);
	}
	
	public Dizzy CreateDizzy(float pX, float pY){
		
		final FixtureDef MyFixtureDef = PhysicsFactory.createFixtureDef(0.5f, 0.1f, 0.2f, false);
		final Dizzy Player = new Dizzy(pX, pY, GfxAssets.mPlayer, GameActivity.mVertexBufferObjectManager);
		Body DizzyBody = PhysicsFactory.createCircleBody(mPhysicsWorld, Player, BodyType.DynamicBody, MyFixtureDef);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(Player, DizzyBody));
//		Player.setScaleCenterY(GfxAssets.mPlayer.getHeight());
//        Player.setScale(2);
//        Player.animate(new long[]{200, 200, 200}, 3, 5, true);     
		GameActivity._main.enableAccelerationSensor(GameActivity._main);		
		this.registerUpdateHandler(mPhysicsWorld);
		return Player;
		
	}
}
