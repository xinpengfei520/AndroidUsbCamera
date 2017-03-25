LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := ImageProc
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_LDLIBS := \
	-llog \
	-ljnigraphics \

LOCAL_SRC_FILES := \
	D:\AndroidStudioProjects\Gyroscope\AndroidUsbCamera\src\main\jni\Android.mk \
	D:\AndroidStudioProjects\Gyroscope\AndroidUsbCamera\src\main\jni\Application.mk \
	D:\AndroidStudioProjects\Gyroscope\AndroidUsbCamera\src\main\jni\ImageProc.c \

LOCAL_C_INCLUDES += D:\AndroidStudioProjects\Gyroscope\AndroidUsbCamera\src\main\jni
LOCAL_C_INCLUDES += D:\AndroidStudioProjects\Gyroscope\AndroidUsbCamera\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
