package cn.bestsort.model.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.bestsort.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/4 23:39
 */

@Data
@Entity
@ToString
@EqualsAndHashCode(callSuper = true)
public class SysRoleUser extends BaseEntity {

    @Column
    private Long userId;

    @Column
    private Long roleId;
}
