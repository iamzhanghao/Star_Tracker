package com.tinymos.demo.star_tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.tinymos.demo.star_tracker.camera.launch.C1CameraPhoneConnection;
import com.tinymos.demo.star_tracker.phone.launch.P1CameraPhoneConnection;
import com.tinymos.demo.star_tracker.phone.launch.P2PiPhoneConnection;

public class SelectDevice extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.select_device);
    }

    public void startPhone(View view)
    {
        Intent intent = new Intent(SelectDevice.this, P2PiPhoneConnection.class);
//        Intent intent = new Intent(SelectDevice.this, P1CameraPhoneConnection.class);
        startActivity(intent);
    }

    public void startCamera(View view)
    {
        Intent intent = new Intent(SelectDevice.this, C1CameraPhoneConnection.class);
//        Intent intent = new Intent(SelectDevice.this, C2Camera.class);

        startActivity(intent);
    }

}
