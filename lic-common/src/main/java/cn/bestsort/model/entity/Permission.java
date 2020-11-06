package cn.bestsort.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 08:28
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_permission", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name"),
})
public class Permission extends BaseEntity {
    @NotNull(message = "parentId不能为空")
    private Long parentId;
    @NotBlank(message = "name不能为空")
    @Length(min = 3, max = 50, message = "名称的长度为3-50位")
    private String name;
    @NotBlank(message = "css不能为空")
    private String css;
    private String href;
    @NotNull(message = "type不能为空")
    private Integer type;
    private String permission;
    @NotNull(message = "sort不能为空")
    @Range(min = 0, max = 1000, message = "排序的范围为0-1000")
    private Integer sort;
}
