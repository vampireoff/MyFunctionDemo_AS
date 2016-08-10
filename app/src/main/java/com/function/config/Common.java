package com.function.config;

import com.function.utils.MySDCardUtil;

/**
 * ³£Á¿Àà
 * @author Administrator
 *
 */
public class Common {

	/**
	 * ipµØÖ·
	 */
	public static final String mainurl = "http://wx.landray.com:81";
//	public static String mainurl = "http://192.168.111.13";
	/**
	 * ½Ó¿ÚµØÖ·
	 */
	public static final String wsurl = "/WQT/Legwork.asmx";
//	public static String wsurl = "/LegworkWS/Legwork.asmx";
	/**
	 * Í¼Æ¬µØÖ·
	 */
	public static final String imgurl = "/WQT";
//	public static String imgurl = "/LegworkWS";
	/**
	 * ÃüÃû¿Õ¼ä
	 */
	public static final String namespace = "http://tempuri.org/";
	/**
	 * ÊÚÈ¨Âë
	 */
	public static final String accesskey = "lwd";
	/**
	 * Ä¬ÈÏÃÜÂë
	 */
	public static final String defpwd = "123456";
	/**
	 * ÕÕÆ¬ÃûÔÝ´æ
	 */
	public static String photoname = "";
	/**
	 * ÅÄÕÕ´æ·ÅµÄÂ·¾¶
	 */
	public static final String takephotourl = MySDCardUtil.getSDPath() + "/legwork/capture";
	/**
	 * log´æ·ÅµÄÂ·¾¶
	 */
	public static final String logurl = MySDCardUtil.getSDPath() + "/legwork/log";
	/**
	 * ÕÕÆ¬´æ·ÅµÄÂ·¾¶
	 */
	public static final String photourl = MySDCardUtil.getSDPath() + "/legwork/photos";
	/**
	 * apk´æ·ÅµÄÂ·¾¶
	 */
	public static final String apkurl = MySDCardUtil.getSDPath() + "/legwork/apk";
	/**
	 * textÎÄ¼þ´æ·ÅµÄÂ·¾¶
	 */
	public static final String texturl = MySDCardUtil.getSDPath() + "/legwork/text";
	/**
	 * ×éÖ¯id´æ·ÅµÄtextÎÄ¼þÃû
	 */
	public static final String orgtextname = "org.txt";
	/**
	 * ¶ÌÐÅkey1
	 */
	public static final String smskey1 = "f5734159d6b8";
	/**
	 * ¶ÌÐÅkey2
	 */
	public static final String smskey2 = "f1ff277b345b5c64ef888ca71f6511ed";
	
	/**
	 * Í¼Æ¬Ñ¹Ëõ²ÎÊý
	 */
	public static int imgoption = 100;
	/**
	 * ÔÊÐíÑ¡ÔñµÄÍ¼Æ¬×ÜÊý
	 */
	public final static int imgnum = 5;
	/**
	 * ¿ªÊ¼Ê±¼ä
	 */
	public final static String stime = " 08:00";
	/**
	 * ½áÊøÊ±¼ä
	 */
	public final static String etime = " 18:00";
}
