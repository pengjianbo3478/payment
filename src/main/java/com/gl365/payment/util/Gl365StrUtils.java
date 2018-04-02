package com.gl365.payment.util;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
public class Gl365StrUtils {
	public static String toStr(Object object) {
		return ToStringBuilder.reflectionToString(object, ToStringStyle.DEFAULT_STYLE);
	}

	public static String toMultiLineStr(Object object) {
		return ToStringBuilder.reflectionToString(object, ToStringStyle.DEFAULT_STYLE);
	}
}
