package cn.bestsort.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "role")
public class Role extends BaseEntity {
    @NotBlank(message = "角色名不能为空")
    @Length(min = 3, max = 20, message = "角色的长度为3-20位")
    private String name;
    @NotBlank(message = "描述不能为空")
    @Length(min = 3, max = 100, message = "描述的长度为3-100位")
    private String description;

}
