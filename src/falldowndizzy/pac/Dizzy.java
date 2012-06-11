package falldowndizzy.pac;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Dizzy extends AnimatedSprite {
	
	public Body DizzyBody;
	public boolean jumping = false;
	
	PhysicsWorld mPhysicsWorld;
	
	public Dizzy(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld pPhysicsWorld) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		mPhysicsWorld = pPhysicsWorld;

		DizzyBody = PhysicsFactory.createCircleBody(mPhysicsWorld, this, BodyType.DynamicBody, Game_Scene.PLAYER_FIXTURE_DEF);
		DizzyBody.setUserData("player");
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, DizzyBody, true, false));	
		mPhysicsWorld.setContactListener(new ContactListener(){
			@Override
			public void beginContact(Contact contact) {
				jumping = false;	
			}
			@Override
			public void endContact(Contact contact)
			{
				jumping = true;	
			}
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				
				
			}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				jumping = (contact.isEnabled())? false : true;				
			}
		});

	}
	
	public void Stay(){		
		setAnimation(16, 23);
		this.DizzyBody.setLinearVelocity(0, 0);	
	}
	
	public void GoLeft(Vector2 velocity){		
		this.DizzyBody.setLinearVelocity(velocity);	
		Vector2Pool.recycle(velocity);
	}
	
	public void GoRight(Vector2 velocity){	
		this.DizzyBody.setLinearVelocity(velocity);	
		Vector2Pool.recycle(velocity);
	}
	
	public void Jump(Vector2 velocity){
		if(velocity.x > 0) setAnimation(32, 39);
		else if(velocity.x < 0) setAnimation(24, 31);
		else setAnimation(16, 23);
		this.DizzyBody.setLinearVelocity(velocity);	
		Vector2Pool.recycle(velocity);
	}
	
	public void setAnimation(int begNum, int endNum){
		this.animate(new long[]{90, 90, 100, 100, 100, 100, 90, 90}, begNum, endNum, true);    
	}
	
	private boolean onBeforePositionChanged(){
		
//		int enemyListSize = Game_Scene._curGameScene.spiderLL.size();
//		for(int i = 0; i < enemyListSize; i++)
//			if(this.collidesWith(Game_Scene._curGameScene.spiderLL.get(i)))
//			{
//				Game_Scene._curGameScene.callbackCollisionEnemy();
//				return false;
//			}
		if(Game_Scene.goodsLL != null)
			for(int i = 0; i < Game_Scene.goodsLL.size(); i++) {
				if(this.collidesWith(Game_Scene.goodsLL.get(i))) {
					Game_Scene._curGameScene.callbackCollisionGoods(i);
					return false;
				}			
			}
		return true;
	}
	
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		onBeforePositionChanged();
	}
	
}

