package com.gl365.payment.service.check.impl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.enums.user.UserStatus;
import com.gl365.payment.exception.MemberRespException;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.service.check.CheckGl365UserService;
@Service
public class CheckGl365UserServiceImpl implements CheckGl365UserService {
	@Override
	public void check(Gl365User gl365User) {
		checkNull(gl365User);
		checkStatus(gl365User);
		checkUserName(gl365User);
		checkAgentNo(gl365User);
		checkAgentType(gl365User);
		//checkUserMobile(gl365User);
		this.checkUserId(gl365User);
	}
	
	public void checkUserId(Gl365User gl365User) {
		String val = gl365User.getUserId();
		if (StringUtils.isEmpty(val)) throw new MemberRespException(Msg.REMOTE_MEM_3009.getCode(), Msg.REMOTE_MEM_3009.getDesc());
	}

	public void checkUserMobile(Gl365User gl365User) {
		String userMobile = gl365User.getUserMobile();
		if (StringUtils.isEmpty(userMobile)) throw new MemberRespException(Msg.REMOTE_MEM_3007.getCode(), Msg.REMOTE_MEM_3007.getDesc());
	}

	public void checkAgentType(Gl365User gl365User) {
		String at = gl365User.getAgentType();
		if (StringUtils.isEmpty(at)) throw new MemberRespException(Msg.REMOTE_MEM_3005.getCode(), Msg.REMOTE_MEM_3005.getDesc());
	}

	public void checkAgentNo(Gl365User gl365User) {
		String agentNo = gl365User.getAgentNo();
		if (StringUtils.isEmpty(agentNo)) throw new MemberRespException(Msg.REMOTE_MEM_3004.getCode(), Msg.REMOTE_MEM_3004.getDesc());
	}

	public void checkUserName(Gl365User gl365User) {
		String userName = gl365User.getUserName();
		if (StringUtils.isEmpty(userName)) throw new MemberRespException(Msg.REMOTE_MEM_3003.getCode(), Msg.REMOTE_MEM_3003.getDesc());
	}

	public void checkNull(Gl365User gl365User) {
		if (gl365User == null) throw new MemberRespException(Msg.REMOTE_MEM_3000.getCode(), Msg.REMOTE_MEM_3000.getDesc());
	}
	
	public void checkStatus(Gl365User gl365User) {
		String status = gl365User.getStatus();
		boolean res = StringUtils.equals(UserStatus.NORMAL.getCode(), status);
		if (!res) throw new ServiceException(Msg.PAY_8001.getCode(), Msg.PAY_8001.getDesc());
	}
}
