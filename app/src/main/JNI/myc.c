//
// Created by lwd on 2016/8/8.
//
#include <com_function_jni_JniUtils.h>
#include <jni.h>

JNIEXPORT jstring JNICALL Java_com_function_jni_JniUtils_getStringFromC
        (JNIEnv * env, jclass jc)
{
    return (*env)->NewStringUTF(env, "这里是来自c的string");
}
