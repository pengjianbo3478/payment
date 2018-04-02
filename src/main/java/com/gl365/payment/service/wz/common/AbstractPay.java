package com.gl365.payment.service.wz.common;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.gl365.payment.enums.pay.PayChannleType;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.service.check.CheckPayRequestService;
import com.gl365.payment.service.dbservice.PayMainService;
import com.gl365.payment.service.dbservice.PayStreamService;
import com.gl365.payment.service.transaction.context.PayContextService;
public abstract class AbstractPay {
	@Autowired
	public Gl365UserService gl365UserService;
	@Autowired
	public Gl365MerchantService gl365MerchantService;
	@Autowired
	public Gl365UserAccountService gl365UserAccountService;
	@Autowired
	public CheckPayRequestService checkPayRequestService;
	@Autowired
	public PayStreamService payStreamService;
	@Autowired
	public PayMainService payMainService;
	@Autowired
	public PayContextService payContextService;

	public abstract TranType initTranType();

	public abstract PayStatus initPayStatus();

	public abstract String initPayCategoryCode();

	public abstract int totalStep();

	public BigDecimal leftTwoDecimal(BigDecimal amount) {
		if (amount == null) amount = BigDecimal.ZERO;
		return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public String getLog(int s, String c) {
		StringBuffer sb = new StringBuffer("[").append(this.totalStep()).append("-").append(s).append("]");
		sb.append("-").append(c);
		return sb.toString();
	}

	public String getPayChannleType(String scene) {
		boolean a = StringUtils.equals(scene, Scene.WX_PAY_PUB.getCode());
		if (a) return PayChannleType.wxpub.getCode();
		boolean b = StringUtils.equals(scene, Scene.WX_PAY_H5.getCode());
		if (b) return PayChannleType.H5.getCode();
		return null;
	}
}
