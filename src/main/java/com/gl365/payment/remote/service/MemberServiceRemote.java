package com.gl365.payment.remote.service;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gl365.payment.remote.dto.member.request.QueryUserInfoReqDTO;
import com.gl365.payment.remote.dto.member.response.QueryUserInfoRespDTO;
import com.gl365.payment.remote.service.impl.MemberServiceRemoteFallback;
@FeignClient(name = "member", fallback = MemberServiceRemoteFallback.class)
public interface MemberServiceRemote {
	/**
	 * <p>通过用户SN查询用户表，取出用户所属发展机构、乐豆支付总开关</p>
	 * @param queryUserInfoReqDTO
	 * @return QueryUserInfoResqDTO
	 */
	@RequestMapping(value = "/member/user/payment/queryUserInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	QueryUserInfoRespDTO queryUserInfo(@RequestBody QueryUserInfoReqDTO queryUserInfoReqDTO);
	
	@RequestMapping(value = "/member/user/payment/queryUserInfoByNewUserId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	QueryUserInfoRespDTO queryUserInfoByNewUserId(@RequestBody QueryUserInfoReqDTO queryUserInfoReqDTO);
}
