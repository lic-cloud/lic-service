package cn.bestsort.entity.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

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
public class SysRoleUser {

    @Id
    private Long userId;

    @Column
    private Long roleId;
}
