package com.socialsaude.login;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.socialsaude.R;
import com.socialsaude.activity.MainScreenSSActivity;

public class SplashScreenActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (mPrefs.getBoolean("isLoggedSharedPrefs", false) == true) {
                    finish();
                    Intent intent = new Intent();
                    intent.setClass(SplashScreenActivity.this, MainScreenSSActivity.class);
                    startActivity(intent);
                } else {
                    finish();
                    Intent intent = new Intent();
                    intent.setClass(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        }, 3000);
    }
}