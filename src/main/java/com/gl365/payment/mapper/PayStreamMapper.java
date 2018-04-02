package com.gl365.payment.mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.gl365.payment.model.PayStream;
@Repository
public interface PayStreamMapper {
	int insert(PayStream payStream);

	PayStream queryByRequestId(@Param("requestId") String requestId);

	PayStream queryByPayId(String payId);

	int updateStatus(@Param("payId") String payId, @Param("dealStatus") String dealStatus, @Param("dealDesc") String dealDesc);

	PayStream queryByRequestIdAndOrganMerNo(@Param("requestId") String requestId, @Param("organMerchantNo") String organMerchantNo);
}