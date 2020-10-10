package cn.bestsort.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.bestsort.model.dto.RoleDTO;
import cn.bestsort.model.entity.Role;
import cn.bestsort.model.entity.RolePermission;
import cn.bestsort.repository.impl.RepositoryEntity;
import cn.bestsort.repository.RolePermissionRepository;
import cn.bestsort.repository.RoleRepository;
import cn.bestsort.repository.RoleUserRepository;
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

    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private RoleUserRepository roleUserRepository;
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
            for (int i = 0; i < permissionIds.size(); i++) {
                lst.add(RolePermission.of(permissionIds.get(i), role.getId()));
            }
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
        rolePermissionRepository.deleteAllByRoleId(role.getId());
        if (!CollectionUtils.isEmpty(permissionIds)) {
            List<RolePermission> lst = new LinkedList<>();
            for (int i = 0; i < permissionIds.size(); i++) {
                lst.add(RolePermission.of(permissionIds.get(i), role.getId()));
            }
            rolePermissionService.saveAll(lst);
        }
        log.debug("修改角色{}", role.getName());
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        rolePermissionRepository.deleteAllByRoleId(id);
        roleUserRepository.deleteAllByRoleId(id);
        roleRepo.deleteAllById(id);
        log.debug("删除角色id:{}", id);
    }

    @Override
    public int countRole(Map<String, Object> params) {
        String name = (String) params.get("name");
        return roleRepo.count(name);
    }

    @Autowired
    private RepositoryEntity rre;

    @Override
    public List<Role> listRole(Map<String, Object> params, int offset, int limit) {
        String name = (String) params.get("name");
        String orderBy = (String) params.get("orderBy");
        return rre.listRole(name, orderBy, offset, limit);
    }

    protected RoleServiceImpl(
        RoleRepository repository, RolePermissionService rolePermissionService) {
        super(repository);
        roleRepo = repository;
        this.rolePermissionService = rolePermissionService;
    }

}
