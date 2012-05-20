package falldowndizzy.pac;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.debug.Debug;

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
	public static TiledTextureRegion mFlare;
	
	public static ITextureRegion mPlatform1;
	
	
	public static Music mMusic;	
	public static Sound mJump, mGameOver, mGetGoods, mNightmare;
	
	
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
	
	    mFlare = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "flare.png", 469, 349, 4, 1);
		
		mPlatform1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, GameActivity._main, "plat1.png", 321, 349);
		
		mAutoParallaxBackgroundTexture.load();
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
