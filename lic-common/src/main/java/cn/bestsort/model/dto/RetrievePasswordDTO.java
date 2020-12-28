package cn.bestsort.model.dto;

import lombok.Data;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/12/28 19:21
 */
@Data
public class RetrievePasswordDTO {
    private String username;
    private String email;
    private String newPassword;
}
