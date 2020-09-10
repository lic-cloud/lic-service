package cn.bestsort.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bestsort.constant.MetaEnum;
import cn.bestsort.model.entity.user.User;
import cn.bestsort.service.UserService;
import cn.bestsort.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 10:52
 */

@WebFilter
@Component
@Slf4j
public class RequestInitFilter implements Filter {

    @Autowired
    UserService userService;
    private static final String REMEMBER_TOKEN  = "rememberToken";

    private static final Cookie EMPTY_REMEMBER_TOKEN = new Cookie(REMEMBER_TOKEN, null);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request    = (HttpServletRequest) servletRequest;
        User               userEntity = (User) request.getSession().getAttribute(MetaEnum.USER_SESSION.getVal());
        request.getSession().setAttribute(MetaEnum.USER_IP.getVal(), RequestUtil.getClientIp(request));
        // add necessary attribute
        request.setAttribute(MetaEnum.REQUEST_START_STAMP.getVal(), System.nanoTime());
        // add user info
        if (userEntity == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (REMEMBER_TOKEN.equals(cookie.getName())) {
                    userEntity = userService.login(cookie.getValue(), RequestUtil.getClientIp(request));
                    if (userEntity == null) {
                        ((HttpServletResponse) response).addCookie(EMPTY_REMEMBER_TOKEN);
                        break;
                    }
                    request.getSession().setAttribute(MetaEnum.USER_SESSION.getVal(), userEntity);
                    log.debug("user {} login by cookie", userEntity.getUserName());
                    break;
                }
            }
        }
        chain.doFilter(request, response);
    }
}
