package com.xpf.camera;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private ImageView imageView;
    private Button btnTakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.surfaceView);
        imageView = findViewById(R.id.imageView);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
    }

    private void takePhoto() {
        CustomCamera.getInstance().takePhoto(surfaceView.getHolder(), new CustomCamera.ITakePhotoListener() {
            @Override
            public void onSuccess(final Bitmap bitmap) {
                Log.e("TAG", "拍照成功~");
                if (bitmap != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            }

            @Override
            public void onFail(String error) {
                Log.e("TAG", "拍照失败:" + error);
            }
        });
    }
}
