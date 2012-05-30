package falldowndizzy.pac;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import android.graphics.Typeface;

public class GfxAssets {
	
	public static BitmapTextureAtlas mAutoParallaxBackgroundTexture;
	public static BitmapTextureAtlas mAutoParallaxBackgroundTexture1;
	public static BitmapTextureAtlas mAutoParallaxBackgroundTexture2;
	public static BitmapTextureAtlas mFontTexture;
	
	public static ITextureRegion mParallaxLayerMountains;
	public static ITextureRegion mParallaxLayerTrees;
	public static ITextureRegion mParallaxLayerTreesBg;
	public static ITextureRegion mParallaxLayerCloud;	
	public static ITextureRegion mPlayGameTextureRegion;
	public static TiledTextureRegion mPlayerTextureRegion;
	public static TiledTextureRegion mFlareTextureRegion;
	public static TiledTextureRegion mSpiderTextureRegion;
	
	public static ITextureRegion mPlatformTextureRegion1;
	public static Font mFont;
	
	public static Music mMusic;	
	public static Sound mJump, mGameOver, mGetGoods, mNightmare;
	
	
//	public static void LoadGFX() {
//		
//		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
//		
//		mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(GameActivity._Engine.getTextureManager(), 1024, 1024);
//		mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "dizzy_parallax_background_layer_back.png", 0, 0);
//		mParallaxLayerCloud = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "clouds_bg.png", 320, 0);
//		mParallaxLayerTrees = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "dizzy_parallax_background_layer_trees.png", 0, 480);
//		
//		mPlayGame = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "play_game_bt.png", 520, 221);
//		
//		mPlayer = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "player.png", 321, 221, 3, 4);
//	
//	    mFlare = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "flare.png", 469, 349, 4, 1);
//		
//		mPlatform1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "plat1.png", 321, 349);
//		
//		mAutoParallaxBackgroundTexture.load();
//	}
	
	public static void LoadGFX_800_480() {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/800_480/");
		
		mAutoParallaxBackgroundTexture1 = new BitmapTextureAtlas(GameActivity._Engine.getTextureManager(), 1024, 1024);
		mParallaxLayerCloud = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture1, GameActivity._main, "dizzy_parallax_background_clouds.png", 0, 0);
		mParallaxLayerMountains = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture1, GameActivity._main, "dizzy_parallax_background_layer_back.png", 0, 151);
		mPlayGameTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture1, GameActivity._main, "play_game_bt.png", 481, 151);
		mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAutoParallaxBackgroundTexture1, GameActivity._main, "player_d.png", 0, 952, 8, 1);
//		mSpiderTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAutoParallaxBackgroundTexture1, GameActivity._main, "target.png", 0, 0, 3, 1);
		mAutoParallaxBackgroundTexture1.load();
		
		mAutoParallaxBackgroundTexture2 = new BitmapTextureAtlas(GameActivity._Engine.getTextureManager(), 1024, 1024);
		mParallaxLayerTreesBg = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture2, GameActivity._main, "dizzy_parallax_background_layer_back_trees.png", 0, 0);
		mParallaxLayerTrees = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture2, GameActivity._main, "dizzy_parallax_background_layer_trees.png", 481, 0);
		mPlatformTextureRegion1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture2, GameActivity._main, "plat1.png", 0, 801);
		mAutoParallaxBackgroundTexture2.load();
		
		mFontTexture = new BitmapTextureAtlas(GameActivity._Engine.getTextureManager(), 256, 256);
		mFont = new Font(GameActivity._main.getFontManager(), mFontTexture, Typeface.create(Typeface.DEFAULT,
				Typeface.BOLD), 100, true, Color.PINK);
		mFontTexture.load();
	}	
	

	public static void LoadMFX(){
		
		MusicFactory.setAssetBasePath("mfx/");
		  try {
		       mMusic = MusicFactory.createMusicFromAsset(GameActivity._Engine.getMusicManager(), GameActivity._main, "music.ogg");
		       mMusic.setLooping(true);
		     } catch (final IOException e) {
		        Debug.e(e);
		}
	
		  
		  SoundFactory.setAssetBasePath("mfx/");
		  try {
   			   mJump = SoundFactory.createSoundFromAsset(GameActivity._Engine.getSoundManager(), GameActivity._main, "jump.ogg");
			   mGameOver = SoundFactory.createSoundFromAsset(GameActivity._Engine.getSoundManager(), GameActivity._main, "gameOver.ogg");
			   mGetGoods = SoundFactory.createSoundFromAsset(GameActivity._Engine.getSoundManager(), GameActivity._main, "getGoods.ogg");
			   mNightmare = SoundFactory.createSoundFromAsset(GameActivity._Engine.getSoundManager(), GameActivity._main, "nightmare.ogg");
		     } catch (final IOException e) {
		          Debug.e(e);
		  }
		  
	}
	
}
