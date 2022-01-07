//package top.lingkang.bean;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import top.lingkang.security.CheckAuth;
//import top.lingkang.security.FinalHttpSecurity;
//import top.lingkang.session.FinalTokenGenerate;
//import top.lingkang.session.impl.FinalRedisSessionManager;
//
//import java.util.HashMap;
//import java.util.UUID;
//
///**
// * @author lingkang
// * @date 2021/8/28 1:00
// * @description
// */
//@Configuration
//public class FinalSecurityConfig {
//    @Bean
//    public FinalHttpSecurity finalHttpSecurity() {
//        FinalHttpSecurity finalHttpSecurity = new FinalHttpSecurity();
//        HashMap<String, CheckAuth> map = new HashMap<String, CheckAuth>();
//        /*map.put("/*", new DefaultCheckAuth().checkLogin().hasRoles("user"));
//        finalHttpSecurity.setCheckAuthHashMap(map);
//        finalHttpSecurity.setExcludePath("/login");*/
//        finalHttpSecurity.setExcludePath("/*");
//        return finalHttpSecurity;
//    }
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Bean
//    public FinalRedisSessionManager finalRedisSessionManager() {
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        return new FinalRedisSessionManager(redisTemplate);
//    }
//
//    @Bean
//    public FinalTokenGenerate generate(){
//        return new FinalTokenGenerate() {
//            @Override
//            public String generateToken() {
//                return UUID.randomUUID().toString();
//            }
//        };
//    }
//}
