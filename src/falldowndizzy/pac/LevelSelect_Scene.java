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
	Camera levelCamera = GameLogicController.camera;
	
	public LevelSelect_Scene(){
		super(GameLogicController.camera);
		setBackgroundEnabled(false);
		final Sprite _sprite = new Sprite(
				0, 0, GameLogicController.camera.getWidth(), GameLogicController.camera.getHeight(), 
				loadLevelBgSprite(), 
				GameLogicController.mVertexBufferObjectManager)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				MainScene.ShowGameScene(levelID);
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
	    		GameLogicController.curEngine.getTextureManager(), 1000, 1000);
	    /**
	     * Create the sprite - region in this atlas.
	     * The level's background - picture is needed
	     */
//	    final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5); 
//	    for(int level = 1; level <= 2; level++){
//			autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(
//					0, new Sprite(0, 0, 
//							Texture1.getHeight(), 
//								Texture1.getWidth(),
//									loadLevelItem(level),
//										GameLogicController.mVertexBufferObjectManager))); 
//			this.setBackground(autoParallaxBackground); 
//	    }
	    return BitmapTextureAtlasTextureRegionFactory.createFromAsset(
	    		Texture1, GameLogicController.gameLogicController.getAssets(), "level_bg.png", 0, 0);
	
	}
	
//	@Override
//	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent){	
//		MainScene.ShowGameScene(levelID);
//		return super.onSceneTouchEvent(pSceneTouchEvent);
//	}
//	
//	public ITextureRegion loadLevelItem(final int level){
//
//		final Sprite sprite = new Sprite(
//				20, 10, 25, 70, BitmapTextureAtlasTextureRegionFactory.createFromAsset(
//			    		new BitmapTextureAtlas(
//			    	    		GameLogicController.curEngine.getTextureManager(), levelCamera.getSurfaceHeight()*3/10, levelCamera.getSurfaceWidth()/2, TextureOptions.NEAREST_PREMULTIPLYALPHA), GameLogicController.gameLogicController, "levelItem" + level + ".png", 0, 0), 
//				GameLogicController.mVertexBufferObjectManager)
//		{
//			@Override
//			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
//					float pTouchAreaLocalX, float pTouchAreaLocalY){
//				levelID = level;
//				return true;
//			}
//		};
//		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
//	    BitmapTextureAtlas Texture1 = new BitmapTextureAtlas(
//	    		GameLogicController.curEngine.getTextureManager(), levelCamera.getSurfaceHeight()*3/10, levelCamera.getSurfaceWidth()/2, TextureOptions.NEAREST_PREMULTIPLYALPHA);
//	    return BitmapTextureAtlasTextureRegionFactory.createFromAsset(
//	    		Texture1, GameLogicController.gameLogicController, "levelItem" + level + ".png", 0, 0);
//		
//	}
//	
}
