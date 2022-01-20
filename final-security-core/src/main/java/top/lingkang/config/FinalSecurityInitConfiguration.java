package top.lingkang.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import top.lingkang.FinalManager;
import top.lingkang.base.FinalExceptionHandler;
import top.lingkang.base.FinalHttpSecurity;
import top.lingkang.base.FinalSessionListener;
import top.lingkang.base.FinalTokenGenerate;
import top.lingkang.base.impl.DefaultFinalExceptionHandler;
import top.lingkang.base.impl.DefaultFinalHttpSecurity;
import top.lingkang.base.impl.DefaultFinalSessionListener;
import top.lingkang.base.impl.DefaultFinalTokenGenerate;
import top.lingkang.filter.FinalSecurityFilter;
import top.lingkang.holder.FinalHolder;
import top.lingkang.session.SessionManager;
import top.lingkang.session.impl.DefaultFinalSessionManager;

/**
 * @author lingkang
 * Created by 2022/1/20
 */
public class FinalSecurityInitConfiguration {

    @ConditionalOnMissingBean(FinalExceptionHandler.class)
    @Bean
    public FinalExceptionHandler exceptionHandler() {
        return new DefaultFinalExceptionHandler();
    }

    @ConditionalOnMissingBean(FinalTokenGenerate.class)
    @Bean
    public FinalTokenGenerate tokenGenerate() {
        return new DefaultFinalTokenGenerate();
    }

    @ConditionalOnMissingBean
    @Bean
    public SessionManager sessionManager() {
        return new DefaultFinalSessionManager();
    }

    @ConditionalOnMissingBean(FinalSessionListener.class)
    @Bean
    public FinalSessionListener finalSessionListener() {
        return new DefaultFinalSessionListener();
    }

    @ConditionalOnMissingBean(FinalHttpSecurity.class)
    @Bean
    public FinalHttpSecurity finalHttpSecurity() {
        return new DefaultFinalHttpSecurity();
    }

    @ConditionalOnMissingBean(FinalConfigProperties.class)
    @Bean
    public FinalConfigProperties finalConfigProperties() {
        return new FinalConfigProperties();
    }

    @Bean
    public FinalSecurityFilter finalSecurityFilter() {
        return new FinalSecurityFilter();
    }

    @Bean
    public FinalHolder finalHolder() {
        return new FinalHolder();
    }

    @Bean
    public FinalManager manager() {
        return new FinalManager();
    }

}
