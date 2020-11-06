package cn.bestsort.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;
/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */

@Data
@Entity
@Table(name = "t_dict")
@EqualsAndHashCode(callSuper = true)
public class Dict extends BaseEntity {

    @NotBlank(message = "type不能为空")
    @Length(min = 1,max = 16, message = "type长度为1-16位")
    private String type;

    @NotBlank(message = "k不能为空")
    @Length(min = 1,max = 8, message = "k长度为1-8个位")
    private String k;

    @NotBlank(message = "val不能为空")
    @Length(min = 1,max = 8, message = "val长度为1-8个位")
    private String val;
}
