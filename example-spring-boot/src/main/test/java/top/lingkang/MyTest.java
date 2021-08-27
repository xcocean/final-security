package top.lingkang;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class MyTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        redisTemplate.opsForValue().set("lk","{\"finalSession\":{\"creationTime\":1630053723618,\"id\":\"lk\",\"token\":\"568c03a1-a97d-42d7-9d34-e303cbe1d80c\"}}");
    }
}
