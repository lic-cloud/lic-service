package cn.bestsort.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import cn.bestsort.model.dto.PermissionDTO;
import cn.bestsort.model.entity.RolePermission;
import cn.bestsort.model.entity.RoleUser;
import cn.bestsort.model.entity.User;
import cn.bestsort.model.vo.LoginUserVO;
import cn.bestsort.service.PermissionService;
import cn.bestsort.service.RolePermissionService;
import cn.bestsort.service.RoleUserService;
import cn.bestsort.service.UserService;
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
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private RoleUserService roleUserService;
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
        LoginUserVO loginUserVO = new LoginUserVO();
        //进行对象之间属性的赋值
        BeanUtils.copyProperties(sysUser, loginUserVO);

        //list all permission about user
        List<PermissionDTO> all = permissionService.listAllByIds(
            rolePermissionService.listByRoleIds(
                roleUserService.listByUserId(sysUser.getId())
                    .stream()
                    .map(RoleUser::getRoleId)
                    .collect(Collectors.toSet()))
                .stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toSet())
        ).stream().map(PermissionDTO::transForm).collect(Collectors.toList());

        loginUserVO.setPermissions(all);

        return loginUserVO;
    }

}
