package falldowndizzy.pac;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

public class LevelSelect_Scene extends CameraScene {

	public static int levelID = 1;
	
	public LevelSelect_Scene(){
		super(GameActivity._Camera);
		
		this.setOnAreaTouchTraversalFrontToBack();
		
		setBackground(this.LoadAutoParalaxBg());
		initLevelTable(levelID);
		final Text ni = new Text(70, 500, GfxAssets.mFont, "NOT IMPLEMENTED\nYET", GameActivity.mVertexBufferObjectManager);
		this.attachChild(ni);
		this.setTouchAreaBindingOnActionDownEnabled(true);
	}
	
	public void Show(){
		setVisible(true);
		setIgnoreUpdate(false);
	}
	
	public void Hide(){
		setVisible(false);
		setIgnoreUpdate(true);
	}
	
	private void initLevelTable(int level){
		
		final Sprite _spriteLevel = new Sprite(50, 200,
					GfxAssets.mLevelBtnTextureRegion, GameActivity.mVertexBufferObjectManager) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				MainState.ShowGameScene();
				return true;
			}
		};
		
		final Text _menuTitle = new Text(0, 0, GfxAssets.mFont, String.valueOf(level), GameActivity._main.getVertexBufferObjectManager());
		_menuTitle.setPosition((_spriteLevel.getWidth() - _menuTitle.getWidthScaled()) / 2 - 5, (_spriteLevel.getHeight() - _menuTitle.getWidthScaled()) / 2 - 5);
		_menuTitle.setScale(0.9f);
		_spriteLevel.attachChild(_menuTitle);	
		
		attachChild(_spriteLevel);
		this.registerTouchArea(_spriteLevel);
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
