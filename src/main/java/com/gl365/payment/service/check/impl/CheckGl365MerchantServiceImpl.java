package com.gl365.payment.service.check.impl;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.gl365.payment.enums.merchant.MerchantPayBean;
import com.gl365.payment.enums.merchant.MerchantStatus;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.MerchantRespException;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
import com.gl365.payment.service.check.CheckGl365MerchantService;
@Service
public class CheckGl365MerchantServiceImpl implements CheckGl365MerchantService {
	@Override
	public void check(Gl365Merchant gl365Merchant) {
		checkStatus(gl365Merchant);
		this.checkMerchantNo(gl365Merchant);
		this.checkMerchantShortname(gl365Merchant);
		this.checkAgentNo(gl365Merchant);
		checkCity(gl365Merchant);
		checkProvince(gl365Merchant);
		checkDistrict(gl365Merchant);
		checkSaleRate(gl365Merchant);
		checkGlfeeRate(gl365Merchant);
		checkGlFeeType(gl365Merchant);
		this.checkPosDebitFeeRate(gl365Merchant);
		this.checkPosDebitMaxAmt(gl365Merchant);
		this.checkPosCreditFeeRate(gl365Merchant);
		this.checkPosCreditMaxAmt(gl365Merchant);
		this.checkOnpayDebitFeeRate(gl365Merchant);
		this.checkOnpayDebitMaxAmt(gl365Merchant);
		this.checkOnpayCreditFeeRate(gl365Merchant);
		this.checkOnpayCreditMaxAmt(gl365Merchant);
		this.checkParentAgentNo(gl365Merchant);
		this.checkSettleOrganNo(gl365Merchant);
		this.checkLdSale(gl365Merchant);
		this.checkOnLinePay(gl365Merchant);
		this.checkParentMerchantNo(gl365Merchant);
		this.checksettleMerchantNo(gl365Merchant);
	}

	private void checkStatus(Gl365Merchant gl365Merchant) {
		String status = gl365Merchant.getStatus();
		boolean ms = StringUtils.equals(status, MerchantStatus.NORMAL.getCode());
		if (!ms) throw new MerchantRespException(Msg.PAY_8002.getCode(), Msg.PAY_8002.getDesc());
	}

	private void checkGlFeeType(Gl365Merchant gl365Merchant) {
		String glt = gl365Merchant.getGlFeeType();
		if (StringUtils.isEmpty(glt)) throw new MerchantRespException(Msg.REMOTE_MER_GlFeeType.getCode(), Msg.REMOTE_MER_GlFeeType.getDesc());
	}

	private void checkGlfeeRate(Gl365Merchant gl365Merchant) {
		BigDecimal glFeeRate = gl365Merchant.getGlFeeRate();
		if (glFeeRate == null) throw new MerchantRespException(Msg.REMOTE_MER_GlFeeRate.getCode(), Msg.REMOTE_MER_GlFeeRate.getDesc());
	}

	private void checkSaleRate(Gl365Merchant gl365Merchant) {
		BigDecimal saleRate = gl365Merchant.getSaleRate();
		if (saleRate == null) throw new MerchantRespException(Msg.REMOTE_MER_SaleRate.getCode(), Msg.REMOTE_MER_SaleRate.getDesc());
		boolean m = saleRate.compareTo(new BigDecimal(99)) == 1;
		if (m) throw new MerchantRespException(Msg.REMOTE_MER_SaleRate.getCode(), Msg.REMOTE_MER_SaleRate.getDesc());
	}

	private void checkDistrict(Gl365Merchant gl365Merchant) {
		Short dct = gl365Merchant.getDistrict();
		if (dct == null) throw new MerchantRespException(Msg.REMOTE_MER_District.getCode(), Msg.REMOTE_MER_District.getDesc());
	}

	private void checkProvince(Gl365Merchant gl365Merchant) {
		Short pe = gl365Merchant.getProvince();
		if (pe == null) throw new MerchantRespException(Msg.REMOTE_MER_Province.getCode(), Msg.REMOTE_MER_Province.getDesc());
	}

