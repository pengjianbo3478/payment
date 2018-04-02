package com.gl365.payment.remote.dto.account.response;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.payment.util.Gl365StrUtils;
public class QueryAccountRespDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String version;
	/**
	 * 绑定ID
	 */
	private String bindId;
	/**
	 * 支付渠道
	 */
	private String organCode;
	/**
	 * 绑卡索引号
	 */
	private String cardIndex;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 卡类型-非空
	 */
	private String cardType;
	/**
	 * 脱敏的卡号-非空
	 */
	private String cardNo;
	/**
	 * 是否默认支付卡
	 */
	private String defaultPay;
	/**
	 * 绑卡状态-非空
	 */
	private String status;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifyTime;
	/**
	 * 会员ID-非空
	 */
	private String userId;
	/**
	 * 返回码
	 */
	private String resultCode;
	/**
	 * 返回描述
	 */
	private String resultDesc;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	/**支付渠道*/
	public String getOrganCode() {
		return organCode;
	}

	/**支付渠道*/
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getDefaultPay() {
		return defaultPay;
	}

	public void setDefaultPay(String defaultPay) {
		this.defaultPay = defaultPay;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(LocalDateTime modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
