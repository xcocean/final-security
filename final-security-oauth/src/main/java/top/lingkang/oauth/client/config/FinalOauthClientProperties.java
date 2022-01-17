package top.lingkang.oauth.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
@Component
@ConfigurationProperties(prefix = "final.security.oauth.client")
public class FinalOauthClientProperties {
    private String serverUrl="http:localhost:8080";
    private String clientId="client_id";
    private String clientSecret="secret";

}
