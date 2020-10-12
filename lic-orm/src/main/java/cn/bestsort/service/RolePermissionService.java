package cn.bestsort.service;

import java.util.Collection;
import java.util.List;

import cn.bestsort.model.entity.RolePermission;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-18 08:23
 */
public interface RolePermissionService extends BaseService<RolePermission, Long> {
    List<RolePermission> listByRoleId(Long roleId);

    List<RolePermission> listByRoleIds(Collection<Long> ids);

    List<RolePermission> listByPermissionId(Long permissionId);
}
