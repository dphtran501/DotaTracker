package vhoang52.cs273.orangecoastcollege.edu.dotatracker;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class ShakeListener implements SensorEventListener {
    private static final float THRESHOLD = 25;

    private OnShakeListener mListener;

    public ShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float xForce = sensorEvent.values[0] - SensorManager.GRAVITY_EARTH;
            float yForce = sensorEvent.values[1];
            float zForce = sensorEvent.values[2];

            float netForce = (float) Math.sqrt(Math.pow(xForce, 2) + Math.pow(yForce, 2) + Math.pow(zForce, 2));
            if (netForce >= THRESHOLD) {
                Log.i(TAG, "onSensorChanged: " + sensorEvent.timestamp + ", " + System.currentTimeMillis());
                mListener.onShake();
            }
        }
    }

    public interface OnShakeListener {
        void onShake();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShakeListener that = (ShakeListener) o;

        return mListener.equals(that.mListener);
    }

    @Override
    public int hashCode() {
        return mListener.hashCode();
    }
}
