package falldowndizzy.pac;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

public class Game_Scene extends CameraScene {
	
	long lDateTime = -1;
	
	public Game_Scene(){
		super(GameActivity._Camera);
		
		final AutoParallaxBackgroundXY autoParallaxBackgroundXY = new AutoParallaxBackgroundXY(0, 0, 0, 5);
		final VertexBufferObjectManager vertexBufferObjectManager = GameActivity._Engine.getVertexBufferObjectManager();
		autoParallaxBackgroundXY.attachParallaxEntityXY(new falldowndizzy.pac.AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerBack, vertexBufferObjectManager)));
		autoParallaxBackgroundXY.attachParallaxEntityXY(new falldowndizzy.pac.AutoParallaxBackgroundXY.ParallaxEntityXY(5.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerCloud, vertexBufferObjectManager)));	
		autoParallaxBackgroundXY.attachParallaxEntityXY(new falldowndizzy.pac.AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerTrees, vertexBufferObjectManager)));
		
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
	
	
}
