package com.gl365.payment.util;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
/**
 * 
 * date: 2017年4月27日 下午3:46:10 <br/>
 *
 * @author lenovo
 * @version
 * @since JDK 1.8
 */
public class IdGenerator {
	static final String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char ch = str.charAt(new Random().nextInt(str.length()));
			sb.append(ch);
		}
		return sb.toString();
	}

	public static String generatePayId(String bizType) {
		return bizType + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmSS")) + generateRandomString(4);
	}

	public static String getUuId32() {
		return StringUtils.remove(UUID.randomUUID().toString(), "-");
	}
	
	/*	public static void main(String[] args) {
	
			System.out.println(generatePayId("94"));
	
		}*/
}
