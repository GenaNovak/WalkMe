package com.dvainrub.walkme;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements SensorEventListener {
    private static final String TAG = "LOGGER_MainActivity";
    private SensorManager mSensorManager;//
    private Sensor mAccelerometer;//
    private float[] accelerometerPoints = new float[3];
    private int counterPushUps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);//
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//

        //Check if the device have an accelerometer
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            Log.d(TAG, "There's a accerelometer sensor.");

        }
        else {
            Log.d(TAG, "// Failure! No accelerometer sensor.");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
            //x axis event.values[0];
            //y axis event.values[1];
            //z axis event.values[2];
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){

            SensorsUtil.isBiceps(event.values[0], event.values[1], event.values[2]);

            // assign directions
            float x=event.values[0];
            float y=event.values[1];
            float z=event.values[2];
            float sum = x+y+z;



            float old_x = accelerometerPoints[0];
            float old_y = accelerometerPoints[1];
            float old_z = accelerometerPoints[2];
            float old_sum = old_x+old_y+old_z;


            float diff = Math.abs(sum-old_sum);
            if (diff>15)
            {
                Log.d(TAG, "diff= " + diff );
                Log.d(TAG, " "+x+", "+y+", "+z+ " = " + sum);
                Log.d(TAG, " "+old_x+", "+old_y+", "+old_z+" = " + old_sum);

                for (int i=0; i<3;i++)
                {
                    accelerometerPoints[i]=event.values[i];
                }

                counterPushUps++;

                if (counterPushUps%2==0){

                    Log.d(TAG, "New Counter: " + counterPushUps/2);


                }
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
