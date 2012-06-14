package falldowndizzy.pac;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.debug.Debug;

import android.graphics.Color;
import android.graphics.Typeface;

public class GfxAssets {
	
	public static BitmapTextureAtlas mAutoParallaxBackgroundTexture;
	public static BitmapTextureAtlas mAutoParallaxBackgroundTexture1;
	public static BitmapTextureAtlas mAutoParallaxBackgroundTexture2;
	
	public static ITextureRegion mParallaxLayerMountains;
	public static ITextureRegion mParallaxLayerTrees;
	public static ITextureRegion mParallaxLayerTreesBg;
	public static ITextureRegion mParallaxLayerCloud;	
	public static ITextureRegion mMenuBtnTextureRegion;
	public static ITextureRegion mLevelBtnTextureRegion;
	
	public static TiledTextureRegion mPlayerTextureRegion;
	
	public static TiledTextureRegion mFlareTextureRegion;
	public static TiledTextureRegion mSpiderTextureRegion;
	
	public static ITextureRegion mRopeTextureRegion;
	
	public static TextureRegion mStrawberryTextureRegion;
	public static TextureRegion mAppleTextureRegion;
	public static TextureRegion mBananaTextureRegion;
	public static TextureRegion mCherryTextureRegion;
	public static ArrayList<TextureRegion> mGoodsArray;
	
	public static ITextureRegion mPlatformTextureRegion1;
	public static ITextureRegion mPlatformLongTextureRegion;
	public static ITextureRegion mPlatform2TextureRegion;
	public static Font mFont;
	
	public static Music mMusic;	
	public static Sound mJump, mGameOver, mGetGoods, mNightmare;
	
	public static void LoadGFX_800_480() {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/800_480/");
		
		mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(GameActivity._Engine.getTextureManager(), 1024, 512);
		mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "player_d_all.png", 0, 0, 8, 5);
		mAutoParallaxBackgroundTexture.load();
		
		mAutoParallaxBackgroundTexture1 = new BitmapTextureAtlas(GameActivity._Engine.getTextureManager(), 1024, 1024);
		mParallaxLayerCloud = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture1, GameActivity._main, "dizzy_parallax_background_clouds.png", 0, 0);
		mParallaxLayerMountains = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture1, GameActivity._main, "dizzy_parallax_background_layer_back.png", 0, 150);
		mMenuBtnTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture1, GameActivity._main, "button_menu.png", 480, 150);
		mLevelBtnTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture1, GameActivity._main, "square_button.png", 68, 950);
		mStrawberryTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(GfxAssets.mAutoParallaxBackgroundTexture1, GameActivity._main, "strawberry.png", 0, 950);
		mAppleTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(GfxAssets.mAutoParallaxBackgroundTexture1, GameActivity._main, "apple.png", 0, 990);
		mBananaTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(GfxAssets.mAutoParallaxBackgroundTexture1, GameActivity._main, "banana.png", 30, 951);
		mCherryTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(GfxAssets.mAutoParallaxBackgroundTexture1, GameActivity._main, "cherry.png", 30, 985);
		mAutoParallaxBackgroundTexture1.load();
		
		mGoodsArray = new ArrayList<TextureRegion>();
		mGoodsArray.add(mStrawberryTextureRegion);
		mGoodsArray.add(mAppleTextureRegion);
		mGoodsArray.add(mBananaTextureRegion);	
		mGoodsArray.add(mCherryTextureRegion);
	
		
		mAutoParallaxBackgroundTexture2 = new BitmapTextureAtlas(GameActivity._Engine.getTextureManager(), 1024, 1024);
		mParallaxLayerTreesBg = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture2, GameActivity._main, "dizzy_parallax_background_layer_back_trees.png", 0, 0);
		mParallaxLayerTrees = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture2, GameActivity._main, "dizzy_parallax_background_layer_trees.png", 480, 0);
		mPlatformTextureRegion1 = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture2, GameActivity._main, "plat1.png", 0, 800);
		mPlatformLongTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture2, GameActivity._main, "bridge_long.png", 0, 847);	
		mPlatform2TextureRegion =  BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture2, GameActivity._main, "bridge_2.png", 147, 800);
		mSpiderTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mAutoParallaxBackgroundTexture2, GameActivity._main, "spider_wr.png", 381, 800, 12, 1);
		mRopeTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAutoParallaxBackgroundTexture2, GameActivity._main, "rope.png", 0, 894);
		mAutoParallaxBackgroundTexture2.load();
		
		mFont = FontFactory
				.create(GameActivity._main.getFontManager(), GameActivity._Engine.getTextureManager(), 256, 256, Typeface
						.create(Typeface.DEFAULT, Typeface.BOLD), 40, true, Color.BLACK);
		mFont.load();
		
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
   			   mJump = SoundFactory.createSoundFromAsset(GameActivity._Engine.getSoundManager(), GameActivity._main, "jump.wav");
			   mGetGoods = SoundFactory.createSoundFromAsset(GameActivity._Engine.getSoundManager(), GameActivity._main, "push.wav");
			   mGameOver = SoundFactory.createSoundFromAsset(GameActivity._Engine.getSoundManager(), GameActivity._main, "gameOver.ogg");
			   mNightmare = SoundFactory.createSoundFromAsset(GameActivity._Engine.getSoundManager(), GameActivity._main, "nightmare.ogg");
		     } catch (final IOException e) {
		          Debug.e(e);
		  }
		  
	}
	
}
