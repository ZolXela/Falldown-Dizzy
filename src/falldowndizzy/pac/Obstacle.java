package falldowndizzy.pac;

import java.io.IOException;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


public class Obstacle extends Sprite {

	PhysicsWorld pPhysicsWorld;
	
	public Obstacle(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld mPhysicsWorld, String xmlFile) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		pPhysicsWorld = mPhysicsWorld;
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
