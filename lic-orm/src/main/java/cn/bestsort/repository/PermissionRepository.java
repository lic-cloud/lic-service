package cn.bestsort.repository;

import java.util.List;

import cn.bestsort.model.entity.Permission;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 18:58
 */
public interface PermissionRepository extends BaseRepository<Permission, Long> {
    List<Permission> findAllByType(Integer type);

    void deleteByParentId(Long id);
}
