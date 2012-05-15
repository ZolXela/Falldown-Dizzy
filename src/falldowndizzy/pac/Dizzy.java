package falldowndizzy.pac;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Dizzy extends AnimatedSprite{
	
	
	int frameCount;
	
	public Dizzy(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
        this.setScaleCenterY(pTiledTextureRegion.getHeight());
//     this.setScale(2);
        this.animate(new long[]{200, 200, 200}, 4, 5, true);     
//        player.registerEntityModifier(new JumpModifier(50, playerX, playerX+30, playerY, playerY, 50, 50));
	}
	
}

