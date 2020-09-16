package cn.bestsort.authority.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 2054813493011812469L;

	private ID id;
	private Date createTime = new Date();
	private Date updateTime = new Date();


}
