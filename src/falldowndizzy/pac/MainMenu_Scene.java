package falldowndizzy.pac;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

public class MainMenu_Scene extends CameraScene {

	public MainMenu_Scene() {
		super(GameActivity._Camera);

		this.setOnAreaTouchTraversalFrontToBack();
		
		setBackground(LoadAutoParalaxBg());
     //   GfxAssets.mMusic.play();
		final Sprite _spriteStart = new Sprite((GameActivity.CAMERA_WIDTH - GfxAssets.mMenuBtnTextureRegion.getWidth()) / 2, 200,
					GfxAssets.mMenuBtnTextureRegion, GameActivity.mVertexBufferObjectManager) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				MainState.ShowGameScene();
				return true;
			}
		};
		
		final Text _menuTitle = new Text(0, 0, GfxAssets.mFont, "START", GameActivity._main.getVertexBufferObjectManager());
		_menuTitle.setPosition((_spriteStart.getWidth() - _menuTitle.getWidth()) / 2 + 15, (_spriteStart.getHeight() - _menuTitle.getWidth()) / 2 + 30);
		_menuTitle.setColor(Color.BLACK);
		_menuTitle.setScale(0.9f);
		_spriteStart.attachChild(_menuTitle);	
		
		attachChild(_spriteStart);
		this.registerTouchArea(_spriteStart);
		
//		final Sprite _spriteLevel = new Sprite((GameActivity.CAMERA_WIDTH - GfxAssets.mLevelBtnTextureRegion.getWidth()) / 2, 400,
//					GfxAssets.mLevelBtnTextureRegion, GameActivity.mVertexBufferObjectManager) {
//			@Override
//			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
//					float pTouchAreaLocalX, float pTouchAreaLocalY) {
//				MainState.ShowLevelScene();
//				return true;
//			}
//		};
//		
//		final Text _levelTitle = new Text(0, 0, GfxAssets.mFont, "LEVEL", GameActivity._main.getVertexBufferObjectManager());
//		_levelTitle.setPosition((_spriteLevel.getWidth() - _levelTitle.getWidth()) / 2 + 15, (_spriteLevel.getHeight() - _levelTitle.getWidth()) / 2 + 30);
//		_levelTitle.setColor(Color.BLACK);
//		_levelTitle.setScale(0.9f);
//		_spriteLevel.attachChild(_levelTitle);	
//		
//		attachChild(_spriteLevel);
//		this.registerTouchArea(_spriteLevel);
		
		this.setTouchAreaBindingOnActionDownEnabled(true);
	}

	public void Show() {
		setVisible(true);
		setIgnoreUpdate(false);
	}

	public void Hide() {
		setVisible(false);
		setIgnoreUpdate(true);
	}

	public AutoParallaxBackgroundXY LoadAutoParalaxBg(){
		
		final AutoParallaxBackgroundXY autoParallaxBackgroundXY = new AutoParallaxBackgroundXY(0, 0, 0, 5);
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerMountains, GameActivity.mVertexBufferObjectManager)));
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(-5.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerCloud, GameActivity.mVertexBufferObjectManager)));	
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerTreesBg, GameActivity.mVertexBufferObjectManager)));
		autoParallaxBackgroundXY.attachParallaxEntityXY(new AutoParallaxBackgroundXY.ParallaxEntityXY(0.0f, 0.0f, new Sprite(0, 0, GfxAssets.mParallaxLayerTrees, GameActivity.mVertexBufferObjectManager)));
		return autoParallaxBackgroundXY;	
	}

}