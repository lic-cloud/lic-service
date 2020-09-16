package cn.bestsort.authority.service.impl;


import cn.bestsort.authority.dao.PermissionDao;
import cn.bestsort.authority.model.Permission;
import cn.bestsort.authority.service.PermissionService;
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
public class PermissionServiceImpl implements PermissionService {


    @Autowired
    private PermissionDao permissionDao;

    @Override
    public void save(Permission permission) {
        permissionDao.save(permission);

        log.debug("新增菜单{}", permission.getName());
    }

    @Override
    public void update(Permission permission) {
        permissionDao.update(permission);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        permissionDao.deleteRolePermission(id);
        permissionDao.delete(id);
        permissionDao.deleteByParentId(id);

        log.debug("删除菜单id:{}", id);
    }

}
