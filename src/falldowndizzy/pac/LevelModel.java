package falldowndizzy.pac;

public class LevelModel {
	private int mBestTime;
	private short mLevelId;
	private boolean mUnlocked;
	
	
	private boolean mFinished;
	public boolean ismFinished() {
		return mFinished;
	}
	public void setFinished(boolean mFinished) {
		this.mFinished = mFinished;
	}
	public int getBestTime() {
		return mBestTime;
	}
	public void setBestTime(int mBestTime) {
		this.mBestTime = mBestTime;
	}
	public short getLevelId() {
		return mLevelId;
	}
	public void setLevelId(short mLevelId) {
		this.mLevelId = mLevelId;
	}
	public boolean isUnlocked() {
		return mUnlocked;
	}
	public void setUnlocked(boolean mUnlocked) {
		this.mUnlocked = mUnlocked;
	}
}
