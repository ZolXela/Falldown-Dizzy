package falldowndizzy.pac;

import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


public class SpiderEnemy extends AnimatedSprite {

	public Body RopeBody;
	public Body SpiderBody;
	private PhysicsWorld mPhysicsWorld;
	public Sprite rope;
	public Path path;
	public static float mDefaultHeight;
	public static float length[] = {0.4096f, 0.5662f, 0.6747f, 0.8313f, 1, 0.9157f, 0.9157f, 1, 0.8313f, 0.6747f, 0.5662f, 0.4096f};
	
	public SpiderEnemy(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld pPhysicsWorld) {
		super(pX, pY + GfxAssets.mRopeTextureRegion.getHeight(), pTiledTextureRegion, pVertexBufferObjectManager);
		mPhysicsWorld = pPhysicsWorld;
		
		SpiderBody = PhysicsFactory.createCircleBody(mPhysicsWorld, this, BodyType.StaticBody, Game_Scene.PLAYER_FIXTURE_DEF);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, SpiderBody, true, false));	
		
		rope = new Sprite(pX, pY - 7, GfxAssets.mRopeTextureRegion, pVertexBufferObjectManager);
		mDefaultHeight = rope.getHeight();
		
		RopeBody = PhysicsFactory.createCircleBody(mPhysicsWorld, this, BodyType.StaticBody, Game_Scene.PLAYER_FIXTURE_DEF);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, RopeBody, true, false));	
		
		this.setScale(1.5f);
		
//		LineJointDef joint = new LineJointDef();
//		joint.initialize(SpiderBody, RopeBody, new Vector2(this.getX(), this.getY()),  new Vector2(this.getX() + this.getWidth() / 2, 0));
//		joint.enableMotor = true;
		
		this.setPosition(100, 50);
		
		
		this.setAnimation(300);

	}
	


	public void setAnimation(long pDuration){
		
		this.animate(pDuration, true, new IAnimationListener(){

			@Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
					int pInitialLoopCount) {
				rope.setHeight(mDefaultHeight * SpiderEnemy.length[0]);
				path = new Path(5).to(rope.getX(), rope.getY() + rope.getHeight());
//				pAnimatedSprite.dispose();
//				pAnimatedSprite.setPosition(pAnimatedSprite.getX(), rope.getY() + rope.getHeight());
//				pAnimatedSprite.registerEntityModifier(new PathModifier(300, new Path(20)));
//				pAnimatedSprite.registerEntityModifier(new PathModifier(10, path));
				pAnimatedSprite.setY(rope.getY() + rope.getHeight());
				
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
					int pOldFrameIndex, int pNewFrameIndex) {
				rope.setHeight(mDefaultHeight * SpiderEnemy.length[pNewFrameIndex]);
//				pAnimatedSprite.dispose();
//				pAnimatedSprite.setPosition(pAnimatedSprite.getX(), rope.getY() + rope.getHeight());
//				pAnimatedSprite.setY(rope.getY() + rope.getHeight());
//				pAnimatedSprite.
//				pAnimatedSprite.registerEntityModifier(new PathModifier(10, path.to(rope.getX(), rope.getY() + rope.getHeight())));
				pAnimatedSprite.setY(rope.getY() + rope.getHeight());
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
					int pRemainingLoopCount, int pInitialLoopCount) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	
}