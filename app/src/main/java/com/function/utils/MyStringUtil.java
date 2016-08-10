package com.function.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ×Ö·û´®´¦Àí¹¤¾ß
 * @author Administrator
 *
 */
public class MyStringUtil {

	/**
	 * xml×ªÂë
	 * @param string
	 * @return
	 */
	public static String transferXML(String string){
		return string.replace("%2f", "/").replace("%3a", ":").replace("%3e", ">").
				replace("%22", "\"").replace("%3d", "=").replace("%3f", "?").
				replace("%3c", "<").replace("+", " ").replace("%2b", "+");
	}
	
	/**
	 * °ÑStream×ª»»³ÉString
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();	
	}	
	
	/**
	 * ÉÏ´«Êý¾ÝÊ±Ë«ÒýºÅ×ª»»
	 * @param string
	 * @return
	 */
	public static String UploadString(String string){
		return string.replace("\"", "&quot;");
	}
	
	/**
	 * ½âÎöÊý¾ÝÊ±Ë«ÒýºÅ×ª»»
	 * @param string
	 * @return
	 */
	public static String ParseString(String string){
		return string.replace("&quot;", "\"");
	}
}
