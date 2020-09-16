package cn.bestsort.authority.dto;


import java.util.List;

import cn.bestsort.authority.model.Role;
import lombok.Data;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class RoleDto extends Role {

    private List<Long> permissionIds;

}
