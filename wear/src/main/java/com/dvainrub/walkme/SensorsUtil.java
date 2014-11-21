package com.dvainrub.walkme;

import android.hardware.SensorEvent;
import android.util.Log;

/**
 * Created by dvainrub on 20-Nov-14.
 */
public class SensorsUtil {

    private static final String TAG ="LOGGER_SensorsUtil" ;
    public static float x, y, z, sum;
    public static float XDiff, YDiff, ZDiff;
    public static String kind;

    public SensorsUtil(String k) {
        x=0;
        y=0;
        z=0;

        sum=0;
        kind=k;
        initializeDiff(k);
    }

    public void initializeDiff (String k){
        if ("PushUp".equals(kind))
        {
            XDiff = 0; //It was <1!
            YDiff = 2;
            ZDiff = 0;
        }
        else if("Abs".equals(kind))
        {
            XDiff = 4;
            YDiff = 0;
            ZDiff = 4;
        }
        else if ("Lifting".equals(kind))
        {
            XDiff = 3;
            YDiff = 3;
            ZDiff = 0;
        }
        else //Squats
        {
            XDiff = 1000;
            YDiff = 1000;
            ZDiff = 1000;
        }
    }

    public static int countAction(float new_x, float new_y, float new_z) {
       // Log.d(TAG, " " + new_x + ", " + new_y + ", " + new_z );
        float deltaX = Math.abs(new_x-x);
        float deltaY = Math.abs(new_y-y);
        float deltaZ = Math.abs(new_z-z);
        int counter = 0;

        if(deltaX>=XDiff && deltaY>=YDiff && deltaZ>=ZDiff)
        {
            x = new_x;
            y = new_y;
            z = new_z;

            counter = 1;

            //Log.d(TAG, "counterAdded");

            }
        return counter;
        }
}
