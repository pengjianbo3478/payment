package com.gl365.payment.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.gl365.payment.model.PayMain;
@Repository
public interface PayMainMapper {
	int insert(PayMain record);

	PayMain queryByTerminalAndRequestId(@Param("terminal") String terminal, @Param("requestId") String requestId);

	PayMain queryByRequestId(@Param("requestId") String requestId);

	PayMain queryByPayId(String payId);

	PayMain queryByMerchantOrderNo(String merchantOrderNo);

	PayMain queryByPayIdAndTranTypeAndTerminal(@Param("payId") String payId, @Param("tranType") String tranType, @Param("terminal") String terminal);

	int updateByPayId(PayMain record);

	int updateStatusByPayId(PayMain record);

	int updateStatus(PayMain payMain);

	int updateNotifyByPayId(String payId);

	String queryNotifyByPayId(String payId);

	PayMain queryByRequestIdAndOrganMerNo(@Param("requestId") String requestId, @Param("organMerchantNo") String organMerchantNo);

	List<PayMain> queryPagePayMain(PayMain payMain);
	
	List<PayMain> queryByGroupOrderId(@Param("groupOrderId") String groupOrderId, @Param("payStatus") String payStatus);
	
	PayMain queryByParam(@Param("merchantOrderNo") String merchantOrderNo, @Param("organCode") String organCode);

	PayMain queryByParamForUpdate(@Param("merchantOrderNo") String merchantOrderNo, @Param("organCode") String organCode);
}