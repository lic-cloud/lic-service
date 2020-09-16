package cn.bestsort.authority.model;

import lombok.Data;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class Notice extends BaseEntity<Long> {
	private String title;
	private String content;
	private Integer status;

	public interface Status {
		int DRAFT = 0;
		int PUBLISH = 1;
	}

}
