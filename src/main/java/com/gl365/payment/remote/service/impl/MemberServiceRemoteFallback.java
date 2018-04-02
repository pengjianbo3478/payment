package com.gl365.payment.remote.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.RemoteServiceException;
import com.gl365.payment.remote.dto.member.request.QueryUserInfoReqDTO;
import com.gl365.payment.remote.dto.member.response.QueryUserInfoRespDTO;
import com.gl365.payment.remote.service.MemberServiceRemote;
@Component
public class MemberServiceRemoteFallback implements MemberServiceRemote {
	private static final Logger LOG = LoggerFactory.getLogger(MemberServiceRemoteFallback.class);

	public QueryUserInfoRespDTO queryUserInfo(QueryUserInfoReqDTO queryUserInfoReqDTO) {
		LOG.error(Msg.MEMGER_SERVICE_ERROR.getDesc());
		throw new RemoteServiceException(Msg.MEMGER_SERVICE_ERROR.getCode(), Msg.MEMGER_SERVICE_ERROR.getDesc());
	}

	public QueryUserInfoRespDTO queryUserInfoByNewUserId(QueryUserInfoReqDTO queryUserInfoReqDTO) {
		LOG.error(Msg.MEMGER_SERVICE_ERROR.getDesc());
		throw new RemoteServiceException(Msg.MEMGER_SERVICE_ERROR.getCode(), Msg.MEMGER_SERVICE_ERROR.getDesc());
	}
}
