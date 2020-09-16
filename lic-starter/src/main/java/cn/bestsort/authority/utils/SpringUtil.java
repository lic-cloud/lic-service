package cn.bestsort.authority.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * spring获取bean工具类
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext CONTEXT = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.CONTEXT = applicationContext;
    }

    public static <T> T getBean(Class<T> cla) {
        return CONTEXT.getBean(cla);
    }

    public static <T> T getBean(String name, Class<T> cal) {
        return CONTEXT.getBean(name, cal);
    }

    public static String getProperty(String key) {
        return CONTEXT.getBean(Environment.class).getProperty(key);
    }
}
