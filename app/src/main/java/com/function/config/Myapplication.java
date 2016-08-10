package com.function.config;

import org.xutils.x;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.function.utils.MyBitmapCache;
import com.squareup.leakcanary.LeakCanary;

import android.app.Application;

/**
 * È«¾ÖÓ¦ÓÃ³ÌÐòÀà
 * @author Administrator
 *
 */
public class Myapplication extends Application {

	public static Myapplication myapplication = null;
	public ImageLoader imageLoader = null;
	
	/**
	 * »ñÈ¡È«¾Öimageloader
	 * @return
	 */
	public ImageLoader getImageloader(RequestQueue requestQueue){
		if (imageLoader == null) {
			synchronized (Myapplication.class) {
				if (imageLoader == null) {
					imageLoader = new ImageLoader(requestQueue, new MyBitmapCache());
				}
			}
		}
		return imageLoader;
	}
	
	/**
	 * µ¥ÀýÄ£Ê½Ë«ÖØËø¶¨
	 * @return
	 */
	public static Myapplication getInstance(){
		if (myapplication == null) {
			synchronized (Myapplication.class) {
				if (myapplication == null) {
					myapplication = new Myapplication();
				}
			}
		}
		return myapplication;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		myapplication = this;
		
		// ³õÊ¼»¯
		x.Ext.init(this);
		// ÉèÖÃÊÇ·ñÊä³ödebug
		x.Ext.setDebug(true);

		LeakCanary.install(this);
	}
}
