//
// Created by lwd on 2016/8/8.
//
#include <com_function_jni_JniUtils.h>
#include <jni.h>
#include <stdio.h>
#include <android/log.h>

static const char *classPathName = "com/function/jni/JniUtils";

//java中静态方法过来这边是用jclass类型的参数，其他的是用jobject类型
JNIEXPORT jstring JNICALL Java_com_function_jni_JniUtils_getStringFromC
        (JNIEnv * env, jclass jc)
{
    return (*env)->NewStringUTF(env, "这里是来自c的string");
}

/**
 * 获取并改变java中的变量值
 */
JNIEXPORT jstring JNICALL Java_com_function_jni_JniUtils_setVar
        (JNIEnv * env, jclass jc)
{
    //获取java类对应的jclass对象
    jclass jclass1 = (*env)->FindClass(env, classPathName);
    jfieldID jfieldID1 = (*env)->GetStaticFieldID(env, jclass1, "var", "I");
    __android_log_print(ANDROID_LOG_INFO, "logvar_jni", "获取到的java变量是%d", (*env)->GetStaticIntField(env, jc, jfieldID1));
    (*env)->SetStaticIntField(env, jc, jfieldID1, 3);
}

JNIEXPORT jstring JNICALL Java_com_function_jni_JniUtils_callback_lmyLog
        (JNIEnv * env, jclass jc)
{
    //获取java类对应的jclass对象
    jclass jclass1 = (*env)->FindClass(env, classPathName);
    if (jclass1 == NULL){
        //log输出字符串
        __android_log_print(ANDROID_LOG_INFO, "jnilog", "找不到类%s", classPathName);
    }
    else
    {
        //获取java里的方法的ID
        jmethodID jmethodID1 = (*env)->GetStaticMethodID(env, jclass1, "myLog", "()V");
        //调用java里的方法
        (*env)->CallStaticVoidMethod(env, jclass1, jmethodID1);
    }
    return (*env)->NewStringUTF(env, "回调myLog方法");
}

/**
 * 获取值后返回
 */
JNIEXPORT jstring JNICALL Java_com_function_jni_JniUtils_sayHello
        (JNIEnv * env, jclass jc, jstring j_str)
{
    const char *c_str = NULL;
    char buff[128] = {0};
    jboolean isCopy;    // 返回JNI_TRUE表示原字符串的拷贝，返回JNI_FALSE表示返回原字符串的指针
    c_str = (*env)->GetStringUTFChars(env, j_str, &isCopy);
    printf("isCopy:%d\n",isCopy);
    if(c_str == NULL)
    {
        return NULL;
    }
    printf("C_str: %s \n", c_str);
    sprintf(buff, "hello %s", c_str);
    (*env)->ReleaseStringUTFChars(env, j_str, c_str);
    return (*env)->NewStringUTF(env,buff);
}
/********************************动态注册************************************/
//Java和JNI函数的绑定表
static JNINativeMethod method_table[] = {
        { "callback_myLog", "()Ljava/lang/String;",
                (void*)Java_com_function_jni_JniUtils_callback_lmyLog }//绑定
};

//注册native方法到java中
static int registerNativeMethods(JNIEnv* env, const char* className,
                                JNINativeMethod* gMethods, int numMethods)
{
    jclass clazz;
    clazz = (*env)->FindClass(env, className);
    if (clazz == NULL) {
        return JNI_FALSE;
    }
    //动态注册本地方法
    if ((*env)->RegisterNatives(env, clazz, gMethods, numMethods) < 0){
        return JNI_FALSE;
    }

    return JNI_TRUE;
}


int register_ndk_load(JNIEnv *env)
{
    // 调用注册方法
    return registerNativeMethods(env, classPathName,
                                 method_table,
                                 sizeof(method_table) / sizeof(method_table[0]));
}

/**
 * onload方法
 */
JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    JNIEnv* env = NULL;
    jint result = -1;
    //获取JNIEnv对象
    if ((*vm)->GetEnv(vm, (void**) &env, JNI_VERSION_1_4) != JNI_OK) {
        return result;
    }

    //动态注册
    register_ndk_load(env);

    // 返回jni的版本
    return JNI_VERSION_1_4;
}