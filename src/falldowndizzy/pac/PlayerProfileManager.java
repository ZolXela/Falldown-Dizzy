package falldowndizzy.pac;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import falldowndizzy.pac.GameLogicController;
import android.content.Context;

public class PlayerProfileManager {
	
	private int unlockedLevelId;
	private GameLogicController gameLogicController;
	public PlayerProfileManager(GameLogicController gameLogicController) {
		// TODO Auto-generated constructor stub
		this.gameLogicController = gameLogicController;
		unlockedLevelId = -1;
		ReadSettings();
		
	}
	
	public boolean isLevelUnlocked(int levelId){
		if((unlockedLevelId + 1 >= levelId)
			|| (unlockedLevelId == -1))
			return true;
		return false;
	}
	
	public void increaseUnlockedLevelNumber(int levelId){
		if(levelId > unlockedLevelId)
			unlockedLevelId++;
		WriteSettings();
	}
	

	
	private void WriteSettings() {
		String FILENAME = "settings2";
		FileOutputStream fos = null;
		DataOutputStream dos;
		

		
		try {
			fos = gameLogicController.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
		}
		try {
			dos=new DataOutputStream(fos);
			
			//dos.write(unlockedLevelId, 0, Integer.SIZE)
			dos.writeInt(unlockedLevelId);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	private void ReadSettings() {
		String FILENAME = "settings2";
		FileInputStream fos = null;
		DataInputStream dos;
		dos = new DataInputStream(fos);
		try {
			fos = gameLogicController.openFileInput(FILENAME);
		} catch (FileNotFoundException e) {
			unlockedLevelId = 0;
			WriteSettings();
			return;

		}
		try {
			dos=new DataInputStream(fos);
			int t = -1;
			t = dos.readInt();
			unlockedLevelId = t;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	}

}
