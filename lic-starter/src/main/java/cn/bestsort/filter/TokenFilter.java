package cn.bestsort.filter;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bestsort.model.dto.PermissionDTO;
import cn.bestsort.model.entity.RolePermission;
import cn.bestsort.model.entity.RoleUser;
import cn.bestsort.model.vo.LoginUserVO;
import cn.bestsort.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Token过滤器
 *
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    public static final String TOKEN_KEY = "token";
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserDetailsService userDetailsService;

    private static final Long MINUTES_10 = 10 * 60 * 1000L;


    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private RoleUserService roleUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        //获取token
        String token = getToken(request);
        if (StringUtils.isNotBlank(token)) {
            //根据token获取当前登录用户
            LoginUserVO loginUserVO = tokenService.getLoginUser(token);
            if (loginUserVO != null) {
                //检查登录时间的方法
                loginUserVO = checkLoginTime(loginUserVO);
                //动态更新权限
                List<PermissionDTO> all = permissionService.listAllByIds(
                    rolePermissionService.listByRoleIds(
                        roleUserService.listByUserId(loginUserVO.getId())
                            .stream()
                            .map(RoleUser::getRoleId)
                            .collect(Collectors.toSet()))
                        .stream()
                        .map(RolePermission::getPermissionId)
                        .collect(Collectors.toSet())
                ).stream().map(PermissionDTO::transForm).collect(Collectors.toList());
                if (all != loginUserVO.getPermissions()) {
                    loginUserVO.setPermissions(all);
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    loginUserVO,
                    null, loginUserVO.getAuthorities());
                //把凭证设置过来 可以获取当前登录用户在上下文 package com.boot.security.server.utils;
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 校验时间<br>
     * 过期时间与当前时间对比，临近过期10分钟内的话，自动刷新缓存
     */
    private LoginUserVO checkLoginTime(LoginUserVO loginUserVO) {
        long expireTime = loginUserVO.getExpireTime();
        //过期时间
        long currentTime = System.currentTimeMillis();
        //当前时间
        if (expireTime - currentTime <= MINUTES_10) {
            String token = loginUserVO.getToken();
            //根据用户名找到当前的登录用户
            loginUserVO = (LoginUserVO) userDetailsService.loadUserByUsername(loginUserVO.getUsername());
            //把老的token再给新查出来的用户设置
            loginUserVO.setToken(token);
            //刷新
            tokenService.refresh(loginUserVO);
        }
        return loginUserVO;
    }

    /**
     * 根据参数或者header获取token
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getParameter(TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            token = request.getHeader(TOKEN_KEY);
        }
        return token;
    }

}
