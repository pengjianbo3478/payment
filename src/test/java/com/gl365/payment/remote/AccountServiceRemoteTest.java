package com.gl365.payment.remote;
import java.math.BigDecimal;
import org.joda.time.LocalDateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.enums.system.Agent;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.remote.dto.account.request.CancelOperateReqDTO;
import com.gl365.payment.remote.dto.account.request.HasNormalBindInfoReqDTO;
import com.gl365.payment.remote.dto.account.request.QueryAccountBalanceInfoReqDTO;
import com.gl365.payment.remote.dto.account.request.QueryAccountReqDTO;
import com.gl365.payment.remote.dto.account.request.ReverseOperateReqDTO;
import com.gl365.payment.remote.dto.account.request.UpdateAccountBalanceOffLineReqDTO;
import com.gl365.payment.remote.dto.account.response.CancelOperateRespDTO;
import com.gl365.payment.remote.dto.account.response.HasNormalBindInfoRespDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountRespDTO;
import com.gl365.payment.remote.dto.account.response.ReverseOperateRespDTO;
import com.gl365.payment.remote.dto.account.response.UpdateAccountBalanceOffLineRespDTO;
import com.gl365.payment.remote.service.AccountServiceRemote;
import com.gl365.payment.util.Gl365StrUtils;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableFeignClients
@Ignore
public class AccountServiceRemoteTest {
	private static final Logger LOG = LoggerFactory.getLogger(AccountServiceRemoteTest.class);
	@Autowired
	private AccountServiceRemote accountServiceRemote;
	private String userId = "52ecaa6c3bb545aea27429a6de3c6d46";

	@Test
	@Ignore
	public void testQueryAccount() {
		QueryAccountReqDTO request = new QueryAccountReqDTO();
		request.setCardIndex("123456782234493");
		request.setOrganCode(OrganCode.FFT.getCode());
		QueryAccountRespDTO result = this.accountServiceRemote.queryAccount(request);
		LOG.debug("result={}", Gl365StrUtils.toStr(result));
	}

	@Test
	@Ignore
	public void testHasNormalBindInfo() {
		HasNormalBindInfoReqDTO request = new HasNormalBindInfoReqDTO();
		request.setUserId(this.userId);
		request.setOrganCode(OrganCode.FFT.getCode());
		HasNormalBindInfoRespDTO result = this.accountServiceRemote.hasNormalBindInfo(request);
		LOG.debug("result={}", Gl365StrUtils.toStr(result));
	}

	@Test
	@Ignore
	public void testQueryAccountBalanceInfo() {
		QueryAccountBalanceInfoReqDTO request = new QueryAccountBalanceInfoReqDTO();
		request.setUserId(this.userId);
		request.setAgentId(Agent.GL365.getKey());
		QueryAccountBalanceInfoRespDTO result = this.accountServiceRemote.queryAccountBalanceInfo(request);
		LOG.debug("result={}", Gl365StrUtils.toStr(result));
	}

	@Test
	@Ignore
	public void testUpdateAccountBalanceOffLine() {
		UpdateAccountBalanceOffLineReqDTO request = new UpdateAccountBalanceOffLineReqDTO();
		request.setUserId(this.userId);
		request.setAgentId(Agent.GL365.getKey());
		request.setOrganCode(OrganCode.GL.getCode());
		request.setPayId("PENG" + LocalDateTime.now().toString("yyyyMMddHHmmss"));
		request.setMerchantNo("商户号-est");
		request.setMerchantName("商户名-test ");
		request.setMerchantOrderNo("MER" + LocalDateTime.now().toString("yyyyMMddHHmmss"));
		request.setOperateType(TranType.ONLINE_CONSUME.getCode());
		request.setOperateAmount(new BigDecimal(20.25));
		request.setGiftAmount(new BigDecimal(2.25));
		request.setDcType(DcType.C.getCode());
		request.setScene(Scene.POS_PAY.getCode());
		UpdateAccountBalanceOffLineRespDTO result = this.accountServiceRemote.updateAccountBalanceOffLine(request);
		LOG.debug("result={}", Gl365StrUtils.toStr(result));
	}

	@Test
	@Ignore
	public void testReverseOperate() {
		ReverseOperateReqDTO request = new ReverseOperateReqDTO();
		request.setUserId(this.userId);
		request.setAgentId(Agent.GL365.getKey());
		request.setOrganCode(OrganCode.GL.getCode());
		request.setPayId("PENG" + LocalDateTime.now().toString("yyyyMMddHHmmss"));
		request.setMerchantNo("商户号-est");
		request.setMerchantName("商户名-test");
		request.setMerchantOrderNo("MER" + LocalDateTime.now().toString("yyyyMMddHHmmss"));
		request.setOperateType(TranType.CONSUME_REVERSE.getCode());
		request.setOperateAmount(new BigDecimal(20.25));
		request.setGiftAmount(new BigDecimal(2.25));
		request.setDcType(DcType.C.getCode());
		request.setScene(Scene.POS_PAY.getCode());
		request.setOrigPayId("PENG20170513125317");
		ReverseOperateRespDTO result = this.accountServiceRemote.reverseOperate(request);
		LOG.debug("result={}", Gl365StrUtils.toStr(result));
	}
	
	@Test
	@Ignore
	public void testCancelOperate() {
		CancelOperateReqDTO request = new CancelOperateReqDTO();
		request.setUserId(this.userId);
		request.setAgentId(Agent.GL365.getKey());
		request.setOrganCode(OrganCode.GL.getCode());
		request.setPayId("PENG" + LocalDateTime.now().toString("yyyyMMddHHmmss"));
		request.setMerchantNo("商户号-est");
		request.setMerchantName("商户名-test");
		request.setMerchantOrderNo("MER" + LocalDateTime.now().toString("yyyyMMddHHmmss"));
		request.setOperateType(TranType.ONLINE_CANCEL.getCode());
		request.setOperateAmount(new BigDecimal(20.25));
		request.setGiftAmount(new BigDecimal(2.25));
		request.setDcType(DcType.C.getCode());
		request.setScene(Scene.POS_PAY.getCode());
		request.setOrigPayId("PENG20170513125317");
		CancelOperateRespDTO result = this.accountServiceRemote.cancelOperate(request);
		LOG.debug("result={}", Gl365StrUtils.toStr(result));
	}
}
