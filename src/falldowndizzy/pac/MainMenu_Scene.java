package falldowndizzy.pac;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

public class MainMenu_Scene extends CameraScene {

	Camera menuCamera = GameLogicController.camera;
	
	public MainMenu_Scene(){		
		super(GameLogicController.camera);
		setBackgroundEnabled(false);
		final Sprite _sprite = new Sprite(
				0, 0, GameLogicController.camera.getWidth(),GameLogicController.camera.getHeight() , 
							loadMenuBgSprite(), 
								GameLogicController.mVertexBufferObjectManager)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY){
				MainScene.ShowGameScene(LevelSelect_Scene.levelID);
				return true;
			}
		};

		attachChild(_sprite);
		_sprite.setColor(Color.CYAN);
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
	
	public ITextureRegion loadMenuBgSprite(){
		/**
         * Direction to the graphic. In our case graphic would be loaded from assets/gfx/ folder
         */
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	    BitmapTextureAtlas Texture1 = new BitmapTextureAtlas(
	    		GameLogicController.curEngine.getTextureManager(), 1000, 1000);
	    /**
	     * Create the sprite - region in this atlas.
	     * The game menu background - picture is needed
	     */
	    
//	    final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5); 
//	    		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(
//	    				0, new Sprite(0, 0, 
//	    						Texture1.getHeight(), 
//	    							Texture1.getWidth(),
//	    								loadGameItem(),
//	    									GameLogicController.mVertexBufferObjectManager))); 
//	    this.setBackground(autoParallaxBackground); 
//	    
//		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(
//				0, new Sprite(0, 0, 
//						menuCamera.getHeight(), 
//							menuCamera.getWidth(),
//								loadLevelItem(),
//									GameLogicController.mVertexBufferObjectManager))); 
//		this.setBackground(autoParallaxBackground); 
	    return BitmapTextureAtlasTextureRegionFactory.createFromAsset(
	    		Texture1, GameLogicController.gameLogicController.getAssets(), "menuBG.png", 0, 0);
	}
	
	public ITextureRegion loadGameItem(){
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	    BitmapTextureAtlas Texture1 = new BitmapTextureAtlas(
	    		GameLogicController.curEngine.getTextureManager(), menuCamera.getSurfaceHeight()*3/10, menuCamera.getSurfaceWidth()/2, TextureOptions.NEAREST_PREMULTIPLYALPHA);
	    return BitmapTextureAtlasTextureRegionFactory.createFromAsset(
	    		Texture1, GameLogicController.gameLogicController, "play.png", 0, 0);
	    
	}
	
	public ITextureRegion loadLevelItem(){
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	    BitmapTextureAtlas Texture1 = new BitmapTextureAtlas(
	    		GameLogicController.curEngine.getTextureManager(), menuCamera.getSurfaceHeight()*7/10, menuCamera.getSurfaceWidth()/2, TextureOptions.NEAREST_PREMULTIPLYALPHA);
	    return BitmapTextureAtlasTextureRegionFactory.createFromAsset(
	    		Texture1, GameLogicController.gameLogicController, "level.png", 0, 0);
	    
	}
	
	
	
}
