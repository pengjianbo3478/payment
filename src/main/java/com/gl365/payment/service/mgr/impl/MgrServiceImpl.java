package com.gl365.payment.service.mgr.impl;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageInfo;
import com.gl365.payment.dto.mgr.reqeust.QueryPagePayMainReqDTO;
import com.gl365.payment.dto.mgr.reqeust.QueryPagePayReturnReqDTO;
import com.gl365.payment.dto.mgr.reqeust.QueryPayDetailByPayIdReqDTO;
import com.gl365.payment.dto.mgr.reqeust.QueryPayMainByPayIdReqDTO;
import com.gl365.payment.dto.mgr.reqeust.QueryPayReturnByPayIdReqDTO;
import com.gl365.payment.dto.mgr.response.QueryPayDetailByPayIdRespDTO;
import com.gl365.payment.dto.mgr.response.QueryPayMainByPayIdRespDTO;
import com.gl365.payment.dto.mgr.response.QueryPayReturnByPayIdRespDTO;
import com.gl365.payment.enums.mgr.MgrMsg;
import com.gl365.payment.enums.msg.Msg;
import com.gl365.payment.exception.ServiceException;
import com.gl365.payment.model.PayDetail;
import com.gl365.payment.model.PayMain;
import com.gl365.payment.model.PayReturn;
import com.gl365.payment.service.dbservice.PayDetailService;
import com.gl365.payment.service.dbservice.PayMainService;
import com.gl365.payment.service.dbservice.PayReturnService;
import com.gl365.payment.service.mgr.MgrService;
import com.gl365.payment.util.Gl365StrUtils;
@Service
public class MgrServiceImpl implements MgrService {
	private static final Logger LOG = LoggerFactory.getLogger(MgrServiceImpl.class);
	@Autowired
	private PayMainService payMainService;
	@Autowired
	private PayReturnService payReturnService;
	@Autowired
	private PayDetailService payDetailService;

	@Override
	public QueryPayMainByPayIdRespDTO queryPayMainByPayId(QueryPayMainByPayIdReqDTO request) {
		if (request == null) throw new ServiceException(MgrMsg.ERROR_01.getCode(), MgrMsg.ERROR_01.getDesc());
		String payId = request.getPayId();
		if (StringUtils.isEmpty(payId)) throw new ServiceException(MgrMsg.ERROR_02.getCode(), MgrMsg.ERROR_02.getDesc());
		PayMain payMain = this.payMainService.queryByPayId(payId);
		QueryPayMainByPayIdRespDTO result = new QueryPayMainByPayIdRespDTO();
		result.setResultCode(Msg.S000.getCode());
		result.setResultDesc(Msg.S000.getDesc());
		result.setResultData(payMain);
		LOG.debug("#####result={}", Gl365StrUtils.toStr(result));
		return result;
	}

	@Override
	public PageInfo<PayMain> queryPagePayMain(QueryPagePayMainReqDTO request) {
		int pageNum = request.getPageNum();
		int pageSize = request.getPageSize();
		LOG.debug("#####result={}", Gl365StrUtils.toStr(request));
		PayMain payMain = new PayMain();
		BeanUtils.copyProperties(request, payMain);
		PageInfo<PayMain> result = this.payMainService.queryPagePayMain(payMain, pageNum, pageSize);
		LOG.debug("#####result={}", Gl365StrUtils.toStr(result));
		return result;
	}

	@Override
	public PageInfo<PayReturn> queryPagePayReturn(QueryPagePayReturnReqDTO request) {
		int pageNum = request.getPageNum();
		int pageSize = request.getPageSize();
		PayReturn payReturn = new PayReturn();
		BeanUtils.copyProperties(request, payReturn);
		PageInfo<PayReturn> result = this.payReturnService.queryPagePayReturn(payReturn, pageNum, pageSize);
		LOG.debug("#####result={}", Gl365StrUtils.toStr(result));
		return result;
	}

	@Override
	public QueryPayReturnByPayIdRespDTO queryPayReturnByPayId(QueryPayReturnByPayIdReqDTO request) {
		String payId = request.getPayId();
		if (StringUtils.isEmpty(payId)) throw new ServiceException(MgrMsg.ERROR_02.getCode(), MgrMsg.ERROR_02.getDesc());
		PayReturn payReturn = this.payReturnService.queryByPayId(payId);
		QueryPayReturnByPayIdRespDTO result = new QueryPayReturnByPayIdRespDTO();
		result.setResultCode(Msg.S000.getCode());
		result.setResultDesc(Msg.S000.getDesc());
		result.setResultData(payReturn);
		return result;
	}

	@Override
	public QueryPayDetailByPayIdRespDTO queryPayDetailByPayId(QueryPayDetailByPayIdReqDTO request) {
		String payId = request.getPayId();
		if (StringUtils.isEmpty(payId)) throw new ServiceException(MgrMsg.ERROR_02.getCode(), MgrMsg.ERROR_02.getDesc());
		List<PayDetail> list = this.payDetailService.queryPayDetailByPayId(payId);
		QueryPayDetailByPayIdRespDTO result = new QueryPayDetailByPayIdRespDTO();
		result.setResultCode(Msg.S000.getCode());
		result.setResultDesc(Msg.S000.getDesc());
		result.setResultData(list);
		return result;
	}
}
