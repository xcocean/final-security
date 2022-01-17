package top.lingkang.oauth.server.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import top.lingkang.oauth.error.impl.DefaultOauthExceptionHandler;
import top.lingkang.oauth.server.base.OauthCodeGenerate;
import top.lingkang.oauth.server.base.impl.DefaultOauthCodeGenerate;

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


}
