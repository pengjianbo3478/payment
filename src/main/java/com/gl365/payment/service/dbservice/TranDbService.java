package com.gl365.payment.service.dbservice;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.dto.base.SuperContext;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.mapper.PayDetailMapper;
import com.gl365.payment.mapper.PayMainMapper;
import com.gl365.payment.mapper.PayPrepayMapper;
import com.gl365.payment.mapper.PayReturnMapper;
import com.gl365.payment.service.transaction.remote.RemoteMicroServiceAgent;
import com.gl365.payment.util.BigDecimalUtil;
/**
 * 事务处理
 * @author duanxz
 *2017年6月2日
 */
public abstract class TranDbService {
	@Autowired
	protected PayMainMapper payMainMapper;
	@Autowired
	protected PayDetailMapper payDetailMapper;
	@Autowired
	protected PayPrepayMapper payPrepayMapper;
	@Autowired
	protected PayReturnMapper payReturnMapper;
	@Autowired
	@Qualifier("remoteMicroServiceAgent")
	protected RemoteMicroServiceAgent remoteMicroServiceAgent;

	public boolean isNeedOperateBeanAmount(BigDecimal beanAmount, BigDecimal giftAmount) {
		if (BigDecimalUtil.GreaterThan0(beanAmount) || BigDecimalUtil.GreaterThan0(giftAmount)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * 提交数据到外围系统、提交数据到交易系统库
	 * @param bc
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public abstract boolean firstCommit(SuperContext bc) throws ServiceException;

	/**
	 * 提交数据到外围系统、提交数据到交易系统库(可能用不上)
	 * @param bc
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public abstract boolean secondCommit(SuperContext bc) throws ServiceException;
}
