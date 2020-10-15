package cn.bestsort.repository;

import java.util.List;

import cn.bestsort.model.entity.RoleUser;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-17 14:59
 */
public interface RoleUserRepository extends BaseRepository<RoleUser, Long> {
    /**
     * 依据用户id获得RoleUser集合
     *
     * @param id
     * @return
     */
    List<RoleUser> findAllByUserId(Long id);

    /**
     * 依据角色id获得RoleUser集合
     *
     * @param id
     * @return
     */
    List<RoleUser> findAllByRoleId(Long id);

    /**
     * 依据用户id删除用户-角色
     *
     * @param id
     */
    void deleteAllByUserId(Long id);

    /**
     * 依据角色id删除用户-角色
     *
     * @param id
     */
    void deleteAllByRoleId(Long id);
}
