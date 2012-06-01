package falldowndizzy.pac;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GoodFruit extends Sprite {

	public PhysicsWorld mPhysicsWorld;
	public Body GoodBody;
	public static GoodFruit mGood;
	
	
	public GoodFruit(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld pPhysicsWorld){
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);

		mPhysicsWorld = pPhysicsWorld;
		mGood = this;
		
		GoodBody = PhysicsFactory.createCircleBody(mPhysicsWorld, this, BodyType.DynamicBody, Game_Scene.PLAYER_FIXTURE_DEF);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, GoodBody, true, false));	
		mPhysicsWorld.setContactListener(new ContactListener(){

			@Override
			public void beginContact(Contact contact) {
				if (contact.isEnabled()) {
					Game_Scene._curGameScene.detachChild(mGood);
					Game_Scene._score.setText(String.valueOf(--Game_Scene.curScore));
				}	
			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
//				if (contact.isEnabled()) {
//					Game_Scene._curGameScene.detachChild(mGood);
//					Game_Scene._score.setText(String.valueOf(--Game_Scene.curScore));
//				}
			}
			
		});
	}

}
