package com.gl365.payment.dto.rollback.request;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.payment.dto.base.request.PayReqDTO;
import com.gl365.payment.util.Gl365StrUtils;
public class RollbackReqDTO extends PayReqDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String origRequestId;
	@JsonFormat(pattern = "yyyyMMdd")
	@DateTimeFormat(pattern = "yyyyMMdd")
	private LocalDate origTxnDate;
	private String origPayId;

	/**
	 * <p>原消费确认交易流水号	String	32	否</p>
	 * @return the origRequestId
	 */
	public String getOrigRequestId() {
		return origRequestId;
	}

	/**
	 * <p>原消费确认交易流水号	String	32	否</p>
	 * @param origRequestId the origRequestId to set
	 */
	public void setOrigRequestId(String origRequestId) {
		this.origRequestId = origRequestId;
	}

	/**
	 * <p>原交易日期	String	8	否</p>
	 * @return the origTxnDate
	 */
	public LocalDate getOrigTxnDate() {
		return origTxnDate;
	}

	/**
	 * <p>原交易日期	String	8	否</p>
	 * @param origTxnDate the origTxnDate to set
	 */
	public void setOrigTxnDate(LocalDate origTxnDate) {
		this.origTxnDate = origTxnDate;
	}

	/**
	 * <p>原消费确认交易流水号</p>
	 * @return the origPayId
	 */
	public String getOrigPayId() {
		return origPayId;
	}

	/**
	 * <p>原消费确认交易流水号</p>
	 * @param origPayId the origPayId to set
	 */
	public void setOrigPayId(String origPayId) {
		this.origPayId = origPayId;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
