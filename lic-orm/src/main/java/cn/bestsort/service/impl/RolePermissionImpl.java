package cn.bestsort.service.impl;

import java.util.Collection;
import java.util.List;

import cn.bestsort.model.entity.RolePermission;
import cn.bestsort.repository.RolePermissionRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.RolePermissionService;
import org.springframework.stereotype.Service;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-18 08:24
 */
@Service
public class RolePermissionImpl extends AbstractBaseService<RolePermission, Long>
    implements RolePermissionService {

    RolePermissionRepository repo;

    @Override
    public List<RolePermission> listByRoleId(Long roleId) {
        return repo.findAllByRoleId(roleId);
    }

    @Override
    public List<RolePermission> listByRoleIds(Collection<Long> ids) {
        return repo.findAllByRoleIdIn(ids);
    }

    @Override
    public List<RolePermission> listByPermissionId(Long permissionId) {
        return repo.findAllByPermissionId(permissionId);
    }

    protected RolePermissionImpl(
        RolePermissionRepository repository) {
        super(repository);
        repo = repository;
    }
}
