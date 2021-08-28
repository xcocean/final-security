package top.lingkang.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lingkang.security.CheckAuth;
import top.lingkang.security.FinalHttpSecurity;
import top.lingkang.security.impl.DefaultCheckAuth;
import top.lingkang.session.impl.FinalRedisSessionManager;

import java.util.HashMap;

/**
 * @author lingkang
 * @date 2021/8/28 1:00
 * @description
 */
@Configuration
public class FinalSecurityConfig {
    @Bean
    public FinalHttpSecurity finalHttpSecurity() {
        FinalHttpSecurity finalHttpSecurity = new FinalHttpSecurity();
        HashMap<String, CheckAuth> map = new HashMap<String, CheckAuth>();
        map.put("/*", new DefaultCheckAuth().checkLogin().hasRoles("user"));
        finalHttpSecurity.setCheckAuthHashMap(map);
        finalHttpSecurity.setExcludePath("/login");
        return finalHttpSecurity;
    }

    /*@Bean
    public FinalRedisSessionManager finalRedisSessionManager() {
        return new FinalRedisSessionManager();
    }*/
}
