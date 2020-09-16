package cn.bestsort.authority.dto;


import cn.bestsort.authority.model.Role;

import java.util.List;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public class RoleDto extends Role {

	private static final long serialVersionUID = 4218495592167610193L;

	private List<Long> permissionIds;

	public List<Long> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(List<Long> permissionIds) {
		this.permissionIds = permissionIds;
	}
}
