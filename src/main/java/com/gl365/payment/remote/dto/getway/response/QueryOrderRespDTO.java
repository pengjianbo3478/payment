package com.gl365.payment.remote.dto.getway.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gl365.payment.util.Gl365StrUtils;

/**
 * 订单查询resp
 * @author liuyang
 *
 */
public class QueryOrderRespDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 版本号	String	8	否	1.0.0
	 */
	private String version;
	
	/**
	 * 请求日期时间	String	14	否	格式为yyyyMMddHHmmss
	 */
	private LocalDateTime reqDate;
	
	/**
	 * 应答日期时间	String	14	否	格式为yyyyMMddHHmmss
	 */
	private LocalDateTime respDate;
	
	/**
	 * 平台流水号	String	32	否	付费通返回的流水号
	 */
	private String smzfMsgId;
	
	/**
	 * 应答类型	String	1	否	S：成功 E：失败 R：不确定（处理中）
	 */
	private String respType;
	
	/**
	 * 应答码	String	6	否	成功：000000 失败：返回具体的响应码
	 */
	private String respCode;
	
	/**
	 * 应答描述	String	256	否	对应应答码信息描述
	 */
	private String respMsg;
	
	/**
	 * 订单金额	BigDecimal		否	订单金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 订单标题	String	256	是	显示在用户app上的订单信息
	 */
	private String subject;
	
	/**
	 * 订单描述	String	512	是	对订单的描述
	 */
	private String desc;
	
	/**
	 * 商户操作员编号	String	32	是	如员工编号
	 */
	private String operatorId;
	
	/**
	 * 商户门店编号	String	32	是	商户的门店编号
	 */
	private String storeId;
	
	/**
	 * 商户机具终端编号	String	32	是	商户的机具终端编号
	 */
	private String terminalId;
	
	/**
	 * 给乐订单号	String	32	否	给乐的订单号
	 */
	private String merOrderid;
	
	/**
	 * 平台订单号	String	32	否	平台的订单号
	 */
	private String fftOrderid;
	
	/**
	 * 支付有效时间	int		是	指定订单的支付有效时间（分钟数），超过有效时间用户将无法支付。
	 * 若不指定该参数则系统默认设置24小时支付有效时间。参数允许设置范围：1-1440区间的整数值，超过1440默认设置1440
	 */
	private int expireTime;
	
	/**
	 * 买家付款金额	BigDecimal		是
	 */
	private BigDecimal buyerPayAmount;
	
	/**
	 * 积分支付金额	BigDecimal		是	优惠金额或折扣券的金额
	 */
	private BigDecimal pointAmount;
	
	/**
	 * 交易支付时间	String	14	是
	 */
	private String payTime;
	
	/**
	 * 对账日期	String	8	是
	 */
	private String settleDate;
	
	/**
	 * 备用域1	String	128	是	备用
	 */
	private String extend1;
	
	/**
	 * 备用域2	String	128	是	备用
	 */
	private String extend2;
	
	/**
	 * 备用域3	String	128	是	备用
	 */
	private String extend3;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public LocalDateTime getReqDate() {
		return reqDate;
	}

	public void setReqDate(LocalDateTime reqDate) {
		this.reqDate = reqDate;
	}

	public LocalDateTime getRespDate() {
		return respDate;
	}

	public void setRespDate(LocalDateTime respDate) {
		this.respDate = respDate;
	}

	public String getSmzfMsgId() {
		return smzfMsgId;
	}

	public void setSmzfMsgId(String smzfMsgId) {
		this.smzfMsgId = smzfMsgId;
	}

	public String getRespType() {
		return respType;
	}

	public void setRespType(String respType) {
		this.respType = respType;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getMerOrderid() {
		return merOrderid;
	}

	public void setMerOrderid(String merOrderid) {
		this.merOrderid = merOrderid;
	}

	public String getFftOrderid() {
		return fftOrderid;
	}

	public void setFftOrderid(String fftOrderid) {
		this.fftOrderid = fftOrderid;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}

	public BigDecimal getBuyerPayAmount() {
		return buyerPayAmount;
	}

	public void setBuyerPayAmount(BigDecimal buyerPayAmount) {
		this.buyerPayAmount = buyerPayAmount;
	}

	public BigDecimal getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(BigDecimal pointAmount) {
		this.pointAmount = pointAmount;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String extend3) {
		this.extend3 = extend3;
	}


	public QueryOrderRespDTO(String version, LocalDateTime reqDate, LocalDateTime respDate, String smzfMsgId, String respType,
			String respCode, String respMsg, BigDecimal totalAmount, String subject, String desc, String operatorId,
			String storeId, String terminalId, String merOrderid, String fftOrderid, int expireTime,
			BigDecimal buyerPayAmount, BigDecimal pointAmount, String payTime, String settleDate, String extend1,
			String extend2, String extend3) {
		super();
		this.version = version;
		this.reqDate = reqDate;
		this.respDate = respDate;
		this.smzfMsgId = smzfMsgId;
		this.respType = respType;
		this.respCode = respCode;
		this.respMsg = respMsg;
		this.totalAmount = totalAmount;
		this.subject = subject;
		this.desc = desc;
		this.operatorId = operatorId;
		this.storeId = storeId;
		this.terminalId = terminalId;
		this.merOrderid = merOrderid;
		this.fftOrderid = fftOrderid;
		this.expireTime = expireTime;
		this.buyerPayAmount = buyerPayAmount;
		this.pointAmount = pointAmount;
		this.payTime = payTime;
		this.settleDate = settleDate;
		this.extend1 = extend1;
		this.extend2 = extend2;
		this.extend3 = extend3;
	}

	public QueryOrderRespDTO() {
		super();
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
	
}
