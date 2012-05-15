package falldowndizzy.pac;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;

public class Game_Scene extends CameraScene {
	
	long lDateTime = -1;
	AutoParallaxBackgroundXY autoParallaxBackgroundXY;
	public static PhysicsWorld mPhysicsWorld;
	public Dizzy myPlayer;
	
	public Game_Scene(){
		super(GameActivity._Camera);
		this.setPhysicsWorld();
		
		autoParallaxBackgroundXY = new AutoParallaxBackgroundXY(0, 0, 0, 5);
		final VertexBufferObjectManager vertexBufferObjectManager = GameActivity._Engine.getVertexBufferObjectManager();
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerBack, vertexBufferObjectManager)));
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(5.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerCloud, vertexBufferObjectManager)));	
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerTrees, vertexBufferObjectManager)));
				
		setBackground(autoParallaxBackgroundXY);
		
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
		this.CreateDizzy(50, 30);
		attachChild(myPlayer);
		myPlayer.FallDown(mPhysicsWorld);
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
	
	private void setPhysicsWorld(){
		mPhysicsWorld = new PhysicsWorld(
				new Vector2(0, SensorManager.GRAVITY_EARTH/20), false);
		this.registerUpdateHandler(mPhysicsWorld);
	}
	
	public void CreateDizzy(float pX, float pY){
		myPlayer = new Dizzy(pX, pY, 
				GfxAssets.mPlayer, GameActivity.mVertexBufferObjectManager);
	}
}
