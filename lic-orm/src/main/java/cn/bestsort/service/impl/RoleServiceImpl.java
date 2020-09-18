package cn.bestsort.service.impl;

import java.util.LinkedList;
import java.util.List;

import cn.bestsort.model.dto.RoleDTO;
import cn.bestsort.model.entity.Role;
import cn.bestsort.model.entity.RolePermission;
import cn.bestsort.repository.RoleRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.RolePermissionService;
import cn.bestsort.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Slf4j
@Service
public class RoleServiceImpl extends AbstractBaseService<Role, Long> implements RoleService {


    private RoleRepository roleRepo;
    private final RolePermissionService rolePermissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(RoleDTO roleDto) {
        List<Long> permissionIds = roleDto.getPermissionIds();
        permissionIds.remove(0L);

        if (roleDto.getId() != null) {
            updateRole(roleDto.supperRole(), permissionIds);
        } else {
            saveRole(roleDto.supperRole(), permissionIds);
        }
    }

    private void saveRole(Role role, List<Long> permissionIds) {
        Role r = roleRepo.findFirstByName(role.getName());
        if (r != null) {
            throw new IllegalArgumentException(role.getName() + "已存在");
        }
        save(role);
        if (!CollectionUtils.isEmpty(permissionIds)) {
            List<RolePermission> lst = new LinkedList<>();
            permissionIds.forEach(
                i -> lst.add(RolePermission.of(role.getId(), i)));
            rolePermissionService.saveAll(lst);
        }
        log.debug("新增角色{}", role.getName());
    }

    private void updateRole(Role role, List<Long> permissionIds) {
        Role r = roleRepo.findFirstByName(role.getName());
        if (r != null && !r.getId().equals(role.getId())) {
            throw new IllegalArgumentException(role.getName() + "已存在");
        }

        update(role, role.getId());

        //TODO roleDao.deleteRolePermission(role.getId());
        if (!CollectionUtils.isEmpty(permissionIds)) {
            //TODO roleDao.saveRolePermission(role.getId(), permissionIds);
        }
        log.debug("修改角色{}", role.getName());
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        //TODO
        //roleDao.deleteRolePermission(id);
        //roleDao.deleteRoleUser(id);
        //roleDao.delete(id);

        log.debug("删除角色id:{}", id);
    }

    protected RoleServiceImpl(
        RoleRepository repository, RolePermissionService rolePermissionService) {
        super(repository);
        roleRepo = repository;
        this.rolePermissionService = rolePermissionService;
    }

}
