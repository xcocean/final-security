package top.lingkang.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lingkang.base.impl.DefaultFinalSessionListener;
import top.lingkang.config.FinalProperties;
import top.lingkang.config.FinalSecurityConfiguration;


/**
 * @author lingkang
 * @date 2022/1/8
 */
@Configuration
public class Myconfig {
    @Bean
    public FinalSecurityConfiguration configuration() {
        FinalSecurityConfiguration configuration = new FinalSecurityConfiguration();
        FinalProperties properties=new FinalProperties();
        properties.setExcludePath(new String[]{"/login","/health"});
        configuration.setProperties(properties);
        configuration.setSessionListener(new DefaultFinalSessionListener());
        return configuration;
    }

}