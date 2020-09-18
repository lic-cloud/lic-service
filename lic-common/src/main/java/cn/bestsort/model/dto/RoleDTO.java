package cn.bestsort.model.dto;


import java.util.List;

import cn.bestsort.model.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleDTO extends Role {

    private List<Long> permissionIds;

}
