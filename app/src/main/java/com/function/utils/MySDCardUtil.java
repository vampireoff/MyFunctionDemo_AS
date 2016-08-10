package com.function.utils;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

/**
 * @author SD¿¨¹¤¾ßÀà£¬´¦ÀíÒ»ÇÐÓësd¿¨Ïà¹ØµÄ²Ù×÷
 * @see isSDCardExist(), ²é¿´ÊÇ·ñ´æÔÚsd¿¨¡£
 * 
 */
public class MySDCardUtil {
	
	
	// SD¿¨²»´æÔÚ
	public static String SDCARD_IS_UNMOUTED = "Ã»ÓÐSD¿¨";

	/**
	 *  ÅÐ¶ÏsdcardÊÇ·ñ´æÔÚ,trueÎª´æÔÚ£¬falseÎª²»´æÔÚ
	 * @return
	 */
	public static boolean isSDCardExist() {
		return Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}
	
	
	/**
	 *  ÅÐ¶ÏsdcardµÄ×´Ì¬£¬²¢¸æÖªÓÃ»§
	 * @return
	 */
	public static String checkAndReturnSDCardStatus() {
		String status = Environment.getExternalStorageState();
		if(status!=null){
		//SDÒÑ¾­¹ÒÔØ,¿ÉÒÔÊ¹ÓÃ
		if (status.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return "1";
		} else if (status.equals(android.os.Environment.MEDIA_REMOVED)) {
			//SD¿¨ÒÑ¾­ÒÑ¾­ÒÆ³ý
			return "SD¿¨ÒÑ¾­ÒÆ³ý»ò²»´æÔÚ";

		} else if (status.equals(android.os.Environment.MEDIA_SHARED)) {
			//SD¿¨ÕýÔÚÊ¹ÓÃÖÐ
			return "SD¿¨ÕýÔÚÊ¹ÓÃÖÐ";

		} else if (status.equals(android.os.Environment.MEDIA_MOUNTED_READ_ONLY)) {
			//SD¿¨Ö»ÄÜ¶Á£¬²»ÄÜÐ´
			return "SD¿¨Ö»ÄÜ¶Á£¬²»ÄÜÐ´";
		} else {
			//SD¿¨µÄÆäËüÇé¿ö
			return "SD¿¨²»ÄÜÊ¹ÓÃ»ò²»´æÔÚ";
		}
		} else {
			//SD¿¨µÄÆäËüÇé¿ö
			return "SD¿¨²»ÄÜÊ¹ÓÃ»ò²»´æÔÚ";
		}
	}

	/**
	 * »ñÈ¡sd¿¨µÄÂ·¾¶(Ã»ÓÐsd¿¨·µ»ØÄÚ»º´æÂ·¾¶)
	 * 
	 * @return Â·¾¶µÄ×Ö·û´®
	 */
	public static String getSDPath() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // ÅÐ¶Ïsd¿¨ÊÇ·ñ´æÔÚ
		if (sdCardExist) {
			return Environment.getExternalStorageDirectory().getPath().toString();// »ñÈ¡sd¿¨Ä¿Â¼
		}else {
			return MyFileUtil.getCachePath();//»ñÈ¡ÄÚ»º´æÄ¿Â¼
		}
	}
	
	/**
	 *  »ñÈ¡sdcardÂ·¾¶
	 * @return
	 */
	public static String getSdcardUrl() {
		File sdDir = null;
		if (isSDCardExist()) {
			sdDir = Environment.getExternalStorageDirectory().getAbsoluteFile();// »ñÈ¡¸úÄ¿Â¼
			return sdDir.toString();
		} else {
			return SDCARD_IS_UNMOUTED;
		}
	}
	
	/**
	 * »ñÈ¡SD¿¨µÄÊ£ÓàÈÝÁ¿ µ¥Î»byte
	 * 
	 * @return
	 */
	public static long getSDCardAllSize()
	{
		if (isSDCardExist())
		{
			StatFs stat = new StatFs(getSdcardUrl());
			// »ñÈ¡¿ÕÏÐµÄÊý¾Ý¿éµÄÊýÁ¿
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// »ñÈ¡µ¥¸öÊý¾Ý¿éµÄ´óÐ¡£¨byte£©
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 * »ñÈ¡Ö¸¶¨Â·¾¶ËùÔÚ¿Õ¼äµÄÊ£Óà¿ÉÓÃÈÝÁ¿×Ö½ÚÊý£¬µ¥Î»byte
	 * 
	 * @param filePath
	 * @return ÈÝÁ¿×Ö½Ú SDCard¿ÉÓÃ¿Õ¼ä£¬ÄÚ²¿´æ´¢¿ÉÓÃ¿Õ¼ä
	 */
	public static long getFreeBytes(String filePath)
	{
		// Èç¹ûÊÇsd¿¨µÄÏÂµÄÂ·¾¶£¬Ôò»ñÈ¡sd¿¨¿ÉÓÃÈÝÁ¿
		if (filePath.startsWith(getSdcardUrl()))
		{
			filePath = getSdcardUrl();
		} else
		{// Èç¹ûÊÇÄÚ²¿´æ´¢µÄÂ·¾¶£¬Ôò»ñÈ¡ÄÚ´æ´æ´¢µÄ¿ÉÓÃÈÝÁ¿
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	/**
	 * »ñÈ¡ÏµÍ³µÄ¸ùÄ¿Â¼
	 * 
	 * @return
	 */
	public static String getRootDirectoryPath()
	{
		return Environment.getRootDirectory().getAbsolutePath();
	}

}
