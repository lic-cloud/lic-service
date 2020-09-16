package cn.bestsort.authority.dto;


import cn.bestsort.authority.model.User;

import java.util.List;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public class UserDto extends User {

	private static final long serialVersionUID = -184009306207076712L;

	private List<Long> roleIds;/*角色id集合（前端勾选的id）*/

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

}
