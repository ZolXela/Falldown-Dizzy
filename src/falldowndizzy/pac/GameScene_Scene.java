package falldowndizzy.pac;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

public class GameScene_Scene extends CameraScene {
	
	public GameScene_Scene(){
		super(GameLogicController.camera);
		setBackgroundEnabled(false);
		final Sprite _sprite = new Sprite(
				0, 0, GameLogicController.camera.getWidth(), GameLogicController.camera.getHeight(), loadBgSprite(), 
				GameLogicController.mVertexBufferObjectManager)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				MainScene.ShowMenuScene();
				return true;
			}
		};
		
		attachChild(_sprite);
		registerTouchArea(_sprite);
	}
	
	public void Show(int levelID){
		setVisible(true);
		setIgnoreUpdate(false);
	}
	
	public void Hide(){
		setVisible(false);
		setIgnoreUpdate(true);
	}
	
	public ITextureRegion loadBgSprite(){
		/**
         * Direction to the graphic. In our case graphic would be loaded from assets/gfx/ folder
         */
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	    BitmapTextureAtlas Texture1 =  new BitmapTextureAtlas(
	    		GameLogicController.curEngine.getTextureManager(), 1000, 1000);
	    /**
	     * Create the sprite - region in this atlas.
	     * The main game background - picture is needed
	     */
	    return BitmapTextureAtlasTextureRegionFactory.createFromAsset(
	    		Texture1, GameLogicController.gameLogicController.getAssets(), "game_bg.png", 0, 0);
	
	}
	
}
