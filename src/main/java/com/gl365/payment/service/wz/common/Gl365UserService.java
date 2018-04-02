package com.gl365.payment.service.wz.common;
import com.gl365.payment.remote.dto.member.Gl365User;
public interface Gl365UserService {
	Gl365User queryGl365User(String userId);

    Gl365User queryGl365UserByNewUserId(String userId);
	
	void checkGl365User(Gl365User gl365User);
}
