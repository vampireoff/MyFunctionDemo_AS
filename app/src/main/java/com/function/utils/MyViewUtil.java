package com.function.utils;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.function.activity.MainActivity;
import com.function.activity.R;
import com.function.views.MyAlertDialog;
import com.function.views.MyAlertDialog.AlertCallback;
import com.function.views.MyDateDialog;
import com.function.views.MyDateDialog.AlertDateCallback;
import com.function.views.MyFewListDialog1;
import com.function.views.MyFewListDialog1.AlertListCallback1;
import com.function.views.MyFewListDialog2;
import com.function.views.MyFewListDialog2.AlertListCallback2;
import com.function.views.MyManyListDialog;
import com.function.views.MyManyListDialog.AlertListCallback3;
import com.function.views.MyMultiDialog;
import com.function.views.MyMultiDialog.AlertMultiCallback;
import com.function.views.MyTimeSelectDialog;
import com.function.views.MyTimeSelectDialog.AlertTimeCallback;

/**
 * ½çÃæÊÓÍ¼¹¤¾ß
 * @author Administrator
 *
 */
public class MyViewUtil {
	
	/**
	 * ÏÔÊ¾notify
	 * @param context
	 */
	public static void showNotify(Context context){
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context); 
        
        mBuilder.setContentTitle("SMSservice runing...")//ÉèÖÃÍ¨ÖªÀ¸±êÌâ  
        .setTicker("runing") //Í¨ÖªÊ×´Î³öÏÖÔÚÍ¨ÖªÀ¸£¬´øÉÏÉý¶¯»­Ð§¹ûµÄ  
        .setContentIntent(PendingIntent.getActivity(
        		context, 110, new Intent(context, MainActivity.class), 1))
        .setWhen(System.currentTimeMillis())//Í¨Öª²úÉúµÄÊ±¼ä£¬»áÔÚÍ¨ÖªÐÅÏ¢ÀïÏÔÊ¾£¬Ò»°ãÊÇÏµÍ³»ñÈ¡µ½µÄÊ±¼ä  
        .setPriority(Notification.PRIORITY_DEFAULT) //ÉèÖÃ¸ÃÍ¨ÖªÓÅÏÈ¼¶  
        .setAutoCancel(false)//ÉèÖÃÕâ¸ö±êÖ¾µ±ÓÃ»§µ¥»÷Ãæ°å¾Í¿ÉÒÔÈÃÍ¨Öª½«×Ô¶¯È¡Ïû    
        .setOngoing(true)//ture£¬ÉèÖÃËûÎªÒ»¸öÕýÔÚ½øÐÐµÄÍ¨Öª¡£ËûÃÇÍ¨³£ÊÇÓÃÀ´±íÊ¾Ò»¸öºóÌ¨ÈÎÎñ,ÓÃ»§»ý¼«²ÎÓë(Èç²¥·ÅÒôÀÖ)»òÒÔÄ³ÖÖ·½Ê½ÕýÔÚµÈ´ý,Òò´ËÕ¼ÓÃÉè±¸(ÈçÒ»¸öÎÄ¼þÏÂÔØ,Í¬²½²Ù×÷,Ö÷¶¯ÍøÂçÁ¬½Ó)  
//        .setDefaults(Notification.DEFAULT_SOUND)//ÏòÍ¨ÖªÌí¼ÓÉùÒô¡¢ÉÁµÆºÍÕñ¶¯Ð§¹ûµÄ×î¼òµ¥¡¢×îÒ»ÖÂµÄ·½Ê½ÊÇÊ¹ÓÃµ±Ç°µÄÓÃ»§Ä¬ÈÏÉèÖÃ£¬Ê¹ÓÃdefaultsÊôÐÔ£¬¿ÉÒÔ×éºÏ  
        //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND Ìí¼ÓÉùÒô // requires VIBRATE permission  
        .setSmallIcon(R.drawable.ic_launcher);//ÉèÖÃÍ¨ÖªÐ¡ICON  
        
