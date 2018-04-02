package com.gl365.payment.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {

	private BigDecimalUtil() {
	}

	public static boolean isEmpty(BigDecimal source) {
		if(source == null) return true;
		return false;
		//return source.compareTo(BigDecimal.ZERO) == 0 ? true : false;
	}
	
	public static boolean GreaterThan0(BigDecimal source) {
		if(source == null) return false;
		return source.compareTo(BigDecimal.ZERO) == 1 ? true : false;
	}
	
	public static boolean GreaterOrEqual(BigDecimal source, BigDecimal target) {
		BigDecimal a = source == null ? BigDecimal.ZERO : source;
		BigDecimal b = target == null ? BigDecimal.ZERO : target;
		if(a.compareTo(b) == 0 || a.compareTo(b) == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * bigdecimal 减法，带非空判断。默认保留2位小数
	 * 
	 * @param source 减数
	 * @param subtrahend 被减数
	 * @return
	 * @Author Xiang xiaowen 2017年4月19日 新建
	 */
	public static BigDecimal subtract(BigDecimal source, BigDecimal subtrahend) {
		return subtract(source, subtrahend, 2);
	}
	
	
	/**
	 * Bigdecimal 减法,带非空判断
	 * @param source 减数
	 * @param subtrahend 被减数 
	 * @param scale 小数位数
	 * @return
	 * @Author Xiang xiaowen 2017年4月21日 新建
	 */
	public static BigDecimal subtract(BigDecimal source, BigDecimal subtrahend, int scale) {
		BigDecimal a = source == null ? BigDecimal.ZERO : source;
		BigDecimal b = subtrahend == null ? BigDecimal.ZERO : subtrahend;
		BigDecimal result= a.subtract(b);
		result=result.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return result;
	}
	
	/**
	 * 连减
	 * @param source
	 * @param subtrahend1
	 * @param subtrahend2
	 * @param scale
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal source, BigDecimal subtrahend1, BigDecimal subtrahend2, int scale) {
		BigDecimal a = source == null ? BigDecimal.ZERO : source;
		BigDecimal b = subtrahend1 == null ? BigDecimal.ZERO : subtrahend1;
		BigDecimal c = subtrahend2 == null ? BigDecimal.ZERO : subtrahend2;
		BigDecimal result= a.subtract(b).subtract(c);
		result=result.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return result;
	}
	
	/**
	 * BigDecimal 加法， 带非空判断
	 * 
	 * @param source
	 * @param augend
	 * @return
	 * @Author Xiang xiaowen 2017年4月19日 新建
	 */
	public static BigDecimal add(BigDecimal source, BigDecimal augend){
		BigDecimal a = source == null ? BigDecimal.ZERO : source;
		BigDecimal b = augend == null ? BigDecimal.ZERO : augend;
		return a.add(b);
	}

	/**  
	* 提供精确的乘法运算。  
	* @param v1 被乘数  
	* @param v2 乘数  
	* @return 两个参数的积  
	*/  
	public static BigDecimal mul(BigDecimal v1,BigDecimal v2, int scale){
		if(v1 == null || v2 == null)
			return BigDecimal.ZERO;
		return v1.multiply(v2).setScale(2,BigDecimal.ROUND_HALF_UP); 
	}   
	/**
	 * BigDecimal 除法，加入非空判断和被除数为0时默认为1
	 * 
	 * @param source 除数
	 * @param divisor 被除数，为空或0时会自动修改为1
	 * @param scale 保留小数位
	 * @param roundingMode 小数位处理方案(舍、入方案，见BigDecimal API)
	 * @return 计算结果
	 * @Author Xiang xiaowen 2017年4月20日 新建
	 */
	public static BigDecimal divide(BigDecimal source, BigDecimal divisor, int scale,
			int roundingMode) {
		BigDecimal a = source == null ? BigDecimal.ZERO : source;
		BigDecimal b = (divisor == null || BigDecimal.ZERO.equals(divisor)) ? new BigDecimal(1) : divisor;
		return a.divide(b, scale, roundingMode);
	}
	
	/**
	 * BigDecimal 除法，加入非空判断和被除数为0时默认为1
	 * 
	 * @param source 除数
	 * @param divisor 被除数，为空或0时会自动修改为1
	 * @return 计算结果
	 * @Author Xiang xiaowen 2017年4月20日 新建
	 */
	public static BigDecimal divide(BigDecimal source, BigDecimal divisor) {
		BigDecimal a = source == null ? BigDecimal.ZERO : source;
		BigDecimal b = (divisor == null || BigDecimal.ZERO.equals(divisor)) ? new BigDecimal(1) : divisor;
		return a.divide(b);
	}
	
	/**
	 * bigdecimal对象比较大小
	 * 
	 * @param source
	 * @param val
	 * @return 参见BigDecimal.compareTo
	 * @Author Xiang xiaowen 2017年4月20日 新建
	 */
	public static int compareTo(BigDecimal source, BigDecimal val){
		BigDecimal a = source == null ? BigDecimal.ZERO : source;
		BigDecimal b = val == null ? BigDecimal.ZERO : val;
		return a.compareTo(b);
	}
	
	/**
	 * BigDecimal是否相等
	 * @param source
	 * @param val
	 * @return
	 */
	public static boolean equals(BigDecimal source, BigDecimal val){
		BigDecimal a = source == null ? BigDecimal.ZERO : source.setScale(2, RoundingMode.HALF_UP);
		BigDecimal b = val == null ? BigDecimal.ZERO : val.setScale(2, RoundingMode.HALF_UP);
		return a.compareTo(b) == 0;
	}
	
	/*public static void main(String[] args) {
		System.out.println(compareTo(new BigDecimal(10), BigDecimal.ZERO));
		System.out.println(subtract(new BigDecimal(10), new BigDecimal(7.8)));
		System.out.println(GreaterThan0(null));
	}*/
}
