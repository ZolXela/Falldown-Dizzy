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
        //GfxAssets.mMusic.play();
		final Sprite _sprite = new Sprite((GameActivity.CAMERA_WIDTH - GfxAssets.mMenuBtnTextureRegion.getWidth()) / 2, 
				(GameActivity.CAMERA_HEIGHT- GfxAssets.mMenuBtnTextureRegion.getHeight()) / 2,
					GfxAssets.mMenuBtnTextureRegion, GameActivity.mVertexBufferObjectManager) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				MainState.ShowGameScene();
				return true;
			}
		};
		
		final Text _menuTitle = new Text(0, 0, GfxAssets.mFont, "START", GameActivity._main.getVertexBufferObjectManager());
		_menuTitle.setPosition((_sprite.getWidth() - _menuTitle.getWidth()) / 2 + 15, (_sprite.getHeight() - _menuTitle.getWidth()) / 2 + 30);
		_menuTitle.setColor(Color.BLACK);
		_menuTitle.setScale(0.9f);
		_sprite.attachChild(_menuTitle);	
		
		attachChild(_sprite);
		this.registerTouchArea(_sprite);
		
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