package com.gl365.payment.remote;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.gl365.payment.remote.dto.getway.request.QueryOrderReqDTO;
import com.gl365.payment.remote.dto.getway.response.QueryOrderRespDTO;
import com.gl365.payment.remote.service.GetwayServiceRemote;
import com.gl365.payment.service.base.BaseServiceTest;
import com.gl365.payment.util.Gl365StrUtils;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableFeignClients
@Ignore
public class GetwayServiceRemoteTest extends BaseServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(GetwayServiceRemoteTest.class);
	@Autowired
	private GetwayServiceRemote getwayServiceRemote;

	@Test
	public void testQueryOrderInfo() {
		QueryOrderReqDTO request = new QueryOrderReqDTO();
		QueryOrderRespDTO result = null;
		try{
			result = this.getwayServiceRemote.queryOrder(request);
		}catch (Exception e) {
			e.printStackTrace();
		}
		LOG.debug("result={}", Gl365StrUtils.toStr(result));
	}
}
