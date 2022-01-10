package top.lingkang.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lingkang.base.FinalSessionListener;
import top.lingkang.base.impl.DefaultFinalSessionListener;
import top.lingkang.config.FinalSecurityConfiguration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * @date 2022/1/8
 */
@Configuration
public class Myconfig {

    @Bean
    public FinalSecurityConfiguration configuration() {
        FinalSecurityConfiguration configuration = new FinalSecurityConfiguration();
//        FinalSecurityProperties properties = new FinalSecurityProperties();
//        properties.setExcludePath(new String[]{"/login","/health"});
//        configuration.setProperties(properties);
        configuration.setSessionListener(new DefaultFinalSessionListener());
        return configuration;
    }

}