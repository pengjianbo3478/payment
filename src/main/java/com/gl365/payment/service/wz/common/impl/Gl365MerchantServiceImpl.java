package com.gl365.payment.service.wz.common.impl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.exception.MerchantRespException;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.remote.dto.merchant.request.QueryMerchantInfoReqDTO;
import com.gl365.payment.remote.dto.merchant.response.QueryMerchantInfoRespDTO;
import com.gl365.payment.remote.dto.merchant.response.QueryMerchantInfoRespDTOBody;
import com.gl365.payment.remote.service.MerchantServiceRemote;
import com.gl365.payment.service.check.CheckGl365MerchantService;
import com.gl365.payment.service.wz.common.Gl365MerchantService;
import com.gl365.payment.util.gson.GsonUtils;
@Service
public class Gl365MerchantServiceImpl implements Gl365MerchantService {
	private static final Logger LOG = LoggerFactory.getLogger(Gl365UserServiceImpl.class);
	@Autowired
	private MerchantServiceRemote merchantServiceRemote;
	@Autowired
	private CheckGl365MerchantService checkGl365MerchantService;

	@Override
	public Gl365Merchant queryGl365Merchant(String organMerchantNo, String organCode, String channleType) {
		QueryMerchantInfoReqDTO request = new QueryMerchantInfoReqDTO(organCode, organMerchantNo);
		request.setChannleType(channleType);
		request.setType("2");
		LOG.info("查询商户信息请求参数JSON={}", GsonUtils.toJson(request));
		QueryMerchantInfoRespDTO resp = this.merchantServiceRemote.queryMerchantInfo(request);
		LOG.info("查询商户信息返回结果JSON={}", GsonUtils.toJson(resp));
		if (resp == null) throw new MerchantRespException(Msg.REMOTE_MER_2000.getCode(), Msg.REMOTE_MER_2000.getDesc());
		String result = resp.getResult();
		boolean b = !StringUtils.equals(result, ResultCode.SUCCESS.getCode());
		if (b) throw new MerchantRespException(Msg.REMOTE_MER_2000.getCode(), Msg.REMOTE_MER_2000.getDesc());
		QueryMerchantInfoRespDTOBody body = resp.getData();
		LOG.info("查询商户信息返回结果详情JSON={}", GsonUtils.toJson(body));
		if (body == null) throw new MerchantRespException(Msg.REMOTE_MER_2000.getCode(), Msg.REMOTE_MER_2000.getDesc());
		Gl365Merchant gl365Merchant = new Gl365Merchant();
		BeanUtils.copyProperties(body, gl365Merchant);
		return gl365Merchant;
	}

	@Override
	public void checkGl365Merchant(Gl365Merchant gl365Merchant) {
		this.checkGl365MerchantService.check(gl365Merchant);
	}
}
