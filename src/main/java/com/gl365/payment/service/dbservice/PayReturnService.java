package com.gl365.payment.service.dbservice;
import java.util.List;
import com.github.pagehelper.PageInfo;
import com.gl365.payment.model.PayReturn;
public interface PayReturnService {
	void insert(PayReturn payReturn);

	List<PayReturn> queryByOrigPayId(String origPayId);

	PageInfo<PayReturn> queryPagePayReturn(PayReturn payReturn, int pageNum, int pageSize);

	PayReturn queryByPayId(String payId);

	void updateStatus(PayReturn payReturn);
	
	PayReturn queryByMerchantOrder(String organCode, String merchantOrderNo);
	
	void updateReturnTime(PayReturn payReturn);
}
