package com.gl365.payment.service.wz.fft;
import java.io.Serializable;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.Gl365UserAccount;
import com.gl365.payment.remote.dto.account.response.QueryAccountBalanceInfoRespDTO;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.remote.dto.merchant.Gl365Merchant;
public class FftWxPayContext implements Serializable {
	private static final long serialVersionUID = 1L;
	private Gl365Merchant gl365Merchant;
	private Gl365User gl365User;
	private Gl365UserAccount gl365UserAccount;
	private QueryAccountBalanceInfoRespDTO gl365UserAccBalance;
	private PayStream payStream = new PayStream();
	private PayMain payMain = new PayMain();

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

	public Gl365UserAccount getGl365UserAccount() {
		return gl365UserAccount;
	}

	public void setGl365UserAccount(Gl365UserAccount gl365UserAccount) {
		this.gl365UserAccount = gl365UserAccount;
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

	public QueryAccountBalanceInfoRespDTO getGl365UserAccBalance() {
		return gl365UserAccBalance;
	}

	public void setGl365UserAccBalance(QueryAccountBalanceInfoRespDTO gl365UserAccBalance) {
		this.gl365UserAccBalance = gl365UserAccBalance;
	}
}
