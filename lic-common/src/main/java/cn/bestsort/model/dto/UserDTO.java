package cn.bestsort.model.dto;


import java.util.List;

import cn.bestsort.model.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends User {
    /**
     * 角色id集合（前端勾选的id）
     */
    @NotEmpty(message = "角色不能为空")
    private List<Long> roleIds;

}