	private void checkCity(Gl365Merchant gl365Merchant) {
		Short city = gl365Merchant.getCity();
		if (city == null) throw new MerchantRespException(Msg.REMOTE_MER_City.getCode(), Msg.REMOTE_MER_City.getDesc());
	}

	private void checkMerchantNo(Gl365Merchant gl365Merchant) {
		String mn = gl365Merchant.getMerchantNo();
		if (StringUtils.isEmpty(mn)) throw new MerchantRespException(Msg.REMOTE_MER_MerchantNo.getCode(), Msg.REMOTE_MER_MerchantNo.getDesc());
	}

	private void checkMerchantShortname(Gl365Merchant gl365Merchant) {
		String msn = gl365Merchant.getMerchantShortname();
		if (StringUtils.isEmpty(msn)) throw new MerchantRespException(Msg.REMOTE_MER_MerchantShortname.getCode(), Msg.REMOTE_MER_MerchantShortname.getDesc());
	}

	private void checkAgentNo(Gl365Merchant gl365Merchant) {
		String an = gl365Merchant.getAgentNo();
		if (StringUtils.isEmpty(an)) throw new MerchantRespException(Msg.REMOTE_MER_AgentNo.getCode(), Msg.REMOTE_MER_AgentNo.getDesc());
	}

	private void checkSettleOrganNo(Gl365Merchant gl365Merchant) {
		String son = gl365Merchant.getSettleOrganNo();
		if (StringUtils.isEmpty(son)) throw new MerchantRespException(Msg.REMOTE_MER_SettleOrganNo.getCode(), Msg.REMOTE_MER_SettleOrganNo.getDesc());
	}

	private void checkParentAgentNo(Gl365Merchant gl365Merchant) {
		String pan = gl365Merchant.getParentAgentNo();
		if (StringUtils.isEmpty(pan)) throw new MerchantRespException(Msg.REMOTE_MER_ParentAgentNo.getCode(), Msg.REMOTE_MER_ParentAgentNo.getDesc());
	}

	private void checkPosDebitFeeRate(Gl365Merchant gl365Merchant) {
		BigDecimal val = gl365Merchant.getPosDebitFeeRate();
		if (val == null) throw new MerchantRespException(Msg.REMOTE_MER_PosDebitFeeRate.getCode(), Msg.REMOTE_MER_PosDebitFeeRate.getDesc());
		boolean b = val.compareTo(BigDecimal.ZERO) == 1;
		if (!b) throw new MerchantRespException(Msg.REMOTE_MER_PosDebitFeeRate.getCode(), Msg.REMOTE_MER_PosDebitFeeRate.getDesc());
	}

	private void checkPosDebitMaxAmt(Gl365Merchant gl365Merchant) {
		BigDecimal val = gl365Merchant.getPosDebitMaxAmt();
		if (val == null) throw new MerchantRespException(Msg.REMOTE_MER_PosDebitMaxAmt.getCode(), Msg.REMOTE_MER_PosDebitMaxAmt.getDesc());
	}

	private void checkPosCreditFeeRate(Gl365Merchant gl365Merchant) {
		BigDecimal val = gl365Merchant.getPosCreditFeeRate();
		if (val == null) throw new MerchantRespException(Msg.REMOTE_MER_PosCreditFeeRate.getCode(), Msg.REMOTE_MER_PosCreditFeeRate.getDesc());
		boolean b = val.compareTo(BigDecimal.ZERO) == 1;
		if (!b) throw new MerchantRespException(Msg.REMOTE_MER_PosCreditFeeRate.getCode(), Msg.REMOTE_MER_PosCreditFeeRate.getDesc());
	}

	private void checkPosCreditMaxAmt(Gl365Merchant gl365Merchant) {
		BigDecimal val = gl365Merchant.getPosCreditMaxAmt();
		if (val == null) throw new MerchantRespException(Msg.REMOTE_MER_PosCreditMaxAmt.getCode(), Msg.REMOTE_MER_PosCreditMaxAmt.getDesc());
	}

