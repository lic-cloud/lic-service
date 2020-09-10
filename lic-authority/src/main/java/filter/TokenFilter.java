package filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.LoginUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import service.TokenService;

/**
 * Token过滤器 实现权限过滤的核心
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

	public static final String TOKEN_KEY = "token";

	@Autowired
	private TokenService tokenService;
	@Autowired
	private UserDetailsService userDetailsService;

	private static final Long MINUTES_10 = 10 * 60 * 1000L; //10分种

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//获取token
		String token = getToken(request);
		if (StringUtils.isNotBlank(token)) {
			//根据token获取当前登录用户
			LoginUser loginUser = tokenService.getLoginUser(token);
			if (loginUser != null) {
				//检查登录时间的方法
				loginUser = checkLoginTime(loginUser);
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
		//过期时间
		long expireTime = loginUser.getExpireTime();
		//当前时间
		long currentTime = System.currentTimeMillis();
		if (expireTime - currentTime <= MINUTES_10) {
			String token = loginUser.getToken();
			//根据用户名找到当前的登录用户
			loginUser = (LoginUser) userDetailsService.loadUserByUsername(loginUser.getUsername());
			//把老的token再给新查出来的用户设置
			loginUser.setToken(token);
			//刷新
			tokenService.refresh(loginUser);
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
