package com.gl365.payment.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串解析工具类
 * @author yanguoqing
 *
 */
public class StringParseUtil {

	private final static String FORMAT_START = "$@";
	
	private final static String FORMAT_END = "@$";
	
	private final static String SPLIT_MARK = "\\|";
	
	private final static String REGEX = "[\\w\\W]*\\|[\\w\\W]*\\|(\\d+(\\.\\d+)?)?";
	
	public static boolean checkMerchantOrderDesc(String str){
		if (StringUtils.isNotBlank(str)) {
			int begin = str.indexOf(FORMAT_START);
			int end = str.indexOf(FORMAT_END);
			if (begin > -1 && end > -1 && end > begin) {
				String temp = str.substring(begin + 1, end);
				boolean m = temp.matches(REGEX);
				return m;
			}
		}
		
		return true;
	}
	
	public static Map<String, String> getOrderDescAndCostPrice(String str){
		Map<String, String> map = new HashMap<>();
		if (StringUtils.isNotBlank(str)) {
			int begin = str.indexOf(FORMAT_START);
			int end = str.indexOf(FORMAT_END);
			if (begin > -1 && end > -1 && end > begin) {
				map.put("orderDesc", str.substring(0, begin));
				String temp = str.substring(begin + 1, end);
				String[] res = StringUtils.splitPreserveAllTokens(temp, SPLIT_MARK);
				map.put("costPrice", res[res.length - 1]);
			}else {
				map.put("orderDesc", str);
			}
		}else {
			map.put("orderDesc", str);
		}
		
		return map;
	}
	
}