	private void checkOnpayDebitFeeRate(Gl365Merchant gl365Merchant) {
		BigDecimal val = gl365Merchant.getOnpayDebitFeeRate();
		if (val == null) throw new MerchantRespException(Msg.REMOTE_MER_OnpayDebitFeeRate.getCode(), Msg.REMOTE_MER_OnpayDebitFeeRate.getDesc());
		boolean b = val.compareTo(BigDecimal.ZERO) == 1;
		if (!b) throw new MerchantRespException(Msg.REMOTE_MER_OnpayDebitFeeRate.getCode(), Msg.REMOTE_MER_OnpayDebitFeeRate.getDesc());
	}

	private void checkOnpayDebitMaxAmt(Gl365Merchant gl365Merchant) {
		BigDecimal val = gl365Merchant.getOnpayDebitMaxAmt();
		if (val == null) throw new MerchantRespException(Msg.REMOTE_MER_OnpayDebitMaxAmt.getCode(), Msg.REMOTE_MER_OnpayDebitMaxAmt.getDesc());
	}

	private void checkOnpayCreditFeeRate(Gl365Merchant gl365Merchant) {
		BigDecimal val = gl365Merchant.getOnpayCreditFeeRate();
		if (val == null) throw new MerchantRespException(Msg.REMOTE_MER_OnpayCreditFeeRate.getCode(), Msg.REMOTE_MER_OnpayCreditFeeRate.getDesc());
		boolean b = val.compareTo(BigDecimal.ZERO) == 1;
		if (!b) throw new MerchantRespException(Msg.REMOTE_MER_OnpayCreditFeeRate.getCode(), Msg.REMOTE_MER_OnpayCreditFeeRate.getDesc());
	}

	private void checkOnpayCreditMaxAmt(Gl365Merchant gl365Merchant) {
		BigDecimal val = gl365Merchant.getOnpayCreditMaxAmt();
		if (val == null) throw new MerchantRespException(Msg.REMOTE_MER_OnpayCreditMaxAmt.getCode(), Msg.REMOTE_MER_OnpayCreditMaxAmt.getDesc());
	}

	private void checkLdSale(Gl365Merchant gl365Merchant) {
		String val = gl365Merchant.getLdSale();
		if (StringUtils.isEmpty(val)) throw new MerchantRespException(Msg.REMOTE_MER_LdSale.getCode(), Msg.REMOTE_MER_LdSale.getDesc());
		boolean b = StringUtils.equals(val, MerchantPayBean.OFF.getKey());
		if (b) throw new MerchantRespException(Msg.REMOTE_MER_LdSale_OFF.getCode(), Msg.REMOTE_MER_LdSale_OFF.getDesc());
	}

	private void checkOnLinePay(Gl365Merchant gl365Merchant) {
		String val = gl365Merchant.getOnLinePayment();
		if (StringUtils.isEmpty(val)) throw new MerchantRespException(Msg.REMOTE_MER_ONlINE_PAY.getCode(), Msg.REMOTE_MER_ONlINE_PAY.getDesc());
	}

	private void checkParentMerchantNo(Gl365Merchant gl365Merchant) {
		String val = gl365Merchant.getParentMerchantNo();
		boolean b = StringUtils.isEmpty(val) || val.length() > 15;
		if (b) throw new MerchantRespException(Msg.PARENT_MERCHANT_NO.getCode(), Msg.PARENT_MERCHANT_NO.getDesc());
	}

	private void checksettleMerchantNo(Gl365Merchant gl365Merchant) {
		String val = gl365Merchant.getSettleMerchant();
		boolean b = StringUtils.isEmpty(val) || val.length() > 15;
		if (b) throw new MerchantRespException(Msg.SETTLE_MERCHANT_NO.getCode(), Msg.SETTLE_MERCHANT_NO.getDesc());
	}
}
