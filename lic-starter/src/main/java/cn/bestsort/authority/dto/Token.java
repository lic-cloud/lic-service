package cn.bestsort.authority.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Restful方式登陆token
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class Token implements Serializable {

	private static final long serialVersionUID = 6314027741784310221L;

	private String token;
	/** 登陆时间戳（毫秒） */
	private Long loginTime;

	public Token(String token, Long loginTime) {
		super();
		this.token = token;
		this.loginTime = loginTime;
	}

}
