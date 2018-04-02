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
import com.gl365.payment.remote.dto.member.request.QueryUserInfoReqDTO;
import com.gl365.payment.remote.dto.member.response.QueryUserInfoRespDTO;
import com.gl365.payment.remote.service.MemberServiceRemote;
import com.gl365.payment.util.Gl365StrUtils;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableFeignClients
@Ignore
public class MemberServiceRemoteTest  {
	private static final Logger LOG = LoggerFactory.getLogger(MemberServiceRemoteTest.class);
	@Autowired
	private MemberServiceRemote memberServiceRemote;

	@Test
	@Ignore
	public void testQueryUserInfo() {
		QueryUserInfoReqDTO request = new QueryUserInfoReqDTO();
		request.setUserId("52ecaa6c3bb545aea27429a6de3c6d46");
		QueryUserInfoRespDTO result = this.memberServiceRemote.queryUserInfo(request);
		LOG.debug("result={}", Gl365StrUtils.toStr(result));
	}
}
