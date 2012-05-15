package falldowndizzy.pac;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.util.GLState;

public class AutoParallaxBackgroundXY extends ParallaxBackground {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private float mParallaxChangePerSecond;

	// ===========================================================
	// Constructors
	// ===========================================================

	public AutoParallaxBackgroundXY(final float pRed, final float pGreen, final float pBlue, final float pParallaxChangePerSecond) {
		super(pRed, pGreen, pBlue);
		this.mParallaxChangePerSecond = pParallaxChangePerSecond;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void setParallaxChangePerSecond(final float pParallaxChangePerSecond) {
		this.mParallaxChangePerSecond = pParallaxChangePerSecond;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onUpdate(final float pSecondsElapsed) {
		super.onUpdate(pSecondsElapsed);

		this.mParallaxValueX += this.mParallaxChangePerSecond * pSecondsElapsed;
		this.mParallaxValueY += this.mParallaxChangePerSecond * pSecondsElapsed;
	}

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final ArrayList<ParallaxEntityXY> mParallaxEntities = new ArrayList<ParallaxEntityXY>();
	private int mParallaxEntityCount;

	protected float mParallaxValueX;
	protected float mParallaxValueY;

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void setParallaxValue(final float pParallaxValueX,final float pParallaxValueY) {
		this.mParallaxValueX = pParallaxValueX;
		this.mParallaxValueY = pParallaxValueY;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onDraw(final GLState pGLState, final Camera pCamera) {
		super.onDraw(pGLState, pCamera);

		final float parallaxValueX = this.mParallaxValueX;
		final float parallaxValueY = this.mParallaxValueY;
		final ArrayList<ParallaxEntityXY> parallaxEntities = this.mParallaxEntities;

		for(int i = 0; i < this.mParallaxEntityCount; i++) {
			parallaxEntities.get(i).onDraw(pGLState, pCamera, parallaxValueX,parallaxValueY);
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void attachParallaxEntityXY(final ParallaxEntityXY pParallaxEntity) {
		this.mParallaxEntities.add(pParallaxEntity);
		this.mParallaxEntityCount++;
	}

	public boolean detachParallaxEntityXY(final ParallaxEntityXY pParallaxEntity) {
		this.mParallaxEntityCount--;
		final boolean success = this.mParallaxEntities.remove(pParallaxEntity);
		if(!success) {
			this.mParallaxEntityCount++;
		}
		return success;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public static class ParallaxEntityXY {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		final float mParallaxFactorX;
		final float mParallaxFactorY;
		final IAreaShape mAreaShape;

		// ===========================================================
		// Constructors
		// ===========================================================

		public ParallaxEntityXY(final float pParallaxFactorX, final float pParallaxFactorY, final IAreaShape pAreaShape) {
			this.mParallaxFactorX = pParallaxFactorX;
			this.mParallaxFactorY = pParallaxFactorY;
			this.mAreaShape = pAreaShape;
		}

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================

		public void onDraw(final GLState pGLState, final Camera pCamera, final float pParallaxValueX, final float pParallaxValueY) {
			pGLState.pushModelViewGLMatrix();
			{
				final float cameraWidth = pCamera.getWidth();
				final float cameraHeight = pCamera.getHeight();
				final float shapeWidthScaled = this.mAreaShape.getWidthScaled();
				final float shapeHeightScaled = this.mAreaShape.getHeightScaled();
				float baseOffsetX = (pParallaxValueX * this.mParallaxFactorX) % shapeWidthScaled;
				float baseOffsetY = (pParallaxValueY * this.mParallaxFactorY) % shapeHeightScaled;

				while(baseOffsetX > 0) {
					baseOffsetX -= shapeWidthScaled;
				}
				while(baseOffsetY > 0) {
					baseOffsetY -= shapeHeightScaled;
				}

				pGLState.translateModelViewGLMatrixf(baseOffsetX, baseOffsetY, 0);

				System.out.println(baseOffsetX + ":" + baseOffsetY); 

				float currentMaxX = baseOffsetX;
				float currentMaxY = baseOffsetY;
				
				do {
					this.mAreaShape.onDraw(pGLState, pCamera);
					pGLState.translateModelViewGLMatrixf(shapeWidthScaled, 0, 0);
					currentMaxX += shapeWidthScaled;
				} while(currentMaxX < cameraWidth);
				do {
					this.mAreaShape.onDraw(pGLState, pCamera);
					pGLState.translateModelViewGLMatrixf(0, shapeHeightScaled, 0);
					currentMaxY += shapeHeightScaled;
				} while(currentMaxY < cameraHeight);
			}
			pGLState.popModelViewGLMatrix();
		}

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
}

