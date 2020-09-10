package config;


import dto.LoginUser;
import dto.ResponseInfo;
import dto.Token;
import filter.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import service.TokenService;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * spring security处理器
 */
@Configuration
public class SecurityHandlerConfig {

    @Autowired
    private TokenService tokenService;

    /**
     * 登陆成功，返回Token
     *
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                //当前登录用户
                LoginUser loginUser = (LoginUser) authentication.getPrincipal();
                //用LoginUser去生成token
                Token token = tokenService.saveToken(loginUser);
                //登录成功之后我们需要返回token到前端，以JSON的形式
                ResponseUtil.responseJson(response, HttpStatus.OK.value(), token);
            }
        };
    }

    /**
     * 登陆失败
     *
     * @return
     */
    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return new AuthenticationFailureHandler() {

            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException exception) throws IOException, ServletException {
                String msg = null;
                if (exception instanceof BadCredentialsException) {
                    msg = "密码错误";
                } else {
                    msg = exception.getMessage();
                }
                ResponseInfo info = new ResponseInfo(HttpStatus.UNAUTHORIZED.value() + "", msg);
                ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), info);
            }
        };

    }

    /**
     * 未登录，返回401
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {

            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {
                ResponseInfo info = new ResponseInfo(HttpStatus.UNAUTHORIZED.value() + "", "请先登录");
                ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), info);
            }
        };
    }

    /**
     * 退出处理
     *
     * @return
     */
    @Bean
    public LogoutSuccessHandler logoutSussHandler() {
        return new LogoutSuccessHandler() {

            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
                ResponseInfo info = new ResponseInfo(HttpStatus.OK.value() + "", "退出成功");

                String token = TokenFilter.getToken(request);
                tokenService.deleteToken(token);

                ResponseUtil.responseJson(response, HttpStatus.OK.value(), info);
            }
        };

    }

}
