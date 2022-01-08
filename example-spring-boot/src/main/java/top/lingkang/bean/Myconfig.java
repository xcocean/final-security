package top.lingkang.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lingkang.base.FinalHttpSecurity;
import top.lingkang.config.FinalSecurityConfiguration;
import top.lingkang.config.FinalSecurityProperties;

import java.util.Arrays;

/**
 * @author lingkang
 * @date 2022/1/8
 */
@Configuration
public class Myconfig {

    @Bean
    public FinalSecurityConfiguration configuration(){
        FinalSecurityConfiguration configuration=new FinalSecurityConfiguration();
        FinalSecurityProperties properties = new FinalSecurityProperties();
        properties.setExcludePath(new String[]{"/login"});
        configuration.setProperties(properties);
        return configuration;
    }
}