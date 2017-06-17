package com.tinymos.demo.star_tracker.phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tinymos.demo.star_tracker.R;

public class TakingPhoto extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taking_photo);




        int secondsDelayed = 2;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent i = new Intent(TakingPhoto.this, SearchStar.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, secondsDelayed * 1000);
    }
}