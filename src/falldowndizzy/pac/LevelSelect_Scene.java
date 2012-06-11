package falldowndizzy.pac;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

public class LevelSelect_Scene extends CameraScene {

	public static int levelID = 1;
	Camera levelCamera = GameActivity._Camera;
	
	public LevelSelect_Scene(){
		super(GameActivity._Camera);
		setBackgroundEnabled(false);
		final Sprite _sprite = new Sprite(
				0, 0, GameActivity._Camera.getWidth(), GameActivity._Camera.getHeight(), 
				loadLevelBgSprite(), 
				GameActivity.mVertexBufferObjectManager)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				MainState.ShowGameScene();
				return true;
			}
		};
		
		attachChild(_sprite);
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
	
	public ITextureRegion loadLevelBgSprite(){
		/**
         * Direction to the graphic. In our case graphic would be loaded from assets/gfx/ folder
         */
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	    BitmapTextureAtlas Texture1 =  new BitmapTextureAtlas(
	    		GameActivity._Engine.getTextureManager(), 1024, 1024);

	    /**
	     * Create the sprite - region in this atlas.
	     * The level's background - picture is needed
	     */

	    return BitmapTextureAtlasTextureRegionFactory.createFromAsset(	
	    		Texture1, GameActivity._main.getAssets(), "dizzy_parallax_background_layer_back.png", 0, 0);
	
	}

}
