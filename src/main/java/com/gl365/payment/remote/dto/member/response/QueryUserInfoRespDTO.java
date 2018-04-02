package com.gl365.payment.remote.dto.member.response;
import java.io.Serializable;
import com.gl365.payment.remote.dto.merchant.response.BaseRespDTO;
import com.gl365.payment.util.Gl365StrUtils;
public class QueryUserInfoRespDTO extends BaseRespDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 0未激活 1正常 2冻结 3注销
	 */
	private String status;
	/**
	 * 发展会员机构ID
	 */
	private String agentNo;
	/**
	 * 发展会员机构类型(1代理商，2商家);
	 */
	private String agentType;
	/**
	 * 乐豆开关
	 */
	private boolean enableHappycoin;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 *发展会员商家店长
	 */
	private String userDevManager;
	/**
	 * 发展会员商家员工
	 */
	private String userDevStaff;
	/**
	 * 会员手机号
	 */
	private String userMobile;
	/**
	 * 加盟方式 1：联明商家（默认）2：合伙商家
	 */
	private String joinType;
	/**
	 * 发展会员机构ID
	 */
	public String getAgentNo() {
		return agentNo;
	}

	/**
	 * 0未激活 1正常 2冻结 3注销
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 0未激活 1正常 2冻结 3注销
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 发展会员机构ID
	 */
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	/**发展会员机构类型(1代理商，2商家);*/
	public String getAgentType() {
		return agentType;
	}

	/**发展会员机构类型(1代理商，2商家);*/
	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * <p></p>
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * <p></p>
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 发展会员商家店长
	 * @return the userDevManager
	 */
	public String getUserDevManager() {
		return userDevManager;
	}

	/**
	 * 发展会员商家店长
	 * @param userDevManager the userDevManager to set
	 */
	public void setUserDevManager(String userDevManager) {
		this.userDevManager = userDevManager;
	}

	/**
	 * 发展会员商家员工
	 * @return the userDevStaff
	 */
	public String getUserDevStaff() {
		return userDevStaff;
	}

	/**
	 * 发展会员商家员工
	 * @param userDevStaff the userDevStaff to set
	 */
	public void setUserDevStaff(String userDevStaff) {
		this.userDevStaff = userDevStaff;
	}

	/**
	 * 会员手机号
	 * @return the userMobile
	 */
	public String getUserMobile() {
		return userMobile;
	}

	/**
	 * 会员手机号
	 * @param userMobile the userMobile to set
	 */
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	/**
	 * @return the enableHappycoin
	 */
	public boolean isEnableHappycoin() {
		return enableHappycoin;
	}

	/**
	 * @param enableHappycoin the enableHappycoin to set
	 */
	public void setEnableHappycoin(boolean enableHappycoin) {
		this.enableHappycoin = enableHappycoin;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
}
