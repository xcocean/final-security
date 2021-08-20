package top.lingkang.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lingkang.security.CheckAuth;
import top.lingkang.security.FinalHttpSecurity;
import top.lingkang.security.impl.DefaultCheckAuth;

import java.util.HashMap;

/**
 * @author lingkang
 * @date 2021/8/20 17:41
 * @description
 */
@Configuration
public class MyFinalHttpSecurity {
    @Bean
    public FinalHttpSecurity checkAuthBean() {
        FinalHttpSecurity finalHttpSecurity = new FinalHttpSecurity();
        HashMap<String, CheckAuth> map = new HashMap<String, CheckAuth>();
        map.put("/test", new DefaultCheckAuth().checkLogin().hasRoles("user"));
        finalHttpSecurity.setCheckAuthHashMap(map);
        return finalHttpSecurity;
    }
}
