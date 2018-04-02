package com.gl365.payment.service.mgr;
import com.github.pagehelper.PageInfo;
import com.gl365.payment.dto.mgr.reqeust.QueryPagePayMainReqDTO;
import com.gl365.payment.dto.mgr.reqeust.QueryPagePayReturnReqDTO;
import com.gl365.payment.dto.mgr.reqeust.QueryPayDetailByPayIdReqDTO;
import com.gl365.payment.dto.mgr.reqeust.QueryPayMainByPayIdReqDTO;
import com.gl365.payment.dto.mgr.reqeust.QueryPayReturnByPayIdReqDTO;
import com.gl365.payment.dto.mgr.response.QueryPayDetailByPayIdRespDTO;
import com.gl365.payment.dto.mgr.response.QueryPayMainByPayIdRespDTO;
import com.gl365.payment.dto.mgr.response.QueryPayReturnByPayIdRespDTO;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayReturn;
public interface MgrService {
	QueryPayMainByPayIdRespDTO queryPayMainByPayId(QueryPayMainByPayIdReqDTO request);

	PageInfo<PayMain> queryPagePayMain(QueryPagePayMainReqDTO request);

	PageInfo<PayReturn> queryPagePayReturn(QueryPagePayReturnReqDTO request);

	QueryPayReturnByPayIdRespDTO queryPayReturnByPayId(QueryPayReturnByPayIdReqDTO request);

	QueryPayDetailByPayIdRespDTO queryPayDetailByPayId(QueryPayDetailByPayIdReqDTO request);
}
