package com.gl365.payment.util;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author liuyang
 *
 */
public class RegExUtil {

	/**
	 * 检查是否只包含字母和数字
	 * @param str
	 * @return
	 */
	public static boolean isLetterAndNum(String str){
		String regEx="^[a-z0-9A-Z]+$";
		return Pattern.compile(regEx).matcher(str).find();
	}
}
