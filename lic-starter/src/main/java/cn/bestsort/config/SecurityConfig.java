package cn.bestsort.config;

import cn.bestsort.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * spring security配置
 *
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenFilter tokenFilter;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用/csrf：为了防止跨站提交攻击
        http.csrf().disable();

        // 基于token，所以不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //有些路径不需要全部去校验
        http.authorizeRequests()
            .antMatchers(
                "/",
                "/*.html",
                "/favicon.ico",
                "/css/**",
                "/js/**",
                "/fonts/**",
                "/layui/**",
                "/img/**",
                "/v2/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**",
                "/pages/**",
                "/druid/**",
                "/file/download/server/**",
                "/statics/**")
            .permitAll()
            .antMatchers(HttpMethod.POST, "/users/register").permitAll()
            .antMatchers(HttpMethod.POST, "/users/retrievePassword").permitAll()
            .antMatchers(HttpMethod.GET, "/dicts").permitAll()
            .antMatchers(HttpMethod.GET, "/install/**").permitAll()
            .antMatchers(HttpMethod.POST, "/install/**").permitAll()
            .antMatchers(HttpMethod.GET, "/share/**").permitAll()
            .antMatchers(HttpMethod.POST, "/share/**").permitAll()
            .antMatchers(HttpMethod.GET, "/share.html/**").permitAll()
            .antMatchers(HttpMethod.GET, "/file/**").permitAll()
            .antMatchers(HttpMethod.POST, "/file/**").permitAll()
            .anyRequest().authenticated();
        //配置登录的核心代码 登录成功失败异常的处理器SecurityHandlerConfig
        http.formLogin().loginPage("/login.html").loginProcessingUrl("/login")
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint);

        http.logout().logoutUrl("/logout")
            .logoutSuccessHandler(logoutSuccessHandler);

        // 解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();
        http.headers().cacheControl();

        //做一个拦截请求 获取到token 对token进行校验
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 配置查询用户的实现
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //根据用户名查询用户  + 密码的校验规则
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}
