package com.helpinghands.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.helpinghand.R;


public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, BubblePickerActivity.class );
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mHandler.postDelayed(runnable,3000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(runnable);
    }

}
