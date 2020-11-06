package cn.bestsort.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@Table(name = "t_notice", uniqueConstraints = {
    @UniqueConstraint(columnNames = "title"),
})
public class Notice extends BaseEntity {
    @NotBlank(message = "title不能为空")
    @Length(min = 3,max = 50,message = "title的长度为3-50位")
    private String title;
    @NotBlank(message = "content不能为空")
    @Length(min = 3,max = 200,message = "content的长度为3-200位")
    private String content;
    @NotNull(message = "状态不能为空")
    private Integer status;

    public interface Status {
        int DRAFT = 0;
        int PUBLISH = 1;
    }

}
