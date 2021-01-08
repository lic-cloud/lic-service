package cn.bestsort.service;

import java.util.Collection;
import java.util.List;

import cn.bestsort.model.entity.RolePermission;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-18 08:23
 */
public interface RolePermissionService extends BaseService<RolePermission, Long> {
    /**
     * 依据角色id获取RolePermission实例集合
     *
     * @param roleId
     * @return
     */
    List<RolePermission> listByRoleId(Long roleId);

    /**
     * 依据角色id集合获取RolePermission实例集合
     *
     * @param ids
     * @return
     */
    List<RolePermission> listByRoleIds(Collection<Long> ids);

    /**
     * 依据权限id获取RolePermission实例集合
     * @param permissionId
     * @return
     */
    List<RolePermission> listByPermissionId(Long permissionId);

}
