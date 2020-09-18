package cn.bestsort.repository;

import cn.bestsort.model.entity.User;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 11:10
 */
public interface UserRepository extends BaseRepository<User, Long> {
    User findByUsername(String username);
}
