package cn.bestsort.util.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/** 分页查询返回
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class PageTableResponse implements Serializable {

	private Integer recordsTotal;
	private Integer recordsFiltered;
	private List<?> data;

	public PageTableResponse(Integer recordsTotal, Integer recordsFiltered, List<?> data) {
		super();
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		this.data = data;
	}
}
