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
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */@Slf4j
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
            LoginUserVO loginUser = (LoginUserVO) authentication.getPrincipal();
            //用LoginUser去生成token
            TokenDTO token = tokenService.saveToken(loginUser);
            //登录成功之后我们需要返回token到前端，以JSON的形式
            responseJson(response, HttpStatus.OK.value(), token);
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
            ResponseInfo info = ResponseInfo.of(HttpStatus.UNAUTHORIZED, msg);
            responseJson(response, HttpStatus.UNAUTHORIZED.value(), info);
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
            ResponseInfo info = ResponseInfo.of(HttpStatus.UNAUTHORIZED, "请先登录");
            responseJson(response, HttpStatus.UNAUTHORIZED.value(), info);
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
            ResponseInfo info = ResponseInfo.of(HttpStatus.OK, "退出成功");

            String token = TokenFilter.getToken(request);
            tokenService.deleteToken(token);

            responseJson(response, HttpStatus.OK.value(), info);
        };

    }
    public static void responseJson(HttpServletResponse response, int status, Object data) {
        //status http的状态码
        //data token(对象)
        try {
            //跨域问题
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            //能否为前端返回token看下面的代码
            response.setContentType("application/json;charset=UTF-8");
            //http的状态码
            response.setStatus(status);
            //data转换为json返回到前端
            response.getWriter().write(JSONObject.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}