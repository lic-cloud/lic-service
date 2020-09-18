package cn.bestsort.model.vo;



import java.util.List;

import cn.bestsort.model.entity.Notice;
import cn.bestsort.model.entity.User;
import lombok.Data;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class NoticeVO {

    private Notice notice;

    private List<User> users;

}
