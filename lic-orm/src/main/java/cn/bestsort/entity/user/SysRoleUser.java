package cn.bestsort.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import cn.bestsort.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/4 23:39
 */
@Entity
@Data
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class SysRoleUser extends BaseEntity {

    @Column
    private Long userId;

    @Column
    private Long roleId;
}
