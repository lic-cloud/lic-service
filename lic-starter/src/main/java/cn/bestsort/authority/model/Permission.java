package cn.bestsort.authority.model;

import lombok.Data;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 08:28
 */

@Data
public class Permission extends BaseEntity {
    private Long                parentId;
    private String              name;
    private String              css;
    private String              href;
    private Integer             type;
    private String              permission;
    private Integer             sort;
}
