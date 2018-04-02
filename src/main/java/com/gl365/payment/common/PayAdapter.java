/**
 * Project Name:payment File Name:PayAdapter.java Package
 * Name:com.gl365.payment.modeladapter Date:2017年4月24日下午3:57:29 Copyright (c)
 * 2017, 深圳市给乐信息科技有限公司 All Rights Reserved.
 *
 */
package com.gl365.payment.common;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.gl365.payment.common.constants.PaymentConstants;
import com.gl365.payment.dto.authconsumeconfirm.AuthConsumeConfirmContext;
import com.gl365.payment.dto.base.PayContext;
import com.gl365.payment.enums.pay.DcType;
import com.gl365.payment.enums.pay.DealStatus;
import com.gl365.payment.enums.pay.Scene;
import com.gl365.payment.enums.pay.TranType;
import com.gl365.payment.enums.system.Agent;
import com.gl365.payment.enums.system.GlSystem;
import com.gl365.payment.enums.system.OrganCode;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayPrepay;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.model.PayStream;
import com.gl365.payment.remote.dto.account.request.CancelOperateReqDTO;
import com.gl365.payment.remote.dto.account.response.QueryAccountRespDTO;
import com.gl365.payment.remote.dto.account.response.RespItemDTO;
import com.gl365.payment.remote.dto.account.response.UpdateAccountBalanceOffLineRespDTO;
import com.gl365.payment.remote.dto.member.response.QueryUserInfoRespDTO;
import com.gl365.payment.remote.dto.merchant.response.QueryMerchantInfoRespDTOBody;
import com.gl365.payment.util.BigDecimalUtil;
import com.gl365.payment.util.IdGenerator;
/**
 * 
 * date: 2017年4月24日 下午3:57:29 <br/>
 *
 * @author lenovo
 * @version 
 * @since JDK 1.8
 */
@Service
public class PayAdapter {
	/**
	 * 90--POS消费交易(确认)
		91--POS消费冲正
		92--POS消费撤单
		93--POS消费撤单冲正
		94--POS消费退货
		95--POS消费退货冲正
		96--预授权完成(确认)
		97--预授权完成冲正
		98--POS预交易查询
		99--POS预授权查询
		80--网上消费
		81--网上消费撤单
		83--网上消费冲正
		84--网上消费撤单冲正
		85--网上消费退货
		86--网上消费退货冲正
	
	 */
	public String buidPayId(TranType tranType, String payId) {
		String bizType = "00";
		switch (tranType) {
			case CONSUME_COMMIT:
				bizType = "90";
				break;
			case CONSUME_REVERSE:
				bizType = "91";
				break;
			case CANCEL:
				bizType = "92";
				break;
			case CANCEL_REVERSE:
				bizType = "93";
				break;
			case REFUND_PART:
				bizType = "85";
				break;
			case REFUND_ALL:
				bizType = "85";
				break;
			case REFUND_REVERSE:
				bizType = "95";
				break;
			case PRE_AUTH_CONSUME_CONFIRM:
				bizType = "96";
				break;
			case PRE_AUTH_CONSUME_CONFIRM_REVERSE:
				bizType = "97";
				break;
			case CONSUME_QUERY:
				bizType = "98";
				break;
			case PRE_AUTH_CONSUME_QUERY:
				bizType = "99";
				break;
			case ONLINE_CONSUME:
				bizType = "80";
				break;
			case ONLINE_CANCEL:
				bizType = "81";
				break;
			case ONLINE_CONSUME_REVERSE:
				bizType = "83";
				break;
			case ONLINE_CANCEL_REVERSE:
				bizType = "84";
				break;
			case REFUND_QUERY:
				bizType = "71";
				break;
			/*case REFUND_PART:
				bizType = "85";
				break;*/
			/*case REFUND_REVERSE:
				bizType = "86";
				break;*/
			default:
				break;
		}
		if (payId != null) {
			return bizType + payId;
		}
		else {
			return IdGenerator.generatePayId(bizType);
		}
	}

	/**
	 * 构建流水 
	 * @param pc
	 * @return
	 */
	public PayStream buildPayStream(PayContext pc) {
		PayStream ps = new PayStream();
		ps.setPayId(pc.getPayId());
		// ps.setOperator();
		ps.setTransType(pc.getTranType().getCode());
		ps.setReturnAmount(null);
		ps.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		ps.setCreateTime(LocalDateTime.now());
		ps.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		ps.setModifyTime(LocalDateTime.now());
		ps.setDealStatus(DealStatus.WAITING_FOR_PAYMENT.getCode());
		return ps;
	}

