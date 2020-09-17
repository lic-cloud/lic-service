package cn.bestsort.authority.config;


import cn.bestsort.authority.dto.LoginUser;
import cn.bestsort.authority.dto.ResponseInfo;
import cn.bestsort.authority.dto.Token;
import cn.bestsort.authority.filter.TokenFilter;
import cn.bestsort.authority.service.TokenService;
import cn.bestsort.authority.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * spring security处理器
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Slf4j
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
        return (request, response, authentication) -> {
            //当前登录用户
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            //用LoginUser去生成token
            Token token = tokenService.saveToken(loginUser);
            //登录成功之后我们需要返回token到前端，以JSON的形式
            ResponseUtil.responseJson(response, HttpStatus.OK.value(), token);
        };
    }

    /**
     * 登陆失败
     *
     * @return
     */
    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
            String msg;
            if (exception instanceof BadCredentialsException) {
                msg = "密码错误";
            } else {
                msg = exception.getMessage();
            }
            ResponseInfo info = new ResponseInfo(HttpStatus.UNAUTHORIZED.value() + "", msg);
            ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), info);
        };

    }

    /**
     * 未登录，返回401
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            ResponseInfo info = new ResponseInfo(HttpStatus.UNAUTHORIZED.value() + "", "请先登录");
            ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), info);
        };
    }

    /**
     * 退出处理
     *
     * @return
     */
    @Bean
    public LogoutSuccessHandler logoutSussHandler() {
        return (request, response, authentication) -> {
            ResponseInfo info = new ResponseInfo(HttpStatus.OK.value() + "", "退出成功");

            String token = TokenFilter.getToken(request);
            tokenService.deleteToken(token);

            ResponseUtil.responseJson(response, HttpStatus.OK.value(), info);
        };

    }

}
