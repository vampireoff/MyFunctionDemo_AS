package com.function.jni;

/**
 * Created by lwd on 2016/8/8.
 */
public class JniUtils {
    static {
        System.loadLibrary("JniTest");
    }

    public static native String getStringFromC();
}
