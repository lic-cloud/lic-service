package cn.bestsort.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.dto.RoleDTO;
import cn.bestsort.model.entity.Role;
import cn.bestsort.model.entity.RolePermission;
import cn.bestsort.repository.RolePermissionRepository;
import cn.bestsort.repository.RoleRepository;
import cn.bestsort.repository.RoleUserRepository;
import cn.bestsort.repository.impl.RepositoryEntity;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleServiceImpl extends AbstractBaseService<Role, Long>
    implements RoleService {
    @Autowired
    private RepositoryEntity rre;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private RoleUserRepository roleUserRepository;

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
            throw ExceptionConstant.ROLE_EXIT;
            /*throw new IllegalArgumentException(role.getName() + "已存在");*/
        }
        save(role);
        if (!CollectionUtils.isEmpty(permissionIds)) {
            List<RolePermission> lst = new LinkedList<>();
            for (int i = 0; i < permissionIds.size(); i++) {
                lst.add(RolePermission.of(permissionIds.get(i), role.getId()));
            }
            rolePermissionRepository.saveAll(lst);
        }
    }

    private void updateRole(Role role, List<Long> permissionIds) {
        Role r = roleRepo.findFirstByName(role.getName());
        //由角色名查询出来的角色id是否与传入的角色id相同  相同更新
        //不同 即已存在相同名称的角色
        if (r != null && !r.getId().equals(role.getId())) {
            throw ExceptionConstant.ROLE_EXIT;
            //throw new IllegalArgumentException(role.getName() + "已存在");
        }
        update(role, role.getId());
        rolePermissionRepository.deleteAllByRoleId(role.getId());
        if (!CollectionUtils.isEmpty(permissionIds)) {
            List<RolePermission> lst = new LinkedList<>();
            for (int i = 0; i < permissionIds.size(); i++) {
                lst.add(RolePermission.of(permissionIds.get(i), role.getId()));
            }
            rolePermissionRepository.saveAll(lst);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        if (!roleUserRepository.findAllByRoleId(id).isEmpty()) {
            throw ExceptionConstant.DICTIONARY_IN_USE;
        }
        rolePermissionRepository.deleteAllByRoleId(id);
        roleUserRepository.deleteAllByRoleId(id);
        roleRepo.deleteAllById(id);
    }

    @Override
    public int count(Map<String, Object> params) {
        String name = (String) params.get("name");
        return roleRepo.count(name);
    }

    @Override
    public List<Role> list(Map<String, Object> params, int offset, int limit) {
        String name = (String) params.get("name");
        String orderBy = (String) params.get("orderBy");
        return rre.listRole(name, orderBy, offset, limit);
    }

    protected RoleServiceImpl(
        RoleRepository repository) {
        super(repository);
        roleRepo = repository;
    }

}
