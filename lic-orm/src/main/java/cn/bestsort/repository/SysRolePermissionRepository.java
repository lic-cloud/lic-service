package cn.bestsort.repository;

import cn.bestsort.model.entity.user.SysRolePermission;
import cn.bestsort.model.entity.user.SysRoleUser;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/10 7:55
 */
public interface SysRolePermissionRepository extends BaseRepository<SysRolePermission,Long>{
}
