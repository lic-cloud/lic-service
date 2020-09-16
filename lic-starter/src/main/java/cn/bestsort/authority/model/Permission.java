package cn.bestsort.authority.model;

import lombok.Data;

import java.util.List;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class Permission extends BaseEntity<Long> {
    private Long parentId;
    private String name;
    private String css;
    private String href;
    private Integer type;
    private String permission;
    private Integer sort;
    private List<Permission> child;
}
