package cn.bestsort.model.dto;

import lombok.Data;

/**
 * Restful方式登陆token
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class TokenDTO {

    private String token;
    /** 登陆时间戳（毫秒） */
    private Long loginTime;

    public TokenDTO(String token, Long loginTime) {
        this.token = token;
        this.loginTime = loginTime;
    }

}
