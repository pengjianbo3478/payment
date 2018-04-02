package com.gl365.payment.service.transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.gl365.payment.common.PayAdapter;
import com.gl365.payment.common.constants.PaymentConstants;
import com.gl365.payment.common.constants.PeripheralSystemConstants;
import com.gl365.payment.dto.base.BaseContext;
import com.gl365.payment.dto.base.request.BaseRequest;
import com.gl365.payment.dto.base.response.BaseResponse;
import com.gl365.payment.enums.mq.MsgCategory;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.OrderType;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.pay.SplitFlag;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.exception.InvalidRequestException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.mapper.PayDetailMapper;
import com.gl365.payment.mapper.PayMainMapper;
import com.gl365.payment.mapper.PayPrepayMapper;
import com.gl365.payment.mapper.PayReturnMapper;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.service.dbservice.PayStreamService;
import com.gl365.payment.service.mq.dto.PaymentBody;
import com.gl365.payment.service.mq.dto.PaymentMQ;
import com.gl365.payment.service.mq.dto.PaymentResult;
import com.gl365.payment.service.mq.product.PaymentProduct;
import com.gl365.payment.service.mq.product.PaymentResultProduct;
import com.gl365.payment.service.transaction.remote.RemoteMicroServiceAgent;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.Gl365StrUtils;
import com.gl365.payment.util.IdGenerator;
import com.gl365.payment.util.LoggerFormat;
import com.gl365.payment.util.LoggerFormat.ExecuteState;
import com.gl365.payment.util.StringParseUtil;
import com.gl365.payment.util.gson.GsonUtils;
import com.netflix.hystrix.exception.HystrixRuntimeException;
public abstract class AbstractTranscation<Q extends BaseRequest, P extends BaseResponse> {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractTranscation.class);
	@Resource
	protected PaymentProduct paymentProduct;
	@Autowired
	private PaymentResultProduct paymentResultProduct;
	@Resource
	protected PayAdapter payAdapter;
	@Autowired
	PayStreamService payStreamService;
	@Autowired
	protected PayMainMapper payMainMapper;
	@Autowired
	protected PayDetailMapper payDetailMapper;
	@Autowired
	protected PayPrepayMapper payPrepayMapper;
	@Autowired
	protected PayReturnMapper payReturnMapper;
	@Autowired
	protected TransactionService transactionService;
	@Autowired
	@Qualifier("remoteMicroServiceAgent")
	protected RemoteMicroServiceAgent remoteMicroServiceAgent;
	public static final BigDecimal ONE = new BigDecimal("1");

	public boolean checkStringNPT(BaseContext<Q, P> bc, Map<PaymentConstants.Column, String> columns) {
		Msg msg = null;
		for (Map.Entry<PaymentConstants.Column, String> entry : columns.entrySet()) {
			if (StringUtils.isEmpty(entry.getValue())) {
				switch (entry.getKey()) {
					case organCode:
						msg = Msg.IVD_NULL_organCode_1001;
						break;
					case requestId:
						msg = Msg.IVD_NULL_requestId_1002;
						break;
					case requestDate:
						msg = Msg.IVD_NULL_requestDate_1003;
						break;
					case organMerchantNo:
						msg = Msg.IVD_NULL_organMerchantNo_1004;
						break;
					case terminal:
						msg = Msg.IVD_NULL_terminal_1005;
						break;
					case operator:
						msg = Msg.IVD_NULL_operator_1006;
						break;
					case cardIndex:
						msg = Msg.IVD_NULL_cardIndex_1007;
						break;
					case origPayId:
						msg = Msg.IVD_NULL_origPayId_1009;
						break;
					case origTxnDate:
						msg = Msg.IVD_NULL_origTxnDate_1010;
						break;
					case merchantOrderTitle:
						msg = Msg.IVD_NULL_merchantOrderTitle_1017;
						break;
					case merchantOrderDesc:
						msg = Msg.IVD_NULL_merchantOrderDesc_1018;
						break;
					case merchantOrderNo:
						msg = Msg.IVD_NULL_merchantOrderNo_1019;
						break;
					case payChannel:
						msg = Msg.IVD_NULL_payChannel_1021;
						break;
					case rewardPayId:
						msg = Msg.IVD_NULL_rewardPayId_1022;
						break;
					case rewardUserId:
						msg = Msg.IVD_NULL_rewardUserId_1023;
						break;
					case payUserId:
						msg = Msg.IVD_NULL_payUserId_1024;
						break;	
					case groupOrderId:
						msg = Msg.IVD_NULL_groupOrderId_1027;
						break;
					case splitFlag:
						msg = Msg.IVD_NULL_splitFlag_1028;
						break;
					case groupMerchantNo:
						msg = Msg.IVD_NULL_groupMerchantNo_1029;
						break;
					default:
						break;
				}
				if (msg != null) break;
			}
		}
		return msg == null ? true : setReturnResponse(bc, msg, null);
	}

	public boolean checkStringLen(BaseContext<Q, P> bc, Map<PaymentConstants.Column, String> columns) {
		Msg msg = null;
		for (Map.Entry<PaymentConstants.Column, String> entry : columns.entrySet()) {
			if (entry.getValue() != null) {
				switch (entry.getKey()) {
					case organCode:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN15) {
							msg = Msg.IVD_LEN_organCode_1001;
						}
						break;
					case requestId:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN32) {
							msg = Msg.IVD_LEN_requestId_1002;
						}
						break;
					case requestDate:
						if (entry.getValue().length() != PeripheralSystemConstants.LEN8) {
							msg = Msg.IVD_LEN_requestDate_1003;
						}
						break;
					case organMerchantNo:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN15) {
							msg = Msg.IVD_LEN_organMerchantNo_1104;
						}
						break;
					case terminal:
						if (entry.getValue().length() != PeripheralSystemConstants.LEN8) {
							msg = Msg.IVD_LEN_terminal_1005;
						}
						break;
					case operator:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN32) {
							msg = Msg.IVD_LEN_operator_1006;
						}
						break;
					case cardIndex:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN32) {
							msg = Msg.IVD_LEN_cardIndex_1007;
						}
						break;
					case origPayId:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN32) {
							msg = Msg.IVD_LEN_origPayId_1009;
						}
						break;
					case origTxnDate:
						if (entry.getValue().length() != PeripheralSystemConstants.LEN8) {
							msg = Msg.IVD_LEN_origTxnDate_1010;
						}
						break;
					case merchantOrderTitle:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN128) {
							msg = Msg.IVD_LEN_merchantOrderTitle_1017;
						}
						break;
					case merchantOrderDesc:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN512) {
							msg = Msg.IVD_LEN_merchantOrderDesc_1018;
						}
						break;
					case merchantOrderNo:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN32) {
							msg = Msg.IVD_LEN_organOrderNo_1022;
						}
						break;
					case organOrderNo:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN32) {
							msg = Msg.IVD_LEN_organOrderNo_1022;
						}
						break;
					case payChannel:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN1) {
							msg = Msg.IVD_LEN_payChannel_1021;
						}
						else {
							if ((!"1".equals(entry.getValue())) && (!"2".equals(entry.getValue()))) {
								msg = Msg.IVD_LEN_payChannel_1021;
							}
						}
						break;
					case payUserId:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN32) {
							msg = Msg.IVD_LEN_payUserId_1025;
						}
						break;
					case rewardPayId:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN32) {
							msg = Msg.IVD_LEN_rewardPayId_1026;
						}
						break;
					case rewardUserId:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN32) {
							msg = Msg.IVD_LEN_rewardUserId_1027;
						}
						break;
					case groupOrderId:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN32) {
							msg = Msg.IVD_LEN_groupOrderId_1028;
						}
						break;
					case groupMerchantNo:
						if (entry.getValue().length() > PeripheralSystemConstants.LEN15) {
							msg = Msg.IVD_LEN_groupMerchantNo_1029;
						}
						break;
					default:
						break;
				}
				if (msg != null) break;
			}
		}
		return msg == null ? true : setReturnResponse(bc, msg, null);
	}

	public boolean checkBigDecimalNPT(BaseContext<Q, P> bc, Map<PaymentConstants.Column, BigDecimal> columns) {
		Msg msg = null;
		for (Map.Entry<PaymentConstants.Column, BigDecimal> entry : columns.entrySet()) {
			if (BigDecimalUtil.isEmpty(entry.getValue())) {
				switch (entry.getKey()) {
					case totalAmount:
						msg = Msg.IVD_NULL_totalAmount_1008;
						break;
					case giftPoint:
						msg = Msg.IVD_NULL_giftPoint_1011;
						break;
					case marketFee:
						msg = Msg.IVD_NULL_marketFee_1012;
						break;
					case coinAmount:
						msg = Msg.IVD_NULL_coinAmount_1013;
						break;
					case beanAmount:
						msg = Msg.IVD_NULL_beanAmount_1014;
						break;
					case cashMoney:
						msg = Msg.IVD_NULL_cashMoney_1015;
						break;
					case giftAmount:
						msg = Msg.IVD_NULL_giftAmount_1016;
						break;
					case noBenefitAmount:
						msg = Msg.IVD_NULL_noBenefitAmount_1020;
						break;
					case payldmoney:
						msg = Msg.IVD_NULL_payldmoney_1025;
						break;
					case groupMainUserPay:
						msg = Msg.IVD_NULL_groupMainUserPay_1026;
						break;
					default:
						break;
				}
				if (msg != null) break;
			}
		}
		return msg == null ? true : setReturnResponse(bc, msg, null);
	}
	
	public boolean checkMerchantOrderDesc(BaseContext<Q, P> bc, String merchantOrderDesc){
		Msg msg = null;
		if (!StringParseUtil.checkMerchantOrderDesc(merchantOrderDesc)) {
			msg = Msg.IVD_FORMAT_merchantOrderDesc_1023;
		}
		
		return msg == null ? true : setReturnResponse(bc, msg, null);
	}
	
	public boolean checkOrderType(BaseContext<Q, P> bc, String orderType) {
		Msg msg = null;
		boolean b = StringUtils.isNotBlank(orderType) && (StringUtils.equals(orderType, OrderType.beanDs.getCode()) 
				|| StringUtils.equals(orderType, OrderType.beanPay.getCode()));
		if (!b) {
			msg = Msg.REQ_0035;
		}
		
		return msg == null ? true : setReturnResponse(bc, msg, null);
	}
	public boolean checkSplitFlag(BaseContext<Q, P> bc, String splitFlag) {
		Msg msg = null;
		boolean b = StringUtils.isNotBlank(splitFlag) && (StringUtils.equals(splitFlag, SplitFlag.childOrder.getCode()));
		if (!b) {
			msg = Msg.REQ_0048;
		}
		
		return msg == null ? true : setReturnResponse(bc, msg, null);
	}
	/**
	 * 输入参数校验
	 * @param request
	 * @throws InvalidRequestException
	 */
	public abstract boolean checkAndSaveInputParameter(BaseContext<Q, P> bc) throws InvalidRequestException;

	/**
	 * 设置交易类型
	 * @param tranType
	 */
	public abstract void setTranType(BaseContext<Q, P> bc);

	/**
	 * 构建交易开始前写一次流水的对象，如果为null,则为不用写流水
	 * @param bc
	 * @return
	 */
	public abstract PayStream buildPayStream(BaseContext<Q, P> bc);

	/**
	 * 构建交易开始前写一次流水的对象，如果为null,则为不用写流水
	 * @param bc
	 * @param dealStatus
	 * @return
	 */
	public abstract PayMain buildPayMain(BaseContext<Q, P> bc, DealStatus dealStatus);

	/**
	 * 构建交易交易明细
	 * @param bc
	 * @param list
	 * @return
	 */
	public abstract List<PayDetail> buildPayDetails(BaseContext<Q, P> bc);

	/**
	 * 根据请求数据调用微服务（外围服务）返回交易过程中必要信息
	 * @param bc
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public abstract void query(BaseContext<Q, P> bc) throws ServiceException;

	/**
	 * 业务逻辑判断
	 * @param bc
	 * @return
	 * @throws ServiceException
	 */
	public abstract boolean bizCheck(BaseContext<Q, P> bc) throws ServiceException;

	/**
	 * 提交数据到外围系统、提交数据到交易系统库
	 * @param bc
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public abstract boolean firstCommit(BaseContext<Q, P> bc) throws ServiceException;

	/**
	 * 提交数据到外围系统、提交数据到交易系统库(可能用不上)
	 * @param bc
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public abstract boolean secondCommit(BaseContext<Q, P> bc) throws ServiceException;

	/**
	 * 准备MQ消息
	 * @param bc
	 */
	public String prepareAyscMQ(BaseContext<Q, P> bc) {
		if (TranType.REFUND_QUERY.getCode().equals(bc.getTranType())) { return null; }
		PaymentBody paymentBody = new PaymentBody();
		paymentBody.setPayMain(bc.getPayMain());
		paymentBody.setPayStream(bc.getPayStream());
		paymentBody.setPayReturn(bc.getPayReturn());
		paymentBody.setPayDetails(bc.getPayDetails());
		paymentBody.setPayModifyStatus(bc.getPayMain().getPayStatus());
		paymentBody.setPayModifyTime(LocalDateTime.now());
		PaymentMQ paymentMQ = new PaymentMQ();
		paymentMQ.setMsgCategory(MsgCategory.normal.getCode());
		paymentMQ.setTranType(bc.getTranType().getCode());
		paymentMQ.setPaymentBody(paymentBody);
		LOG.debug("#####paymentMQ={}", Gl365StrUtils.toMultiLineStr(paymentMQ));
		String json = GsonUtils.toJson(paymentMQ);
		return json;
	}

	/**
	 * 构建返回对象
	 * @param bc
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	public abstract P buildReturnResponse(BaseContext<Q, P> bc) throws ServiceException;

	public P returnResponse(BaseContext<Q, P> bc) {
		bc.getResponse().setPayId(bc.getPayId());
		return bc.getResponse();
	}

	public P setReturnResponse(BaseContext<Q, P> bc, Msg msg) {
		if (msg == null) {
			msg = Msg.UNKNOW_FAIL;
		}
		bc.getResponse().setPayStatus(msg.getCode());
		bc.getResponse().setPayDesc(msg.getDesc());
		bc.getResponse().setPayId(bc.getPayId());
		return bc.getResponse();
	}

	public boolean setReturnResponse(BaseContext<Q, P> bc, Msg msg, Integer len) {
		if (msg == null) {
			msg = Msg.UNKNOW_FAIL;
		}
		bc.getResponse().setPayId(bc.getPayId());
		bc.getResponse().setPayStatus(msg.getCode());
		if (len != null) {
			bc.getResponse().setPayDesc(msg.getDesc() + "，输入的参数长度是" + len + "位");
		}
		else {
			bc.getResponse().setPayDesc(msg.getDesc());
		}
		if (Msg.S000.equals(msg)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * 获取外围系统对于的操作类型
	 * @param bc
	 * @return
	 */
	public TranType getOperateType(BaseContext<Q, P> bc) {
		return bc.getTranType();
	}

	public Scene getScene(String onlineOffline) {
		if ("01".equals(onlineOffline) || "2".equals(onlineOffline)) { // 交易接口文档
			return Scene.FAST_PAY;
		}
		if ("00".equals(onlineOffline) || "1".equals(onlineOffline)) { // 交易接口文档
			return Scene.POS_PAY;
		}
		return Scene.FAST_PAY;
	}

	public TranType getTranTypeByScene(Scene scene) {
		if (Scene.FAST_PAY.equals(scene)) {
			return TranType.ONLINE_CONSUME;
		}
		else {
			return TranType.CONSUME_COMMIT;
		}
	}

	public DcType getDcType(String cardType) {
		if (PeripheralSystemConstants.CARD_TYPE_D.equalsIgnoreCase(cardType)) {
			return DcType.D;
		}
		else {
			return DcType.C;
		}
	}

	@Deprecated
	public boolean isNeedOperateBeanAmount(BigDecimal beanAmount, BigDecimal giftAmount) {
		if (BigDecimalUtil.GreaterThan0(beanAmount) || BigDecimalUtil.GreaterThan0(giftAmount)) {
			return true;
		}
		else {
			return false;
		}
	}

	private void log(BaseContext<Q, P> bc, int step, ExecuteState state, String resultCode, String resultDesc, String info) {
		LOG.info(LoggerFormat.logTran(bc.getTranType(), bc.getPayId(), step, state, resultCode, resultDesc, info));
	}

	/**
	 * 交易处理整体逻辑
	 * @param bc
	 * @return
	 */
	private P trade(BaseContext<Q, P> bc) {
		try {
			// 设置交易类型
			setTranType(bc);
			if (bc.getTranType() == null) {
				log(bc, 1, ExecuteState.RUN, DealStatus.SUCCESS.getCode(), "交易类型校验", "交易提前终止，原因：" + Msg.IVD_NULL_tranType_1099.getCode() + "," + Msg.IVD_NULL_tranType_1099.getDesc());
				return setReturnResponse(bc, Msg.IVD_NULL_tranType_1099);
			}
			// 重新设置流水号
			bc.setPayId(payAdapter.buidPayId(bc.getTranType(), bc.getPayId()));
			if (!checkAndSaveInputParameter(bc)) {
				log(bc, 1, ExecuteState.RUN, DealStatus.SUCCESS.getCode(), "输入参数校验完成", "交易提前终止，原因：" + bc.getResponse().getPayStatus() + "," + bc.getResponse().getPayDesc());
				return returnResponse(bc);
			}
			log(bc, 1, ExecuteState.RUN, DealStatus.SUCCESS.getCode(), "输入参数校验完成", "正常");
			// 交易开始
			// 写流水
			PayStream ps = buildPayStream(bc);
			if (ps != null) {
				this.payStreamService.save(ps);
				log(bc, 2, ExecuteState.RUN, DealStatus.SUCCESS.getCode(), "流水表写入完成", "正常");
			}
			else {
				log(bc, 2, ExecuteState.RUN, DealStatus.SUCCESS.getCode(), "此交易不用写流水表", "正常");
			}
			query(bc);
			log(bc, 3, ExecuteState.RUN, DealStatus.SUCCESS.getCode(), "业务查询完成", "正常");
			if (!bizCheck(bc)) {
				log(bc, 3, ExecuteState.RUN, DealStatus.SUCCESS.getCode(), "业务逻辑校验完成", "交易提前终止，原因：" + bc.getResponse().getPayStatus() + "," + bc.getResponse().getPayDesc());
				return returnResponse(bc);
			}
			else {
				log(bc, 4, ExecuteState.RUN, DealStatus.SUCCESS.getCode(), "业务逻辑校验完成", "正常");
			}
			// 事务提交
			boolean firstCommit = firstCommit(bc);
			log(bc, 5, ExecuteState.RUN, DealStatus.SUCCESS.getCode(), "第一阶段提交执行完成", (firstCommit ? "正常" : "交易提前终止，原因：" + bc.getResponse().getPayStatus() + "," + bc.getResponse().getPayDesc()));
			if (!firstCommit) return returnResponse(bc);
			boolean secondCommit = secondCommit(bc);
			log(bc, 6, ExecuteState.RUN, DealStatus.SUCCESS.getCode(), "第二阶段提交执行完成", (firstCommit ? "正常" : "交易提前终止，原因：" + bc.getResponse().getPayStatus() + "," + bc.getResponse().getPayDesc()));
			if (!firstCommit || !secondCommit) { return returnResponse(bc); }
			// 推送消息
			try {
				String json = prepareAyscMQ(bc);
				if (json == null) {
					log(bc, 7, ExecuteState.SMQ, DealStatus.SUCCESS.getCode(), "不需要推送MQ消息", "");
				}
				else {
					this.paymentProduct.send(json);
					log(bc, 7, ExecuteState.SMQ, ExecuteState.SMQ.getDesc(), "", "推送的数据:" + json);
				}
				if (isSendPaymentResultMQ()) {
					this.sendPaymentResultMQ(bc);
					log(bc, 8, ExecuteState.SMQ, ExecuteState.SMQ.getDesc(), "", "推送的数据:" + json);
				}
				if (isSendRewardResultMQ()) {
					json = this.sendRewardResultMQ(bc);
					log(bc, 8, ExecuteState.SMQ, ExecuteState.SMQ.getDesc(), "", "推送的数据:" + json);
				}
			}
			catch (Throwable t) {
				LOG.error("message queue push failed.", t);
			}
			return buildReturnResponse(bc);
		}
		catch (Throwable t) {
			if (t instanceof HystrixRuntimeException) {
				LOG.error(LoggerFormat.logTran(bc.getTranType(), bc.getPayId(), 6, ExecuteState.RUN, DealStatus.FAIL.getCode(), "异常1", "异常描述:'" + t.getMessage() + ""));
				return setReturnResponse(bc, Msg.MICRO_SERVICE_FAIL);
			}
			if (t instanceof ServiceException) {
				bc.getResponse().setPayStatus(((ServiceException) t).getCode());
				bc.getResponse().setPayDesc(((ServiceException) t).getDesc());
				LOG.error(LoggerFormat.logTran(bc.getTranType(), bc.getPayId(), 6, ExecuteState.RUN, DealStatus.FAIL.getCode(), "异常2", "异常描述:'" + t.getMessage() + ""));
				return returnResponse(bc);
			}
			if (t instanceof DateTimeParseException) {
				LOG.error(LoggerFormat.logTran(bc.getTranType(), bc.getPayId(), 6, ExecuteState.RUN, DealStatus.FAIL.getCode(), "异常3", "异常描述:'" + t.getMessage() + ""));
				return setReturnResponse(bc, Msg.PARSE_FORMAT_FAIL);
			}
			LOG.error(LoggerFormat.logTran(bc.getTranType(), bc.getPayId(), 6, ExecuteState.RUN, DealStatus.FAIL.getCode(), "异常4", "异常描述:'" + t.getMessage() + "',异常堆栈信息如下："), t);
			bc.getResponse().setPayStatus(Msg.UNKNOW_FAIL.getCode());
			bc.getResponse().setPayDesc(Msg.UNKNOW_FAIL.getDesc());
			return returnResponse(bc);
		}
	}
	
	public abstract boolean isSendPaymentResultMQ();

	public abstract boolean isSendRewardResultMQ();

	private void sendPaymentResultMQ(BaseContext<Q, P> bc) {
		PayMain pm = bc.getPayMain();
		String payId = pm.getPayId();
		PaymentResult paymentResult = new PaymentResult();
		paymentResult.setRequestId(pm.getRequestId());
		paymentResult.setCardIndex(pm.getCardIndex());
		paymentResult.setOrganMerchantNo(pm.getOrganMerchantNo());
		paymentResult.setRequestDate(pm.getRequestDate());
		paymentResult.setTerminal(pm.getTerminal());
		paymentResult.setTotalAmount(pm.getTotalAmount());
		paymentResult.setPayId(payId);
		String content = GsonUtils.toJson(paymentResult);
		this.paymentResultProduct.send(content);
	}
	
	public String sendRewardResultMQ(BaseContext<Q, P> bc) {
		PaymentBody body = new PaymentBody();
		body.setPayMain(bc.getPayMain());
		body.setPayStream(bc.getPayStream());
		body.setPayModifyTime(java.time.LocalDateTime.now());
		PaymentMQ res = new PaymentMQ();
		res.setMsgCategory(MsgCategory.push.getCode());
		res.setTranType(bc.getPayStream().getTransType());
		res.setPaymentBody(body);
		String content = GsonUtils.toJson(res);
		this.paymentProduct.send(content);
		return content;
	}

	/**
	 * 交易处理入口
	 * @param req
	 * @param resp
	 * @return
	 */
	public P service(Q req, P resp) {
		BaseContext<Q, P> bc = new BaseContext<Q, P>();
		// 设置流水号
		bc.setPayId(IdGenerator.generatePayId(""));
		bc.setRequest(req);
		bc.setResponse(resp);
		log(bc, 0, ExecuteState.BEG, DealStatus.SUCCESS.getCode(), "交易开始", "request=" + req);
		P response = trade(bc);
		log(bc, 10, ExecuteState.END, DealStatus.SUCCESS.getCode(), "交易结束", "response=" + response);
		return response;
	}
}
