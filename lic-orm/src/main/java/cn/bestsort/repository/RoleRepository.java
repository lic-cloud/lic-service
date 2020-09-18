package cn.bestsort.repository;

import cn.bestsort.model.entity.Role;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 14:58
 */
public interface RoleRepository extends BaseRepository<Role, Long> {
    Role findFirstByName(String name);
}
