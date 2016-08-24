//
// Created by lwd on 2016/8/8.
//
#include <com_function_jni_JniUtils.h>
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <android/log.h>

#define LOG_TAG "JniLog"
#define LOG_I(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG, __VA_ARGS__)
#define LOG_E(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

static const char *classPathName = "com/function/jni/JniUtils";
jmethodID jmethodID1;

//类静态初始化缓存
JNIEXPORT void JNICALL Java_com_function_jni_JniUtils_initID
        (JNIEnv *env, jclass jc) {
    static jobject sresult;
    if ((*env)->PushLocalFrame(env, 10) < 0) {
        if ((*env)->EnsureLocalCapacity(env, 8) != 0) {
            /*申请8个局部引用的内存空间失败 OutOfMemoryError*/
            return;
        }
        int i;
        for (i = 0; i < 8; i++) {
            jstring jstr = "";
            // ... 使用jstr字符串
            /*这里没有删除在for中临时创建的局部引用*/
        }
        /* 调用PushLocalFrame获取10个局部引用失败，不需要调用PopLocalFrame */
        return;
    }
    jobject result;
    if ((*env)->IsSameObject(env, result, NULL) == JNI_TRUE) {
        //相等，result等于null
    }
    sresult = (*env)->NewGlobalRef(env, result);//变成全局变量。还有局部变量和全局弱变量
    jmethodID1 = (*env)->GetStaticMethodID(env, jc, "getStringFromC", "()Ljava/lang/String;");
    result = (*env)->PopLocalFrame(env, result);//返回result，其他的局部变量销毁，不返回的话result改为null
    (*env)->DeleteGlobalRef(env, sresult);//释放全局变量的引用

    if ((*env)->ExceptionCheck(env)) {  // 检查JNI调用是否有引发异常
        (*env)->ExceptionDescribe(env);
        (*env)->ExceptionClear(env);        // 清除引发的异常，在Java层不会打印异常的堆栈信息
        (*env)->ThrowNew(env, (*env)->FindClass(env, "java/lang/Exception"), "JNI抛出的异常！");
        //return;
    }
}

//java中静态方法过来这边是用jclass类型的参数，其他的是用jobject类型
JNIEXPORT jstring JNICALL Java_com_function_jni_JniUtils_getStringFromC
        (JNIEnv *env, jclass jc) {
    return (*env)->NewStringUTF(env, "这里是来自c的string");
}

/**
 * 获取并改变java中的变量值
 */
JNIEXPORT void JNICALL Java_com_function_jni_JniUtils_setVar
        (JNIEnv *env, jclass jc) {
    //获取java类对应的jclass对象
    jclass jclass1 = (*env)->FindClass(env, classPathName);
    jfieldID jfieldID1 = (*env)->GetStaticFieldID(env, jclass1, "var", "I");
    __android_log_print(ANDROID_LOG_INFO, "logvar_jni", "获取到的java变量是%d",
                        (*env)->GetStaticIntField(env, jc, jfieldID1));
    (*env)->SetStaticIntField(env, jc, jfieldID1, 3);
}

/**
 * 回调java静态方法
 */
