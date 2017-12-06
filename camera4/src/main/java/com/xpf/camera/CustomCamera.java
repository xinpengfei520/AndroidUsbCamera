package com.xpf.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xpf on 2017/12/5 :)
 * Function:自定义相机拍照类(单例)
 */

public class CustomCamera implements Camera.AutoFocusCallback, Camera.PictureCallback {

    private static final String TAG = CustomCamera.class.getSimpleName();
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private ITakePhotoListener mListener;

    private static class LazyHolder {
        private static final CustomCamera instance = new CustomCamera();
    }

    private CustomCamera() {
    }

    public static CustomCamera getInstance() {
        return LazyHolder.instance;
    }

    @Override
    public void onAutoFocus(boolean b, Camera camera) {
        if (camera != null && b) {
            try {
                camera.takePicture(null, null, CustomCamera.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        if (mListener != null) {
            mListener.onSuccess(bitmap);
        }
        releaseCamera();
    }

    /**
     * 开启摄像头进行预览拍照
     */
    public void takePhoto(SurfaceHolder holder, ITakePhotoListener listener) {
        Log.i(TAG, "点击拍照了~");
        this.mHolder = holder;
        this.mListener = listener;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                openCamera();
            }
        });
    }

    /**
     * 打开相机(默认为0)
     */
    public void openCamera() {
        try {
            // 尝试开启摄像头
            mCamera = Camera.open(0);
            Camera.Parameters param = mCamera.getParameters();
            param.setPictureSize(640, 480);
            param.setExposureCompensation(param.getMaxExposureCompensation());
            mCamera.setParameters(param);
            // 这里的myCamera为已经初始化的Camera对象
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mCamera.autoFocus(CustomCamera.this); // 自动对焦
        } catch (Exception e) {
            e.printStackTrace();
            if (mListener != null) {
                mListener.onFail(e.getMessage());
            }
            releaseCamera();
        }
    }

    /**
     * 释放相机
     */
    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            mHolder = null;
        }
    }

    public interface ITakePhotoListener {
        void onSuccess(Bitmap bitmap);

        void onFail(String ex);
    }
}
