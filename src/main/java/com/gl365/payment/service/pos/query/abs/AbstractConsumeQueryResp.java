package com.gl365.payment.service.pos.query.abs;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gl365.payment.dto.pretranscation.PreTranContext;
import com.gl365.payment.dto.pretranscation.request.PreTranReqDTO;
import com.gl365.payment.dto.pretranscation.response.PreTranRespDTO;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.util.Gl365StrUtils;

public abstract class AbstractConsumeQueryResp extends AbstractConsumeQueryInit {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractConsumeQueryResp.class);

	public PreTranRespDTO buildResp(PreTranContext ctx) {
		PreTranRespDTO resp = new PreTranRespDTO();
		PreTranReqDTO req = ctx.getPreTranReqDTO();
		Gl365Merchant merchant = ctx.getGl365Merchant();
		PayPrepay pp = ctx.getPayPrepay();
		resp.setOrganCode(req.getOrganCode());// 机构代码
		resp.setOrganMerchantNo(merchant.getMerchantNo());// 商户号
		resp.setTerminal(req.getTerminal());// 终端号
		resp.setOrigRequestId(req.getRequestId());// 原请求交易流水号
		resp.setCardIndex(req.getCardIndex());// 绑卡索引号
		resp.setPayId(pp.getPayId());// 给乐流水号
		resp.setTotalMoney(leftTwoDecimal(resp.getTotalMoney()));// 消费金额
		resp.setMarketFee(leftTwoDecimal(pp.getMarcketFee()));// 营销费
		resp.setCoinAmount(leftTwoDecimal(pp.getCoinAmount()));// 乐币
		resp.setBeanAmount(leftTwoDecimal(pp.getBeanAmount()));// 乐豆
		resp.setCashMoney(leftTwoDecimal(pp.getCashAmount()));// 实扣金额
		resp.setGiftAmount(leftTwoDecimal(pp.getGiftAmount()));// 赠送金额
		resp.setGiftPoint(leftTwoDecimal(pp.getGiftPoint()));// 赠送积分
		resp.setTxnDate(LocalDate.now());// 交易日期
		resp.setTotalMoney(leftTwoDecimal(pp.getTotalAmount()));
		String payStatus = this.initPayStatus().getCode();
		resp.setPayStatus(payStatus);// 返回代码
		resp.setPayDesc(this.initPayStatus().getDesc());// 返回描述
		LOG.debug("result={}", Gl365StrUtils.toStr(resp));
		return resp;
	}

	/**
	 * 保留两位小数,四舍五入
	 * 
	 * @param amount
	 * @return
	 */
	public BigDecimal leftTwoDecimal(BigDecimal amount) {
		if (amount == null)
			amount = BigDecimal.ZERO;
		return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}
