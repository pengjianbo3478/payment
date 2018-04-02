package com.gl365.payment.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.gl365.payment.model.PayDetail;
@Repository
public interface PayDetailMapper {
	int insert(PayDetail record);

	List<PayDetail> queryPayDetailByPayId(@Param("payId") String payId);

	int deleteByPayId(@Param("payId") String payId);
}