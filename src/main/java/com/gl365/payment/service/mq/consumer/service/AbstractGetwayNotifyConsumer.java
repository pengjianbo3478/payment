package com.gl365.payment.service.mq.consumer.service;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.getway.request.QueryOrderReqDTO;
import com.gl365.payment.remote.service.GetwayServiceRemote;
import com.gl365.payment.service.dbservice.PayMainService;
import com.gl365.payment.service.dbservice.PayNotifyService;
import com.gl365.payment.service.dbservice.PayStreamService;
import com.gl365.payment.service.mq.dto.PaymentBody;
import com.gl365.payment.service.mq.dto.PaymentMQ;
import com.gl365.payment.service.mq.dto.PaymentResult;
import com.gl365.payment.service.mq.product.PaymentProduct;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.gson.GsonUtils;
public abstract class AbstractGetwayNotifyConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractGetwayNotifyConsumer.class);
	@Autowired
	public PayMainService payMainService;
	@Autowired
	public PayStreamService payStreamService;
	@Autowired
	public PaymentProduct paymentProduct;
	@Autowired
	public PayNotifyService payNotifyService;
	@Autowired
	private GetwayServiceRemote getwayServiceRemote;

	public String buildContent(String msgCategory, PayMain payMain, PayStream payStream) {
		PaymentBody body = new PaymentBody();
		body.setPayMain(payMain);
		body.setPayStream(payStream);
		body.setPayModifyTime(java.time.LocalDateTime.now());
		PaymentMQ res = new PaymentMQ();
		res.setMsgCategory(msgCategory);
		res.setTranType(payStream.getTransType());
		res.setPaymentBody(body);
		LOG.debug("#####send mq paymentMQ={}", Gl365StrUtils.toStr(res));
		String content = GsonUtils.toJson(res);
		LOG.debug("#####send mq json content={}", content);
		return content;
	}

	public List<String> getRespCode() {
		List<String> list = new ArrayList<String>();
		// 000100-交易撤销成功000000-交易成功 补单成功
		list.add("000000");
		list.add("000090");
		list.add("000100");
		return list;
	}

	public List<String> getCommitList() {
		List<String> commits = new ArrayList<String>();
		commits.add(TranType.CONSUME_COMMIT.getCode());
		commits.add(TranType.ONLINE_CONSUME.getCode());
		return commits;
	}

	public List<String> getCancelList() {
		List<String> cancels = new ArrayList<String>();
		cancels.add(TranType.CANCEL.getCode());
		cancels.add(TranType.ONLINE_CANCEL.getCode());
		return cancels;
	}

	public void queryOrder(PaymentResult paymentResult) {
		LOG.debug("#####开始调用网关查询订单处理结果请求参数={}", paymentResult);
		QueryOrderReqDTO request = new QueryOrderReqDTO();
		BeanUtils.copyProperties(paymentResult, request);
		this.getwayServiceRemote.queryOrder(request);
	}
}
