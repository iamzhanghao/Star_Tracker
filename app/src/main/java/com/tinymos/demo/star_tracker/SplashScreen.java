package com.tinymos.demo.star_tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {


                Intent i = new Intent(SplashScreen.this, SelectDevice.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                finish();
            }
        }, secondsDelayed * 4000);

    }
}
