package com.gl365.payment.service.transaction;
import java.util.HashMap;
import java.util.Map;
import com.gl365.payment.dto.base.PayContext;
import com.gl365.payment.dto.base.request.PayReqDTO;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.service.mq.AbstractMq;
public abstract class AbstractPay<Ctx extends PayContext, Req extends PayReqDTO> extends AbstractMq {
	/**
	 * <p>设置交易类别</p>
	 * @return String
	 */
	public abstract String initPayCategoryCode();

	/**
	 * <p>设置交易类型</p>
	 * @return String
	 */
	public abstract String initTranType();

	/**
	 * <p>设置状态</p>
	 * @return
	 */
	public abstract PayStatus initPayStatus();

	public abstract void checkReqeust(Req request);

	protected abstract void checkRequestParams(Req request);

	public String getTranName() {
		Map<String, String> list = new HashMap<String, String>();
		list.put(TranType.CONSUME_QUERY.getCode(), TranType.CONSUME_QUERY.getDesc());
		list.put(TranType.CONSUME_COMMIT.getCode(), TranType.CONSUME_COMMIT.getDesc());
		list.put(TranType.ONLINE_CONSUME.getCode(), TranType.ONLINE_CONSUME.getDesc());
		list.put(TranType.ONLINE_CONSUME_REVERSE.getCode(), TranType.ONLINE_CONSUME_REVERSE.getDesc());
		list.put(TranType.CANCEL.getCode(), TranType.CANCEL.getDesc());
		list.put(TranType.CANCEL_REVERSE.getCode(), TranType.CANCEL_REVERSE.getDesc());
		list.put(TranType.ONLINE_CANCEL.getCode(), TranType.ONLINE_CANCEL.getDesc());
		list.put(TranType.ONLINE_CANCEL_REVERSE.getCode(), TranType.ONLINE_CANCEL_REVERSE.getDesc());
		list.put(TranType.REFUND_ALL.getCode(), TranType.REFUND_ALL.getDesc());
		list.put(TranType.REFUND_PART.getCode(), TranType.REFUND_PART.getDesc());
		list.put(TranType.ONLINE_REFUND_ALL.getCode(), TranType.ONLINE_REFUND_ALL.getDesc());
		list.put(TranType.ONLINE_REFUND_PART.getCode(), TranType.ONLINE_REFUND_PART.getDesc());
		list.put(TranType.REFUND_REVERSE.getCode(), TranType.REFUND_REVERSE.getDesc());
		list.put(TranType.PRE_AUTH_CONSUME_QUERY.getCode(), TranType.PRE_AUTH_CONSUME_QUERY.getDesc());
		list.put(TranType.PRE_AUTH_CONSUME_CONFIRM.getCode(), TranType.PRE_AUTH_CONSUME_CONFIRM.getDesc());
		list.put(TranType.PRE_AUTH_CONSUME_CONFIRM_REVERSE.getCode(), TranType.PRE_AUTH_CONSUME_CONFIRM_REVERSE.getDesc());
		list.put(TranType.REFUND_QUERY.getCode(), TranType.REFUND_QUERY.getDesc());
		list.put(TranType.GIFT.getCode(), TranType.GIFT.getDesc());
		list.put(TranType.CANCEL_GIFT.getCode(), TranType.CANCEL_GIFT.getDesc());
		return list.get(this.initTranType());
	}
}
