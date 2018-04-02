package com.gl365.payment.service.wz.mq.consumer.order;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gl365.aliyun.ons.OnsListener;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.order.OrderStatus;
import com.gl365.payment.enums.order.OrderTranType;
import com.gl365.payment.enums.pay.OrganPayStatus;
import com.gl365.payment.enums.system.DateFormatStyle;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.service.dbservice.PayReturnService;
import com.gl365.payment.service.wz.mq.dto.OrderMain;
import com.gl365.payment.service.wz.mq.dto.OrderRefund;
import com.gl365.payment.util.gson.GsonUtils;
import com.google.gson.Gson;
@Component("order-return-notify-consumer")
public class OrderReturnNotifyConsumer implements OnsListener {
	private static final Logger LOG = LoggerFactory.getLogger(OrderReturnNotifyConsumer.class);
	@Autowired
	private PayReturnService payReturnService;

	@Override
	public void receive(byte[] data) {
		String message = new String(data);
		LOG.info("接受订单退货推送通知消息={}", message);
		Gson gson = GsonUtils.getGson();
		OrderMain orderMain = gson.fromJson(message, OrderMain.class);
		LOG.info("OrderMain={}", GsonUtils.toJson(orderMain));
		String tranType = orderMain.getTranType();
		boolean b = StringUtils.equals(tranType, OrderTranType.refund.getCode());
		if (!b) return;
		LOG.info("有订单退货成功通知消息={}", message);
		OrderRefund orderRefund = orderMain.getRefund();
		LOG.info("orderRefund={}", GsonUtils.toJson(orderRefund));
		if (orderRefund == null) return;
		Integer orderStatus = orderRefund.getOrderStatus();
		LOG.info("订单状态={}", orderStatus);
		if (orderStatus != OrderStatus.refund.getCode()) return;
		String paymentTime = orderRefund.getPaymentTime();
		LOG.info("支付确认支付时间={}", paymentTime);
		if (StringUtils.isEmpty(paymentTime)) {
			LOG.error(Msg.PAY_P8027.getDesc());
			throw new ServiceException(Msg.PAY_P8027.getCode(), Msg.PAY_P8027.getDesc());
		}
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateFormatStyle.FULL.getValue());
		LocalDateTime pt = LocalDateTime.parse(paymentTime, fmt);
		String merchantOrderNo = orderRefund.getOrderSn();
		if (StringUtils.isEmpty(merchantOrderNo)) {
			LOG.error(Msg.PAY_P8028.getDesc());
			throw new ServiceException(Msg.PAY_P8028.getCode(), Msg.PAY_P8028.getDesc());
		}
		PayReturn payReturn = new PayReturn();
		payReturn.setMerchantOrderNo(merchantOrderNo);
		payReturn.setOrganPayStatus(OrganPayStatus.REFUND_ALL.getCode());
		payReturn.setOrganReturnTime(pt);
		payReturn.setOrganCode(OrganCode.WX.getCode());
		LOG.info("payReturn={}", GsonUtils.toJson(payReturn));
		this.payReturnService.updateReturnTime(payReturn);
		LOG.info("接受订单退货推送通知消息处理完成");
	}
}
