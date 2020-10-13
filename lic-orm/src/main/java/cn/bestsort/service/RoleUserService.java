package cn.bestsort.service;

import java.util.List;

import cn.bestsort.model.entity.RoleUser;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 15:06
 */
public interface RoleUserService extends BaseService<RoleUser, Long> {
    List<RoleUser> listByRoleId(Long id);

    List<RoleUser> listByUserId(Long id);
}
