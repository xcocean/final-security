package top.lingkang.oauth.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lingkang
 * @date 2022/1/10
 */
@Component(value = "finalOauthServerProperties")
@ConfigurationProperties(prefix = "final.security.oauth.server")
public class FinalOauthServerProperties {
    private Long tokenMaxTime = 1800000L;// 30分钟
    private Long refreshTokenMaxTime=2592000000L;// 30 天


    /*private Boolean sessionPrepareCheck = false;// 会话检查
    private Long sessionPrepareTime = 180000L;// 预留3分钟执行后续操操作*/





}
