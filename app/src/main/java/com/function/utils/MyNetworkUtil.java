package com.function.utils;

import com.function.config.Myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ÍøÂç¹¤¾ßÀà
 * @author zwb
 */
public class MyNetworkUtil {
	
	/**
	 * ÅÐ¶ÏÊÇ·ñÓÐÍøÂçÁ¬½Ó
	 * @param activity
	 * @return true/false
	 */
	public static boolean isNetWorkConnected() {
		ConnectivityManager manager = (ConnectivityManager)Myapplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isAvailable();
		}
		return false;
	}
	
	/**********************
	 *ÊÇ·ñÎªwifiÍøÂç
	 * @param icontext
	 * @return
	 */
	public static boolean isWifiActive(){
		Context context = Myapplication.getInstance().getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info;
		if (connectivity != null) {
			info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getTypeName().equals("WIFI") &&
							info[i].isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	/**********************
	 *ÊÇ·ñÎªÊÖ»úÍøÂç
	 * @param icontext
	 * @return
	 */
	public static boolean isMobileActive(){
		Context context = Myapplication.getInstance().getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info;
		if (connectivity != null) {
			info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getTypeName().equals("MOBILE") &&
							info[i].isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * ´ò¿ªÍøÂçÉèÖÃ½çÃæ
	 */
	public static void openSetting(Activity activity)
	{
		Intent intent = new Intent("/");
		ComponentName cm = new ComponentName("com.android.settings",
				"com.android.settings.WirelessSettings");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);
	}
}
