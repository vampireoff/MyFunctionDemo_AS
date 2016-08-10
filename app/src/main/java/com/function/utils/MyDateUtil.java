package com.function.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

/**
 * ÈÕÆÚ¹¤¾ß
 * @author Administrator
 * 
 * HH£º24Ð¡Ê±ÖÆ£¬hh£º12Ð¡Ê±ÖÆ
 */
@SuppressLint("SimpleDateFormat")
public class MyDateUtil {

	/**
	 * string×ªDate
	 * @param string
	 * @return
	 */
	public static Date string2date(String string){
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return date;
	}
	
	/**
	 * Date×ªstring(Êä³ö¸ñÊ½£ºyyyy-MM-dd HH:mm:ss)
	 * @param string
	 * @return
	 */
	public static String date2string(Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	/**
	 * ÈÕÆÚ×Ö·û´®¸ñÊ½»¯ÎªÄêÔÂÈÕÐÇÆÚ(·µ»Ø¸ñÊ½£º2016-04-20 ÖÜÈý)
	 * @param date
	 * @return
	 */
	public static String time2ymde(String date){
		return new SimpleDateFormat("yyyy-MM-dd E").format(string2date(date));
	}
	
	/**
	 * ÈÕÆÚ×Ö·û´®¸ñÊ½»¯ÎªÐÇÆÚ(·µ»Ø¸ñÊ½£ºÖÜÈý)
	 * @param date
	 * @return
	 */
	public static String time2e(String date){
		return new SimpleDateFormat("E").format(string2date(date));
	}
	
	/**
	 * ÈÕÆÚ×Ö·û´®¸ñÊ½»¯ÎªÄêÔÂÈÕ(·µ»Ø¸ñÊ½£º2016-04-20)
	 * @param date
	 * @return
	 */
	public static String time2ymd(String date){
		return new SimpleDateFormat("yyyy-MM-dd").format(string2date(date));
	}
	
	/**
	 * ÈÕÆÚ×Ö·û´®¸ñÊ½»¯ÎªÄêÔÂ(·µ»Ø¸ñÊ½£º2016-04)
	 * @param date
	 * @return
	 */
	public static String time2ym(String date){
		return new SimpleDateFormat("yyyy-MM").format(string2date(date));
	}
	
	/**
	 * ÈÕÆÚ×Ö·û´®¸ñÊ½»¯ÎªÄê(·µ»Ø¸ñÊ½£º2016)
	 * @param date
	 * @return
	 */
	public static String time2y(String date){
		return new SimpleDateFormat("yyyy").format(string2date(date));
	}
	
	/**
	 * ÈÕÆÚ×Ö·û´®¸ñÊ½»¯ÎªÔÂ(·µ»Ø¸ñÊ½£º4)
	 * @param date
	 * @return
	 */
	public static String time2m(String date){
		return new SimpleDateFormat("M").format(string2date(date));
	}
	
	/**
	 * Ê±¼ä×Ö·û´®¸ñÊ½»¯ÎªÄêÔÂÈÕÊ±·ÖÃë(·µ»Ø¸ñÊ½£º2016-04-20 09:20:00)
	 * @param date
	 * @return
	 */
	public static String time2ymdhms(String date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(string2date(date));
	}
	
	/**
	 * Ê±¼ä×Ö·û´®¸ñÊ½»¯ÎªÈÕÆÚ(·µ»Ø¸ñÊ½£º20)
	 * @param date
	 * @return
	 */
	public static String time2d(String date){
		return new SimpleDateFormat("d").format(string2date(date));
	}
	
	/** 
	 * ½«Ê±¼ä×Ö·û´®×ªÎª´ú±í"¾àÏÖÔÚ¶à¾ÃÖ®Ç°"µÄ×Ö·û´® 
	 * @param timeStr   Ê±¼ä×Ö·û´®£¬¸ñÊ½(yyyy-MM-dd HH:mm:ss)
	 * @return 
	 */  
	public static String getStandardDate(String timeStr) {  
	  
		if (timeStr == null || timeStr.equals("")) {
			return "";
		}
	    StringBuffer sb = new StringBuffer();  
	    long t = getStringToDate(timeStr);
	    long time = System.currentTimeMillis() - t;  
	    long mill = (long) Math.ceil(time /1000);//ÃëÇ°  
	  
	    long minute = (long) Math.ceil(time/60/1000.0f);// ·ÖÖÓÇ°  
	  
	    long hour = (long) Math.ceil(time/60/60/1000.0f);// Ð¡Ê±  
	  
	    long day = (long) Math.ceil(time/24/60/60/1000.0f);// ÌìÇ°  
	  
	    if (day - 1 > 0) {  
	    	if ((day - 1) == 1) {
	    		sb.append("×òÌì");  
			}else if((day - 1) < 4){
				sb.append((day - 1) + "ÌìÇ°");  
			}else {
				sb.append(timeStr.split(" ")[0].replace("/", "-"));
			}
	    } else if (hour - 1 > 0) {  
	        if (hour >= 24) {  
	            sb.append("×òÌì");  
	        } else {  
	            sb.append((hour - 1) + "Ð¡Ê±Ç°");  
	        }  
	    } else if (minute - 1 > 0) {  
	        if (minute == 60) {  
	            sb.append("1" + "Ð¡Ê±Ç°");  
	        } else {  
	            sb.append((minute - 1) + "·ÖÖÓÇ°");  
	        }  
	    } else if (mill - 1 > 0) {  
	        if (mill == 60) {  
	            sb.append("1" + "·ÖÖÓÇ°");  
	        } else {  
	            sb.append("¸Õ¸Õ");  
	        }  
	    } else {  
	        sb.append("¸Õ¸Õ");  
	    }  
	    return sb.toString();  
	}  
	
	
	 /**
	  * ½«×Ö·û´®×ªÎªÊ±¼ä´Á
	  * */
	public static long getStringToDate(String time) {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try{
            date = sdf.parse(time.replace("/", "-"));
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
	
	/**
	 * »ñÈ¡µ±Ç°ÔÂµÚÒ»Ìì
	 * @return
	 */
	public static String getMonthFirst(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//ÉèÖÃÎª1ºÅ,µ±Ç°ÈÕÆÚ¼ÈÎª±¾ÔÂµÚÒ»Ìì 
		return formatter.format(c.getTime());
	}
	
	/**
	 * »ñÈ¡µ±Ç°ÔÂ×îºóÒ»Ìì
	 * @return
	 */
	public static String getMonthLast(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance(); 
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		return formatter.format(ca.getTime());
	}
	
	/**
	 * »ñÈ¡Ïà¶Ô½ñÌìµÄ¼Ó¼õÈÕÆÚ
	 * @return
	 */
	public static String getAddMinDate_today(int day){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return formatter.format(calendar.getTime());
	}
	
	/**
	 * »ñÈ¡Ïà¶ÔÄ³¸öÊ±¼äµÄ¼Ó¼õÐ¡Ê±
	 * @return
	 */
	public static String getAddMinHour(String time, int num){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //ÉèÖÃÊ±¼ä¸ñÊ½
		
		Calendar calendar = Calendar.getInstance(); //µÃµ½ÈÕÀú
		try {
			calendar.setTime(sdf.parse(time));	//°Ñµ±Ç°Ê±¼ä¸³¸øÈÕÀú
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.HOUR_OF_DAY, num);

		return sdf.format(calendar.getTime());
	}
	
	/**
	 * ¼ÆËãÊ±¼ä²î£¨Ð¡Ê±£©
	 * @return
	 */
	public static int getTimeDiff(String t1, String t2){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try
		{
			Date d1 = df.parse(t1);
			Date d2 = df.parse(t2);
			long diff = Math.abs(d1.getTime() - d2.getTime());//ÕâÑùµÃµ½µÄ²îÖµÊÇÎ¢Ãë¼¶±ð
			
			long days = diff / (1000 * 60 * 60 * 24);
			long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
//			long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
			
			return (int)hours;
		}
		catch (Exception e)
		{
			return 404;
		}
	}
	
	/**
	 * »ñÈ¡½ñÌìµÄÈÕÆÚ(yyyy-mm-dd)
	 * @return
	 */
	public static String getToday(){
		return (new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
	}
	
	/**
	 * »ñÈ¡µ±Ç°µÄÊ±¼ä
	 * @return
	 */
	public static String getTime(){
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
	}
	
	/**
	 * »ñÈ¡ÌØ¶¨ÈÕÆÚµÄ¼Ó¼õÈÕÆÚ
	 * @param date
	 * @return
	 */
	public static String getAddMinDate_oneday(String date, int i){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //ÉèÖÃÊ±¼ä¸ñÊ½
		
		Calendar calendar = Calendar.getInstance(); //µÃµ½ÈÕÀú
		try {
			calendar.setTime(sdf.parse(date));	//°Ñµ±Ç°Ê±¼ä¸³¸øÈÕÀú
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, i);  //ÉèÖÃÎªÇ°Ò»Ìì

		return sdf.format(calendar.getTime());
	}
	
	/**
	 * ±È½ÏÁ½¸öÈÕÆÚ´óÐ¡(0:ÏàµÈ, -1:µÚÒ»¸öÐ¡, 1:µÚÒ»¸ö´ó, 404:³ö´í)
	 * @param date1
	 * @param date2
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static int comparedate(String date1, String date2){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(formatter.parse(date1));
			c2.setTime(formatter.parse(date2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 404;
		}
		
		return c1.compareTo(c2);
	}
	
	/**
	 * ±È½ÏÁ½¸öÊ±¼ä´óÐ¡(0:ÏàµÈ, -1:µÚÒ»¸öÐ¡, 1:µÚÒ»¸ö´ó, 404:³ö´í)
	 * @param date1
	 * @param date2
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static int compareTime(String date1, String date2){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(formatter.parse(date1));
			c2.setTime(formatter.parse(date2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 404;
		}
		
		return c1.compareTo(c2);
	}
}
