package v.sdk.com.camera1;

/**
 * Created by xpf on 2017/4/13 :)
 * Function:枚举本地相机
 */

public class LocalCameraStreamParameters {

    public static enum CameraType {
        BACK, FRONT, UNKNOWN;

        private CameraType() {
        }
    }

    private static int cameraNum = 2;
    private static boolean hasCamera;
    private static CameraType[] cameraList = new CameraType[cameraNum];

    static {
        if (cameraNum < 1) {
            hasCamera = false;
        } else {
            hasCamera = true;
            initCameraList();
        }
    }

    private static void initCameraList() {
        if (cameraNum == 0) {
            return;
        }
        cameraList[0] = CameraType.FRONT;
    }

    public static CameraType[] getCameraList() {
        return cameraList;
    }
}
