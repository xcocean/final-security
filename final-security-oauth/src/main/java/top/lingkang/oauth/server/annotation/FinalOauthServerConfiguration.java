package top.lingkang.oauth.server.annotation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import top.lingkang.oauth.error.OauthExceptionHandler;
import top.lingkang.oauth.error.impl.DefaultOauthExceptionHandler;
import top.lingkang.oauth.server.OauthServerHolder;
import top.lingkang.oauth.server.OauthServerManager;
import top.lingkang.oauth.server.base.OauthCodeGenerate;
import top.lingkang.oauth.server.base.OauthTokenGenerate;
import top.lingkang.oauth.server.base.impl.DefaultOauthCodeGenerate;
import top.lingkang.oauth.server.base.impl.DefaultOauthTokenGenerate;
import top.lingkang.oauth.server.config.OauthServerConfig;
import top.lingkang.oauth.server.controller.AuthServerController;
import top.lingkang.oauth.server.storage.OauthStorageManager;
import top.lingkang.oauth.server.storage.impl.OauthMemorySessionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lingkang
 * Created by 2022/1/11
 */
public class FinalOauthServerConfiguration {
    @Bean
    @ConditionalOnMissingBean(OauthCodeGenerate.class)
    public OauthCodeGenerate oauthCodeGenerate() {
        return new DefaultOauthCodeGenerate();
    }

    @Bean
    @ConditionalOnMissingBean
    public OauthExceptionHandler oauthExceptionHandler() {
        return new DefaultOauthExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public OauthStorageManager storageManager() {
        return new OauthMemorySessionManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthServerController authController() {
        return new AuthServerController();
    }

    @Bean
    @ConditionalOnMissingBean
    public OauthServerConfig oauthServerConfig() {
        return new OauthServerConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    public OauthTokenGenerate oauthTokenGenerate() {
        return new DefaultOauthTokenGenerate();
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(@Qualifier("authController") AuthServerController authServerController) {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        // 需要优于 ResourceHttpRequestHandler 处理，否则 404 无法进入到 SimpleUrlHandlerMapping 的映射
        mapping.setOrder(Integer.MAX_VALUE - 2);
        Map<String, Object> map = new HashMap<>();
        map.put("/oauth/**", authServerController);
        mapping.setUrlMap(map);
        return mapping;
    }

    @Bean
    public OauthServerHolder oauthServerHolder() {
        return new OauthServerHolder();
    }

    @Bean
    public OauthServerManager oauthServerManager() {
        return new OauthServerManager();
    }
}
