package cn.bestsort.authority.service.impl;


import cn.bestsort.authority.dao.RoleDao;
import cn.bestsort.authority.dto.RoleDto;
import cn.bestsort.authority.model.Role;
import cn.bestsort.authority.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {


	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional
	public void saveRole(RoleDto roleDto) {
		Role role = roleDto;
		List<Long> permissionIds = roleDto.getPermissionIds();
		permissionIds.remove(0L);

		if (role.getId() != null) {
			// 修改
			updateRole(role, permissionIds);
		} else {
			// 新增
			saveRole(role, permissionIds);
		}
	}

	private void saveRole(Role role, List<Long> permissionIds) {
		Role r = roleDao.getRole(role.getName());
		if (r != null) {
			throw new IllegalArgumentException(role.getName() + "已存在");
		}

		roleDao.save(role);
		if (!CollectionUtils.isEmpty(permissionIds)) {
			roleDao.saveRolePermission(role.getId(), permissionIds);
		}
		log.debug("新增角色{}", role.getName());
	}

	private void updateRole(Role role, List<Long> permissionIds) {
		Role r = roleDao.getRole(role.getName());
		if (r != null && r.getId() != role.getId()) {
			throw new IllegalArgumentException(role.getName() + "已存在");
		}

		roleDao.update(role);

		roleDao.deleteRolePermission(role.getId());
		if (!CollectionUtils.isEmpty(permissionIds)) {
			roleDao.saveRolePermission(role.getId(), permissionIds);
		}
		log.debug("修改角色{}", role.getName());
	}

	@Override
	@Transactional
	public void deleteRole(Long id) {
		roleDao.deleteRolePermission(id);
		roleDao.deleteRoleUser(id);
		roleDao.delete(id);

		log.debug("删除角色id:{}", id);
	}

}
