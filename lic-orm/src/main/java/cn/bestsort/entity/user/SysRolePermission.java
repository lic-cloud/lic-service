package cn.bestsort.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import cn.bestsort.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/4 23:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@ToString
public class SysRolePermission extends BaseEntity {
    @Id
    private Long roleId;

    @Column
    private Long permissionId;

}
