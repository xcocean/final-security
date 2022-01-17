package top.lingkang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import top.lingkang.base.*;
import top.lingkang.base.impl.DefaultFinalExceptionHandler;
import top.lingkang.base.impl.DefaultFinalHttpSecurity;
import top.lingkang.base.impl.DefaultFinalSessionListener;
import top.lingkang.base.impl.DefaultFinalTokenGenerate;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionManager;
import top.lingkang.session.impl.DefaultFinalSessionManager;
import top.lingkang.session.impl.FinalRedisSessionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

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

    /*@Bean
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
    }*/

   /* @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public SessionManager sessionManager() {
        return new FinalRedisSessionManager(redisTemplate);
    }*/

    //@Bean
    public FinalTokenGenerate tokenGenerate() {
        return new customTokenGenerate();
    }

    class customTokenGenerate implements FinalTokenGenerate {
        @Override
        public String generateToken() {
            return UUID.randomUUID().toString();
        }
    }

    @Bean
    public FinalExceptionHandler exceptionHandler() {
        // 实现FinalExceptionHandler接口进行定制处理，，可参照 DefaultFinalExceptionHandler
        return new customExceptionHandler();
    }

    class customExceptionHandler implements FinalExceptionHandler {
        @Override
        public void tokenException(Exception e, HttpServletRequest request, HttpServletResponse response) {
            // 做些什么？
        }

        @Override
        public void permissionException(Exception e, HttpServletRequest request, HttpServletResponse response) {
            // 做些什么？
        }

        @Override
        public void exception(Exception e, HttpServletRequest request, HttpServletResponse response) {
            // 做些什么？
        }
    }

    /*@Bean
    public SessionManager sessionManager(){
        return new DefaultFinalSessionManager();
    }*/

    //@Bean
    public FinalSessionListener finalSessionListener(){
        return new FinalSessionListener() {
            @Override
            public void create(FinalSession session, HttpServletRequest request, HttpServletResponse response) {
                // 创建时做些什么？
            }

            @Override
            public void delete(FinalSession session, HttpServletRequest request, HttpServletResponse response) {
                // 移除时做些什么？
            }
        };
    }
}