package cn.bestsort.model.entity;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Notice extends BaseEntity {
    private String title;
    private String content;
    private Integer status;

    public interface Status {
        int DRAFT = 0;
        int PUBLISH = 1;
    }

}
