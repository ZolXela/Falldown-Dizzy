package falldowndizzy.pac;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.ease.EaseSineInOut;

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
	public PhysicsConnector mSpPhysicsConnector;
	public PhysicsConnector mRpPhysicsConnector;
	
	public SpiderEnemy(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld pPhysicsWorld) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		mPhysicsWorld = pPhysicsWorld;
		this.setScale(1.2f);
		
		SpiderBody = PhysicsFactory.createBoxBody(mPhysicsWorld, SpiderEnemy.this, BodyType.StaticBody, Game_Scene.PLAYER_FIXTURE_DEF);
		mSpPhysicsConnector = new PhysicsConnector(this, SpiderBody, true, false);	
		mPhysicsWorld.registerPhysicsConnector(mSpPhysicsConnector);	
		
		rope = new Sprite(pX, pY - 7, GfxAssets.mRopeTextureRegion, pVertexBufferObjectManager);
		
		RopeBody = PhysicsFactory.createBoxBody(mPhysicsWorld, rope, BodyType.StaticBody, Game_Scene.PLAYER_FIXTURE_DEF);
		mRpPhysicsConnector = new PhysicsConnector(this, RopeBody, true, false);
		mPhysicsWorld.registerPhysicsConnector(mRpPhysicsConnector);	
		
		rope.setHeight(rope.getHeight() * 1.5f);
		mDefaultHeight = rope.getHeight();
		
		this.setPosition(100, 50);
		path = new Path(12).to(pX, pY + mDefaultHeight * length[0]).to(pX, pY + mDefaultHeight * length[1]).to(pX, pY + mDefaultHeight * length[2]).to(pX, pY + mDefaultHeight * length[3]).to(pX, pY + mDefaultHeight * length[4])
				.to(pX, pY + mDefaultHeight * length[5]).to(pX, pY + mDefaultHeight * length[6]).to(pX, pY + mDefaultHeight * length[7]).to(pX, pY + mDefaultHeight * length[8]).to(pX, pY + mDefaultHeight * length[9]).to(pX, pY + mDefaultHeight * length[10])
				.to(pX, pY + mDefaultHeight * length[11]);

		this.registerEntityModifier(new LoopEntityModifier(new PathModifier(4, path, null, new IPathModifierListener() {
            @Override
            public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) {
                    rope.setHeight(mDefaultHeight * length[0] + SpiderEnemy.this.getHeightScaled() * 0.45f);
                    
            }

            @Override
            public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {            	   
                    SpiderEnemy.this.setCurrentTileIndex(pWaypointIndex);
                    rope.setHeight(mDefaultHeight * length[(pWaypointIndex + 1) % 12] + SpiderEnemy.this.getHeightScaled() * 0.45f);

            }

            @Override
            public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
            	
            }

            @Override
            public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) {
            	
            }

		}, EaseSineInOut.getInstance())));
		
		
	}

	public void Destructor(){
		
		mPhysicsWorld.unregisterPhysicsConnector(mSpPhysicsConnector);
		mPhysicsWorld.unregisterPhysicsConnector(mRpPhysicsConnector);
		mPhysicsWorld.destroyBody(SpiderBody);
		mPhysicsWorld.destroyBody(RopeBody);
		rope.detachSelf();
		this.detachSelf();
		
	}
	
	
}