package com.gl365.payment.service.wz.common.impl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gl365.payment.dto.wx.response.WxPrePayRespDetailDTO;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.system.ResultCode;
import com.gl365.payment.exception.AccountRespException;
import com.gl365.payment.exception.MerchantRespException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.remote.dto.settlement.request.ConfirmPreSettleDateReqDTO;
import com.gl365.payment.remote.dto.settlement.request.CurrentPayProfitReqDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDTO;
import com.gl365.payment.remote.dto.settlement.response.ConfirmPreSettleDateRespDataDTO;
import com.gl365.payment.remote.dto.settlement.response.CurrentPayProfitDetail;
import com.gl365.payment.remote.dto.settlement.response.CurrentPayProfitRespDTO;
import com.gl365.payment.remote.service.SettlementServiceRemote;
import com.gl365.payment.service.wz.common.Gl365SettlementService;
import com.gl365.payment.util.gson.GsonUtils;
@Service
public class Gl365SettlementServiceImpl implements Gl365SettlementService {
	private static final Logger LOG = LoggerFactory.getLogger(Gl365SettlementServiceImpl.class);
	@Autowired
	private SettlementServiceRemote settlementServiceRemote;

	@Override
	public List<WxPrePayRespDetailDTO> calculateCurrentPayProfit(CurrentPayProfitReqDTO request) {
		LOG.info("实时计算提成请求参数={}", GsonUtils.toJson(request));
		CurrentPayProfitRespDTO<CurrentPayProfitDetail> result = this.settlementServiceRemote.calculateCurrentPayProfit(request);
		LOG.info("实时计算提成返回结果={}", GsonUtils.toJson(result));
		if (result == null) throw new AccountRespException(Msg.REMOTE_SETTLE_6000.getCode(), Msg.REMOTE_SETTLE_6000.getDesc());
		String resultCode = result.getResultCode();
		boolean b = !StringUtils.equals(resultCode, ResultCode.SUCCESS.getCode());
		if (b) throw new MerchantRespException(result.getResultCode(), result.getResultDesc());
		List<CurrentPayProfitDetail> data = result.getData();
		if (data == null) throw new MerchantRespException(Msg.REMOTE_SETTLE_6000.getCode(), Msg.REMOTE_SETTLE_6000.getDesc());
		List<WxPrePayRespDetailDTO> list = new ArrayList<WxPrePayRespDetailDTO>();
		for (CurrentPayProfitDetail item : data) {
			WxPrePayRespDetailDTO dto = new WxPrePayRespDetailDTO();
			BeanUtils.copyProperties(item, dto);
			list.add(dto);
		}
		LOG.info("提成明细结果记录数={}", list.size());
		return list;
	}

	@Override
	public ConfirmPreSettleDateRespDTO getConfirmPreSettleDate(ConfirmPreSettleDateReqDTO request) {
		LOG.info("获取确认待清算日期请求参数={}", GsonUtils.toJson(request));
		ConfirmPreSettleDateRespDTO result = this.settlementServiceRemote.confirmPreSettleDate(request);
		LOG.info("获取确认待清算日期返回结果={}", GsonUtils.toJson(result));
		if (result == null) throw new AccountRespException(Msg.REMOTE_SETTLE_6000.getCode(), Msg.REMOTE_SETTLE_6000.getDesc());
		String resultCode = result.getResultCode();
		boolean b = StringUtils.equals(resultCode, ResultCode.SUCCESS.getCode());
		if (!b) throw new ServiceException(result.getResultCode(), result.getResultDesc());
		ConfirmPreSettleDateRespDataDTO data = result.getData();
		if (data == null) throw new ServiceException(Msg.REMOTE_SETTLE_P6002.getCode(), Msg.REMOTE_SETTLE_P6002.getDesc());
		LocalDate date = data.getPreSettleDate();
		if (date == null) throw new ServiceException(Msg.REMOTE_SETTLE_P6001.getCode(), Msg.REMOTE_SETTLE_P6001.getDesc());
		return result;
	}
}
