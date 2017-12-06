package com.kale.camerademo;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by xpf on 2017/09/07 :)
 * Function:相机拍照页面
 * 参考1：http://blog.csdn.net/a740169405/article/details/12207229
 * 参考2：http://mobile.51cto.com/amedia-376703.htm
 */
public class MainActivity extends Activity {

    private CameraManager frontCameraManager;
    private CameraManager backCameraManager;
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private boolean isBackOpened = false;
    private boolean isFrontOpened = false;
    private Camera mBackCamera;
    private Camera mFrontCamera;
    private ImageButton ibTakePhoto;
    private ImageButton ibSwitchCamera;
    private boolean isBackCamera = true;// 默认为后置相机

    /**
     * 自动对焦的回调方法，用来处理对焦成功/不成功后的事件
     */
    private AutoFocusCallback mAutoFocus = new AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            //TODO:空实现
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    /**
     * 初始化相机View等参数
     */
    private void initView() {
        ibTakePhoto = (ImageButton) findViewById(R.id.ibTakePhoto);
        ibSwitchCamera = (ImageButton) findViewById(R.id.ibSwitchCamera);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        holder = surfaceView.getHolder();
        frontCameraManager = new CameraManager(mFrontCamera, holder);
        backCameraManager = new CameraManager(mBackCamera, holder);
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        // 点击拍照的监听
        ibTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        // 切换相机的监听
        ibSwitchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBackCamera = !isBackCamera;
                Toast.makeText(MainActivity.this,
                        "已切换到" + (isBackCamera ? "后置" : "前置") + "相机", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void takePhoto() {
        if (isBackCamera) {
            takeBackPhoto();
        } else {
            takeFrontPhoto();
        }
    }

    /**
     * 开启前置摄像头照相
     */
    private void takeFrontPhoto() {
        if (!isFrontOpened && frontCameraManager.openCamera(Camera.CameraInfo.CAMERA_FACING_FRONT)) {
            mFrontCamera = frontCameraManager.getCamera();
            // 自动对焦
            mFrontCamera.autoFocus(mAutoFocus);
            isFrontOpened = true;
            // 拍照
            mFrontCamera.takePicture(null, null, frontCameraManager.new PicCallback(mFrontCamera));
        }
    }

    /**
     * 开启后置摄像头照相
     */
    private void takeBackPhoto() {
        if (!isBackOpened && backCameraManager.openCamera(Camera.CameraInfo.CAMERA_FACING_BACK)) {
            mBackCamera = backCameraManager.getCamera();
            // 自动对焦
            mBackCamera.autoFocus(mAutoFocus);
            isBackOpened = true;
            // 拍照
            mBackCamera.takePicture(null, null, backCameraManager.new PicCallback(mBackCamera));
        }
    }

}
