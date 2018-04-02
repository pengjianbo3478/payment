package com.gl365.payment.service.base;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.gl365.payment.dto.authconsumeconfirm.request.AuthConsumeConfirmReqDTO;
import com.gl365.payment.dto.authconsumeconfirm.response.AuthConsumeConfirmRespDTO;
import com.gl365.payment.dto.consumeconfirm.request.ConsumeConfirmReqDTO;
import com.gl365.payment.dto.consumeconfirm.response.ConsumeConfirmRespDTO;
import com.gl365.payment.dto.pretranscation.request.PreTranReqDTO;
import com.gl365.payment.dto.pretranscation.response.PreTranRespDTO;
import com.gl365.payment.dto.rollback.request.RollbackReqDTO;
import com.gl365.payment.dto.rollback.response.RollbackRespDTO;
import com.gl365.payment.enums.pay.PayStatus;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.service.pos.cancel.PosConsumeCancelService;
import com.gl365.payment.service.pos.cancelreverse.PosConsumeCancelReverseService;
import com.gl365.payment.service.pos.confirm.ConsumeConfirmService;
import com.gl365.payment.service.pos.pre.confirm.PosPreAuthConsumeConfirm;
import com.gl365.payment.service.pos.pre.query.PosPreAuthQueryService;
import com.gl365.payment.service.pos.pre.reverse.PosPreAuthReverseService;
import com.gl365.payment.service.pos.query.PosConsumeQueryService;
import com.gl365.payment.service.pos.reverse.PosConsumeReverseService;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.IdGenerator;
public class BasePosServiceTest extends BaseServiceTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(BasePosServiceTest.class);
	@Autowired
	public PosConsumeQueryService posConsumeQueryService;
	@Autowired
	public ConsumeConfirmService consumeConfirmService;
	public PreTranReqDTO preTranReqDTO = new PreTranReqDTO();
	public ConsumeConfirmReqDTO consumeConfirmDTO = new ConsumeConfirmReqDTO();
	@Autowired
	public PosConsumeCancelService posConsumeCancelService;
	public RollbackReqDTO consumeCancelDTO = new RollbackReqDTO();
	@Autowired
	public PosConsumeCancelReverseService posConsumeCancelReverseService;
	public RollbackReqDTO consumeCancelReverseDTO = new RollbackReqDTO();
	@Autowired
	public PosConsumeReverseService posConsumeReverseService;
	public RollbackReqDTO consumeReverseDTO = new RollbackReqDTO();
	
	
	@Autowired
	public PosPreAuthQueryService posPreAuthService;
	public PreTranReqDTO preAuthTranReqDTO = new PreTranReqDTO();
	@Autowired
	public PosPreAuthConsumeConfirm posPreAuthConsumeConfirm;
	public AuthConsumeConfirmReqDTO authConsumeConfirmReqDTO = new AuthConsumeConfirmReqDTO();
	@Autowired
	public PosPreAuthReverseService posPreAuthReverseService;
	public RollbackReqDTO preAuthReverseDTO = new RollbackReqDTO();

	@Before
	public void before() throws ServiceException {
		this.preTranReqDTO.setRequestId(this.getRequestId());
		this.preTranReqDTO.setRequestDate(LocalDate.now());
		this.preTranReqDTO.setOrganCode(this.organCode);
		this.preTranReqDTO.setOrganMerchantNo(this.organMerchantNo);
		this.preTranReqDTO.setTerminal(this.terminal);
		this.preTranReqDTO.setCardIndex(this.cardIndex);
		this.preTranReqDTO.setMerchantOrderNo(IdGenerator.getUuId32());
		this.preTranReqDTO.setMerchantOrderTitle(this.title);
		this.preTranReqDTO.setMerchantOrderDesc(this.desc);
		this.preTranReqDTO.setOperator(this.operator);
		this.preTranReqDTO.setScene(Scene.POS_PAY.getCode());
		this.preTranReqDTO.setTotalAmount(new BigDecimal(10));
		this.preTranReqDTO.setNoBenefitAmount(BigDecimal.ZERO);
		this.consumeConfirmDTO.setOrganCode(this.organCode);
		this.consumeConfirmDTO.setRequestId(this.getRequestId());
		this.consumeConfirmDTO.setRequestDate(this.requestDate);
		this.consumeConfirmDTO.setOrganMerchantNo(this.organMerchantNo);
		this.consumeConfirmDTO.setTerminal(this.terminal);
		this.consumeConfirmDTO.setCardIndex(this.cardIndex);
		this.consumeConfirmDTO.setNoBenefitAmount(BigDecimal.ZERO);
		this.consumeCancelDTO.setOrganCode(this.organCode);
		this.consumeCancelDTO.setOrganMerchantNo(this.organMerchantNo);
		this.consumeCancelDTO.setRequestId(this.getRequestId());
		this.consumeCancelDTO.setRequestDate(LocalDate.now());
		this.consumeCancelDTO.setCardIndex(this.cardIndex);
		this.consumeCancelDTO.setTerminal(this.terminal);
		this.consumeCancelDTO.setOrigTxnDate(LocalDate.now());
		this.consumeCancelReverseDTO.setOrganCode(this.organCode);
		this.consumeCancelReverseDTO.setRequestId(this.getRequestId());
		this.consumeCancelReverseDTO.setRequestDate(LocalDate.now());
		this.consumeCancelReverseDTO.setOrganMerchantNo(this.organMerchantNo);
		this.consumeCancelReverseDTO.setTerminal(this.terminal);
		this.consumeCancelReverseDTO.setCardIndex(this.cardIndex);
		this.consumeCancelReverseDTO.setTotalAmount(new BigDecimal(10));
		this.consumeCancelReverseDTO.setOrigTxnDate(LocalDate.now());
		this.consumeReverseDTO.setOrganCode(this.organCode);
		this.consumeReverseDTO.setRequestId(this.requestId);
		this.consumeReverseDTO.setRequestDate(LocalDate.now());
		this.consumeReverseDTO.setOrganMerchantNo(this.organMerchantNo);
		this.consumeReverseDTO.setTerminal(this.terminal);
		this.consumeReverseDTO.setCardIndex(this.cardIndex);
		this.consumeReverseDTO.setOrigTxnDate(LocalDate.now());
	}
	
	public ConsumeConfirmRespDTO consumeConfirm(PreTranRespDTO preTranRespDTO) {
		ConsumeConfirmRespDTO consumeConfirmRespDTO = new ConsumeConfirmRespDTO(PayStatus.COMPLETE_PAY.getCode(), PayStatus.COMPLETE_PAY.getDesc());
		this.consumeConfirmDTO.setOrigPayId(preTranRespDTO.getPayId());
		this.consumeConfirmDTO.setTotalAmount(preTranRespDTO.getTotalMoney());
		this.consumeConfirmDTO.setCashMoney(preTranRespDTO.getCashMoney());
		this.consumeConfirmDTO.setBeanAmount(preTranRespDTO.getBeanAmount());
		this.consumeConfirmDTO.setMarketFee(preTranRespDTO.getMarketFee());
		this.consumeConfirmDTO.setCoinAmount(preTranRespDTO.getCoinAmount());
		this.consumeConfirmDTO.setGiftAmount(preTranRespDTO.getGiftAmount());
		this.consumeConfirmDTO.setGiftPoint(preTranRespDTO.getGiftPoint());
		consumeConfirmRespDTO = this.consumeConfirmService.consumeConfirm(consumeConfirmDTO, consumeConfirmRespDTO);
		LOG.debug("#####consumeConfirm result={}", Gl365StrUtils.toMultiLineStr(consumeConfirmRespDTO));
		return consumeConfirmRespDTO;
	}

	public RollbackRespDTO consumeCancel(ConsumeConfirmRespDTO consumeConfirmRespDTO) {
		this.consumeCancelDTO.setTotalAmount(new BigDecimal(10));
		this.consumeCancelDTO.setOrigPayId(consumeConfirmRespDTO.getPayId());
		RollbackRespDTO cancelResult = this.posConsumeCancelService.execute(consumeCancelDTO);
		LOG.debug("#####consumeCancel result={}", Gl365StrUtils.toMultiLineStr(cancelResult));
		return cancelResult;
	}

	public void consumeCancelReverse(RollbackRespDTO rollbackRespDTO) {
		this.consumeCancelReverseDTO.setOrigRequestId(rollbackRespDTO.getRequestId());
		RollbackRespDTO calcelReverseResult = this.posConsumeCancelReverseService.execute(consumeCancelReverseDTO);
		LOG.debug("#####consumeCancelReverse result={}", Gl365StrUtils.toMultiLineStr(calcelReverseResult));
	}

	public void consumeReverse(ConsumeConfirmRespDTO consumeConfirmRespDTO) {
		this.consumeReverseDTO.setOrigRequestId(consumeConfirmRespDTO.getRequestId());
		this.consumeReverseDTO.setTotalAmount(consumeConfirmRespDTO.getTotalMoney());
		RollbackRespDTO respDTO = this.posConsumeReverseService.execute(this.consumeReverseDTO);
		LOG.info("######consumeReverse result={}", Gl365StrUtils.toMultiLineStr(respDTO));
	}
	
	public PreTranRespDTO consumeQuery() {
		PreTranRespDTO preTranRespDTO = this.posConsumeQueryService.execute(preTranReqDTO);
		LOG.debug("#####query result={}", Gl365StrUtils.toMultiLineStr(preTranRespDTO));
		return preTranRespDTO;
	}
	
	public AuthConsumeConfirmRespDTO posPreAuthConsumeConfirm(PreTranRespDTO preTranRespDTO) {
		AuthConsumeConfirmRespDTO authConsumeConfirmRespDTO = new AuthConsumeConfirmRespDTO(PayStatus.WAIT_PAY.getCode(), PayStatus.WAIT_PAY.getDesc());
		this.authConsumeConfirmReqDTO.setRequestId(this.getRequestId());
		this.authConsumeConfirmReqDTO.setRequestDate(LocalDateTime.now().toString("yyyyMMdd"));
		this.authConsumeConfirmReqDTO.setOrganCode(this.organCode);
		this.authConsumeConfirmReqDTO.setOrganMerchantNo(this.organMerchantNo);
		this.authConsumeConfirmReqDTO.setTerminal(this.terminal);
		this.authConsumeConfirmReqDTO.setCardIndex(this.cardIndex);
		this.authConsumeConfirmReqDTO.setTotalAmount(preTranRespDTO.getTotalMoney());
		this.authConsumeConfirmReqDTO.setCashMoney(preTranRespDTO.getCashMoney());
		this.authConsumeConfirmReqDTO.setBeanAmount(preTranRespDTO.getBeanAmount());
		this.authConsumeConfirmReqDTO.setMarketFee(preTranRespDTO.getMarketFee());
		this.authConsumeConfirmReqDTO.setCoinAmount(preTranRespDTO.getCoinAmount());
		this.authConsumeConfirmReqDTO.setGiftAmount(preTranRespDTO.getGiftAmount());
		this.authConsumeConfirmReqDTO.setGiftPoint(preTranRespDTO.getGiftPoint());
		this.authConsumeConfirmReqDTO.setOrigPayId(preTranRespDTO.getPayId());
		authConsumeConfirmRespDTO = this.posPreAuthConsumeConfirm.authConsumeConfirm(authConsumeConfirmReqDTO, authConsumeConfirmRespDTO);
		LOG.info("######posPreAuthConsumeConfirm={}", Gl365StrUtils.toMultiLineStr(authConsumeConfirmRespDTO));
		return authConsumeConfirmRespDTO;
	}

	public void posPreAuthReverse(AuthConsumeConfirmRespDTO authConsumeConfirmRespDTO) {
		this.preAuthReverseDTO.setTotalAmount(authConsumeConfirmRespDTO.getTotalMoney());
		this.preAuthReverseDTO.setOrigRequestId(authConsumeConfirmRespDTO.getRequestId());
		RollbackRespDTO result = this.posPreAuthReverseService.execute(preAuthReverseDTO);
		LOG.info("######posPreAuthReverse={}", Gl365StrUtils.toMultiLineStr(result));
	}

	@Before
	public void init() throws ServiceException {
		this.preAuthTranReqDTO.setRequestId(this.getRequestId());
		this.preAuthTranReqDTO.setRequestDate(LocalDate.now());
		this.preAuthTranReqDTO.setOrganCode(this.organCode);
		this.preAuthTranReqDTO.setOrganMerchantNo(this.organMerchantNo);
		this.preAuthTranReqDTO.setTerminal(this.terminal);
		this.preAuthTranReqDTO.setCardIndex(this.cardIndex);
		this.preAuthTranReqDTO.setTotalAmount(new BigDecimal(300));
		this.preAuthTranReqDTO.setScene(Scene.POS_PAY.getCode());
		this.preAuthTranReqDTO.setNoBenefitAmount(BigDecimal.ZERO);
		this.preAuthTranReqDTO.setMerchantOrderNo(IdGenerator.getUuId32());
		this.preAuthTranReqDTO.setMerchantOrderTitle("订单标题-测试");
		this.preAuthTranReqDTO.setMerchantOrderDesc("订单描述-测试");
		this.preAuthTranReqDTO.setOperator("商户操作员编号-001");
		this.preAuthReverseDTO.setOrganCode(this.organCode);
		this.preAuthReverseDTO.setOrganMerchantNo(this.organMerchantNo);
		this.preAuthReverseDTO.setRequestId(this.getRequestId());
		this.preAuthReverseDTO.setRequestDate(LocalDate.now());
		this.preAuthReverseDTO.setTerminal(this.terminal);
		this.preAuthReverseDTO.setCardIndex(this.cardIndex);
		this.preAuthReverseDTO.setOrigTxnDate(LocalDate.now());
	}
	
	public PreTranRespDTO posPreAuthQuery() {
		PreTranRespDTO preTranRespDTO = this.posPreAuthService.execute(preTranReqDTO);
		LOG.info("######posPreAuthQuery={}", Gl365StrUtils.toMultiLineStr(preTranRespDTO));
		return preTranRespDTO;
	}
}
