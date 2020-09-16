package cn.bestsort.authority.dto;


import cn.bestsort.authority.model.Notice;
import lombok.Data;

import java.util.Date;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class NoticeReadVO extends Notice {
    private Long userId;
    private Date readTime;
    private Boolean isRead;
}
