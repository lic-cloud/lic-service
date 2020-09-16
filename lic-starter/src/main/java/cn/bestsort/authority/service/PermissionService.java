package cn.bestsort.authority.service;


import cn.bestsort.authority.model.Permission;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public interface PermissionService {

    void save(Permission permission);

    void update(Permission permission);

    void delete(Long id);
}
