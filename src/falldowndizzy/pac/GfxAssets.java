package falldowndizzy.pac;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class GfxAssets {
	
	public static BitmapTextureAtlas mAutoParallaxBackgroundTexture;
	public static BuildableBitmapTextureAtlas mAnimatedSpriteTexture;
	
	public static ITextureRegion mParallaxLayerBack;
	public static ITextureRegion mParallaxLayerTrees;
	public static ITextureRegion mParallaxLayerCloud;	
	public static ITextureRegion mOnScreenControlBaseTextureRegion;
	public static ITextureRegion mOnScreenControlKnobTextureRegion;
	public static ITextureRegion mPlayGame;
	public static TiledTextureRegion mPlayer;	

	public static void LoadGFX() {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(GameActivity._Engine.getTextureManager(), 1024, 1024);
		mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "dizzy_parallax_background_layer_back.png", 0, 0);
		mParallaxLayerCloud = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "clouds_bg.png", 320, 0);
		mParallaxLayerTrees = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "dizzy_parallax_background_layer_trees.png", 0, 480);

		mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "onscreen_control_base.png", 392, 221);
		mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "onscreen_control_knob.png", 960, 0);
		
		mPlayGame = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "play_game_bt.png", 520, 221);
		
		mPlayer = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "player.png", 321, 221, 3, 4);
		mAutoParallaxBackgroundTexture.load();
	}

}
