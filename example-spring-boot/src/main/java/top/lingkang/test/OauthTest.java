package top.lingkang.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author lingkang
 * Created by 2022/1/25
 */
@SpringBootTest
public class OauthTest {
    @Test
    public void login(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        requestFactory.setConnectTimeout(1000);
//        requestFactory.setReadTimeout(1000);

        RestTemplate restTemplate = new RestTemplate(requestFactory);


        DemoUser user=new DemoUser();
        user.setUser("asdasaaa");
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<String, Object>();
        postParameters.add("owner", "11");
        postParameters.add("role", new String[]{"a","ddd"});
        postParameters.add("user", user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", "e348bc22-5efa-4299-9142-529f07a18ac9");

        HttpEntity<MultiValueMap<String, Object>> requestEntity  = new HttpEntity<MultiValueMap<String, Object>>(postParameters, headers);

        ResponseEntity<Object> objectResponseEntity = restTemplate.postForEntity("http://127.0.0.1:8082/oauth/token", requestEntity, Object.class);

        System.out.println(objectResponseEntity);
    }
}
