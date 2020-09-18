package cn.bestsort.model.vo;


import java.util.Date;

import cn.bestsort.model.entity.Notice;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeReadVO extends Notice {
    private Long userId;
    private Date readTime;
    private Boolean isRead;
}
