package com.gl365.payment.remote;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.gl365.payment.enums.pay.PayChannleType;
import com.gl365.payment.service.base.BaseServiceTest;
import com.gl365.payment.service.wz.common.Gl365MerchantService;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableFeignClients
@Ignore
public class MerchantServiceRemoteTest extends BaseServiceTest {
	@Autowired
	private Gl365MerchantService gl365MerchantService;

	@Test
	public void testqueryGl365Merchant() {
		String merchantNo = "4300001000208";
		String organCode = "10003";
		String channleType = PayChannleType.H5.getCode();
		this.gl365MerchantService.queryGl365Merchant(merchantNo, organCode, channleType);
	}
}
