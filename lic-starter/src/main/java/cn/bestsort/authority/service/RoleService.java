package cn.bestsort.authority.service;


import cn.bestsort.authority.dto.RoleDto;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public interface RoleService {

    void saveRole(RoleDto roleDto);

    void deleteRole(Long id);
}
