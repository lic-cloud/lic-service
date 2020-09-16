package cn.bestsort.authority.model;

import lombok.Data;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class Dict extends BaseEntity<Long> {

	private static final long serialVersionUID = -2431140186410912787L;
	private String type;
	private String k;
	private String val;
}
