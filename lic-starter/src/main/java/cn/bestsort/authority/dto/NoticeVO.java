package cn.bestsort.authority.dto;



import java.io.Serializable;
import java.util.List;

import cn.bestsort.authority.model.Notice;
import cn.bestsort.authority.model.User;
import lombok.Data;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class NoticeVO implements Serializable {

    private Notice notice;

    private List<User> users;


}
