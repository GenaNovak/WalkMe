package com.dvainrub.walkme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MyActivity extends Activity {
    public static final String KIND = "Kind";
    public static String TAG = "LOGGER_MyActivity";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
    }

    public void chooseKind(View view){
        Log.d(TAG, "chooseKind()");
        Intent intent = new Intent(this, FitnessActivity.class);
        String kind = (String) view.getTag();
        Log.d(TAG, "Kind sent: " + kind);
        intent.putExtra(KIND, kind);
        startActivity(intent);
    }


}
