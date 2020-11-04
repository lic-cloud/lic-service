package cn.bestsort.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/11/4 16:51
 */
@AllArgsConstructor
@Data
public class OtherSetVO {
    private String dir;
    private Long length;
    private Long expire;
    private Long time;
    private String unit;
}
