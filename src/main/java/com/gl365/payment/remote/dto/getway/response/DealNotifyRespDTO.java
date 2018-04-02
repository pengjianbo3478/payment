package com.gl365.payment.remote.dto.getway.response;
import java.io.Serializable;
import java.math.BigDecimal;
import com.gl365.payment.util.Gl365StrUtils;
public class DealNotifyRespDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String interfaceType; // 通知1，查询结果2
	private String merchantCode;// 商户号 AN15
	private String terminalCode;// 终端号 AN8
	private BigDecimal totalFee;// 消费金额 N10
	private String cardIndex;// 卡号索引 AN12
	private String origPayId;// 付费通原交易流水 AN20
	private String origTransDate;// 付费通原交易日期 YYYYMMDD
	private String origReturnCode;// 原交易应答码 N2 付费通填写
									// 00为交易成功，但不表示可以结算该交易，服务商还需根据以下 8，9 域进行判断
	private String withdrawFlag;// 撤销标志 N1 付费通填写 0为未撤销 1为已撤销
	private String sectorFlag;// 冲正标志 N1 付费通填写 0为未冲正，1为已冲正
	private String remark;// 备注（广告） ANS200
	private String expand1;// 扩展1
	private String expand2;// 扩展2
	private String expand3;// 扩展3

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public String getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

	public String getOrigPayId() {
		return origPayId;
	}

	public void setOrigPayId(String origPayId) {
		this.origPayId = origPayId;
	}

	public String getOrigTransDate() {
		return origTransDate;
	}

	public void setOrigTransDate(String origTransDate) {
		this.origTransDate = origTransDate;
	}

	public String getOrigReturnCode() {
		return origReturnCode;
	}

	public void setOrigReturnCode(String origReturnCode) {
		this.origReturnCode = origReturnCode;
	}

	public String getWithdrawFlag() {
		return withdrawFlag;
	}

	public void setWithdrawFlag(String withdrawFlag) {
		this.withdrawFlag = withdrawFlag;
	}

	public String getSectorFlag() {
		return sectorFlag;
	}

	public void setSectorFlag(String sectorFlag) {
		this.sectorFlag = sectorFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExpand1() {
		return expand1;
	}

	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}

	public String getExpand2() {
		return expand2;
	}

	public void setExpand2(String expand2) {
		this.expand2 = expand2;
	}

	public String getExpand3() {
		return expand3;
	}

	public void setExpand3(String expand3) {
		this.expand3 = expand3;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toMultiLineStr(this);
	}
}
