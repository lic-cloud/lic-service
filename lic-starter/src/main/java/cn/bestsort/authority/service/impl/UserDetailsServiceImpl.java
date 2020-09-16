package cn.bestsort.authority.service.impl;


import java.util.List;

import cn.bestsort.authority.dao.PermissionDao;
import cn.bestsort.authority.dto.LoginUser;
import cn.bestsort.authority.model.Permission;
import cn.bestsort.authority.model.User;
import cn.bestsort.authority.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * spring security登陆处理
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User sysUser = userService.getUser(username);
        if (sysUser == null) {
            throw new AuthenticationCredentialsNotFoundException("用户名不存在");
        } else if (sysUser.getStatus() == User.Status.LOCKED) {
            throw new LockedException("用户被锁定,请联系管理员");
        } else if (sysUser.getStatus() == User.Status.DISABLED) {
            throw new DisabledException("用户已作废");
        }
        //UserDetails是一个接口 定义一个LoginUser实现这个接口
        LoginUser loginUser = new LoginUser();
        //进行对象之间属性的赋值
        BeanUtils.copyProperties(sysUser, loginUser);

        List<Permission> permissions = permissionDao.listByUserId(sysUser.getId());
        loginUser.setPermissions(permissions);

        return loginUser;
    }

}
