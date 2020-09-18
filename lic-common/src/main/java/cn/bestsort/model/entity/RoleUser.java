package cn.bestsort.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 15:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class RoleUser extends BaseEntity {
    @Column
    Long userId;

    @Column
    Long roleId;

    public static RoleUser of(Long userId, Long roleId) {
        RoleUser roleUser = new RoleUser();
        roleUser.roleId = roleId;
        roleUser.userId = userId;
        return roleUser;
    }
}
