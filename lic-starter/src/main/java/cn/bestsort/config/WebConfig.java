package cn.bestsort.config;

import java.util.List;

import cn.bestsort.util.page.PageTableArgumentResolver;
import cn.bestsort.util.page.PageableResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 11:08
 */


@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 跨域支持
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }
    /**
     * datatable分页解析
     *
     * @return
     */
    @Bean
    public PageTableArgumentResolver tableHandlerMethodArgumentResolver() {
        return new PageTableArgumentResolver();
    }
    @Bean
    public PageableResolver PageableResolver() {
        return new PageableResolver();
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(tableHandlerMethodArgumentResolver());
        argumentResolvers.add(PageableResolver());
    }

}
