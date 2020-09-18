package cn.bestsort.model.dto;

import java.util.List;

import cn.bestsort.model.entity.Permission;
import cn.bestsort.util.SpringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionDTO extends Permission {
    public static PermissionDTO transForm(Permission p) {
        PermissionDTO dto = new PermissionDTO();
        SpringUtil.cloneWithoutNullVal(p, dto);
        return dto;
    }
    private List<PermissionDTO> child;
}
