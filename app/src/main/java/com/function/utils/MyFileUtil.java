package com.function.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import android.net.Uri;

import com.function.config.Common;
import com.function.config.Myapplication;
/**
 * ÎÄ¼þ´¦Àí¹¤¾ßÀà
 * @author Administrator
 *
 */
public class MyFileUtil {
	
	/**
	 * Â·¾¶×ªUri
	 * @param url
	 * @return
	 */
	public static Uri urltouri(String url){
		return Uri.fromFile(new File(url));
	}
	
	/**
	 * ¸ù¾Ýurl»ñÈ¡ÎÄ¼þÃû
	 * @param tag
	 * @param msg
	 */
	public static String getUrlFilename(String url){
		String[] strings = url.split("/");
		return strings[strings.length - 1];
	}
	
	/**
	 * ´ÓtxtÎÄ¼þÀï¶ÁÊý¾Ý³öÀ´
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String readfromtxt(String url){
		File file = new File(url);
		
		if (!file.exists()) {
			return "";
		}
		
		byte[] b = null;
		
		try {
			FileInputStream inputStream = new FileInputStream(file);
			int len = inputStream.available();
			if (len == 0) return "";
			
			b = new byte[len];
			inputStream.read(b);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return EncodingUtils.getString(b, "UTF-8");
	}

	/**
	 * ±£´æÊý¾Ýµ½txtÎÄ¼þ
	 * @param url (Ä¿Â¼Ãû)
	 * @param name (ÎÄ¼þÃû)
	 * @param text
	 * @param append (true:¸½¼Ó£¬false:¸²¸Ç)
	 */
	public static void savetotext(String url, String name, String text, boolean append){
		createDirectory(url);
		
		File file = new File(url, name);
		if (file.exists() && !append) {
			file.delete();
		}
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(file, append);
			if (text != null && !text.equals("")) {
				byte[] b = text.getBytes();
				outputStream.write(b);
				outputStream.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ±£´ælogµ½txtÎÄ¼þÀï
	 * @param string
	 */
	public static void savelog(String text){
		createDirectory(Common.logurl);
		
		File file = new File(Common.logurl, "log.txt");
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(file, true);
			if (text != null && !text.equals("")) {
				byte[] b = text.getBytes();
				outputStream.write(b);
				outputStream.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ±£´ælogµ½txtÎÄ¼þÀï£¨´øÊ±¼ä£©
	 * @param string
	 */
	public static void savelogwithtime(String text){
		createDirectory(Common.logurl);
		
		File file = new File(Common.logurl, "log.txt");
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(file, true);
			if (text != null && !text.equals("")) {
				text += "\n" + MyDateUtil.getTime() + "\n\n";
				byte[] b = text.getBytes();
				outputStream.write(b);
				outputStream.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ´´½¨ÎÄ¼þÄ¿Â¼
	 * @param url
	 */
	public static void createDirectory (String url){
		File path = new File(url);
		if (!path.exists()) {
			path.mkdirs();
		}
	}
	
	
	/**
	 * »ñÈ¡Ó¦ÓÃÍâ»º´æÂ·¾¶
	 * 
	 * @return Â·¾¶µÄ×Ö·û´®
	 */
	public static String getExterCachePath() {
		return Myapplication.getInstance().getExternalCacheDir().getPath().toString();
	}
	
	/**
	 * »ñÈ¡Ó¦ÓÃÄÚ»º´æÂ·¾¶
	 * 
	 * @return Â·¾¶µÄ×Ö·û´®
	 */
	public static String getCachePath() {
		return Myapplication.getInstance().getCacheDir().getPath().toString();
	}
	
	/**
	 * ÅÐ¶ÏÎÄ¼þÊÇ·ñ´æÔÚ
	 * @param uri
	 * @return
	 */
	public static boolean isFileExist(String uri){
		try {
			File file = new File(uri);
			if (file.exists()) {
				return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return false;
	}
	
	 /**
     * É¾³ýÎÄ¼þ¼ÐÀïÃæµÄËùÓÐÎÄ¼þ
     * @param path String ÎÄ¼þ¼ÐÂ·¾¶ 
     */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			}
			else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
		}
	}
	
	
	/**
	 * É¾³ýÎÄ¼þ
	 * @param dirString
	 */
	public static void deleteFile(String dirString){
		File file = new File(dirString);
		if(file.exists() && file.isFile()){
			file.delete();
        }
	}

	/**
	 * É¾³ýÄ¿Â¼
	 * @param dirString
	 */
	public static void deleteDir(String dirString) {
		File dir = new File(dirString);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); 
			else if (file.isDirectory())
				deleteDir(file.getAbsolutePath()); 
		}
		dir.delete();
	}

}
