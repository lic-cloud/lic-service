package cn.bestsort.service;

import java.util.List;

import cn.bestsort.model.entity.Permission;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public interface PermissionService extends BaseService<Permission, Long> {

    List<Permission> listByType(Integer type);

    void delete(Long id);
}
