package cn.bestsort.repository;

import java.util.List;

import cn.bestsort.model.entity.Permission;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-17 18:58
 */
public interface PermissionRepository extends BaseRepository<Permission, Long> {
    /**
     * 依据type查询获得权限实例集合
     *
     * @param type
     * @return
     */
    List<Permission> findAllByType(Integer type);

    /**
     * 依据ParentId删除权限
     *
     * @param id
     */
    void deleteByParentId(Long id);
}
