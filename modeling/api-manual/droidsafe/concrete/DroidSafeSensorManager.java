package droidsafe.concrete;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import droidsafe.annotations.DSC;
import droidsafe.annotations.DSModeled;

public class DroidSafeSensorManager extends SensorManager {

	public DroidSafeSensorManager() {
		super(null);	
	}

	@DSModeled(DSC.SAFE)
	public Sensor getDefaultSensor(int i) {
		return null;
	}
	
	@DSModeled(DSC.SAFE)
	public boolean registerListener(SensorEventListener listener, Sensor sensor, int rate) {
		SensorEvent se = new SensorEvent(0);
		se.sensor = sensor;
		
		listener.onSensorChanged(se);
		return true;
	}
	
	@DSModeled(DSC.SAFE)
	public void unregisterListener(SensorEventListener sel) {
		
	}
}