	/**
	 * 构建交易主表对象
	 * @param pc
	 * @param dealStatus
	 * @return
	 */
	public PayMain buildPayMain(PayContext pc, DealStatus dealStatus) {
		PayMain ps = new PayMain();
		ps.setPayId(pc.getPayId());
		ps.setPayTime(LocalDateTime.now());
		ps.setTransType(pc.getTranType().getCode());
		ps.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		ps.setCreateTime(LocalDateTime.now());
		ps.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		ps.setModifyTime(LocalDateTime.now());
		ps.setPayStatus(dealStatus.getCode());
		ps.setPayDesc(dealStatus.getDesc());
		return ps;
	}

	/**
	 * 构建交易主表对象
	 * @param payPrepay
	 * @param dealStatus
	 * @return
	 */
	public PayMain buildPayMainFromPayPrepay(PayPrepay payPrepay, DealStatus dealStatus) {
		PayMain ps = new PayMain();
		BeanUtils.copyProperties(payPrepay, ps);
		ps.setPrePayId(payPrepay.getPayId());
		// ps.setOperator(PeripheralSystemConstants.OPERATOR);
		ps.setCreateBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		ps.setCreateTime(LocalDateTime.now());
		ps.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		ps.setModifyTime(LocalDateTime.now());
		ps.setPayStatus(dealStatus.getCode());
		ps.setPayDesc(dealStatus.getDesc());
		return ps;
	}

	/**
	 * 赋值
	 * @param payMain
	 * @param account
	 * @return
	 */
	public PayMain setPayMain(PayMain payMain, QueryAccountRespDTO account) {
		if (account == null) return payMain;
		// payMain.setMerchantAgentNo(account.getBankName());
		// payMain.setMerchantAgentNo(account.getBindId());
		// payMain.setMerchantAgentNo(account.getCardIndex());
		// payMain.setMerchantAgentNo(account.getCardNo());
		payMain.setPayFeeType(account.getCardType());
		// payMain.setMerchantAgentNo(account.getDefaultPay());
		payMain.setOrganCode(account.getOrganCode());
		payMain.setUserId(account.getUserId());
		payMain.setCardNo(account.getCardNo());
		return payMain;
	}

	/**
	 * 赋值
	 * @param payMain
	 * @param user
	 * @return
	 */
	public PayMain setPayMain(PayMain payMain, QueryUserInfoRespDTO user) {
		if (user == null) return payMain;
		payMain.setUserAgentNo(user.getAgentNo());
		payMain.setUserDevManager(user.getUserDevManager());
		payMain.setUserDevStaff(user.getUserDevStaff());
		payMain.setUserName(user.getUserName());
		payMain.setUserAgentType(user.getAgentType() + "");
		payMain.setUserMobile(user.getUserMobile());
		payMain.setJoinType(user.getJoinType());
		return payMain;
	}

	/**
	 * 赋值
	 * @param payMain
	 * @param merchant
	 * @param dcType
	 * @param scene
	 * @return
	 */
	public PayMain setPayMain(PayMain payMain, QueryMerchantInfoRespDTOBody merchant, DcType dcType, Scene scene) {
		if (merchant == null) return payMain;
		payMain.setMerchantAgentNo(merchant.getAgentNo());
		payMain.setCity(merchant.getCity());
		// payMain.setUserAgentType(merchant.getDistAgent());
		payMain.setDistrict(merchant.getDistrict());
		// payMain.setUserAgentType(merchant.getGlFeeType());
		payMain.setMerchantNo(merchant.getMerchantNo());
		payMain.setMerchantName(merchant.getMerchantShortname());
		payMain.setProvince(merchant.getProvince());
		// payMain.setUserAgentType(merchant.getStatus());
		payMain.setGiftRate(merchant.getSaleRate());
		payMain.setCommRate(merchant.getGlFeeRate());
		// payMain.setUserAgentType(merchant.getLdSale());
		payMain.setInviteAgentNo(merchant.getInviteAgentNo());
		payMain.setSettleOrganNo(merchant.getSettleOrganNo());
		payMain.setParentAgentNo(merchant.getParentAgentNo());
		payMain.setSettleMerchantNo(merchant.getSettleMerchant());
		payMain.setParentMerchantNo(merchant.getParentMerchantNo());
		if (Scene.FAST_PAY.equals(scene)) {
			if (DcType.C.equals(dcType)) {
				payMain.setPayFeeRate(merchant.getOnpayCreditFeeRate());
				payMain.setMaxPayFee(merchant.getOnpayCreditMaxAmt());
			}
			else {
				payMain.setPayFeeRate(merchant.getOnpayDebitFeeRate());
				payMain.setMaxPayFee(merchant.getOnpayDebitMaxAmt());
			}
		}
		else {
			if (DcType.C.equals(dcType)) {
				payMain.setPayFeeRate(merchant.getPosCreditFeeRate());
				payMain.setMaxPayFee(merchant.getPosCreditMaxAmt());
			}
			else {
				payMain.setPayFeeRate(merchant.getPosDebitFeeRate());
				// payMain.setMaxPayFee(merchant.getPostCommMaxAmt());
			}
		}
		// payMain.setUserAgentType(merchant.getSaleRate());
		return payMain;
	}
	
