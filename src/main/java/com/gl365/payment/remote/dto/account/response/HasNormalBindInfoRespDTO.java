package com.gl365.payment.remote.dto.account.response;
import java.io.Serializable;
import com.gl365.payment.util.Gl365StrUtils;
public class HasNormalBindInfoRespDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String resultCode;
	private String resultDesc;
	/**
	 * <p>是否有正常绑卡信息</p>
	 * <p>Y：有N：无</p>
	 */
	private String flag;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return Gl365StrUtils.toStr(this);
	}
}
