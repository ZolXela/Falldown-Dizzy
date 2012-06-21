package falldowndizzy.pac;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;


public class MainMenu_Scene extends CameraScene {

	Sprite _spriteStart;
	Sprite _spriteLevel;
	
	
	public MainMenu_Scene() {
		super(GameActivity._Camera);

		this.setOnAreaTouchTraversalFrontToBack();
		
		setBackground(LoadAutoParalaxBg());
		GfxAssets.mMusic.setVolume(GfxAssets.mMusic.getVolume() / 4);
		
        GfxAssets.mMusic.play();
		setPlayBtn();
		setLevelBtn();	
		
		this.setTouchAreaBindingOnActionDownEnabled(true);
	}
	
	private void setPlayBtn(){
		
		_spriteStart = new Sprite((GameActivity.CAMERA_WIDTH - GfxAssets.mMenuBtnTextureRegion.getWidth()) / 2, 200,
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
		_menuTitle.setScale(0.9f);
		_spriteStart.attachChild(_menuTitle);	
		
	}

	private void setLevelBtn(){
		
		_spriteLevel = new Sprite((GameActivity.CAMERA_WIDTH - GfxAssets.mMenuBtnTextureRegion.getWidth()) / 2, 400,
					GfxAssets.mMenuBtnTextureRegion, GameActivity.mVertexBufferObjectManager) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				MainState.ShowLevelScene();
				return true;
			}
		};
		
		final Text _levelTitle = new Text(0, 0, GfxAssets.mFont, "LEVEL", GameActivity._main.getVertexBufferObjectManager());
		_levelTitle.setPosition((_spriteLevel.getWidth() - _levelTitle.getWidth()) / 2 + 15, (_spriteLevel.getHeight() - _levelTitle.getWidth()) / 2 + 30);
		_levelTitle.setScale(0.9f);
		_spriteLevel.attachChild(_levelTitle);	
	}
	
	public void Show() {
		setVisible(true);
		attachChild(_spriteStart);
		attachChild(_spriteLevel);
		this.registerTouchArea(_spriteStart);
		this.registerTouchArea(_spriteLevel);
		setIgnoreUpdate(false);
	}

	public void Hide() {
		setVisible(false);
		setIgnoreUpdate(true);	
		this.unregisterTouchArea(_spriteStart);
		this.unregisterTouchArea(_spriteLevel);
		detachChild(_spriteStart);
		detachChild(_spriteLevel);
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