	public PayMain setPayMain(PayMain payMain, QueryMerchantInfoRespDTOBody merchant, QueryMerchantInfoRespDTOBody groupMerchant, DcType dcType, Scene scene) {
		if (merchant == null) return payMain;
		payMain.setMerchantAgentNo(merchant.getAgentNo());
		payMain.setCity(merchant.getCity());
		payMain.setDistrict(merchant.getDistrict());
		payMain.setMerchantNo(merchant.getMerchantNo());
		payMain.setMerchantName(merchant.getMerchantShortname());
		payMain.setProvince(merchant.getProvince());
		payMain.setGiftRate(groupMerchant.getSaleRate());
		payMain.setCommRate(groupMerchant.getGlFeeRate());
		payMain.setInviteAgentNo(merchant.getInviteAgentNo());
		payMain.setSettleOrganNo(merchant.getSettleOrganNo());
		payMain.setParentAgentNo(merchant.getParentAgentNo());
		if (Scene.FAST_PAY.equals(scene)) {
			if (DcType.C.equals(dcType)) {
				payMain.setPayFeeRate(groupMerchant.getOnpayCreditFeeRate());
				payMain.setMaxPayFee(groupMerchant.getOnpayCreditMaxAmt());
			}
			else {
				payMain.setPayFeeRate(groupMerchant.getOnpayDebitFeeRate());
				payMain.setMaxPayFee(groupMerchant.getOnpayDebitMaxAmt());
			}
		}
		else {
			if (DcType.C.equals(dcType)) {
				payMain.setPayFeeRate(groupMerchant.getPosCreditFeeRate());
				payMain.setMaxPayFee(groupMerchant.getPosCreditMaxAmt());
			}
			else {
				payMain.setPayFeeRate(groupMerchant.getPosDebitFeeRate());
			}
		}
		return payMain;
	}

	/**
	 * 赋值
	 * @param payMain
	 * @param finance
	 * @return
	 */
	public PayMain setPayMain(PayMain payMain, Finance finance) {
		payMain.setPayFee(finance.getPayFee());
		payMain.setPayFeeRate(finance.getFeeRate());
		payMain.setBeanAmount(finance.getBeanAmount());
		payMain.setCoinAmount(BigDecimal.ZERO);
		payMain.setMarcketFee(finance.getMarcketFee());
		payMain.setCommAmount(finance.getCommAmount());
		payMain.setCashAmount(finance.getCashAmount());
		payMain.setGiftAmount(finance.getGiftAmount());
		payMain.setGiftPoint(finance.getGiftAmount());
		payMain.setMerchantSettleAmount(finance.getMerchantSettlAmount());
		return payMain;
	}

	/**
	 * 拷贝对象，并修改状态值及修改时间
	 * @param payMain
	 * @param dealStatus
	 * @return
	 */
	public PayMain clonePayMain(PayMain payMain, DealStatus dealStatus, String payId) {
		PayMain ps = payMain.clone();
		if (StringUtils.isNotEmpty(payId)) {
			ps.setPayId(payId);
		}
		ps.setIsNotify("N");
		ps.setPayTime(LocalDateTime.now());
		ps.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		ps.setModifyTime(LocalDateTime.now());
		ps.setPayStatus(dealStatus.getCode());
		ps.setPayDesc(dealStatus.getDesc());
		return ps;
	}

