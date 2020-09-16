package cn.bestsort.authority.dto;

import java.io.Serializable;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public class ResponseInfo implements Serializable {

	private static final long serialVersionUID = -4417715614021482064L;

	private String code;
	private String message;

	public ResponseInfo(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
