package dto;

import cn.bestsort.model.entity.user.User;

import java.util.List;

public class UserDto extends User {

    private static final long serialVersionUID = -184009306207076712L;
    //角色id集合（前端勾选的id）
    private List<Long> roleIds;

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

}
