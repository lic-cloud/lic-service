package cn.bestsort.service.impl;

import java.util.List;

import cn.bestsort.model.entity.Permission;
import cn.bestsort.repository.PermissionRepository;
import cn.bestsort.repository.RolePermissionRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Slf4j
@Service
public class PermissionServiceImpl extends AbstractBaseService<Permission, Long>
    implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepo;
    @Autowired
    private RolePermissionRepository rolePermission;

    @Override
    public List<Permission> listByType(Integer type) {
        return permissionRepo.findAllByType(type);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        rolePermission.deleteByPermissionId(id);
        permissionRepo.deleteById(id);
        permissionRepo.deleteByParentId(id);
    }

    @Override
    public Permission findByName(String name) {
        return permissionRepo.findByName(name);
    }

    protected PermissionServiceImpl(
        PermissionRepository repository) {
        super(repository);
        permissionRepo = repository;
    }

}
