package cn.bestsort.service;

import java.util.List;

import cn.bestsort.model.entity.Permission;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public interface PermissionService extends BaseService<Permission, Long> {
    /**
     * 依据type获取权限集合
     *
     * @param type
     * @return
     */
    List<Permission> listByType(Integer type);

    /**
     * 依据权限id删除权限
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 依据name获取权限集合
     * @param name
     * @return
     */
    Permission findByName(String name);
}
