package falldowndizzy.pac;

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;


public interface dizzyAnalogScreenController extends IAnalogOnScreenControlListener {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void onControlSleep(final AnalogOnScreenControl pAnalogOnScreenControl);

}
