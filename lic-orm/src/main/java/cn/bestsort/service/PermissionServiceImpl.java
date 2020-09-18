package cn.bestsort.service;


import java.util.List;

import cn.bestsort.model.entity.Permission;
import cn.bestsort.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
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

    private PermissionRepository permissionRepo;

    @Override
    public List<Permission> listByType(Integer type) {
        return permissionRepo.findAllByType(type);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        //TODO fix

        //permissionDao.deleteRolePermission(id);
        //permissionDao.delete(id);
        //permissionDao.deleteByParentId(id);

        log.debug("删除菜单id:{}", id);
    }

    protected PermissionServiceImpl(
        PermissionRepository repository) {
        super(repository);
        permissionRepo = repository;
    }

}
