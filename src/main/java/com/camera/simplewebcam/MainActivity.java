package com.camera.simplewebcam;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    CameraPreview cp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        cp = (CameraPreview) findViewById(R.id.cp);
    }

}
