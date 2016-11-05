package com.cw14.dbz_punching_machine;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by lucas on 11/5/16.
 */

public class PunchActivity extends AppCompatActivity  {

    private SensorEventListener myAccelerometerListener, myMagneticFieldListener;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    //private Sensor magField;
    private Boolean mInitialized = false;
    private float mLastX, mLastY, mLastZ;
    private float[] accelerometerValues, magneticFieldValues;
    private float[] outR = new float[9];

    // Get norm given a vector.
    protected double getNorm(float x, float y, float z) {
        double sum = x*x + y*y + z*z;
        return Math.sqrt(sum);
    }

    // Accelerator stuff.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(myAccelerometerListener, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);

        myAccelerometerListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float NOISE = 0;
                TextView tvX = (TextView) findViewById(R.id.x_axis);
                TextView tvY = (TextView) findViewById(R.id.y_axis);
                TextView tvZ = (TextView) findViewById(R.id.z_axis);
                //ImageView iv = (ImageView) findViewById(R.id.image);
                float x = event.values[0]; // X
                float y = event.values[1]; // Y
                float z = event.values[2]; // Z
                if (!mInitialized) {
                    mLastX = x;
                    mLastY = y;
                    mLastZ = z;
                    tvX.setText("0.0");
                    tvY.setText("0.0");
                    tvZ.setText("0.0");
                    mInitialized = true;
                } else {
                    float deltaX = Math.abs(mLastX - x);
                    float deltaY = Math.abs(mLastY - y);
                    float deltaZ = Math.abs(mLastZ - z);
                    if (deltaX < NOISE) deltaX = (float) 0.0;
                    if (deltaY < NOISE) deltaY = (float) 0.0;
                    if (deltaZ < NOISE) deltaZ = (float) 0.0;
                    mLastX = x;
                    mLastY = y;
                    mLastZ = z;
                    tvX.setText(Float.toString(deltaX));
                    tvY.setText(Float.toString(deltaY));
                    tvZ.setText(Float.toString(deltaZ));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(myAccelerometerListener, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(myAccelerometerListener);
    }

}
