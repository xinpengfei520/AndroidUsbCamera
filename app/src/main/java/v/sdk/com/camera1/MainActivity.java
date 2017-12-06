package v.sdk.com.camera1;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * 参考自：http://blog.csdn.net/a740169405/article/details/12207229
 * 还可以参考：http://mobile.51cto.com/amedia-376703.htm
 */
public class MainActivity extends Activity {

    private CameraManager frontCameraManager;
    private CameraManager backCameraManager;
    /**
     * 定义前置有关的参数
     */
    private SurfaceView frontSurfaceView;
    private SurfaceHolder frontHolder;
    private Camera mFrontCamera;
    /**
     * 定义后置有关的参数
     */
    private SurfaceView backSurfaceView;
    private SurfaceHolder backHolder;
    private boolean isBackOpened = false;
    private Camera mBackCamera;
    private int cameraID = 0;

    /**
     * 自动对焦的回调方法，用来处理对焦成功/不成功后的事件
     */
    private AutoFocusCallback mAutoFocus = new AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            // TODO:空实现
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    public void buttonListener(View view) {
        switch (view.getId()) {
            case R.id.openFront_button:
//                takeFrontPhoto();
                TakePhotoUtil.takePhoto(frontHolder);
                break;
            case R.id.openBack_button:
                takeBackPhoto();
                break;
        }
    }

    private void initView() {
        // 初始化前置相机参数
        frontSurfaceView = (SurfaceView) findViewById(R.id.front_surfaceview);
        frontHolder = frontSurfaceView.getHolder();
        frontCameraManager = new CameraManager(mFrontCamera, frontHolder);
        // 初始化后置相机参数
        backSurfaceView = (SurfaceView) findViewById(R.id.back_surfaceview);
        backHolder = backSurfaceView.getHolder();
        backCameraManager = new CameraManager(mBackCamera, backHolder);
    }

    /**
     * 开启前置摄像头照相
     */
    private void takeFrontPhoto() {
        LocalCameraStreamParameters.CameraType[] cameraType
                = LocalCameraStreamParameters.getCameraList();
        int cameraNum = cameraType.length;
        if (cameraNum == 0) {
            Log.e("TAG", "你的手机没有相机");
            return;
        }
        String cameraLists[] = new String[cameraNum];
        cameraID = -1;
        for (int i = 0; i < cameraNum; i++) {
            if (cameraType[i] == LocalCameraStreamParameters.CameraType.BACK) {
                cameraLists[i] = "Back";
            } else if (cameraType[i] == LocalCameraStreamParameters.CameraType.FRONT) {
                cameraLists[i] = "Front";
                cameraID = i;
            } else if (cameraType[i] == LocalCameraStreamParameters.CameraType.UNKNOWN) {
                cameraLists[i] = "Unknown";
                cameraID = i;
            }
        }

        if (cameraID >= 0) {
            if (frontCameraManager.openCamera(cameraID)) {
                mFrontCamera = frontCameraManager.getCamera();
                mFrontCamera.autoFocus(mAutoFocus); //自动对焦
                mFrontCamera.takePicture(null, null,
                        frontCameraManager.new PicCallback(mFrontCamera)); // 拍照
            }
        }

    }

    /**
     * 开启后置摄像头照相
     */
    private void takeBackPhoto() {
        if (isBackOpened == false && backCameraManager.openCamera(Camera.CameraInfo.CAMERA_FACING_BACK)) {
            mBackCamera = backCameraManager.getCamera();
            mBackCamera.autoFocus(mAutoFocus); //自动对焦
            isBackOpened = true;
            mBackCamera.takePicture(null, null,
                    backCameraManager.new PicCallback(mBackCamera)); // 拍照
        }
    }


}
