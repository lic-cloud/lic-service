package cn.bestsort.repository;

import java.util.Collection;
import java.util.List;

import cn.bestsort.model.entity.RolePermission;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-18 08:23
 */
public interface RolePermissionRepository extends BaseRepository<RolePermission, Long> {
    List<RolePermission> findAllByRoleId(Long roleId);
    List<RolePermission> findAllByPermissionId(Long permissionId);
    List<RolePermission> findAllByRoleIdIn(Collection<Long> ids);
}