	/**
	 * 00：现金
		01：乐豆
		02：机构积分
		03：商户积分
		04：红包
	 */
	public ArrayList<PayDetail> buildPayDetails(AuthConsumeConfirmContext ccc, UpdateAccountBalanceOffLineRespDTO updateAccountBalanceOffLineRespDTO) {
		ArrayList<PayDetail> list = new ArrayList<PayDetail>();
		PayDetail ps = new PayDetail();
		ps.setPayId(ccc.getPayId());
		ps.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		ps.setCreateTime(LocalDateTime.now());
		ps.setOrganCode(ccc.getAuthConsumeConfirmReqDTO().getOrganCode());
		if (BigDecimalUtil.compareTo(ccc.getPayPrepay().getCashAmount(), BigDecimal.ZERO) == 1) {
			ps.setPayType(PaymentConstants.PayType.PAY_CASH.getCode());
			ps.setPayAmount(ccc.getPayPrepay().getCashAmount());
			list.add(ps);
		}
		List<RespItemDTO> resultData = updateAccountBalanceOffLineRespDTO.getResultData();
		if (resultData != null) {
			for (RespItemDTO itemDTO : resultData) {
				PayDetail psfor = ps.clone();
				psfor.setPayType(itemDTO.getPayType());
				// psfor.setPayAmount(itemDTO.getPayAccount());
				// psfor.setPayAmount(itemDTO.getp());
				list.add(psfor);
				// ps.setPayType(PaymentConstants.PayType.PAY_MERCHANT_POINTS.getCode());
				// ps.setPayAccount("" + ccc.getPayPrepay().getCoinAmount());
			}
		}
		return list;
	}

	/**
	 * 构建付款明细
	 * @param payMain
	 * @param payId
	 * @return
	 */
	public List<PayDetail> buildPayDetailsFromPayMain(PayMain payMain, String payId) {
		ArrayList<PayDetail> payDetails = new ArrayList<PayDetail>();
		PayDetail ps = new PayDetail();
		if (StringUtils.isNotEmpty(payId)) {
			ps.setPayId(payId);
		}
		ps.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		ps.setCreateTime(LocalDateTime.now());
		ps.setOrganCode(payMain.getOrganCode());
		if (BigDecimalUtil.GreaterThan0(payMain.getCashAmount())) {
			ps.setPayType(PaymentConstants.PayType.PAY_CASH.getCode());
			// ps.setPayAccount(payMain.getCardNo());
			ps.setPayAccount(payMain.getUserId());
			ps.setPayAmount(payMain.getCashAmount());
			payDetails.add(ps);
		}
		if (BigDecimalUtil.GreaterThan0(payMain.getBeanAmount())) {
			PayDetail ps2 = ps.clone();
			ps2.setPayType(PaymentConstants.PayType.PAY_BEAN.getCode());
			ps2.setPayAccount(payMain.getUserId());
			ps2.setPayAmount(payMain.getBeanAmount());
			payDetails.add(ps2);
		}
		return payDetails;
	}

	public PayPrepay clonePayPrepay(PayPrepay payPrepay, DealStatus dealStatus, String payId) {
		PayPrepay ps = payPrepay.clone();
		if (StringUtils.isNotEmpty(payId)) {
			ps.setPayId(payId);
		}
		ps.setPayStatus(dealStatus.getCode());
		ps.setPayDesc(dealStatus.getDesc());
		ps.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		ps.setModifyTime(LocalDateTime.now());
		return ps;
	}

	/**
	 * 构建退货表对象
	 * @param pc
	 * @param dealStatus
	 * @return
	 */
	public PayReturn buildPayReturn(PayContext pc, DealStatus dealStatus) {
		PayReturn ps = new PayReturn();
		ps.setPayId(pc.getPayId());
		ps.setPayTime(LocalDateTime.now());
		ps.setTransType(pc.getTranType().getCode());
		ps.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		ps.setCreateTime(LocalDateTime.now());
		ps.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		ps.setModifyTime(LocalDateTime.now());
		ps.setPayStatus(dealStatus.getCode());
		ps.setPayDesc(dealStatus.getDesc());
		return ps;
	}

