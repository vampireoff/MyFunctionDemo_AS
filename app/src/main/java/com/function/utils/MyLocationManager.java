package com.function.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.security.Permission;

/**
 * 定位管理工具
 * @author Administrator
 *
 */
public class MyLocationManager {
	private final String TAG = "FzLocationManager";
	private Context mContext;
	private LocationManager gpsLocationManager;
	private LocationManager networkLocationManager;
	private static final int MINTIME = 2000;
	private static final int MININSTANCE = 2;
	private Location lastLocation = null;
	private LocationCallBack mCallback;
	
	
	public MyLocationManager(Context c, LocationCallBack callback) {
		mContext = c.getApplicationContext();
		mCallback = callback;

		try {

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
				if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
						PackageManager.PERMISSION_GRANTED){
					// Gps 定位
					gpsLocationManager = (LocationManager) mContext
							.getSystemService(Context.LOCATION_SERVICE);
					gpsLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
							MINTIME, MININSTANCE, locationListener);
					// 基站定位
					networkLocationManager = (LocationManager) mContext
							.getSystemService(Context.LOCATION_SERVICE);
					networkLocationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, MINTIME, MININSTANCE,
							locationListener);
				}else {
					MyUtils.showToast2(mContext, "请允许请求的权限");
				}

			}else {
				// Gps 定位
				gpsLocationManager = (LocationManager) mContext
						.getSystemService(Context.LOCATION_SERVICE);
				gpsLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
						MINTIME, MININSTANCE, locationListener);
				// 基站定位
				networkLocationManager = (LocationManager) mContext
						.getSystemService(Context.LOCATION_SERVICE);
				networkLocationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, MINTIME, MININSTANCE,
						locationListener);
			}

		}catch (SecurityException e)
		{
			Log.i("location", e.getMessage());
			MyUtils.showToast2(mContext, e.getMessage());
		}

	}


	private void updateLocation(Location location) {
		lastLocation = location;
		mCallback.onCurrentLocation(location);
	}

	
	private LocationListener locationListener = new LocationListener() {
		
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.d(TAG, "onStatusChanged");
		}

		
		public void onProviderEnabled(String provider) {
			MyUtils.showToast2(mContext, "onProEnable");
			Log.d(TAG, "onProviderEnabled");
		}

		
		public void onProviderDisabled(String provider) {
			MyUtils.showToast2(mContext, "onProDisabled");
			Log.d(TAG, "onProviderDisabled");
		}

		
		public void onLocationChanged(Location location) {
			MyUtils.showToast2(mContext, "onLocationChanged");
			Log.d(TAG, "onLocationChanged");
			updateLocation(location);
		}
	};

	public Location getMyLocation() {
		return lastLocation;
	}
	
	
	public interface LocationCallBack{
		/**
		 * 当前位置
		 * @param location 
		 */
		void onCurrentLocation(Location location);
	}
	
	
	public void destoryLocationManager(){
		Log.d(TAG, "destoryLocationManager");

		try{
			if (Build.VERSION.SDK_INT >= 23) {
				if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
						PackageManager.PERMISSION_GRANTED) {
					gpsLocationManager.removeUpdates(locationListener);
					networkLocationManager.removeUpdates(locationListener);
				}
			}else {
				gpsLocationManager.removeUpdates(locationListener);
				networkLocationManager.removeUpdates(locationListener);
			}
		}catch (SecurityException e)
		{
			Log.i("location", e.getMessage());
		}
		mCallback = null;
		mContext = null;
	}
}
