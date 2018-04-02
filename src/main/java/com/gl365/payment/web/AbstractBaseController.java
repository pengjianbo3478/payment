package com.gl365.payment.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gl365.payment.dto.base.request.BaseRequest;
import com.gl365.payment.dto.base.response.BaseResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
/**
 * controller
 * date: 2017年4月27日 下午4:27:50 <br/>
 *
 * @author lenovo
 * @version 
 * @since JDK 1.8
 */
@RestController
public abstract class AbstractBaseController<R extends BaseRequest, V extends BaseResponse> {
	Logger logger = LoggerFactory.getLogger(AbstractBaseController.class);

	@HystrixCommand(fallbackMethod = "fallback")
	public abstract V service(@RequestBody R reqDTO);

	public BaseResponse fallback(@RequestBody R reqDto) {
		logger.error("request is fallback,request body:=[" + reqDto.toString() + "]");
		return BaseResponse.fallback();
	}
}
