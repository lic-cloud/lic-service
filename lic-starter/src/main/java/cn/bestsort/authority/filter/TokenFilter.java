package cn.bestsort.authority.filter;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bestsort.authority.dto.LoginUser;
import cn.bestsort.authority.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Token过滤器
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
    //10分种

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String token = getToken(request);
        //获取token
        if (StringUtils.isNotBlank(token)) {
            LoginUser loginUser = tokenService.getLoginUser(token);
            //根据token获取当前登录用户
            if (loginUser != null) {
                loginUser = checkLoginTime(loginUser);
                //检查登录时间的方法
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser,
                                                                                                             null, loginUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                //把凭证设置过来 可以获取当前登录用户在上下文 package com.boot.security.server.utils;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 校验时间<br>
     * 过期时间与当前时间对比，临近过期10分钟内的话，自动刷新缓存
     *
     * @param loginUser
     * @return
     */
    private LoginUser checkLoginTime(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        //过期时间
        long currentTime = System.currentTimeMillis();
        //当前时间
        if (expireTime - currentTime <= MINUTES_10) {
            String token = loginUser.getToken();
            loginUser = (LoginUser) userDetailsService.loadUserByUsername(loginUser.getUsername());
            //根据用户名找到当前的登录用户
            loginUser.setToken(token);
            //把老的token再给新查出来的用户设置
            tokenService.refresh(loginUser);
            //刷新
        }
        return loginUser;
    }

    /**
     * 根据参数或者header获取token
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getParameter(TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            token = request.getHeader(TOKEN_KEY);
        }
        return token;
    }

}
