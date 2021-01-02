package cn.bestsort.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-18 08:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "role_permission")
public class RolePermission extends BaseEntity {
    @Column
    Long permissionId;

    @Column
    Long roleId;

    public static RolePermission of(Long permissionId, Long roleId) {
        RolePermission permission = new RolePermission();
        permission.permissionId = permissionId;
        permission.roleId = roleId;
        return permission;
    }
}
