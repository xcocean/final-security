package top.lingkang.oauth.server.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import top.lingkang.oauth.error.impl.DefaultOauthExceptionHandler;
import top.lingkang.oauth.server.base.OauthCodeGenerate;
import top.lingkang.oauth.server.base.impl.DefaultOauthCodeGenerate;
import top.lingkang.oauth.server.controller.FinalAuthController;
import top.lingkang.oauth.server.storage.OauthStorageManager;
import top.lingkang.oauth.server.storage.impl.DefaultOauthStorageManager;

import java.util.Properties;

/**
 * @author lingkang
 * Created by 2022/1/11
 */
public class FinalOauthServerConfiguration {
    @ConditionalOnMissingBean(OauthCodeGenerate.class)
    @Bean
    public OauthCodeGenerate oauthCodeGenerate() {
        return new DefaultOauthCodeGenerate();
    }

    @ConditionalOnMissingBean
    @Bean
    public DefaultOauthExceptionHandler oauthExceptionHandler() {
        return new DefaultOauthExceptionHandler();
    }

    @ConditionalOnMissingBean
    @Bean
    public OauthStorageManager storageManager(){
        return new DefaultOauthStorageManager();
    }

    @Bean(name = "authController")
    public FinalAuthController authController(){
        return new FinalAuthController();
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(){
        SimpleUrlHandlerMapping mapping=new SimpleUrlHandlerMapping();
        Properties properties=new Properties();
        properties.setProperty("/oauth/**","authController");
        mapping.setMappings(properties);
        return mapping;
    }

}
