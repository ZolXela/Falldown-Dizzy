package falldowndizzy.pac;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;


public class SpiderEnemy extends AnimatedSprite {

	public TargetsPool mTargetsPool;
	public Body EnemyBody;
	private PhysicsWorld pPhysicsWorld;
	public boolean killed = false;
	
	
	public SpiderEnemy(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld mPhysicsWorld, String xmlFile) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		mTargetsPool = new TargetsPool(pTiledTextureRegion);
		pPhysicsWorld = mPhysicsWorld;

		EnemyBody = PhysicsFactory.createCircleBody(pPhysicsWorld, this, BodyType.DynamicBody, Game_Scene.PLAYER_FIXTURE_DEF);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, EnemyBody, true, false));	
		pPhysicsWorld.setContactListener(new ContactListener(){
			@Override
			public void beginContact(Contact contact) {
				killed = false;
			}
			@Override
			public void endContact(Contact contact)
			{
			}
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				
				
			}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				killed = contact.isEnabled() ? true : false;				
			}
		});
		
//		target = 
		mTargetsPool.obtainPoolItem();
		this.setPosition(pX, pY);
		this.animate(300);
	}

	public class TargetsPool extends GenericPool<AnimatedSprite> {
	
		private ITiledTextureRegion mTextureRegion;
		
		public TargetsPool(ITiledTextureRegion pTiledTextureRegion){
			super();
	        if (pTiledTextureRegion == null) {
	            throw new IllegalArgumentException("The texture region must not be NULL");
	        }
	        mTextureRegion = pTiledTextureRegion;
		}
	
		@Override
		protected AnimatedSprite onAllocatePoolItem() {
			return new AnimatedSprite(0, 0, mTextureRegion.deepCopy(), GameActivity.mVertexBufferObjectManager);
		}
		
	    protected void onHandleRecycleItem(final AnimatedSprite target) {
	        target.clearEntityModifiers();
	        target.clearUpdateHandlers();
	        target.setVisible(false);
	        target.detachSelf();
	        target.reset();
	        target.animate(300);

	    }
	
		
	};

}