package cn.bestsort.model.dto;

import cn.bestsort.model.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/11/4 14:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRegisterDTO extends User {
    private String address;
}
