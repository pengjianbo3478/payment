package com.gl365.payment.service.check.impl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.CardType;
import com.gl365.payment.enums.user.UserAccountStatus;
import com.gl365.payment.exception.AccountRespException;
import com.gl365.payment.remote.dto.account.Gl365UserAccount;
import com.gl365.payment.service.check.CheckGl365UserAccountService;
@Service
public class CheckGl365UserAccountServiceImpl implements CheckGl365UserAccountService {
	@Override
	public void check(Gl365UserAccount gl365UserAccount) {
		checkNull(gl365UserAccount);
		checkUserId(gl365UserAccount);
		checkCardNo(gl365UserAccount);
		checkCardType(gl365UserAccount);
		checkStatus(gl365UserAccount);
	}

	private void checkNull(Gl365UserAccount gl365UserAccount) {
		if (gl365UserAccount == null) throw new AccountRespException(Msg.REMOTE_ACC_4000.getCode(), Msg.REMOTE_ACC_4000.getDesc());
	}

	private void checkUserId(Gl365UserAccount gl365UserAccount) {
		String userId = gl365UserAccount.getUserId();
		if (StringUtils.isEmpty(userId)) throw new AccountRespException(Msg.REMOTE_ACC_4001.getCode(), Msg.REMOTE_ACC_4001.getDesc());
	}

	private void checkStatus(Gl365UserAccount gl365UserAccount) {
		String status = gl365UserAccount.getStatus();
		if (StringUtils.isEmpty(status)) throw new AccountRespException(Msg.REMOTE_ACC_4005.getCode(), Msg.REMOTE_ACC_4005.getDesc());
		boolean b = StringUtils.equals(status, UserAccountStatus.NORMAL.getCode());
		if (!b) throw new AccountRespException(Msg.REMOTE_ACC_4006.getCode(), Msg.REMOTE_ACC_4006.getDesc());
	}

	private void checkCardNo(Gl365UserAccount gl365UserAccount) {
		String cardNo = gl365UserAccount.getCardNo();
		if (StringUtils.isEmpty(cardNo)) throw new AccountRespException(Msg.REMOTE_ACC_4003.getCode(), Msg.REMOTE_ACC_4003.getDesc());
	}

	private void checkCardType(Gl365UserAccount gl365UserAccount) {
		String val = gl365UserAccount.getCardType();
		if (StringUtils.isEmpty(val)) throw new AccountRespException(Msg.REMOTE_ACC_4004.getCode(), Msg.REMOTE_ACC_4004.getDesc());
		boolean c = StringUtils.equals(val, CardType.C.getCode());
		boolean d = StringUtils.equals(val, CardType.D.getCode());
		if (!c && !d) throw new AccountRespException(Msg.REMOTE_ACC_4007.getCode(), Msg.REMOTE_ACC_4007.getDesc());
	}
}
