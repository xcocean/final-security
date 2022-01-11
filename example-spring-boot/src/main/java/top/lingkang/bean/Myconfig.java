package top.lingkang.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lingkang.base.FinalAuth;
import top.lingkang.base.FinalHttpSecurity;
import top.lingkang.base.impl.DefaultFinalHttpSecurity;
import top.lingkang.base.impl.DefaultFinalSessionListener;
import top.lingkang.config.FinalProperties;
import top.lingkang.config.FinalSecurityConfiguration;

import java.util.HashMap;


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
        properties.setTokenName("lk");
        properties.setExcludePath(new String[]{"/login","/health"});
        configuration.setProperties(properties);

        FinalHttpSecurity httpSecurity=new DefaultFinalHttpSecurity();
        HashMap<String, FinalAuth> map=new HashMap<>();
        map.put("/test",new FinalAuth().hasRoles("admin","system").hasPermission("get"));

        httpSecurity.setCheckAuths(map);
        configuration.setHttpSecurity(httpSecurity);
        configuration.setSessionListener(new DefaultFinalSessionListener());
        return configuration;
    }
}