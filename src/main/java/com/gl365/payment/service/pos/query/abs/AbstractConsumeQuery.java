package com.gl365.payment.service.pos.query.abs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.service.dbservice.PayPrepayService;
import com.gl365.payment.util.Gl365StrUtils;
public abstract class AbstractConsumeQuery extends AbstractConsumeQueryRemote {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractConsumeQuery.class);
	@Autowired
	private PayPrepayService payPrepayService;

	@Transactional(propagation = Propagation.REQUIRED)
	public int savePrepay(PayPrepay payPrepay) {
		LOG.debug("payPrepay={}", Gl365StrUtils.toStr(payPrepay));
		return this.payPrepayService.save(payPrepay);
	}
}
