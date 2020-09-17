package cn.bestsort.authority.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public abstract class BaseEntity<ID extends Long> implements Serializable {

    private static final long serialVersionUID = 2054813493011812469L;

    private ID id;
    private Date createTime = new Date();
    private Date updateTime = new Date();


}
