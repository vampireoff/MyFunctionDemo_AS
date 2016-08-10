LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := JniTest
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	E:\Android_Demo\MyFunctionDemo1\app\src\main\JNI\myc.c \

LOCAL_C_INCLUDES += E:\Android_Demo\MyFunctionDemo1\app\src\main\JNI
LOCAL_C_INCLUDES += E:\Android_Demo\MyFunctionDemo1\app\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
