package cn.bestsort.model.entity.user;

import javax.persistence.Entity;

import cn.bestsort.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class Notice extends BaseEntity {
    private String title;
    private String content;
    private Integer status;

    public interface Status {
        int DRAFT = 0;
        int PUBLISH = 1;
    }

}
