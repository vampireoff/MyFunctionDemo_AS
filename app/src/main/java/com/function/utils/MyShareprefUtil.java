package com.function.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.function.config.Myapplication;

/**
 * SharedPreferencesÊý¾Ý´¦Àí¹¤¾ß
 * @author Administrator
 *
 */
public class MyShareprefUtil {

	/**
	 * »ñÈ¡editor¶ÔÏó
	 * @param name
	 * @param key
	 * @param value
	 */
	public static SharedPreferences.Editor getEditor(String name){
		SharedPreferences shared = Myapplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
		return shared.edit();
	}
	/**
	 * ±£´æÊý¾Ýµ½±¾µØ
	 * @param name
	 * @param key
	 * @param value
	 */
	public static void saveShared(String name, String key, String value){
		SharedPreferences shared = Myapplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = shared.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * »ñÈ¡sharedpreferences¶ÔÓ¦µÄÖµ
	 * @param mcontext
	 * @param name
	 * @param key
	 * @return
	 */
	public static String getSharedstring(String name, String key){
		SharedPreferences shared = Myapplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
		return shared.getString(key, "");
	}
	
	/**
	 * »ñÈ¡sharedpreferences¶ÔÏó
	 * @param mcontext
	 * @param name
	 * @param key
	 * @return
	 */
	public static SharedPreferences getShared(String name){
		return Myapplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
	}
}
