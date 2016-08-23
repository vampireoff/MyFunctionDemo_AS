package com.function.jni;

import android.util.Log;

/**
 * Created by lwd on 2016/8/8.
 */
public class JniUtils {
    public static int var = 0;
    private String str = "123";

    public String getStr(){
        return str;
    }

    static {
        System.loadLibrary("JniTest");
        initID();//在一开始的时候初始化本地代码中的ID，以后就可以直接调用，提升性能（类静态初始化缓存）
    }

    public static void myLog() {
        Log.i("javalog", "jni调用java函数");
    }

    private void callInstanceMethod(String str, int i) {
        System.out.format("ClassMethod::callInstanceMethod called!-->str=%s, " +
                "i=%d\n", str, i);
    }

    public static native void initID();
    public static native String getStringFromC();
    public static native String callback_myLog();
    public static native void setVar();
    public native static String sayHello(String text);
    public native static int sumArray(int[] arr);
    public native static int[][] initInt2DArray(int arr);
    public native static void callJavaInstanceMethod();
    public native static void accessInstanceField(JniUtils utils);
    public native static String[] getStrings(int m, String ss);
}
