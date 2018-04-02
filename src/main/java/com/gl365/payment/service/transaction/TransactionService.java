package com.gl365.payment.service.transaction;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.util.BigDecimalUtil;
/**
 * 
 * date: 2017年4月28日 下午3:12:23 <br/> 
 *
 * @author lenovo
 * @version
 */
@Service
public class TransactionService {

	/**
	 * 1、状态是否为“已交易” 2、用户卡索引、交易金额是否一致
	 */
	public boolean isInvalid(String cardIndex, BigDecimal totalAmount, PayPrepay payPrepay) {
		if (DealStatus.ALREADY_PAID.getCode().equals(payPrepay.getPayStatus().trim())) { return true; }
		if (!payPrepay.getCardIndex().trim().equals(cardIndex)) { return true; }
		if (BigDecimalUtil.compareTo(payPrepay.getCashAmount(), totalAmount) != 0) { return true; }
		return false;
	}

	/**
	 * 是否是“已交易”状态
	 * @param dealStatus
	 * @return
	 */
	public boolean isValidStatusWithTranCommit(String dealStatus) {
		if (DealStatus.ALREADY_PAID.getCode().equals(dealStatus)) { return true; }
		return false;
	}

	/**
	 * 是否是有效退货状态
	 * @param dealStatus
	 * @return
	 */
	public boolean isValidStatusWithRefund(String dealStatus) {
		if ((!DealStatus.ALREADY_PAID.getCode().equals(dealStatus)) && (!DealStatus.PARTIAL_RETURN.getCode().equals(dealStatus))) {
			return true;
		}
		else {
			return false;
		}
	}

	/*@Async
	public void pushAyscMQ(HashMap<SystemType, List<MqCell>> mqContents, TranType tranType) {
		for (Map.Entry<SystemType, List<MqCell>> entry : mqContents.entrySet()) {
			SystemType systemType = entry.getKey();
			List<MqCell> list = entry.getValue();
			for (MqCell mqCell : list) {
				boolean result = productFacade.batchPush(systemType, tranType, mqCell.getMqContent());
				logger.info(LoggerFormat.logTran(mqCell.getLogBase(), (result == true ? "0" : "1"), "发送给'" + entry.getKey().getDesc() + "'" + (result == true ? "成功" : "失败"), "MQ推送内容:" + mqCell.getMqContent()));
			}
		}
	}*/
}
