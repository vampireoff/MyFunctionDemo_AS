package com.function.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.util.Log;
/**
 * Æ´Òô¹¤¾ßÀà
 * @author Administrator
 *
 */
public class MyPingYinUtil {
	/**
	 * È¡ºº×ÖÊ×´óÐ´×ÖÄ¸
	 * @param chines
	 * @return
	 */
	public static String converterToFirstSpell(String chines){             
		String pinyinName = "";      
		char[] nameChar = chines.toCharArray();      
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();      
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);      
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);      
		for (int i = 0; i < nameChar.length; i++) {      
			if (nameChar[i] > 128) {
				//Èç¹ûÊÇºº×Ö£¬×ª»¯ÎªÆ´Òô      
				try {      
					Log.i("xxx", String.valueOf(nameChar[i]));
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);      
				} catch (BadHanyuPinyinOutputFormatCombination e) {      
					e.printStackTrace();      
				}      
			}else{      
				pinyinName += nameChar[i];      
			}      
		}      
		return pinyinName;      
	}
	/**
	 * »ñÈ¡ºº×ÖµÄÆ´Òô×ÖÄ¸
	 *
	 * @param inputString
	 * @return
	 */
	public static String getPingYin(String inputString) {
		HanyuPinyinOutputFormat format = new
				HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		
		char[] input = inputString.trim().toCharArray();
		String output = "";
		
		try {
			for (int i = 0; i < input.length; i++) {
				if (java.lang.Character.toString(input[i]).
						matches("[\\u4E00-\\u9FA5]+")) {
					String[] temp = PinyinHelper.
							toHanyuPinyinStringArray(input[i],
									format);
					output += temp[0];
				} else
					output += java.lang.Character.toString(
							input[i]);
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return output;
	}
	
	/**
	 * °ë½Ç×ª»»ÎªÈ«½Ç
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
	
	/**
	 * È¥³ýÌØÊâ×Ö·û»ò½«ËùÓÐÖÐÎÄ±êºÅÌæ»»ÎªÓ¢ÎÄ±êºÅ
	 * 
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str) {
		str = str.replaceAll("¡¾", "[").replaceAll("¡¿", "]").replaceAll("£¬", ",")
				.replaceAll("£¡", "!").replaceAll("£º", ":");// Ìæ»»ÖÐÎÄ±êºÅ
		String regEx = "[¡º¡»]"; // Çå³ýµôÌØÊâ×Ö·û
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
}