	/**
	 * 赋值
	 * @param payReturn
	 * @param payMain
	 * @param finance
	 * @return
	 */
	public PayReturn setPayReturn(PayReturn payReturn, PayMain payMain) {
		if (payMain == null) return payReturn;
		payReturn.setMerchantNo(payMain.getMerchantNo());
		payReturn.setMerchantName(payMain.getMerchantName());
		payReturn.setMerchantAgentNo(payMain.getMerchantAgentNo());
		payReturn.setUserAgentType(payMain.getUserAgentType());
		payReturn.setUserAgentNo(payMain.getUserAgentNo());
		payReturn.setUserDevManager(payMain.getUserDevManager());
		payReturn.setUserDevStaff(payMain.getUserDevStaff());
		payReturn.setProvince(payMain.getProvince());
		payReturn.setCity(payMain.getCity());
		payReturn.setDistrict(payMain.getDistrict());
		payReturn.setSettleOrganNo(payMain.getSettleOrganNo());
		payReturn.setParentAgentNo(payMain.getParentAgentNo());
		payReturn.setInviteAgentNo(payMain.getInviteAgentNo());
		payReturn.setCardNo(payMain.getCardNo());
		payReturn.setUserMobile(payMain.getUserMobile());
		payReturn.setUserId(payMain.getUserId());
		payReturn.setUserName(payMain.getUserName());
		payReturn.setNoBenefitAmount(payMain.getNoBenefitAmount());
		payReturn.setPayFeeRate(payMain.getPayFeeRate());
		payReturn.setCommRate(payMain.getCommRate());
		payReturn.setGiftRate(payMain.getGiftRate());
		payReturn.setPayFeeType(payMain.getPayFeeType());
		payReturn.setMaxPayFee(payMain.getMaxPayFee());
		payReturn.setCashAmount(payMain.getCashAmount());
		payReturn.setMerchantSettleAmount(payMain.getMerchantSettleAmount());
		payReturn.setJoinType(payMain.getJoinType());
		payReturn.setPayTime(LocalDateTime.now());
		payReturn.setCreateTime(LocalDateTime.now());
		payReturn.setCreateBy(GlSystem.CREATE_BY_GL_SYSTEM.getCode());
		payReturn.setModifyBy(GlSystem.MODIFY_BY_GL_SYSTEM.getCode());
		payReturn.setModifyTime(LocalDateTime.now());
		payReturn.setTransactionId(payMain.getTransactionId());
		payReturn.setJoinType(payMain.getJoinType());
		payReturn.setScene(payMain.getScene());
		payReturn.setOrderType(payMain.getOrderType());
		payReturn.setMerchantOrderTitle(payMain.getMerchantOrderTitle());
		payReturn.setMerchentOrderDesc(payMain.getMerchentOrderDesc());
		payReturn.setRewardUserId(payMain.getRewardUserId());
		payReturn.setRewardPayId(payMain.getRewardPayId());
		payReturn.setGroupOrderId(payMain.getGroupOrderId());
		payReturn.setSplitFlag(payMain.getSplitFlag());
		payReturn.setGroupMainuserPay(payMain.getGroupMainuserPay());
		payReturn.setGroupPtPay(payMain.getGroupPtPay());
		payReturn.setGroupMainuserPayBean(payMain.getGroupMainuserPayBean());
		payReturn.setGroupGiftAmount(payMain.getGroupGiftAmount());
		payReturn.setGroupMerchantNo(payMain.getGroupMerchantNo());
		payReturn.setDecAmount(payMain.getDecAmount());
		payReturn.setParentMerchantNo(payMain.getParentMerchantNo());
		payReturn.setSettleMerchantNo(payMain.getSettleMerchantNo());
		return payReturn;
	}

	/**
	 * 赋值
	 * @param payReturn
	 * @param finance
	 * @return
	 */
	public PayReturn setPayReturn(PayReturn payReturn, Finance finance) {
		if (finance == null) return payReturn;
		payReturn.setTransType(finance.getRefundType().getCode());
		payReturn.setCashAmount(finance.getCashAmount());
		payReturn.setBeanAmount(finance.getBeanAmount());
		payReturn.setCoinAmount(finance.getCoinAmount());
		payReturn.setPayFee(finance.getPayFee());
		payReturn.setCommAmount(finance.getCommAmount());
		payReturn.setMarcketFee(finance.getMarcketFee());
		payReturn.setGiftAmount(finance.getGiftAmount());
		payReturn.setGiftPoint(finance.getGiftPoint());
		payReturn.setMerchantSettleAmount(finance.getMerchantSettlAmount());
		payReturn.setPayStatus(finance.getRefundType().getCode());
		payReturn.setPayDesc(finance.getRefundType().getDesc());
		return payReturn;
	}

	/**
	 * 构建账号系统《9、余额交易(线下)( updateAccountBalanceOffLine)》的请求参数
	 * @param payMain
	 * @param operateType
	 * @return
	 */
	public CancelOperateReqDTO buildUpdateAccount(PayMain payMain, String operateType, String origPayId) {
		CancelOperateReqDTO request = new CancelOperateReqDTO();
		request.setUserId(payMain.getUserId());
		request.setOrganCode(OrganCode.GL.getCode());
		// request.setPayId(payMain.getPayId());
		request.setMerchantNo(payMain.getMerchantNo());
		request.setMerchantName(payMain.getMerchantName());
		request.setMerchantOrderNo(payMain.getMerchantOrderNo());
		request.setOperateType(operateType);
		request.setOperateAmount(payMain.getBeanAmount());
		request.setGiftAmount(payMain.getGiftAmount());
		// request.setDcType(payMain.getPayFeeType());
		request.setScene(payMain.getScene());
		request.setAgentId(Agent.GL365.getKey());
		request.setOrigPayId(origPayId);
		return request;
	}
}
