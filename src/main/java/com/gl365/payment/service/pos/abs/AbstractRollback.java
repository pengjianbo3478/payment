package com.gl365.payment.service.pos.abs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.mapper.PayMainMapper;
import com.gl365.payment.service.check.CheckPayRequestService;
import com.gl365.payment.service.check.CheckRollbackService;
import com.gl365.payment.service.transaction.AbstractPay;
import com.gl365.payment.service.transaction.context.PayContextService;
public abstract class AbstractRollback extends AbstractPay<RollbackContext, RollbackReqDTO> {
	@Autowired
	@Qualifier("payContextService")
	public PayContextService payContextService;
	@Autowired
	public PayMainMapper payMainMapper;
	@Autowired
	public CheckPayRequestService checkPayRequestService;
	@Autowired
	public CheckRollbackService checkRollbackService;

	/**
	 * <p>查询原交易单</p>
	 * @param ctx
	 * @return
	 */
	public abstract void queryPayMian(RollbackContext ctx);

	/**
	 * <p>设置乐豆流水类别</p>
	 * <p>取DcType类值</p>
	 * @return
	 */
	public abstract String initDcType();

	/**
	 * 检查原单状态
	 * @param ctx
	 */
	public abstract void checkPayMianStatus(RollbackContext ctx);

	/**
	 * 调帐户系统的接口-余额交易(线下)扣乐豆-返利处理
	 * @param ctx
	 */
	public abstract void updateAccountBalance(RollbackContext ctx);

	public abstract void checkTransType(RollbackContext ctx);

	/**
	 * 设置日志交易类型
	 * @return
	 */
	public abstract TranType initLongTranType();
	
	
	public abstract RollbackRespDTO getResp(RollbackContext ctx);
	
	
	public abstract String getPayStreamOrigRequestId(RollbackContext ctx);
	
	public abstract boolean isSendResultMQ();
}
