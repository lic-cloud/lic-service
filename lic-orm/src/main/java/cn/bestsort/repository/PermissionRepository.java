package cn.bestsort.repository;

import antlr.collections.impl.LList;
import cn.bestsort.model.entity.user.Permission;
import cn.bestsort.model.entity.user.SysRoleUser;
import cn.bestsort.model.entity.user.User;
import cn.bestsort.repository.BaseRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-09 11:10
 */
public interface PermissionRepository extends BaseRepository<Permission, Long> {
}
