package falldowndizzy.pac;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

public class GfxAssets {
	
	public static BitmapTextureAtlas mAutoParallaxBackgroundTexture;
	
	public static ITextureRegion mParallaxLayerBack;
	public static ITextureRegion mParallaxLayerTrees;
	public static ITextureRegion mParallaxLayerCloud;	

	public static void LoadGFX() {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(GameActivity._Engine.getTextureManager(), 1024, 1024);
		mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "dizzy_parallax_background_layer_back.png", 0, 0);
		mParallaxLayerCloud = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "clouds_bg.png", 321, 0);
		mParallaxLayerTrees = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "dizzy_parallax_background_layer_trees.png", 0, 481);
		mAutoParallaxBackgroundTexture.load();
		
	}

}
