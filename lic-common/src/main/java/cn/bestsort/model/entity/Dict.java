package cn.bestsort.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name = "t_dict")
public class Dict extends BaseEntity {

    private String type;
    private String k;
    private String val;
}
