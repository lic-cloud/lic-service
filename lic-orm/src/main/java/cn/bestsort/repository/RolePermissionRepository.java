package cn.bestsort.repository;

import java.util.Collection;
import java.util.List;

import cn.bestsort.model.entity.RolePermission;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-18 08:23
 */
public interface RolePermissionRepository extends BaseRepository<RolePermission, Long> {
    /**
     * 依据角色id获得RolePermission实例集合
     *
     * @param roleId
     * @return
     */
    List<RolePermission> findAllByRoleId(Long roleId);

    /**
     * 依据权限id获得RolePermission实例集合
     * @param permissionId
     * @return
     */
    List<RolePermission> findAllByPermissionId(Long permissionId);
    /**
     * 依据角色id集合获得RolePermission实例集合
     *
     * @param ids
     * @return
     */
    List<RolePermission> findAllByRoleIdIn(Collection<Long> ids);

    /**
     * 依据权限id删除符合的RolePermission
     *
     * @param id
     */
    void deleteByPermissionId(Long id);

    /**
     * 依据角色id删除符合的RolePermission
     *
     * @param id
     */
    void deleteAllByRoleId(Long id);
}