JNIEXPORT jstring JNICALL Java_com_function_jni_JniUtils_callback_lmyLog
        (JNIEnv *env, jclass jc) {
    //获取java类对应的jclass对象
    jclass jclass1 = (*env)->FindClass(env, classPathName);
    if (jclass1 == NULL) {
        //log输出字符串
        __android_log_print(ANDROID_LOG_INFO, "jnilog", "找不到类%s", classPathName);
    }
    else {
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
        (JNIEnv *env, jclass jc, jstring j_str) {
    /**
    //普通方法
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
    **/

    //大字符串用此方法
//    const jchar* c_str= NULL;
//    char buff[128] = "hello ";
//    char* pBuff = buff + 6;
    /*
     * 在GetStringCritical/RealeaseStringCritical之间是一个关键区。
     * 在这关键区之中,绝对不能呼叫JNI的其他函数和会造成当前线程中断或是会让当前线程等待的任何本地代码，
     * 否则将造成关键区代码执行区间垃圾回收器停止运作，任何触发垃圾回收器的线程也会暂停。
     * 其他触发垃圾回收器的线程不能前进直到当前线程结束而激活垃圾回收器。
     */
//    c_str = (*env)->GetStringCritical(env,j_str,NULL);   // 返回源字符串指针的可能性
//    if (c_str == NULL)  // 验证是否因为字符串拷贝内存溢出而返回NULL
//    {
//        return NULL;
//    }
//    while(*c_str)
//    {
//        *pBuff++ = *c_str++;
//    }
//    (*env)->ReleaseStringCritical(env,j_str,c_str);
//    return (*env)->NewStringUTF(env,buff);

    //小字符串用此方法
    jsize len = (*env)->GetStringLength(env, j_str);  // 获取unicode字符串的长度
    printf("str_len:%d\n", len);
    char buff[128] = "hello ";
    char *pBuff = buff + 6;
    // 将JVM中的字符串以utf-8编码拷入C缓冲区,该函数内部不会分配内存空间
    (*env)->GetStringUTFRegion(env, j_str, 0, len, pBuff);
    return (*env)->NewStringUTF(env, buff);
}

/**
 * 计算数组的和
 */
JNIEXPORT jint JNICALL Java_com_function_jni_JniUtils_sumArray
        (JNIEnv *env, jclass jc, jintArray j_array) {
    /**
    //少量，固定大小的数组用此方法
    jint i, sum = 0;
    jint *c_array;
    jint arr_len;
    //1. 获取数组长度
    arr_len = (*env)->GetArrayLength(env,j_array);
    //2. 根据数组长度和数组元素的数据类型申请存放java数组元素的缓冲区
    c_array = (jint*)malloc(sizeof(jint) * arr_len);
    //3. 初始化缓冲区
    memset(c_array,0,sizeof(jint)*arr_len);
    __android_log_print(ANDROID_LOG_INFO, "jnilog", "arr_len = %d ", arr_len);
    //4. 拷贝Java数组中的所有元素到缓冲区中
    (*env)->GetIntArrayRegion(env,j_array,0,arr_len,c_array);
    for (i = 0; i < arr_len; i++) {
        sum += c_array[i];  //5. 累加数组元素的和
    }
    free(c_array);  //6. 释放存储数组元素的缓冲区
    return sum;
    **/

    /**
    //安全的方法
    jint i, sum = 0;
    jint *c_array;
    jint arr_len;
    // 可能数组中的元素在内存中是不连续的，JVM可能会复制所有原始数据到缓冲区，然后返回这个缓冲区的指针
    c_array = (*env)->GetIntArrayElements(env,j_array,NULL);
    if (c_array == NULL) {
        return 0;   // JVM复制原始数据到缓冲区失败
    }
    arr_len = (*env)->GetArrayLength(env,j_array);
    printf("arr_len = %d\n", arr_len);
    for (i = 0; i < arr_len; i++) {
        sum += c_array[i];
    }
    (*env)->ReleaseIntArrayElements(env,j_array, c_array, 0); // 释放可能复制的缓冲区
    return sum;
     **/

    /**
     * 如果不想预先分配C缓冲区，并且原始数组长度也不确定，
     * 而本地代码又不想在获取数组元素指针时被阻塞的话
     **/
    jint i, sum = 0;
    jint *c_array;
    jint arr_len;
    jboolean isCopy;
    c_array = (*env)->GetPrimitiveArrayCritical(env, j_array, &isCopy);
    printf("isCopy: %d \n", isCopy);
    if (c_array == NULL) {
        return 0;
    }
    arr_len = (*env)->GetArrayLength(env, j_array);
    printf("arr_len = %d\n", arr_len);
    for (i = 0; i < arr_len; i++) {
        sum += c_array[i];
    }
    (*env)->ReleasePrimitiveArrayCritical(env, j_array, c_array, 0);
    return sum;
}

/**
 * 返回一个二维数组
 */
JNIEXPORT jstring JNICALL Java_com_function_jni_JniUtils_initInt2DArray
        (JNIEnv *env, jclass jc, jint size) {
    jobjectArray result;
    jclass clsIntArray;
    jint i, j;
    // 1.获得一个int型二维数组类的引用
    clsIntArray = (*env)->FindClass(env, "[I");
    if (clsIntArray == NULL) {
        return NULL;
    }
    // 2.创建一个数组对象（里面每个元素用clsIntArray表示）
    result = (*env)->NewObjectArray(env, size, clsIntArray, NULL);
    if (result == NULL) {
        return NULL;
    }

    // 3.为数组元素赋值
    for (i = 0; i < size; ++i) {
        jint buff[256];
        jintArray intArr = (*env)->NewIntArray(env, size);
        if (intArr == NULL) {
            return NULL;
        }
        for (j = 0; j < size; j++) {
            buff[j] = i + j;
        }
        (*env)->SetIntArrayRegion(env, intArr, 0, size, buff);
        (*env)->SetObjectArrayElement(env, result, i, intArr);
        (*env)->DeleteLocalRef(env, intArr);
    }

    (*env)->DeleteLocalRef(env, clsIntArray);

    return result;
}

/**
 * 调用java实例方法
 */
JNIEXPORT jstring JNICALL Java_com_function_jni_JniUtils_callJavaInstanceMethod
        (JNIEnv *env, jclass jc) {
    jclass clazz = NULL;
    jobject jobj = NULL;
    jmethodID mid_construct = NULL;
    jmethodID mid_instance = NULL;
    jstring str_arg = NULL;
    // 1、从classpath路径下搜索ClassMethod这个类，并返回该类的Class对象
    clazz = (*env)->FindClass(env, classPathName);
    if (clazz == NULL) {
        printf("找不到'com.study.jnilearn.ClassMethod'这个类");
        return;
    }

    // 2、获取类的默认构造方法ID
    mid_construct = (*env)->GetMethodID(env, clazz, "<init>", "()V");
    if (mid_construct == NULL) {
        printf("找不到默认的构造方法");
        return;
    }

    // 3、查找实例方法的ID
    mid_instance = (*env)->GetMethodID(env, clazz, "callInstanceMethod", "(Ljava/lang/String;I)V");
    if (mid_instance == NULL) {

        return;
    }

    // 4、创建该类的实例
    jobj = (*env)->NewObject(env, clazz, mid_construct);
    if (jobj == NULL) {
        printf("在com.study.jnilearn.ClassMethod类中找不到callInstanceMethod方法");
        return;
    }

    // 5、调用对象的实例方法
    str_arg = (*env)->NewStringUTF(env, "我是实例方法");
    (*env)->CallVoidMethod(env, jobj, mid_instance, str_arg, 200);//最后两个是方法的两个参数

    // 删除局部引用
    (*env)->DeleteLocalRef(env, clazz);
    (*env)->DeleteLocalRef(env, jobj);
    (*env)->DeleteLocalRef(env, str_arg);
}

/**
 * 访问java实例变量
 */
JNIEXPORT jstring JNICALL Java_com_function_jni_JniUtils_accessInstanceField
        (JNIEnv *env, jclass jc, jobject obj) {
    jclass clazz;
    jfieldID fid;
    jstring j_str;
    jstring j_newStr;
    const char *c_str = NULL;

    // 1.获取Jniutils类的Class引用
    clazz = (*env)->GetObjectClass(env, obj);//也可以用findclass
    if (clazz == NULL) {
        return;
    }

    // 2. 获取Jniutils类实例变量str的属性ID
    fid = (*env)->GetFieldID(env, clazz, "str", "Ljava/lang/String;");
    if (fid == NULL) {
        return;
    }

    // 3. 获取实例变量str的值
    j_str = (jstring) (*env)->GetObjectField(env, obj, fid);

    // 4. 将unicode编码的java字符串转换成C风格字符串
    c_str = (*env)->GetStringUTFChars(env, j_str, NULL);
    if (c_str == NULL) {
        return;
    }
    __android_log_print(ANDROID_LOG_INFO, "jnilog", "In C--->ClassField.str = %s\n", c_str);
    (*env)->ReleaseStringUTFChars(env, j_str, c_str);

    // 5. 修改实例变量str的值
    j_newStr = (*env)->NewStringUTF(env, "This is C String");
    if (j_newStr == NULL) {
        return;
    }

    (*env)->SetObjectField(env, obj, fid, j_newStr);

    // 6.删除局部引用
    (*env)->DeleteLocalRef(env, clazz);
    (*env)->DeleteLocalRef(env, j_str);
    (*env)->DeleteLocalRef(env, j_newStr);
}

/**
 * 内存表溢出demo
 */
JNIEXPORT jobjectArray JNICALL Java_com_function_jni_JniUtils_getStrings
        (JNIEnv *env, jclass jc, jint count, jstring sample) {
    jobjectArray str_array = NULL;
    jclass cls_string = NULL;
    jmethodID mid_string_init;
    jobject obj_str = NULL;
    const char *c_str_sample = NULL;
    char buff[256];
    int i;

    // 保证至少可以创建3个局部引用（str_array，cls_string，obj_str）
    if ((*env)->EnsureLocalCapacity(env, 3) != JNI_OK) {
        return NULL;
    }

    c_str_sample = (*env)->GetStringUTFChars(env, sample, NULL);
    if (c_str_sample == NULL) {
        return NULL;
    }

    cls_string = (*env)->FindClass(env, "java/lang/String");
    if (cls_string == NULL) {
        return NULL;
    }

    // 获取String的构造方法
    mid_string_init = (*env)->GetMethodID(env, cls_string, "<init>", "()V");
    if (mid_string_init == NULL) {
        (*env)->DeleteLocalRef(env, cls_string);
        return NULL;
    }
    obj_str = (*env)->NewObject(env, cls_string, mid_string_init);
    if (obj_str == NULL) {
        (*env)->DeleteLocalRef(env, cls_string);
        return NULL;
    }

    // 创建一个字符串数组
    str_array = (*env)->NewObjectArray(env, count, cls_string, obj_str);
    if (str_array == NULL) {
        (*env)->DeleteLocalRef(env, cls_string);
        (*env)->DeleteLocalRef(env, obj_str);
        return NULL;
    }

    // 给数组中每个元素赋值
    for (i = 0; i < count; ++i) {
        memset(buff, 0, sizeof(buff));   // 初始一下缓冲区
        sprintf(buff, c_str_sample, i);
        jstring newStr = (*env)->NewStringUTF(env, buff);
        (*env)->SetObjectArrayElement(env, str_array, i, newStr);
        (*env)->DeleteLocalRef(env, newStr);   // Warning: 这里如果不手动释放局部引用，很有可能造成局部引用表溢出
    }

    // 释放模板字符串所占的内存
    (*env)->ReleaseStringUTFChars(env, sample, c_str_sample);

    // 释放局部引用所占用的资源
    (*env)->DeleteLocalRef(env, cls_string);
    (*env)->DeleteLocalRef(env, obj_str);

    return str_array;
}

/********************************动态注册************************************/
//Java和JNI函数的绑定表
static JNINativeMethod method_table[] = {//绑定
        {"callback_myLog",         "()Ljava/lang/String;",
                                             (void *) Java_com_function_jni_JniUtils_callback_lmyLog},
        {"sayHello",               "(Ljava/lang/String;)Ljava/lang/String;",
                                             (void *) Java_com_function_jni_JniUtils_sayHello},
        {"sumArray",               "([I)I",  (void *) Java_com_function_jni_JniUtils_sumArray},
        {"initInt2DArray",         "(I)[[I", (void *) Java_com_function_jni_JniUtils_initInt2DArray},
        {"callJavaInstanceMethod", "()V",
                                             (void *) Java_com_function_jni_JniUtils_callJavaInstanceMethod},
        {"accessInstanceField",    "(Lcom/function/jni/JniUtils;)V",
                                             (void *) Java_com_function_jni_JniUtils_accessInstanceField},
        {"getStrings",             "(ILjava/lang/String;)[Ljava/lang/String;",
                                             (void *) Java_com_function_jni_JniUtils_getStrings},
};

//注册native方法到java中
static int registerNativeMethods(JNIEnv *env, const char *className,
                                 JNINativeMethod *gMethods, int numMethods) {
    jclass clazz;
    clazz = (*env)->FindClass(env, className);
    if (clazz == NULL) {
        return JNI_FALSE;
    }
    //动态注册本地方法
    if ((*env)->RegisterNatives(env, clazz, gMethods, numMethods) < 0) {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}


int register_ndk_load(JNIEnv *env) {
    // 调用注册方法
    return registerNativeMethods(env, classPathName,
                                 method_table,
                                 sizeof(method_table) / sizeof(method_table[0]));
}

static jclass g_cls_MainActivity = NULL;

/**
 * onload方法
 */
JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    jint result = -1;
    //获取JNIEnv对象
    if ((*vm)->GetEnv(vm, (void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return result;
    }

    // 查找要加载的本地方法Class引用
    jclass cls = (*env)->FindClass(env, classPathName);
    if (cls == NULL) {
        return JNI_ERR;
    }
    // 将class的引用缓存到全局变量中
    g_cls_MainActivity = (*env)->NewWeakGlobalRef(env, cls);

    (*env)->DeleteLocalRef(env, cls);   // 手动删除局部引用是个好习惯

    //动态注册
    register_ndk_load(env);

    // 返回jni的版本
    return JNI_VERSION_1_4;
}

/**
 * 卸载so文件
 */
JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *reserved) {
    LOG_I("JNI_OnUnload method call begin");
    JNIEnv *env = NULL;
    if ((*vm)->GetEnv(vm, (void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return;
    }
    (*env)->UnregisterNatives(env, g_cls_MainActivity); // so被卸载的时候解除注册
    (*env)->DeleteWeakGlobalRef(env, g_cls_MainActivity);
}


