package cn.bestsort.service;

import java.util.List;

import cn.bestsort.model.entity.RoleUser;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-17 15:06
 */
public interface RoleUserService extends BaseService<RoleUser, Long> {

    /**
     * 通过用户id获取RoleUser集合
     *
     * @param id
     * @return
     */
    List<RoleUser> listByUserId(Long id);

    /**
     * 通过角色id获取RoleUser集合
     * @param id
     * @return
     */
    List<RoleUser> listByRoleId(Long id);
}
