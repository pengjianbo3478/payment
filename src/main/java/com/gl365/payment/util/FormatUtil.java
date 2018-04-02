/**
 * Project Name:payment
 * File Name:FormatUtil.java
 * Package Name:com.gl365.payment.util
 * Date:2017年4月24日下午4:17:33
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/
/**
 * Date:2017年4月24日下午4:17:33
 * Copyright (c) 2017, 深圳市给乐信息科技有限公司 All Rights Reserved.
 *
 */

package com.gl365.payment.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * date: 2017年4月24日 下午4:17:33 <br/>
 *
 * @author lenovo
 * @version 
 * @since JDK 1.8
 */
public class FormatUtil {
	
	public static final String UniqueSerial_Separator = "-_-";
	/**
	 * yyyyMMdd转LocalDate
	 */
	public static LocalDate parseYyyyMMdd(String dateStr) {
		if(null == dateStr || "".equals(dateStr)) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
		return LocalDate.parse(dateStr, formatter);
	}
	
	public static String formatYyyyMMdd(LocalDateTime time) {
		if(null == time || "".equals(time)) {
			return "";
		}
		return time.format(DateTimeFormatter.BASIC_ISO_DATE);
	}
	
	public static boolean greaterThan(LocalDateTime ldt, long minutes) {
		if(ldt == null) {
			return true;
		}
//		System.out.println(ldt.plusMinutes(minutes));
		return ldt.plusMinutes(minutes).isBefore(LocalDateTime.now());
	}
	
	public static String mergeString(String... strings) {
		String result = "";
		if(strings != null) {
			for(int i = 0; i<strings.length; i++) {
				if(i == strings.length - 1) {
					result += strings[i];
				} else {
					result += strings[i] + UniqueSerial_Separator;
				}
				
			}
		}
		return result;
	}
	
	/*public static void main(String[] args) {

		//System.out.println(greaterThan(LocalDateTime.of(2017, 4, 24, 17, 43), 2));
		System.out.println(parseYyyyMMdd("20170303"));
		System.out.println(formatYyyyMMdd(LocalDateTime.now()));
		System.out.println(","+mergeString(null)+",");
		System.out.println(","+mergeString("")+",");
		System.out.println(","+mergeString("a")+",");
		System.out.println(","+mergeString("a", "b")+",");
		System.out.println(","+mergeString("a", "b", "c")+",");
		
	}*/
}

