package com.function.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * ImageloaderÍ¼Æ¬ÏÔÊ¾¹¤¾ß
 * @author Administrator
 *
 */
public class MyImgloadUtil {

	private ImageLoader loader;
	private DisplayImageOptions options;
	
	public MyImgloadUtil(Context context, int img){
		loader = ImageLoader.getInstance();
		loader.init(ImageLoaderConfiguration.createDefault(context));
		
		//Í¼Æ¬²ÎÊýÉèÖÃ
		options = new DisplayImageOptions.Builder()
		.showStubImage(img)
		.showImageForEmptyUri(img)
		.showImageOnFail(img).cacheOnDisc()
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new FadeInBitmapDisplayer(300)).build();
	}
	
	/**
	 * ÇåÀí»º´æ
	 */
	public void clearimg(){
		loader.clearDiscCache();
		loader.clearMemoryCache();
	}
	
	/**
	 * ÏÔÊ¾Í¼Æ¬
	 * @param url
	 * @param view
	 */
	public void show(String url, ImageView view){
		loader.displayImage(url, view, options);
	}
	
}
