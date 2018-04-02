package com.gl365.payment.service.pos.query.abs;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.gl365.payment.dto.pretranscation.PreTranContext;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.system.GlSystem;
import com.gl365.payment.model.PayPrepay;
public abstract class AbstractConsumeQueryInit extends AbstractPrepayMain {
	@Override
	public PayStatus initPayStatus() {
		return PayStatus.WAIT_PAY;
	}

	public void initPayDefault(PreTranContext ctx) {
		final BigDecimal cashAmount = ctx.getPreTranReqDTO().getTotalAmount();
		PayPrepay pp = ctx.getPayPrepay();
		pp.setPayId(ctx.getPayId());
		pp.setBeanAmount(BigDecimal.ZERO);
		pp.setCashAmount(cashAmount);
		pp.setGiftPoint(BigDecimal.ZERO);
		if (pp.getNoBenefitAmount() == null) pp.setNoBenefitAmount(BigDecimal.ZERO);
		pp.setTransType(this.initTranType());
		pp.setPayStatus(this.initPayStatus().getCode());
		pp.setPayDesc(this.initPayStatus().getDesc());
		LocalDateTime now = LocalDateTime.now();
		pp.setCreateTime(now);
		pp.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		pp.setModifyTime(now);
		pp.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		pp.setCoinAmount(BigDecimal.ZERO);
	}
}
