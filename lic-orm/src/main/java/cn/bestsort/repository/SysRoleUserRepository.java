package cn.bestsort.repository;

import cn.bestsort.model.entity.user.Permission;
import cn.bestsort.model.entity.user.SysRoleUser;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/9 22:15
 */
public interface SysRoleUserRepository extends BaseRepository<SysRoleUser,Long> {
    //@Query(value = "select role_id from sys_role_user where user_id = ?1 ", nativeQuery = true)
    //List<Long> findAllByUserId(Long userId);
}