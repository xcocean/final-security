package top.lingkang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lingkang.base.FinalAuth;
import top.lingkang.base.FinalHttpSecurity;
import top.lingkang.base.impl.DefaultFinalHttpSecurity;
import top.lingkang.base.impl.DefaultFinalSessionListener;

import java.util.HashMap;

/**
 * @author lingkang
 * @date 2022/1/8
 */
@Configuration
public class Myconfig {



    /*@Bean
    public FinalSecurityConfiguration configuration() {
        FinalSecurityConfiguration configuration = new FinalSecurityConfiguration();
        FinalConfigProperties configProperties = new FinalConfigProperties();
        configProperties.setTokenName("lk");
        configProperties.setExcludePath(new String[]{"/login", "/health"});
        configuration.setConfigProperties(configProperties);

        FinalHttpSecurity httpSecurity = new DefaultFinalHttpSecurity();
        HashMap<String, FinalAuth> map = new HashMap<>();
        map.put("/test", new FinalAuth().hasRoles("admin", "system").hasPermission("get"));

        httpSecurity.setCheckAuths(map);
        configuration.setHttpSecurity(httpSecurity);
        configuration.setSessionListener(new DefaultFinalSessionListener());

        *//*configuration.setRememberHandler(new FinalRememberHandler() {
            @Override
            public boolean doLogin(String id, FinalSession rememberSession, HttpServletRequest request, HttpServletResponse response) {
                FinalHolder.login(id);// 登录
                return true;
            }
        });*//*
        return configuration;
    }*/

    /*@Bean
    public FinalConfigProperties configProperties(){
        FinalConfigProperties properties=new FinalConfigProperties();
        properties.setExcludePath(new String[]{"/login","/logout"});
        return properties;
    }*/

    @Bean
    public FinalHttpSecurity httpSecurity(){
        FinalHttpSecurity security=new DefaultFinalHttpSecurity();
        HashMap<String, FinalAuth> map = new HashMap<>();
        map.put("/user", new FinalAuth().hasRoles("user"));// 必须拥有user角色
        map.put("/about", new FinalAuth().hasPermission("get"));// 必须拥有get权限
        map.put("/updatePassword", new FinalAuth().hasRoles("user").hasPermission("update"));// 需要拥有user角色和update权限
        map.put("/index", new FinalAuth().hasRoles("admin", "system").hasPermission("get"));// 至少有一个角色并拥有get权限
        map.put("/vip", new FinalAuth().hasAllRoles("user","vip"));// 需要同时拥有角色
        security.setCheckAuths(map);
        return security;
    }
}