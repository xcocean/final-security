package top.lingkang.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring bean对象
 */
@Component
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanUtils.applicationContext == null) {
            SpringBeanUtils.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        try {
            return getApplicationContext().getBean(name);
        } catch (Exception e) {
            return null;
        }
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        try {
            return getApplicationContext().getBean(clazz);
        } catch (Exception e) {
        }
        return null;
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        try {
            return getApplicationContext().getBean(name, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isExistsBean(Class<?> clazz) {
        try {
            return getApplicationContext().getBean(clazz) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
