package cn.bestsort.model.entity;

import javax.persistence.Entity;

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
public class Permission extends BaseEntity {
    private Long                parentId;
    private String              name;
    private String              css;
    private String              href;
    //TODO ?
    private Integer             type;
    private String              permission;
    private Integer             sort;
}
