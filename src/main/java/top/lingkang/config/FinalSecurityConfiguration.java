package top.lingkang.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import top.lingkang.FinalManager;
import top.lingkang.error.FinalExceptionHandler;
import top.lingkang.error.impl.DefaultFinalExceptionHandler;
import top.lingkang.http.impl.FinalRequestSpringMVC;
import top.lingkang.http.impl.FinalResponseSpringMVC;
import top.lingkang.session.FinalTokenGenerate;
import top.lingkang.session.SessionListener;
import top.lingkang.session.impl.DefaultFinalTokenGenerate;

import java.util.Map;

/**
 * @author lingkang
 * @date 2021/8/10 15:22
 * @description
 */
//@ComponentScan("top.lingkang")
@Configuration
@EnableConfigurationProperties(FinalSecurityProperties.class)
public class FinalSecurityConfiguration implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Autowired
    private FinalSecurityProperties properties;


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public FinalSecurityConfig getFinalSecurityConfig() {
        Map<String, FinalSecurityConfig> finalSecurityConfigMap = applicationContext.getBeansOfType(FinalSecurityConfig.class);
        if (!finalSecurityConfigMap.isEmpty()) {
            for (Map.Entry<String, FinalSecurityConfig> entry : finalSecurityConfigMap.entrySet()) {
                return entry.getValue();
            }
        }

        // 返回一个空对象
        return new FinalSecurityConfig();
    }

    @Bean
    @Order(0)
    public void FinalSecurityInit() {
        // 填加配置
        addFinalSecurityProperties();
        // 初始化http请求上下文
        addHttpServletRequest();
        addHttpServletResponse();

        // 自定义配置大于默认
        FinalSecurityConfig finalSecurityConfig = getFinalSecurityConfig();

        // 初始化添加会话监听
        addSessionListener(finalSecurityConfig.getSessionListener());

        // 添加session token生成方式
        addSessionTokenGenerate(finalSecurityConfig.getFinalTokenGenerate());

        // 添加异常处理
        addFinalExceptionHandler(finalSecurityConfig.getFinalExceptionHandler());

    }


    private void addFinalSecurityProperties() {
        FinalManager.setFinalSecurityProperties(properties);
    }

    private void addSessionListener(SessionListener sessionListener) {
        if (sessionListener != null) {
            FinalManager.setSessionListener(sessionListener);
            return;
        }
        SessionListener beanByClass = getBeanByClass(SessionListener.class);
        if (beanByClass != null) {
            FinalManager.setSessionListener(beanByClass);
        }
    }


    private void addHttpServletRequest() {
        FinalManager.setFinalRequest(new FinalRequestSpringMVC(null));
    }

    private void addHttpServletResponse() {
        FinalManager.setFinalResponse(new FinalResponseSpringMVC());
    }

    private void addSessionTokenGenerate(FinalTokenGenerate finalTokenGenerate) {
        if (finalTokenGenerate != null) {
            FinalManager.setFinalTokenGenerate(finalTokenGenerate);
            return;
        }
        FinalTokenGenerate beanByClass = getBeanByClass(FinalTokenGenerate.class);
        if (beanByClass != null) {
            FinalManager.setFinalTokenGenerate(beanByClass);
        } else { // 默认token生成方式为 UUID
            FinalManager.setFinalTokenGenerate(new DefaultFinalTokenGenerate());
        }
    }

    private void addFinalExceptionHandler(FinalExceptionHandler finalExceptionHandler) {
        if (finalExceptionHandler != null) {
            FinalManager.setFinalExceptionHandler(finalExceptionHandler);
            return;
        }
        FinalExceptionHandler beanByClass = getBeanByClass(FinalExceptionHandler.class);
        if (beanByClass != null) {
            FinalManager.setFinalExceptionHandler(beanByClass);
        } else {
            // 默认
            FinalManager.setFinalExceptionHandler(new DefaultFinalExceptionHandler());
        }

    }


    private <T> T getBeanByClass(Class<T> clazz) {
        Map<String, T> beansOfType = applicationContext.getBeansOfType(clazz);
        if (!beansOfType.isEmpty()) {
            for (Map.Entry<String, T> entry : beansOfType.entrySet()) {
                return entry.getValue();
            }
        }
        return null;
    }
}
