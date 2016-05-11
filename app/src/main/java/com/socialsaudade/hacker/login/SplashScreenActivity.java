package com.socialsaudade.hacker.login;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.socialsaude.hacker.login.R;

public class SplashScreenActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                finish();

                Intent intent = new Intent();
                intent.setClass(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }

}