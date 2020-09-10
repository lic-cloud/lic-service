package cn.bestsort.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 11:08
 */


//@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/META-INF/resources/")
            .addResourceLocations("classpath:/resources/")
            .addResourceLocations("classpath:/resources/templates/")
            .addResourceLocations("classpath:/static/")
            .addResourceLocations("classpath:/public/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> exclude = new ArrayList<>(8);
        exclude.add("/js/**");
        exclude.add("/css/**");
        exclude.add("/favicon.ico");
        exclude.add("/");
    }
}
