package com.function.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.function.activity.R;
import com.function.config.Myapplication;

/**
 * ¶¯»­¹¤¾ß
 * @author Administrator
 *
 */
public class MyAnimationUtil {

	private static Animation hyperspaceJumpAnimation;
	
	/**
	 * ÄæÊ±ÕëÐý×ª
	 * @param view
	 */
	public static void startRotate_n(View view){
		//Ðý×ª¶¯»­
		hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				Myapplication.getInstance().getApplicationContext(), R.anim.rotate2);
		hyperspaceJumpAnimation.setInterpolator(new LinearInterpolator());
		//Ê¹ÓÃViewÏÔÊ¾¶¯»­
		view.startAnimation(hyperspaceJumpAnimation);
	}
	
	/**
	 * Ë³Ê±ÕëÐý×ª
	 * @param view
	 */
	public static void startRotate_s(View view){
		//Ðý×ª¶¯»­
		hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				Myapplication.getInstance().getApplicationContext(), R.anim.rotate);
		hyperspaceJumpAnimation.setInterpolator(new LinearInterpolator());
		//Ê¹ÓÃViewÏÔÊ¾¶¯»­
		view.startAnimation(hyperspaceJumpAnimation);
	}
	
}
