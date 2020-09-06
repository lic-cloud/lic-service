package cn.bestsort.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/4 23:49
 */
@Data
@Entity
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class SysRolePermission {
    @Id
    private Long roleId;

    @Column
    private Long permissionId;

}
