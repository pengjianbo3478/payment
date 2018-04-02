package com.gl365.payment.dto.base;
import java.io.Serializable;
import java.util.List;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.Gl365UserAccount;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
public class PayContext implements Serializable {
	private static final long serialVersionUID = 1L;
	private TranType tranType;
	private String cardIndex;
	private String terminal;
	private String organCode;
	private String payId;
	private String requestId;
	private String payStreamRequestId;
	private Gl365UserAccount gl365UserAccount;
	private Gl365Merchant gl365Merchant;
	private Gl365User gl365User;
	private PayMain payMain;
	private PayStream payStream;
	private List<PayDetail> payDetails;
	private String merchantNo;
	private Gl365Merchant groupMerchant;
	
	public Gl365Merchant getGroupMerchant() {
		return groupMerchant;
	}

	public void setGroupMerchant(Gl365Merchant groupMerchant) {
		this.groupMerchant = groupMerchant;
	}

	public Gl365UserAccount getGl365UserAccount() {
		return gl365UserAccount;
	}

	public void setGl365UserAccount(Gl365UserAccount gl365UserAccount) {
		this.gl365UserAccount = gl365UserAccount;
	}

	public Gl365Merchant getGl365Merchant() {
		return gl365Merchant;
	}

	public void setGl365Merchant(Gl365Merchant gl365Merchant) {
		this.gl365Merchant = gl365Merchant;
	}

	public Gl365User getGl365User() {
		return gl365User;
	}

	public void setGl365User(Gl365User gl365User) {
		this.gl365User = gl365User;
	}

	public String getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public PayMain getPayMain() {
		return payMain;
	}

	public void setPayMain(PayMain payMain) {
		this.payMain = payMain;
	}

	public PayStream getPayStream() {
		return payStream;
	}

	public void setPayStream(PayStream payStream) {
		this.payStream = payStream;
	}

	public List<PayDetail> getPayDetails() {
		return payDetails;
	}

	public void setPayDetails(List<PayDetail> payDetails) {
		this.payDetails = payDetails;
	}

	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the payStreamRequestId
	 */
	public String getPayStreamRequestId() {
		return payStreamRequestId;
	}

	/**
	 * @param payStreamRequestId the payStreamRequestId to set
	 */
	public void setPayStreamRequestId(String payStreamRequestId) {
		this.payStreamRequestId = payStreamRequestId;
	}

	public TranType getTranType() {
		return tranType;
	}

	public void setTranType(TranType tranType) {
		this.tranType = tranType;
	}

	/**
	 * <p></p>
	 * @return the merchantNo
	 */
	public String getMerchantNo() {
		return merchantNo;
	}

	/**
	 * <p></p>
	 * @param merchantNo the merchantNo to set
	 */
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	/**
	 * @return the organCode
	 */
	public String getOrganCode() {
		return organCode;
	}

	/**
	 * @param organCode the organCode to set
	 */
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	/**
	 * @return the terminal
	 */
	public String getTerminal() {
		return terminal;
	}

	/**
	 * @param terminal the terminal to set
	 */
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
}
