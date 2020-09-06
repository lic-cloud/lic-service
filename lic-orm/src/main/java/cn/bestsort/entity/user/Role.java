package cn.bestsort.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/4 23:12
 */

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity{

    @Column(nullable = false,length = 50)
    private String roleName;

    @Column(nullable = false,length = 100)
    private String roleDescription;

}
