package cn.bestsort.authority.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class ResponseInfo implements Serializable {

	private String code;
	private String message;

	public ResponseInfo(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}


}
