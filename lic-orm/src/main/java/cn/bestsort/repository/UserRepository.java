package cn.bestsort.repository;

import java.util.Optional;

import cn.bestsort.model.user.User;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 11:10
 */
public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByUserName(String username);
}
