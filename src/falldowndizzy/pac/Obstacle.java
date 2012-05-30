package falldowndizzy.pac;

import java.io.IOException;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


public class Obstacle extends Sprite {

	public Body ObstacleBody;
	
	PhysicsWorld pPhysicsWorld;
	
	public Obstacle(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld mPhysicsWorld, String xmlFile) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		pPhysicsWorld = mPhysicsWorld;

		ObstacleBody = PhysicsFactory.createCircleBody(pPhysicsWorld, this, BodyType.DynamicBody, Game_Scene.PLAYER_FIXTURE_DEF);
		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, ObstacleBody, true, false));	
		
		final PhysicsEditorLoader loader = new PhysicsEditorLoader();
		loader.setAssetBasePath("xml/");
		try {
		     loader.load(GameActivity._main, pPhysicsWorld, xmlFile, this,
					false, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
	
}
