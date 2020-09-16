package cn.bestsort.authority.model;

import lombok.Data;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class Role extends BaseEntity<Long> {

    private static final long serialVersionUID = -3802292814767103648L;

    private String name;

    private String description;

}
