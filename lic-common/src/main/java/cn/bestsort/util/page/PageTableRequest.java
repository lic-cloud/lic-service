package cn.bestsort.util.page;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/** 分页查询参数
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class PageTableRequest implements Serializable {
	private Integer offset;
	private Integer limit;
	private Map<String, Object> params;
}
