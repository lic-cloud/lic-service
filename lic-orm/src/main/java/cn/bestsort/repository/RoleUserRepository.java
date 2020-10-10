package cn.bestsort.repository;

import java.util.List;

import cn.bestsort.model.entity.RoleUser;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 14:59
 */
public interface RoleUserRepository extends BaseRepository<RoleUser, Long> {
    List<RoleUser> findAllByUserId(Long id);

    List<RoleUser> findAllByRoleId(Long id);

    void deleteAllByUserId(Long id);
    void deleteAllByRoleId(Long id);
}
