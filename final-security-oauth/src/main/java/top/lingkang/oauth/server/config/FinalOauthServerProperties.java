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
    private String sessionName = "f-s";
    private Integer sessionMaxTime = 1800000;// 30分钟
    /*private Boolean sessionPrepareCheck = false;// 会话检查
    private Long sessionPrepareTime = 180000L;// 预留3分钟执行后续操操作*/

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public Integer getSessionMaxTime() {
        return sessionMaxTime;
    }

    public void setSessionMaxTime(Integer sessionMaxTime) {
        this.sessionMaxTime = sessionMaxTime;
    }
}
