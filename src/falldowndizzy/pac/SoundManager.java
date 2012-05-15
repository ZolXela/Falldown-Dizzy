package falldowndizzy.pac;

import org.andengine.audio.sound.Sound;

public class SoundManager {

	private Sound mGameOverSound;
	private Sound mMunchSound ;
	private boolean soundEnabled;
	
	public SoundManager(){
		mGameOverSound = null;
		mMunchSound = null;
		soundEnabled = false;
	}
	public void SetSound(Sound mGameOverSound, Sound mMunchSound) {
		this.mGameOverSound = mGameOverSound;
		this.mMunchSound = mMunchSound;
	}
	
	public void playGameOver(){
		if((mGameOverSound != null) && (soundEnabled))
			mGameOverSound.play();
	}
	public void playmMunchSound(){
		if((mMunchSound != null) && (soundEnabled))
			mMunchSound.play();
	}
	public void setSoundEnabled(boolean soundEnabled) {
		this.soundEnabled = soundEnabled;
	}
	
}
