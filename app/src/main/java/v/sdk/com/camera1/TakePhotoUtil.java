package v.sdk.com.camera1;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by xpf on 2017/4/15 :)
 * Function:
 */

public class TakePhotoUtil {


    /**
     * 自动对焦的回调方法，用来处理对焦成功/不成功后的事件
     */
    private static Camera.AutoFocusCallback mAutoFocus = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            // TODO:空实现
        }
    };

    /**
     * 开启前置摄像头照相
     */
    public static void takePhoto(SurfaceHolder holder) {
        CameraManager frontCameraManager;
        Camera mFrontCamera = null;
        int cameraID;
        frontCameraManager = new CameraManager(mFrontCamera, holder);

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
}
