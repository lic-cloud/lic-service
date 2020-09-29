package cn.bestsort.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 08:28
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_permission")
public class Permission extends BaseEntity {
    private Long                parentId;
    private String              name;
    private String              css;
    private String              href;
    private Integer             type;
    private String              permission;
    private Integer             sort;
}
