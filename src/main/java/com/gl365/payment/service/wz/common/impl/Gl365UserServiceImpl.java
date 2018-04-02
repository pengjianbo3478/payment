package com.gl365.payment.service.wz.common.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.MemberRespException;
import com.gl365.payment.remote.dto.member.Gl365User;
import com.gl365.payment.remote.dto.member.request.QueryUserInfoReqDTO;
import com.gl365.payment.remote.dto.member.response.QueryUserInfoRespDTO;
import com.gl365.payment.remote.service.MemberServiceRemote;
import com.gl365.payment.service.check.CheckGl365UserService;
import com.gl365.payment.service.wz.common.Gl365UserService;
import com.gl365.payment.util.gson.GsonUtils;
@Service
public class Gl365UserServiceImpl implements Gl365UserService {
	private static final Logger LOG = LoggerFactory.getLogger(Gl365UserServiceImpl.class);
	@Autowired
	private CheckGl365UserService checkGl365UserService;
	@Autowired
	private MemberServiceRemote memberServiceRemote;

	@Override
	public Gl365User queryGl365User(String userId) {
		QueryUserInfoReqDTO request = new QueryUserInfoReqDTO();
		request.setUserId(userId);
		LOG.info("查询用户信息请求参数JSON={}", GsonUtils.toJson(request));
		QueryUserInfoRespDTO resp = this.memberServiceRemote.queryUserInfo(request);
		LOG.info("查询用户信息返回结果JSON={}", GsonUtils.toJson(resp));
		if (resp == null) throw new MemberRespException(Msg.REMOTE_MEM_3000.getCode(), Msg.REMOTE_MEM_3000.getDesc());
		Gl365User gl365User = new Gl365User();
		BeanUtils.copyProperties(resp, gl365User);
		return gl365User;
	}
	
	@Override
	public Gl365User queryGl365UserByNewUserId(String userId) {
		QueryUserInfoReqDTO request = new QueryUserInfoReqDTO();
		request.setUserId(userId);
		LOG.info("查询用户信息请求参数JSON={}", GsonUtils.toJson(request));
		QueryUserInfoRespDTO resp = this.memberServiceRemote.queryUserInfoByNewUserId(request);
		LOG.info("查询用户信息返回结果JSON={}", GsonUtils.toJson(resp));
		if (resp == null) throw new MemberRespException(Msg.REMOTE_MEM_3000.getCode(), Msg.REMOTE_MEM_3000.getDesc());
		Gl365User gl365User = new Gl365User();
		BeanUtils.copyProperties(resp, gl365User);
		return gl365User;
	}

	@Override
	public void checkGl365User(Gl365User gl365User) {
		checkGl365UserService.checkNull(gl365User);
		checkGl365UserService.checkStatus(gl365User);
		checkGl365UserService.checkUserName(gl365User);
		checkGl365UserService.checkAgentNo(gl365User);
		checkGl365UserService.checkAgentType(gl365User);
		checkGl365UserService.checkUserId(gl365User);
	}
}
