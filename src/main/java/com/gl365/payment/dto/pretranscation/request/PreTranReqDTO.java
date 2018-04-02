package com.gl365.payment.dto.pretranscation.request;
import java.math.BigDecimal;
import com.gl365.payment.dto.base.request.PayReqDTO;
import com.gl365.payment.util.Gl365StrUtils;
public class PreTranReqDTO extends PayReqDTO {
	private static final long serialVersionUID = 1L;
	private String scene;
	private String merchantOrderTitle;
	private String merchantOrderDesc;
	private BigDecimal noBenefitAmount;
	private String merchantOrderNo;

	/**
	 * <p>支付场景	String	2	是</p>
	 * @return the scene
	 */
	public String getScene() {
		return scene;
	}

	/**
	 * <p>支付场景	String	2	是</p>
	 * @param scene the scene to set
	 */
	public void setScene(String scene) {
		this.scene = scene;
	}

	/**
	 * <p>订单标题	String	128	是</p>
	 * @return the merchantOrderTitle
	 */
	public String getMerchantOrderTitle() {
		return merchantOrderTitle;
	}

	/**
	 * <p>订单标题	String	128	是</p>
	 * @param merchantOrderTitle the merchantOrderTitle to set
	 */
	public void setMerchantOrderTitle(String merchantOrderTitle) {
		this.merchantOrderTitle = merchantOrderTitle;
	}

	/**
	 * <p>订单描述	String	512	是</p>
	 * @return the merchantOrderDesc
	 */
	public String getMerchantOrderDesc() {
		return merchantOrderDesc;
	}

	/**
	 * <p>订单描述	String	512	是</p>
	 * @param merchantOrderDesc the merchantOrderDesc to set
	 */
	public void setMerchantOrderDesc(String merchantOrderDesc) {
		this.merchantOrderDesc = merchantOrderDesc;
	}

	/**
	 * <p>不可返利金额	BigDecimal		否</p>
	 * @return the noBenefitAmount
	 */
	public BigDecimal getNoBenefitAmount() {
		return noBenefitAmount;
	}

	/**
	 * <p>不可返利金额	BigDecimal		否</p>
	 * @param noBenefitAmount the noBenefitAmount to set
	 */
	public void setNoBenefitAmount(BigDecimal noBenefitAmount) {
		this.noBenefitAmount = noBenefitAmount;
	}

	/**
	 * <p>给乐订单号	String	32	是</p>
	 * @return the merchantOrderNo
	 */
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	/**
	 * <p>给乐订单号	String	32	是</p>
	 * @param merchantOrderNo the merchantOrderNo to set
	 */
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
