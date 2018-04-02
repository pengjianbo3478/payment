package com.gl365.payment.service.check;
import com.gl365.payment.remote.dto.member.Gl365User;
public interface CheckGl365UserService {
	void check(Gl365User gl365User);

	void checkUserId(Gl365User gl365User);

	void checkUserMobile(Gl365User gl365User);

	void checkAgentType(Gl365User gl365User);

	void checkAgentNo(Gl365User gl365User);

	void checkUserName(Gl365User gl365User);

	void checkNull(Gl365User gl365User);

	void checkStatus(Gl365User gl365User);
}
