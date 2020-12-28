package cn.bestsort.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(name = "sys_role_permission", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"permissionId", "roleId"})
})
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
