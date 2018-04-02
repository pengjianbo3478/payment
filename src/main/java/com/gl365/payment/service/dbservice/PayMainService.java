package com.gl365.payment.service.dbservice;
import java.util.List;
import com.github.pagehelper.PageInfo;
import com.gl365.payment.model.PayMain;
public interface PayMainService {
	int save(PayMain payMain);

	PayMain queryByTerminalAndRequestId(String terminal, String requestId);

	PayMain queryByRequestId(String requestId);

	PayMain queryByPayId(String payId);

	PayMain queryByMerchantOrderNo(String merchantOrderNo);

	int updateByPayId(PayMain record);

	int updateStatusByPayId(PayMain record);
	
	int updateStatusByPayIdNew(PayMain record);

	int updateStatus(PayMain payMain);

	int updateNotifyByPayId(String payId);

	String queryNotifyByPayId(String payId);

	PayMain queryByRequestIdAndOrganMerNo(String requestId, String organMerchantNo);

	PageInfo<PayMain> queryPagePayMain(PayMain payMain, int pageNum, int pageSize);

	List<PayMain> queryByGroupOrderId(String groupOrderId);

	PayMain queryByParam(String merchantOrderNo, String organCode);
	
	PayMain queryByParamForUpdate(String merchantOrderNo, String organCode);
}
