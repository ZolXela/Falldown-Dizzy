package falldowndizzy.pac;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class GoodFruit extends Sprite {

	public PhysicsWorld mPhysicsWorld;
	private PhysicsConnector mPhysicsConnector;
	public Body GoodBody;
	
	
	public GoodFruit(final float pX, final float pY, final ITextureRegion pTextureRegion, 
			final VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld pPhysicsWorld){
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);

		mPhysicsWorld = pPhysicsWorld;
		
		GoodBody = PhysicsFactory.createCircleBody(mPhysicsWorld, this, BodyType.DynamicBody, Game_Scene.PLAYER_FIXTURE_DEF);
		mPhysicsConnector = new PhysicsConnector(this, GoodBody, true, false);
		mPhysicsWorld.registerPhysicsConnector(mPhysicsConnector);	
		GoodBody.setUserData("good");
	}
	
	public void setCollision(){
		mPhysicsWorld.unregisterPhysicsConnector(mPhysicsConnector);	
		mPhysicsWorld.destroyBody(GoodBody);
	}
	
	public void Destructor(){
		
		mPhysicsWorld.unregisterPhysicsConnector(mPhysicsConnector);
		mPhysicsWorld.destroyBody(GoodBody);
		this.detachSelf();
		
	}
	

}
