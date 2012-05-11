package falldowndizzy.pac;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
<<<<<<< HEAD
=======
import org.andengine.opengl.texture.TextureOptions;
>>>>>>> 54d7ba15fbb419d583fed1c0d3ec1e4cf43b01a1
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

public class GameScene_Scene extends CameraScene {
	
	public GameScene_Scene(){
		super(GameLogicController.camera);
		setBackgroundEnabled(false);
		final Sprite _sprite = new Sprite(
<<<<<<< HEAD
				0, 0, GameLogicController.camera.getWidth(), GameLogicController.camera.getHeight(), loadBgSprite(), 
=======
				20, 10, 25, 70, loadBgSprite(), 
>>>>>>> 54d7ba15fbb419d583fed1c0d3ec1e4cf43b01a1
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
<<<<<<< HEAD
	    BitmapTextureAtlas Texture1 =  new BitmapTextureAtlas(
	    		GameLogicController.curEngine.getTextureManager(), 1000, 1000);
=======
	    BitmapTextureAtlas Texture1 = new BitmapTextureAtlas(
	    		GameLogicController.curEngine.getTextureManager(), 512, 512, TextureOptions.NEAREST_PREMULTIPLYALPHA);
>>>>>>> 54d7ba15fbb419d583fed1c0d3ec1e4cf43b01a1
	    /**
	     * Create the sprite - region in this atlas.
	     * The main game background - picture is needed
	     */
	    return BitmapTextureAtlasTextureRegionFactory.createFromAsset(
<<<<<<< HEAD
	    		Texture1, GameLogicController.gameLogicController.getAssets(), "game_bg.png", 0, 0);
=======
	    		Texture1, GameLogicController.gameLogicController, "game_bg.png", 0, 0);
>>>>>>> 54d7ba15fbb419d583fed1c0d3ec1e4cf43b01a1
	
	}
	
}
