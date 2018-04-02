package com.gl365.payment.service.pos.pre.confirm.abs;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.authconsumeconfirm.request.AuthConsumeConfirmReqDTO;
import com.gl365.payment.dto.authconsumeconfirm.response.AuthConsumeConfirmRespDTO;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayPrepay;
public abstract class AbstractPosPreAuthConsumeConfirm extends AbstractPosPreAuthConsumeConfirmBuild {
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public void query(BaseContext<AuthConsumeConfirmReqDTO, AuthConsumeConfirmRespDTO> bc) throws ServiceException {
		// 通过查询号查询支付预交易表
		PayPrepay payPrepay = payPrepayMapper.queryByPayId(bc.getRequest().getOrigPayId());
		if (payPrepay != null) bc.setPayPrepay(payPrepay);
	}
}
