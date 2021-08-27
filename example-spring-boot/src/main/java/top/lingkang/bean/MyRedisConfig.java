package top.lingkang.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class MyRedisConfig {
/*    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public FinalRedisSessionManager defaultRedisSessionManager(){
        return new FinalRedisSessionManager(redisTemplate);
    }*/
}
