package com.gl365.payment.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.gl365.payment.model.PayReturn;
@Repository
public interface PayReturnMapper {
	void insert(PayReturn payReturn);

	List<PayReturn> queryByOrigPayId(String origPayId);

	List<PayReturn> queryPagePayReturn(PayReturn payReturn);

	PayReturn queryByPayId(@Param("payId") String payId);

	void updateStatus(PayReturn payReturn);

	PayReturn queryByMerchantOrder(@Param("organCode") String organCode, @Param("merchantOrderNo") String merchantOrderNo);

	void updateReturnTime(PayReturn payReturn);
}