package com.gl365.payment.service.base;
import java.math.BigDecimal;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.util.IdGenerator;
public class BaseServiceTest {
	public String organCode = OrganCode.FFT.getCode();
	public String organMerchantNo = "1707121000098";
	public String cardIndex = "123456782234493";
	public String requestId = IdGenerator.getUuId32();
	public String terminal = "12345678";
	public String requestDate = LocalDate.now().toString("yyyyMMdd");
	public String title = "订单标题"+ LocalDateTime.now().toString("yyyyMMddHHmmss");
	public String desc = "订单描述"+ LocalDateTime.now().toString("yyyyMMddHHmmss");
	public String operator = "zhang";
	public String organOrderNo = IdGenerator.getUuId32();
	public String merchantOrderDesc = "订单描述$@||10@$"; 
	public String UserId = "52ecaa6c3bb545aea27429a6de3c6d46";
	public String merchantOrderNo = "afbcacf723b64d2d844209553af1125";
	public String rewardUserId = "3000036";
	public BigDecimal payldmoney = new BigDecimal(3);
	public String prevMerchanOrderNo = IdGenerator.getUuId32();
	
	public String getRequestId() {
		return IdGenerator.getUuId32();
	}
}
