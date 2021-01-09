package cn.bestsort.config;


import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.bestsort.filter.TokenFilter;
import cn.bestsort.model.dto.TokenDTO;
import cn.bestsort.model.vo.LoginUserVO;
import cn.bestsort.model.vo.ResponseInfo;
import cn.bestsort.service.TokenService;
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
 *
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
     */
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> {
            //获取当前登录用户
            LoginUserVO loginUser = (LoginUserVO) authentication.getPrincipal();
            //用LoginUserVO去生成token
            TokenDTO token = tokenService.saveToken(loginUser);
            //登录成功将token以JSON的形式返回到前端，
            responseJson(response, HttpStatus.OK.value(), token);
        };
    }

    /**
     * 登陆失败
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
            ResponseInfo info = ResponseInfo.of(HttpStatus.UNAUTHORIZED, msg);
            responseJson(response, HttpStatus.UNAUTHORIZED.value(), info);
        };

    }

    /**
     * 未登录，返回401
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            ResponseInfo info = ResponseInfo.of(HttpStatus.UNAUTHORIZED, "请先登录");
            responseJson(response, HttpStatus.UNAUTHORIZED.value(), info);
        };
    }

    /**
     * 退出处理
     */
    @Bean
    public LogoutSuccessHandler logoutSussHandler() {
        return (request, response, authentication) -> {
            ResponseInfo info = ResponseInfo.of(HttpStatus.OK, "退出成功");
            String token = TokenFilter.getToken(request);
            tokenService.deleteToken(token);
            responseJson(response, HttpStatus.OK.value(), info);
        };

    }

    public static void responseJson(HttpServletResponse response, int status, Object data) {
        try {
            //跨域问题
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(status);
            //data转换为json返回到前端
            response.getWriter().write(JSONObject.toJSONString(data));
        } catch (IOException e) {
            log.error("response 设置失败， response:{}, data:{}", response, data, e);
        }
    }
}
