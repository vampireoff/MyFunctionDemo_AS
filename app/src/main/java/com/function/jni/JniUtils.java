package com.function.jni;

import android.util.Log;

/**
 * Created by lwd on 2016/8/8.
 */
public class JniUtils {
    public static int var = 0;

    static {
        System.loadLibrary("JniTest");
    }

    public static void myLog() {
        Log.i("javalog", "jni调用java函数");
    }

    public static native String getStringFromC();
    public static native String getStringFromC2();
    public static native void setVar();
}
