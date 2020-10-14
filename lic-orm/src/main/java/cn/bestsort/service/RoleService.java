package cn.bestsort.service;

import cn.bestsort.model.dto.RoleDTO;
import cn.bestsort.model.entity.Role;
import cn.bestsort.util.page.Listable;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public interface RoleService extends BaseService<Role, Long>, Listable<Role> {
    /**
     * 保存角色
     *
     * @param roleDto
     */
    void saveRole(RoleDTO roleDto);

    /**
     * 删除角色
     *
     * @param id
     */
    void deleteRole(Long id);

}
