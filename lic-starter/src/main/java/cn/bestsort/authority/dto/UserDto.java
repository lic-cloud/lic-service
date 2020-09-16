package cn.bestsort.authority.dto;


import cn.bestsort.authority.model.User;
import lombok.Data;

import java.util.List;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Data
public class UserDto extends User {
	/**
	 * 角色id集合（前端勾选的id）
	 */
    private List<Long> roleIds;

}
