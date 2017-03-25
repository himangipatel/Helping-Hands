package com.helpinghands.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.helpinghand.R;
import com.helpinghands.SharedPreferenceHelper;


public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            SharedPreferenceHelper preferenceHelper = new SharedPreferenceHelper(SplashActivity.this);
            if (preferenceHelper.isLogin()){
                Intent intent = new Intent(SplashActivity.this,BubblePickerActivity.class );
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class );
                startActivity(intent);
                finish();
            }
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
