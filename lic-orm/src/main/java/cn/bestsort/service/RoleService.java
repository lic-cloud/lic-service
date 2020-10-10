package cn.bestsort.service;

import cn.bestsort.model.dto.RoleDTO;
import cn.bestsort.model.entity.Role;
import cn.bestsort.model.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public interface RoleService extends BaseService<Role, Long> {

    void saveRole(RoleDTO roleDto);

    void deleteRole(Long id);

    int countRole(Map<String, Object> params);

    List<Role> listRole(Map<String, Object> params, int offset, int limit);
}