        notificationManager.notify(110, mBuilder.build());
	}
	
	/**
	 * ÏÔÊ¾ÌáÊ¾¶Ô»°¿ò
	 * @param context
	 * @param callback
	 * @param resid
	 */
	public static void showAlertDialog(Activity context, AlertCallback callback, int resid){
		new MyAlertDialog(context, callback).showMyAlertDialog(resid);
	}
	
	/**
	 * ÏÔÊ¾¶àÑ¡¶Ô»°¿ò
	 * @param context
	 * @param callback
	 * @param arrs
	 * @param boos
	 */
	public static void showMultiDialog(Activity context, AlertMultiCallback callback, String[] arrs, boolean[] boos){
		new MyMultiDialog(context, arrs, boos, callback).showMultiChoiceDialog();
	}
	
	/**
	 * ÏÔÊ¾Ê±¼äÑ¡Ôñ¶Ô»°¿ò
	 * @param context
	 * @param callback
	 */
	public static void showTimeDialog(Activity context, AlertTimeCallback callback){
		new MyTimeSelectDialog(context, callback).showTimeDialog();
	}
	
	/**
	 * ÏÔÊ¾´óÁ¿Êý¾ÝµÄÁÐ±í¶Ô»°¿ò
	 * @param context
	 * @param callback
	 * @param stringid
	 * @param list
	 */
	public static void showManyListDialog(Activity context, AlertListCallback3 callback, int stringid, List<Map<String, String>> list){
		new MyManyListDialog(context, callback, list, stringid).showMyListDialog();
	}
	
	/**
	 * ÏÔÊ¾ÉÙÁ¿Êý¾ÝµÄÁÐ±í¶Ô»°¿òList<<String>>
	 * @param context
	 * @param callback
	 * @param list
	 * @param stringid (±êÌâ)
	 */
	public static void showFewListDialog1(Activity context, AlertListCallback1 callback, List<String> list, int stringid){
		new MyFewListDialog1(context, callback, list, stringid).showMyListDialog();
	}
	
	/**
	 * ÏÔÊ¾ÉÙÁ¿Êý¾ÝµÄÁÐ±í¶Ô»°¿òList<Map<String, String>>
	 * @param context
	 * @param callback
	 * @param list
	 * @param stringid (±êÌâ)
	 */
	public static void showFewListDialog2(Activity context, AlertListCallback2 callback, List<Map<String, String>> list, int stringid){
		new MyFewListDialog2(context, callback, list, stringid).showMyListDialog();
	}
	
	/**
	 * ÈÕÆÚÑ¡Ôñ¶Ô»°¿ò
	 * @param context
	 * @param callback
	 * @param flag (false£ºµ¥Ñ¡£¬true£º¶àÑ¡)
	 * @param dList (µ¥Ñ¡´«null)
	 */
	public static void showDateDialog(Activity context, AlertDateCallback callback, boolean flag, List<String> dList){
		new MyDateDialog(context, callback).showMyDateDialog(flag, dList);
	}
	
	/**
	 *  ÐÞ¸ÄÕû¸ö½çÃæËùÓÐ¿Ø¼þµÄ×ÖÌå
	 * @param root
	 * @param path
	 * @param act
	 */
	public static void changeFonts(ViewGroup root,String path, Activity act) {  
		//pathÊÇ×ÖÌåÂ·¾¶
		Typeface tf = Typeface.createFromAsset(act.getAssets(),path);  
		for (int i = 0; i < root.getChildCount(); i++) {  
			View v = root.getChildAt(i); 
			if (v instanceof TextView) {  
				((TextView) v).setTypeface(tf);  
			} else if (v instanceof Button) {  
				((Button) v).setTypeface(tf);  
			} else if (v instanceof EditText) {  
				((EditText) v).setTypeface(tf);  
			} else if (v instanceof ViewGroup) {  
				changeFonts((ViewGroup) v, path,act);  
			} 
		}  
	}
	
	/**
	 *  ÐÞ¸ÄÕû¸ö½çÃæËùÓÐ¿Ø¼þµÄ×ÖÌå´óÐ¡
	 */
	public static void changeTextSize(ViewGroup root,int size, Activity act) {  
		for (int i = 0; i < root.getChildCount(); i++) {  
			View v = root.getChildAt(i);  
			if (v instanceof TextView) {  
				((TextView) v).setTextSize(size);
			} else if (v instanceof Button) {  
				((Button) v).setTextSize(size);
			} else if (v instanceof EditText) {  
				((EditText) v).setTextSize(size);  
			} else if (v instanceof ViewGroup) {  
				changeTextSize((ViewGroup) v,size,act);  
			}  
		}  
	}
	
	/**
	 *  ²»¸Ä±ä¿Ø¼þÎ»ÖÃ£¬ÐÞ¸Ä¿Ø¼þ´óÐ¡
	 * @param v
	 * @param W
	 * @param H
	 */
	public static void changeWH(View v,int W,int H)
	{
		LayoutParams params = (LayoutParams)v.getLayoutParams();
		params.width = W;
		params.height = H;
		v.setLayoutParams(params);
	}
	
	/**
	 *  ÐÞ¸Ä¿Ø¼þµÄ¸ß
	 * @param v
	 * @param H
	 */
	public static void changeH(View v,int H)
	{
		LayoutParams params = (LayoutParams)v.getLayoutParams();
		params.height = H;
		v.setLayoutParams(params);
	}
	
	 /**
     * »ñÈ¡¿Ø¼þµÄ¸ß¶È£¬Èç¹û»ñÈ¡µÄ¸ß¶ÈÎª0£¬ÔòÖØÐÂ¼ÆËã³ß´çºóÔÙ·µ»Ø¸ß¶È
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredHeight(View view) {
        calcViewMeasure(view);
        return view.getMeasuredHeight();
    }

    /**
     * »ñÈ¡¿Ø¼þµÄ¿í¶È£¬Èç¹û»ñÈ¡µÄ¿í¶ÈÎª0£¬ÔòÖØÐÂ¼ÆËã³ß´çºóÔÙ·µ»Ø¿í¶È
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredWidth(View view) {
        calcViewMeasure(view);
        return view.getMeasuredWidth();
    }

    /**
     * ²âÁ¿¿Ø¼þµÄ³ß´ç
     *
     * @param view
     */
    public static void calcViewMeasure(View view) {

        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
    }
}
