package com.gl365.payment.service.mq.consumer.service.impl;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.gl365.payment.enums.getway.GetWayNotifyType;
import com.gl365.payment.enums.getway.OrderQueryResult;
import com.gl365.payment.enums.mq.MsgCategory;
import com.gl365.payment.enums.system.Flag;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayNotify;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.getway.response.DealNotifyRespDTO;
import com.gl365.payment.service.mq.consumer.service.AbstractGetwayNotifyConsumer;
import com.gl365.payment.service.mq.consumer.service.PaymentConsumerService;
import com.gl365.payment.service.mq.dto.PaymentResult;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.gson.GsonUtils;
import com.google.gson.Gson;
@Service
public class PaymentConsumerServiceImpl extends AbstractGetwayNotifyConsumer implements PaymentConsumerService {
	private static final Logger LOG = LoggerFactory.getLogger(PaymentConsumerServiceImpl.class);
	private static final String PAY_MAIN = "pm";
	private static final String PAY_STREAM = "ps";

	@Override
	public void exePaymentResult(String message) {
		PaymentResult paymentResult = GsonUtils.getGson().fromJson(message, PaymentResult.class);
		LOG.debug("#####接收Payment延时消息={}", Gl365StrUtils.toStr(paymentResult));
		PayNotify payNotify = new PayNotify();
		payNotify.setRequestId(paymentResult.getRequestId());
		payNotify.setPayId(paymentResult.getPayId());
		PayNotify result = this.payNotifyService.queryByPayNotify(payNotify);
		LOG.debug("#####订单是否已推送通知={}", BooleanUtils.isTrue(result == null));
		if (result != null) return;
		// 未推送通知则调用网关接口查询订单在付费通交易状态
		this.queryOrder(paymentResult);
	}

	@Override
	public void exeGetWayPaymentNotify(String message) {
		Gson gson = GsonUtils.getGson();
		DealNotifyRespDTO response = gson.fromJson(message, DealNotifyRespDTO.class);
		LOG.debug("#####接收网关消息={}", response);
		String type = response.getInterfaceType();
		boolean a = StringUtils.equals(type, GetWayNotifyType.normal.getKey());
		if (a) this.normalNotify(response);
		boolean b = StringUtils.equals(type, GetWayNotifyType.query.getKey());
		if (b) this.delayNotify(response);
	}

	private void delayNotify(DealNotifyRespDTO response) {
		LOG.debug("#####接收网关订单查询结果={}", response);
		String code = response.getOrigReturnCode();
		if (!StringUtils.equals(OrderQueryResult.SUCCESS.getKey(), code)) return;
		String requestId = response.getOrigPayId();
		Map<String, Object> result = this.getPayMain(response);
		PayMain pm = (PayMain) result.get(PAY_MAIN);
		PayStream ps = (PayStream) result.get(PAY_STREAM);
		String payId = pm.getPayId();
		PayNotify payNotify = new PayNotify();
		payNotify.setRequestId(requestId);
		payNotify.setPayId(payId);
		PayNotify resultPayNotify = this.payNotifyService.queryByPayNotify(payNotify);
		LOG.debug("#####查询交易通知记录表={}", resultPayNotify);
		if (resultPayNotify != null) return;
		String content = this.buildContent(MsgCategory.push.getCode(), pm, ps);
		LOG.debug("#####发送Jpush={}", content);
		this.paymentProduct.send(content);
	}

	private Map<String, Object> getPayMain(DealNotifyRespDTO response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String requestId = response.getOrigPayId();
		String organMerchantNo = response.getMerchantCode();
		PayStream ps = this.payStreamService.queryByRequestIdAndOrganMerNo(requestId, organMerchantNo);
		result.put(PAY_STREAM, ps);
		String transType = ps.getTransType();
		boolean commit = this.getCommitList().contains(transType);
		if (commit) result.put(PAY_MAIN, this.payMainService.queryByPayId(ps.getPayId()));
		boolean cancel = this.getCancelList().contains(transType);
		if (cancel) result.put(PAY_MAIN, this.getCancelPayMain(ps.getOrigRequestId()));
		LOG.debug("#####通过请求流水号查询原单结果={}", result);
		return result;
	}

	private PayMain getCancelPayMain(String payId) {
		PayMain payMain = this.payMainService.queryByPayId(payId);
		LOG.debug("#####撤销交易找到原单记录={}", payMain);
		return payMain;
	}

	private void normalNotify(DealNotifyRespDTO response) {
		LOG.debug("#####正常接收网关交易成功通知");
		String requestId = response.getOrigPayId();
		Map<String, Object> result = this.getPayMain(response);
		PayMain pm = (PayMain) result.get(PAY_MAIN);
		PayStream ps = (PayStream) result.get(PAY_STREAM);
		// 发送红包MQ
		String rpContent = this.buildContent(MsgCategory.redPacket.getCode(), pm, ps);
		LOG.debug("#####发送红包消息={}", rpContent);
		this.paymentProduct.send(rpContent);
		// 组装消息
		String content = this.buildContent(MsgCategory.push.getCode(), pm, ps);
		LOG.debug("#####发送Jpush消息={}", content);
		// 发送MQ
		this.paymentProduct.send(content);
		// 插入交易通知表
		PayNotify payNotify = new PayNotify();
		payNotify.setPayId(pm.getPayId());
		payNotify.setRequestId(requestId);
		payNotify.setStatus(Flag.N.getCode());
		LOG.debug("#####写交易通知表");
		int i = this.payNotifyService.insert(payNotify);
		LOG.debug("#####插入交易通知表={}", i);
	}
}
