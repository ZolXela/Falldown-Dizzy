package falldowndizzy.pac;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;

import falldowndizzy.pac.GameActivity;


public class SoundManager {

	private Sound mGameOverSound;
	private Sound mFoneSound ;
	private boolean soundEnabled;
	
	public void LoadSound(){
	
	this.mGameOverSound = SoundFactory.createSoundFromAsset(GameActivity._Engine.getSoundManager(), this, "gfx/game_over.ogg");
	this.mFoneSound = SoundFactory.createSoundFromAsset(GameActivity._Engine.getSoundManager(), this, "gfx/wagner_the_ride_of_the_valkyries.ogg");
	
	}
	
	public SoundManager(){
		mGameOverSound = null;
		mFoneSound = null;
		soundEnabled = false;
	}
	public void SetSound(Sound mGameOverSound, Sound mFoneSound) {
		this.mGameOverSound = mGameOverSound;
		this.mFoneSound = mFoneSound;
	}
	
	public void playGameOver(){
		if((mGameOverSound != null) && (soundEnabled))
			mGameOverSound.play();
	}
	public void playmMunchSound(){
		if((mFoneSound != null) && (soundEnabled))
			mFoneSound.play();
	}
	public void setSoundEnabled(boolean soundEnabled) {
		this.soundEnabled = soundEnabled;
	}
}
