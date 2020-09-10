package cn.bestsort.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 20:41
 */
@AllArgsConstructor
@Data
public class UserVo {
    String  username;
    Integer userId;
    String  token;
}
