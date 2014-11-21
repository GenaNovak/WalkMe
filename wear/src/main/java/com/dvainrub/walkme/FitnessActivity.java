package com.dvainrub.walkme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class FitnessActivity extends Activity implements SensorEventListener{
    public static String TAG = "LOGGER_NavigationActivity";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private TextView mTextView;
    private float[] accelerometerPoints = new float[3];
    private int counterPushUps = 0;
    private String kind;
    private SensorsUtil sensorUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d(TAG, "onCreate");
        Bundle extras = getIntent().getExtras();
        kind = "Unknown Kind";

        if (extras != null)
        {

            kind = extras.getString(MyActivity.KIND);
            Log.d(TAG, "Kind received: " + kind);
        }

        final String k = kind;
        /*RelativeLayout rl = (RelativeLayout)findViewById(R.id.activityNavigation);

        if ("PushUp".equals(kind))
        {
            rl.setBackgroundColor(Color.parseColor("#ff24994d"));
        }
        else if("Abs".equals(kind))
        {
            rl.setBackgroundColor(Color.parseColor("#ff55bc82"));
        }
        else if ("Lifting".equals(kind))
        {
            rl.setBackgroundColor(Color.parseColor("#ff4ab4de"));
        }
        else //Rest
        {
            rl.setBackgroundColor(Color.parseColor("#ff357bbf"));
        }*/





        if (!k.equals("Rest"))
        {
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            //Check if the device have an accelerometer
            if (mAccelerometer != null){
                Log.d(TAG, "There's a accerelometer sensor.");

            }
            else {
                Log.d(TAG, "// Failure! No accelerometer sensor.");

            }

            setContentView(R.layout.activity_navigation);
            final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
            stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
                @Override
                public void onLayoutInflated(WatchViewStub stub) {
                    mTextView = (TextView) stub.findViewById(R.id.navigationText);
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    mTextView.setText(""+3);
                    v.vibrate(100);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mTextView.setText("" + 2);
                    v.vibrate(100);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mTextView.setText(""+1);
                    v.vibrate(400);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });



            sensorUtil = new SensorsUtil(kind);
        }
        else
        {
            startStopWatch();
        }


       }

    private void startStopWatch() {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextView.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mTextView.setText("done!");
            }
        }.start();
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

        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            counterPushUps += SensorsUtil.countAction(event.values[0], event.values[1], event.values[2]);
            mTextView.setText(""+counterPushUps/2);
            Log.d(TAG,""+counterPushUps/2);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}


    }
