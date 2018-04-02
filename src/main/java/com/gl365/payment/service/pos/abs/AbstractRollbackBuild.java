package com.gl365.payment.service.pos.abs;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gl365.payment.common.constants.PaymentConstants.PayType;
import com.gl365.payment.dto.rollback.RollbackContext;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.enums.system.Flag;
import com.gl365.payment.enums.system.GlSystem;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.IdGenerator;
public abstract class AbstractRollbackBuild extends AbstractRollbackCheck {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractRollbackBuild.class);

	public void buildPayDetails(RollbackContext ctx) {
		boolean igpmo = this.isGroupPayMainOrder(ctx.getPayMain());
		if (igpmo) ctx.setPayDetails(this.buildGroupMainOrderPayDetail(ctx));
		else ctx.setPayDetails(this.buildNpayOrderPayDetails(ctx));
	}

	private List<PayDetail> buildNpayOrderPayDetails(RollbackContext ctx) {
		List<PayDetail> payDetails = new ArrayList<PayDetail>();
		PayMain pm = ctx.getPayMain();
		String payId = ctx.getPayId();
		BigDecimal beanAmt = pm.getBeanAmount();
		BigDecimal cashAmt = pm.getCashAmount();
		if (beanAmt.compareTo(BigDecimal.ZERO) == 1) {
			PayDetail pdBean = new PayDetail();
			pdBean.setPayId(payId);
			pdBean.setOrganCode(pm.getOrganCode());
			pdBean.setPayAccount(pm.getUserId());
			pdBean.setPayType(PayType.PAY_BEAN.getCode());
			pdBean.setPayAmount(pm.getBeanAmount());
			pdBean.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
			pdBean.setCreateTime(LocalDateTime.now());
			payDetails.add(pdBean);
		}
		if (cashAmt.compareTo(BigDecimal.ZERO) == 1) {
			PayDetail pdCash = new PayDetail();
			pdCash.setPayId(payId);
			pdCash.setOrganCode(pm.getOrganCode());
			pdCash.setPayAccount(pm.getUserId());
			pdCash.setPayType(PayType.PAY_CASH.getCode());
			pdCash.setPayAmount(pm.getCashAmount());
			pdCash.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
			pdCash.setCreateTime(LocalDateTime.now());
			payDetails.add(pdCash);
		}
		return payDetails;
	}

	public boolean isGroupPayMainOrder(PayMain pm) {
		String orderType = pm.getOrderType();
		boolean isGroupPay = StringUtils.equals(orderType, OrderType.groupPay.getCode());
		if (isGroupPay) {
			String splitFlag = pm.getSplitFlag();
			return StringUtils.equals(SplitFlag.mainOrder.getCode(), splitFlag);
		}
		return false;
	}

	private List<PayDetail> buildGroupMainOrderPayDetail(RollbackContext ctx) {
		List<PayDetail> result = new ArrayList<PayDetail>();
		PayMain pm = ctx.getPayMain();
		String payId = ctx.getPayId();
		if (BigDecimalUtil.GreaterThan0(pm.getGroupPtPay())) {
			PayDetail ps = new PayDetail();
			ps.setPayId(payId);
			ps.setPayType(PayType.PAY_BEAN.getCode());
			ps.setOrganCode(pm.getOrganCode());
			ps.setPayAmount(pm.getGroupPtPay());
			ps.setPayAccount("1000001900343");
			ps.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
			ps.setCreateTime(LocalDateTime.now());
			result.add(ps);
		}
		if (BigDecimalUtil.GreaterThan0(pm.getGroupMainuserPayBean())) {
			PayDetail ps2 = new PayDetail();
			ps2.setPayId(payId);
			ps2.setPayType(PayType.PAY_BEAN.getCode());
			ps2.setOrganCode(pm.getOrganCode());
			ps2.setPayAccount(pm.getUserId());
			ps2.setPayAmount(pm.getGroupMainuserPayBean());
			ps2.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
			ps2.setCreateTime(LocalDateTime.now());
			result.add(ps2);
		}
		if (BigDecimalUtil.GreaterThan0(pm.getCashAmount())) {
			PayDetail ps3 = new PayDetail();
			ps3.setPayId(payId);
			ps3.setPayType(PayType.PAY_CASH.getCode());
			ps3.setOrganCode(pm.getOrganCode());
			ps3.setPayAmount(pm.getCashAmount());
			ps3.setPayAccount(pm.getUserId());
			ps3.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
			ps3.setCreateTime(LocalDateTime.now());
			result.add(ps3);
		}
		return result;
	}

	private void buildInit(RollbackContext ctx) {
		ctx.setPayStatus(this.initPayStatus());
	}

	public void buildPayStream(RollbackContext ctx) {
		this.buildInit(ctx);
		RollbackReqDTO request = ctx.getRollbackReqDTO();
		PayStream ps = new PayStream();
		ps.setPayId(ctx.getPayId());
		ps.setRequestId(request.getRequestId());
		ps.setRequestDate(request.getRequestDate());
		ps.setOrigRequestId(this.getPayStreamOrigRequestId(ctx));
		ps.setOrigPayDate(request.getOrigTxnDate());
		ps.setOrganCode(request.getOrganCode());
		ps.setOrganMerchantNo(request.getOrganMerchantNo());
		ps.setTerminal(request.getTerminal());
		ps.setOperator(StringUtils.EMPTY);
		ps.setTransType(this.initTranType());
		ps.setTotalAmount(request.getTotalAmount());
		ps.setReturnAmount(BigDecimal.ZERO);
		ps.setUniqueSerial(IdGenerator.getUuId32());
		ps.setDealStatus(DealStatus.SUCCESS.getCode());
		ps.setCreateTime(LocalDateTime.now());
		ps.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		ps.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		ps.setModifyTime(LocalDateTime.now());
		ctx.setPayStream(ps);
		LOG.debug("PayStream={}", Gl365StrUtils.toStr(ps));
	}

	public int updatePayMainStatus(RollbackContext ctx) {
		String payId = ctx.getPayMain().getPayId();
		PayMain payMain = new PayMain();
		payMain.setPayId(payId);
		payMain.setPayStatus(this.initPayStatus().getCode());
		payMain.setIsNotify(Flag.N.getCode());
		payMain.setPayDesc(this.initPayStatus().getDesc());
		payMain.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		payMain.setModifyTime(LocalDateTime.now());
		int res = this.payMainMapper.updateStatus(payMain);
		LOG.info("更新原单状态={}", res);
		return res;
	}
}
